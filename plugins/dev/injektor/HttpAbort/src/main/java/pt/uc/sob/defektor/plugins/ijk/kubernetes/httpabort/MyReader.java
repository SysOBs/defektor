package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;

public class MyReader extends BufferedReader {
    Param param;

    public MyReader(Reader in, Param param) {
        super(in);
        this.param = param;
    }

    @Override
    public String readLine() throws IOException {
        String lBuf = super.readLine();
        if(lBuf == null) return null;
        lBuf = lBuf.replace("<namespace>", param.getNamespace());
        lBuf = lBuf.replace("<svc-name>", param.getService());
        lBuf = lBuf.replace("<host>", param.getHost());
        lBuf = lBuf.replace("<percentage>", param.getFaultOccurrence());
        lBuf = lBuf.replace("<httpStatus>", param.getHttpStatus());
        return lBuf;
    }
}