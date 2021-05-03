package pt.uc.sob.defektor.common.com.sysconfigs;

public class AbstractSysConfig {
    Object[] objects;
    public AbstractSysConfig(Object... objects) {
        this.objects = objects;
    }

    public Object[] getObjects() {
        return objects;
    }
}
