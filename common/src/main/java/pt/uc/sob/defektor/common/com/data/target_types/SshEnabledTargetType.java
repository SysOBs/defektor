package pt.uc.sob.defektor.common.com.data.target_types;

public enum SshEnabledTargetType implements TargetType {
    INSTANCE("ssh-enabled:linux:instance"),
    PROCESS("ssh-enabled:linux:process");

    private final String text;

    SshEnabledTargetType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
