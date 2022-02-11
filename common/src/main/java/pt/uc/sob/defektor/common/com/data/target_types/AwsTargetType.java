package pt.uc.sob.defektor.common.com.data.target_types;

public enum AwsTargetType implements TargetType{
    EC2_INSTANCE("aws:ec2:instance"),
    EC2_SPOT_INSTANCE("aws:ec2:spot-instance"),
    ECS_CLUSTER("aws:ecs:cluster"),
    EKS_NODE_GROUP("aws:eks:nodegroup"),
    IAM_ROLE("aws:iam:role"),
    RDS_CLUSTER("aws:rds:cluster"),
    RDS_DB("aws:rds:db");

    private final String text;

    AwsTargetType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
