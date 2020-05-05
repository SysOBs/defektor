package pt.uc.sob.defektor.app.ijk.injector;

import pt.uc.sob.defektor.app.ijk.InjectorOptions;

import java.time.Duration;

public abstract class ContinuousInjector extends Injector {
	public ContinuousInjector(InjectorOptions options) {
		super(options);
	}

	/**
	 * Starts the fault/failure injection.
	 */
	//TODO Make this nonblocking, start a separate thread or a thread pool to deal with this
	abstract void start();

	/**
	 * Starts the fault/failure injection with a defined duration.
	 * @param duration the duration of the injection
	 * @throws InterruptedException if the method is interrupted while waiting
	 */
	void start(Duration duration) throws InterruptedException {
		start();
		//TODO Make this nonblocking, start a separate thread or a thread pool to deal with this
		wait(duration.toMillis());
		stop();
	}

	/**
	 * Stops the fault/failure injection.
	 */
	abstract void stop();
}
