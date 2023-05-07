package pt.uc.sob.defektor.plugins.ijk.aws.stopec2instance;

import pt.uc.sob.defektor.common.data.target_types.TargetType;

public class EC2Target implements TargetType {
    private final String text;

    EC2Target() {
        this.text = "aws:ec2:instance";
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
