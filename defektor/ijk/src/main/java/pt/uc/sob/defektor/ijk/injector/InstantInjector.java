package pt.uc.sob.defektor.ijk.injector;

import pt.uc.sob.defektor.ijk.InjectorOptions;

public abstract class InstantInjector extends Injector {
	public InstantInjector(InjectorOptions options) {
		super(options);
	}

	@Override
	public abstract void execute();
}
