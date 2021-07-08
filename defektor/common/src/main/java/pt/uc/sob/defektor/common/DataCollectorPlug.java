package pt.uc.sob.defektor.common;

public abstract class DataCollectorPlug {

    public DataCollectorPlug() {}

    public abstract void help();

    public abstract void configure();

    public abstract byte[] getData();
}
