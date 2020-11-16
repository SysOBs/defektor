package pt.uc.sob.defektor.app.ijk.injector;

import pt.uc.sob.defektor.app.ijk.InjectorOptions;
import pt.uc.sob.defektor.app.ijk.TargetType;

import java.util.List;

/**
 * Interface defining an Injector.
 * An Injector object defines the object that encapsulates a particular fault/failure injector.
 */
public abstract class Injector {
	private final InjectorOptions options;

	public abstract List<TargetType> getTargetTypes();

	Injector(InjectorOptions options) {
		this.options = options;
	}

	/**
	 * Methods that executes the actual fault/failure injection.
	 */
	protected abstract void execute();
}
