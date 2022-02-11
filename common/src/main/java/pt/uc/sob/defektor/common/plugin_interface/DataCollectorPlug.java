package pt.uc.sob.defektor.common.plugin_interface;

import pt.uc.sob.defektor.common.Parameters;
import pt.uc.sob.defektor.common.com.exception.CampaignException;

import java.util.Map;

public abstract class DataCollectorPlug {

    protected final Parameters parameters;

    public DataCollectorPlug(Parameters parameters) {this.parameters = parameters;}

    public abstract void help();

    public abstract void configure();

    public abstract byte[] getData() throws CampaignException;
}
