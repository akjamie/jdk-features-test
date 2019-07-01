package org.akj.hystrix.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import org.akj.hystrix.service.command.RaceListofTodayCommand;
import org.akj.hystrix.service.command.cache.HorsesInRaceWithCachingCommand;
import org.akj.hystrix.service.entity.Horse;
import org.akj.hystrix.service.entity.RaceCourse;
import org.akj.hystrix.service.exception.RemoteServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import rx.Observable;

class BettingServiceTest {

	private static final String RACE_1 = "course_france";
	private static final String HORSE_1 = "horse_white";
	private static final String HORSE_2 = "horse_black";

	private static final String ODDS_RACE_1_HORSE_1 = "10/1";
	private static final String ODDS_RACE_1_HORSE_2 = "100/1";

	private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("GetterCommand");

	private List<RaceCourse> races = Arrays.asList(new RaceCourse(RACE_1, "France"));;
	private List<Horse> horses = Arrays.asList(new Horse(HORSE_1, "White"), new Horse(HORSE_2, "Black"));;

	private BettingService mockService;

	/**
	 * Set up the shared Unit Test environment
	 */
	@BeforeEach
	public void setUp() {
		mockService = mock(BettingService.class);
		when(mockService.getTodaysRaces()).thenReturn(getRaceCourses());
		when(mockService.getHorsesInRace(RACE_1)).thenReturn(getHorsesAtFrance());
		when(mockService.getOddsForHorse(RACE_1, HORSE_1)).thenReturn(ODDS_RACE_1_HORSE_1);
		when(mockService.getOddsForHorse(RACE_1, HORSE_2)).thenReturn(ODDS_RACE_1_HORSE_2);

//		races = Arrays.asList(new RaceCourse(RACE_1, "France"));
//		horses = Arrays.asList(new Horse(HORSE_1, "White"), new Horse(HORSE_2, "Black"));
	}

	/**
	 * Command GetRaces - Execute (synchronous call).
	 */
	@Test
	public void testSynchronous() {

		RaceListofTodayCommand commandGetRaces = new RaceListofTodayCommand(mockService);
		// Assertions.assertEquals(getRaceCourses(), commandGetRaces.execute());
		Assertions.assertArrayEquals(getRaceCourses().toArray(), commandGetRaces.execute().toArray());

		verify(mockService).getTodaysRaces();
		verifyNoMoreInteractions(mockService);
	}

	@Test
	public void testSynchronousFailSilently() {

		RaceListofTodayCommand commandGetRacesFailure = new RaceListofTodayCommand(mockService);
		// override mock to mimic an error being thrown for this test
		when(mockService.getTodaysRaces()).thenThrow(new RuntimeException("Error!!"));
		Assertions.assertIterableEquals(new ArrayList<RaceCourse>(), commandGetRacesFailure.execute());

		// Verify
		verify(mockService).getTodaysRaces();
		verifyNoMoreInteractions(mockService);
	}

	@Test
	public void testSynchronousFailFast() {
		RaceListofTodayCommand commandGetRacesFailure = new RaceListofTodayCommand(mockService, false);
		// override mock to mimic an error being thrown for this test
		when(mockService.getTodaysRaces()).thenThrow(new RuntimeException("Error!!"));
		try {
			commandGetRacesFailure.execute();
		} catch (HystrixRuntimeException hre) {
			Assertions.assertEquals(RemoteServiceException.class, hre.getFallbackException().getClass());
		}

		verify(mockService).getTodaysRaces();
		verifyNoMoreInteractions(mockService);
	}

	@Test
	public void testAsynchronous() throws Exception {
		RaceListofTodayCommand commandGetRaces = new RaceListofTodayCommand(mockService);
		Future<List<RaceCourse>> future = commandGetRaces.queue();
		Assertions.assertIterableEquals(getRaceCourses(), future.get());

		verify(mockService).getTodaysRaces();
		verifyNoMoreInteractions(mockService);
	}

	@Test
	public void testObservable() throws Exception {
		RaceListofTodayCommand commandGetRaces = new RaceListofTodayCommand(mockService);
		Observable<List<RaceCourse>> observable = commandGetRaces.observe();
		// blocking observable
		Assertions.assertIterableEquals(getRaceCourses(), observable.toBlocking().single());

		observable.map(response -> commandGetRaces.handleResponse(response));

		verify(mockService).getTodaysRaces();
		verifyNoMoreInteractions(mockService);
	}

	@Test
	public void testWithCacheHits() {

		HystrixRequestContext context = HystrixRequestContext.initializeContext();

		try {
			HorsesInRaceWithCachingCommand commandFirst = new HorsesInRaceWithCachingCommand(mockService, RACE_1);
			HorsesInRaceWithCachingCommand commandSecond = new HorsesInRaceWithCachingCommand(mockService,
					RACE_1);

			commandFirst.execute();
			// this is the first time we've executed this command with
			// the value of "2" so it should not be from cache
			Assertions.assertFalse(commandFirst.isResponseFromCache());

			verify(mockService).getHorsesInRace(RACE_1);
			verifyNoMoreInteractions(mockService);

			commandSecond.execute();
			// this is the second time we've executed this command with
			// the same value so it should return from cache
			Assertions.assertTrue(commandSecond.isResponseFromCache());

		} finally {
			context.shutdown();
		}

		// start a new request context
		context = HystrixRequestContext.initializeContext();
		try {
			HorsesInRaceWithCachingCommand commandThree = new HorsesInRaceWithCachingCommand(mockService, RACE_1);
			commandThree.execute();
			// this is a new request context so this
			// should not come from cache
			Assertions.assertFalse(commandThree.isResponseFromCache());

			// Flush the cache
			HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(RACE_1);

		} finally {
			context.shutdown();
		}
	}

	private List<RaceCourse> getRaceCourses() {
		return races;
	}

	private List<Horse> getHorsesAtFrance() {
		return horses;
	}

//	private List<RaceCourse> getRaceCourses(){
//		RaceCourse course1 = new RaceCourse(RACE_1, "France");
//		return Arrays.asList(course1);
//	}
//	
//	private List<Horse> getHorsesAtFrance(){
//		Horse horse1 = new Horse(HORSE_1, "White");
//		Horse horse2 = new Horse(HORSE_2, "Black");
//		return Arrays.asList(horse1, horse2);
//	}
}
