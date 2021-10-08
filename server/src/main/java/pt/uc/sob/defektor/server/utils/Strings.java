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
        public static final String CAMPAIGN_DB_PATH = "state" + File.separator + "campaign.db";
    }

    public static class Errors {
        public static final String SLAVE_NOT_FOUND = "Slave not found";
        public static final String PLAN_NOT_FOUND = "Plan not found";
        public static final String NO_SYSTEMS_CONFIGURED = "No valid system configured";
    }

    public static class Run {
        public static final String RUNNING_GOLDEN_RUN = "Running golden run";
        public static final String RUNNING_FAULT_INJECTION_RUN = "Running fault injection run";
        public static final String FINISHED = "Finished run";
        public static final String WAITING_TO_START = "Waiting to start";
    }

    public class DataCollector {
        public static final String GOLDEN_RUN_FILE_SUFFIX = ".GOLDEN_RUN";
        public static final String FAULT_INJECTION_RUN_FILE_SUFFIX = ".FAULT_INJECTION_RUN";
        public static final String PARENT_DIRECTORY = "results";
        public static final String RUN_NUMBER_PREFIX = "RUN_";
    }
}
