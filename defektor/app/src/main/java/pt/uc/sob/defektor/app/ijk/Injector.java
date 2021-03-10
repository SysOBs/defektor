package pt.uc.sob.defektor.app.ijk;

import java.util.List;

/**
 * Interface defining an Injector.
 * An Injector object defines the object that encapsulates a particular fault/failure injector.
 */
public interface Injector<T> {
	public List<TargetType> getTargetTypes();
	public List<Target> getTargetInstanceByType(TargetType targetType);
//	public List<Target> getSystemTypeTarget(TargetType targetType);
	public void connect(T stuff);
	public Class getTheNameOfTheClass();
}
