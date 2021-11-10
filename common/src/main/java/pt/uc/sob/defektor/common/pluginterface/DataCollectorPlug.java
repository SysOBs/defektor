package pt.uc.sob.defektor.common.pluginterface;

import pt.uc.sob.defektor.common.com.collectorparams.DataCollectorParams;
import pt.uc.sob.defektor.common.com.exception.CampaignException;

public abstract class DataCollectorPlug {

    protected final DataCollectorParams params;

    public DataCollectorPlug(DataCollectorParams params) {this.params = params;}

    public abstract void help();

    public abstract void configure();

    public abstract byte[] getData() throws CampaignException;
}
