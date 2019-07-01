package org.akj.hystrix.service.command;

import java.util.ArrayList;
import java.util.List;

import org.akj.hystrix.service.BettingService;
import org.akj.hystrix.service.entity.RaceCourse;
import org.akj.hystrix.service.exception.RemoteServiceException;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class RaceListofTodayCommand extends HystrixCommand<List<RaceCourse>> {

	private final BettingService service;
	private final boolean failSilently;

	/**
	 * CommandGetTodaysRaces
	 * 
	 * @param service
	 *            Remote Broker Service
	 * @param failSilently
	 *            If <code>true</code> will return an empty list if a remote service exception is thrown, if
	 *            <code>false</code> will throw a BettingServiceException.
	 */
	public RaceListofTodayCommand(BettingService service, boolean failSilently) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BettingServiceGroup"))
				.andThreadPoolKey(
						HystrixThreadPoolKey.Factory.asKey("BettingServicePool")));

		this.service = service;
		this.failSilently = failSilently;
	}

	public RaceListofTodayCommand(BettingService service) {
		this(service, true);
	}

	@Override
	protected List<RaceCourse> run() {
		return service.getTodaysRaces();
	}

	@Override
	protected List<RaceCourse> getFallback() {
		// can log here, throw exception or return default
		if (failSilently) {
			return new ArrayList<RaceCourse>();
		}
			
		throw new RemoteServiceException("Unexpected error retrieving todays races");
	}

	public List<RaceCourse> handleResponse(List<RaceCourse> races){
		return races;
	}
}
