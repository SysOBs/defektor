package pt.uc.sob.defektor.common.data;

import pt.uc.sob.defektor.common.data.target_types.TargetType;

public abstract class Target {
	private TargetType type;
	//TODO consider creating an Identifier type (it would have all the information necessary to connect to said target)
	abstract public String getIdentifier();
}
