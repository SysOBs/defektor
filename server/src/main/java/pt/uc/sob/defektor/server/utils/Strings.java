package pt.uc.sob.defektor.server.utils;

import java.io.File;

public class Strings {

    public static class Systems {
        public static final String VIRTUAL_MACHINE = "virtualmachine";
        public static final String KUBERNETES = "kubernetes";
    }

    public static class DB {
        public static final String PLAN_DB_PATH = "state" + File.separator + "plan.db";
        public static final String SLAVE_DB_PATH = "state" + File.separator + "slave.db";
        public static final String SYS_CONFIG_DB_PATH = "state" + File.separator + "sysconfig.db";
    }

    public static class Errors {
        public static final String SLAVE_NOT_FOUND = "Slave not found";
        public static final String PLAN_NOT_FOUND = "Plan not found";
        public static final String NO_SYSTEMS_CONFIGURED = "No systems configured";
    }

    public class Campaign {
        public static final String RUNNING_GOLDEN_RUN = "Running golden run";
        public static final String RUNNING_FAULT_INJECTION_RUN = "Running fault injection run";
    }
}
