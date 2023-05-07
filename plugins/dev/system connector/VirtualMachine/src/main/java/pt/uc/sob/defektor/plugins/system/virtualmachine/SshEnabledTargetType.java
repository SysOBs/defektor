package pt.uc.sob.defektor.plugins.system.virtualmachine;

import pt.uc.sob.defektor.common.data.target_types.TargetType;

public enum SshEnabledTargetType implements TargetType {
    INSTANCE("ssh-enabled:linux:instance"),
    PROCESS("ssh-enabled:linux:process");

    private final String text;

    SshEnabledTargetType(final String text) {
        this.text = text;
    }

    @Override
    public String getName() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }
}
