package pt.uc.sob.defektor.server.api.mapper;

import pt.uc.sob.defektor.server.api.data.DataOutputURIData;
import pt.uc.sob.defektor.server.model.DataOutputURI;

public class DataOutputMapper {

    public static DataOutputURIData convertToDAO(DataOutputURI dataOutputURI) {
        DataOutputURIData dataOutputURIData = new DataOutputURIData();
        dataOutputURIData.setGoldenRunURI(dataOutputURI.getGoldenRunURI());
        dataOutputURIData.setFaultInjectionURI(dataOutputURI.getFaultInjectionURI());

        return dataOutputURIData;
    }

    public static DataOutputURI convertToDTO(DataOutputURIData dataOutputURIData) {
        DataOutputURI dataOutputURI = new DataOutputURI();
        dataOutputURI.setGoldenRunURI(dataOutputURIData.getGoldenRunURI());
        dataOutputURI.setFaultInjectionURI(dataOutputURIData.getFaultInjectionURI());
        return dataOutputURI;
    }
}
