package pt.uc.sob.defektor.server.utils;

import org.jetbrains.annotations.NotNull;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.server.api.data.DataOutputURIData;
import pt.uc.sob.defektor.server.api.data.KeyValueData;
import pt.uc.sob.defektor.server.api.data.RunData;
import pt.uc.sob.defektor.server.campaign.run.data.RunStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class Utils {

    public static String getSystemNameFromClassName(String pluginClassName) {
        return pluginClassName.split(".ijk.")[1].split("\\.")[0];
    }

    public static String envVarsToString(List<KeyValueData> env) {
        StringBuilder returnedString = new StringBuilder();
        for(KeyValueData keyValue : env) {
            returnedString.append(" -e ").append(keyValue.getKey()).append("=").append(keyValue.getValue());
        }
        return returnedString.toString();
    }

    public static UUID generateUUID() {
        return UUID.randomUUID();
    }

    public static class Time {
        public static String getCurrentTimestamp() {
            return new Timestamp(System.currentTimeMillis()).getTime() + "000";
        }
    }

    public static class DataCollector {

        public static String getFileName(String id, String currentRun, RunData runData) {
            String fileName = "results" + File.separator + id + File.separator + "RUN_" + currentRun;
            if (runData.getStatus() == RunStatus.RUNNING_GOLDEN_RUN) {
                fileName += ".GOLDEN_RUN";
                runData.getDataOutputURI().setGoldenRunURI(new File(fileName).toURI().toString());

            } else if (runData.getStatus() == RunStatus.RUNNING_FAULT_INJECTION_RUN) {
                fileName += ".FAULT_INJECTION_RUN";
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
}
