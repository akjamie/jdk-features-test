package org.akj.hystrix.service.command.cache;

import java.util.ArrayList;
import java.util.List;

import org.akj.hystrix.service.BettingService;
import org.akj.hystrix.service.entity.Horse;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class HorsesInRaceWithCachingCommand extends HystrixCommand<List<Horse>> {

	private final BettingService service;
	private final String raceCourseId;

	/**
	 * CommandGetHorsesInRaceWithCaching.
	 * @param service
	 *            Remote Broker Service
	 * @param raceCourseId
	 *            Id of race course
	 */
	public HorsesInRaceWithCachingCommand(BettingService service, String raceCourseId) {
		super(Setter.withGroupKey(
				HystrixCommandGroupKey.Factory.asKey("BettingServiceGroup"))
				.andThreadPoolKey(
						HystrixThreadPoolKey.Factory.asKey("BettingServicePool")));
		
		this.service = service;
		this.raceCourseId = raceCourseId;
	}
	
	@Override
	protected List<Horse> run() {
		return service.getHorsesInRace(raceCourseId);
	}

	@Override
	protected List<Horse> getFallback() {
		return new ArrayList<Horse>();
	}

	@Override
	protected String getCacheKey() {
		return raceCourseId;
	}
	
}
