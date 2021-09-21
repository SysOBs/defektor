package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class MyReader extends BufferedReader {
    Params params;

    public MyReader(Reader in, Params params) {
        super(in);
        this.params = params;
    }

    @Override
    public String readLine() throws IOException {
        String lBuf = super.readLine();
        if(lBuf == null) return null;
        lBuf = lBuf.replace("<namespace>", params.getNamespace());
        lBuf = lBuf.replace("<svc-name>", params.getService());
        lBuf = lBuf.replace("<host>", params.getHost());
        lBuf = lBuf.replace("<percentage>", params.getFaultOccurrence());
        lBuf = lBuf.replace("<httpStatus>", params.getHttpStatus());
        return lBuf;
    }
}