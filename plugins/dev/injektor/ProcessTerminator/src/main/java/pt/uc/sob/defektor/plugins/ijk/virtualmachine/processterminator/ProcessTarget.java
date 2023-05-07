package pt.uc.sob.defektor.plugins.ijk.virtualmachine.processterminator;

import pt.uc.sob.defektor.common.data.target_types.TargetType;

public class ProcessTarget implements TargetType {
    private final String text;

    ProcessTarget() {
        this.text = "ssh-enabled:linux:process";
    }

    // @Override TODO - rebuild commons
    public String getName() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}

