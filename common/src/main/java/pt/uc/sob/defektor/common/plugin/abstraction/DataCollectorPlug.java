package pt.uc.sob.defektor.common.plugin.abstraction;

import pt.uc.sob.defektor.common.config.DataCollectorParams;
import pt.uc.sob.defektor.common.exception.CampaignException;

public abstract class DataCollectorPlug {

    protected final DataCollectorParams parameters;

    public DataCollectorPlug(DataCollectorParams parameters) {this.parameters = parameters;}

    public abstract void help();

    public abstract void configure();

    public abstract byte[] getData() throws CampaignException;
}
