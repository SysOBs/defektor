package pt.uc.sob.defektor.plugins.ijk.virtualmachine.instancereboot;

import pt.uc.sob.defektor.common.data.target_types.TargetType;

public class MachineInstanceTarget implements TargetType {
    private final String text;

    MachineInstanceTarget() {
        this.text = "ssh-enabled:linux:instance";
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
