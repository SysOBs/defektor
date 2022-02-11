package pt.uc.sob.defektor.server.utils;

import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.server.api.data.*;
import pt.uc.sob.defektor.server.orchestrator.campaign.injection.run.RunStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Utils {

    public static String getSystemNameFromClassName(String pluginClassName) {
        return pluginClassName.split(".ijk.")[1].split("\\.")[0];
    }

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    public static <T> String getIjkName(Map<String, T> map) {
        return getFirstKey(map);
    }

    public static <T> T getIjkData(Map<String, T> map) {
        return getFirstValue(map);
    }

    private static <T> String getFirstKey(Map<String, T> map) {
        Map.Entry<String,T> entry = map.entrySet().iterator().next();
        return entry.getKey();
    }

    private static <T> T getFirstValue(Map<String, T> map) {
        Map.Entry<String,T> entry = map.entrySet().iterator().next();
        return entry.getValue();
    }

    public static class Time {
        public static String getCurrentTimestamp() {
            return new Timestamp(System.currentTimeMillis()).getTime() + "000";
        }
    }

    public static class DataCollector {

        public static String getFileName(String id, String currentRun, RunData runData) {
            String fileName = new StringBuilder()
                    .append(Strings.DataCollector.PARENT_DIRECTORY)
                    .append(File.separator)
                    .append(id).append(File.separator)
                    .append(Strings.DataCollector.RUN_NUMBER_PREFIX)
                    .append(currentRun)
                    .toString();

            if (runData.getStatus() == RunStatus.RUNNING_GOLDEN_RUN) {
                fileName += Strings.DataCollector.GOLDEN_RUN_FILE_SUFFIX;
                runData.getDataOutputURI().setGoldenRunURI(new File(fileName).toURI().toString());
            } else if (runData.getStatus() == RunStatus.RUNNING_FAULT_INJECTION_RUN) {
                fileName += Strings.DataCollector.FAULT_INJECTION_RUN_FILE_SUFFIX;
                runData.getDataOutputURI().setFaultInjectionURI(new File(fileName).toURI().toString());
            }
            return fileName;
        }

        public static void writeStringToFile(String fileName, String content) throws CampaignException {
            try {
                File file = new File(fileName);
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream oFile = new FileOutputStream(file, false);
                oFile.write(content.getBytes(StandardCharsets.UTF_8));
                oFile.close();
            } catch (IOException | SecurityException e) {
                throw new CampaignException(e.getMessage());
            }
        }
    }

    public static class WorkloadGen {
        public static String envVarsToString(List<KeyValueData> env) {
            StringBuilder returnedString = new StringBuilder();
            for (KeyValueData keyValue : env) {
                returnedString.append(" -e ").append(keyValue.getKey()).append("=").append(keyValue.getValue());
            }
            return returnedString.toString();
        }
    }

    public static class Logging {

        public static class Campaign {
            public static String startLogMessage(CampaignData campaignData) {
                return campaignLogBuilder(campaignData, "started campaign");

            }

            public static String finishLogMessage(CampaignData campaignData) {
                return campaignLogBuilder(campaignData, "finished campaign");
            }

            private static String campaignLogBuilder(CampaignData campaignData, String message) {
                return new StringBuilder()
                        .append("(")
                        .append(campaignData.getName())
                        .append(") ")
                        .append(campaignData.getId())
                        .append(" - ")
                        .append(message)
                        .toString();
            }
        }

        public static class Injection {
            public static String startLogMessage(CampaignData campaignData, InjectionData injectionData) {
                return injectionLogBuilder(campaignData, injectionData, "started injection");
            }

            public static String finishLogMessage(CampaignData campaignData, InjectionData injectionData) {
                return injectionLogBuilder(campaignData, injectionData, "finished injection");
            }

            private static String injectionLogBuilder(CampaignData campaignData, InjectionData injectionData, String message) {
                return new StringBuilder()
                        .append("(")
                        .append(campaignData.getName())
                        .append(") ")
                        .append(campaignData.getId())
                        .append("/injection:")
                        .append(injectionData.getInjectionNumber())
                        .append(" - ")
                        .append(message)
                        .toString();
            }
        }

        public static class Run {

            public static String startRun(CampaignData campaignData, InjectionData injectionData, RunData runData) {
                return runLogBuilder(campaignData, injectionData, runData, "started run");
            }

            public static String finishRun(CampaignData campaignData, InjectionData injectionData, RunData runData) {
                return runLogBuilder(campaignData, injectionData, runData, "finished run");
            }

            public static String applyWorkload(CampaignData campaignData, InjectionData injectionData, RunData runData, Integer workloadDuration) {
                return runLogBuilder(campaignData, injectionData, runData, "applying workload for " + workloadDuration + "s");
            }

            public static String stopWorkload(CampaignData campaignData, InjectionData injectionData, RunData runData) {
                return runLogBuilder(campaignData, injectionData, runData, "stopped workload");
            }

            public static String performInjektion(CampaignData campaignData, InjectionData injectionData, RunData runData, String ijkName) {
                return runLogBuilder(campaignData, injectionData, runData, "performing injektion (" + ijkName + ")");
            }

            public static String stopInjektion(CampaignData campaignData, InjectionData injectionData, RunData runData) {
                return runLogBuilder(campaignData, injectionData, runData, "removed injektion");
            }

            public static String collectData(CampaignData campaignData, InjectionData injectionData, RunData runData) {
                return runLogBuilder(campaignData, injectionData, runData, "collecting data");
            }

            public static String abnormallyInterrupted(CampaignData campaignData, InjectionData injectionData, RunData runData, String message) {
                return runLogBuilder(campaignData, injectionData, runData, message);
            }

            private static String runLogBuilder(CampaignData campaignData, InjectionData injectionData, RunData runData, String message) {
                return new StringBuilder()
                        .append("(")
                        .append(campaignData.getName())
                        .append(") ")
                        .append(campaignData.getId())
                        .append("/injection:")
                        .append(injectionData.getInjectionNumber())
                        .append("/run:")
                        .append(runData.getRunNumber())
                        .append(" - ")
                        .append(message)
                        .toString();
            }
        }
    }
}
