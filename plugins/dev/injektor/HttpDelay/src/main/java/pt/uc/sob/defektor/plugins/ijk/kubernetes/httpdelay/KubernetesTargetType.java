package pt.uc.sob.defektor.common.data.target_types;

public enum KubernetesTargetType implements TargetType {
    POD("kubernetes:pod"),
    NODE("kubernetes:node"),
    SERVICE("kubernetes:service");

    private final String text;

    KubernetesTargetType(final String text) {
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
