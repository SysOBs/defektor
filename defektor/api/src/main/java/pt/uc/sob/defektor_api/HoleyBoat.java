package pt.uc.sob.defektor_api;

import org.pf4j.ExtensionPoint;

public interface HoleyBoat extends ExtensionPoint {

    void start(InjektorConfig injectionConfig);
}
