package pt.uc.sob.defektor.common;

import pt.uc.sob.defektor.common.com.collectorparams.DataCollectorParams;

public abstract class DataCollectorPlug {

    protected DataCollectorParams params;

    public DataCollectorPlug(DataCollectorParams params) {this.params = params;}

    public abstract void help();

    public abstract void configure();

    public abstract byte[] getData();
}
