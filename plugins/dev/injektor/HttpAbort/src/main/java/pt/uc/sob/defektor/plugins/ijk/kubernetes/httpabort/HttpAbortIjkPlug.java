package pt.uc.sob.defektor.plugins.ijk.kubernetes.httpabort;

import org.json.JSONArray;
import org.json.JSONObject;
import pt.uc.sob.defektor.common.com.exception.CampaignException;
import pt.uc.sob.defektor.common.pluginterface.InjektorPlug;
import pt.uc.sob.defektor.common.pluginterface.SystemConnectorPlug;
import pt.uc.sob.defektor.common.com.data.Target;
import pt.uc.sob.defektor.common.com.data.TargetType;
import pt.uc.sob.defektor.common.com.ijkparams.IjkParams;
import pt.uc.sob.defektor.plugins.system.kubernetes.KubernetesSystemPlug;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HttpAbortIjkPlug extends InjektorPlug<KubernetesSystemPlug> {

    private File yamlFile = null;
    private Params params;

    public HttpAbortIjkPlug(SystemConnectorPlug system) {
        super(system);
    }

    @Override
    public void help() {
        
    }

    @Override
    public void performInjection(IjkParams ijkParam) throws CampaignException {
        this.params = Utils.JSON.jsonToObject(ijkParam.getJsonIjkParams().toString());
        try {

            JSONObject virtualServiceList = this.system.listCustomResource(
                            Utils.buildCustomResourceDefinitionContext(),
                            this.params.getNamespace()
            );

//            if (virtualServiceList != null && doesVirtualServiceExists(virtualServiceList)) {
//                addVirtualServiceToVSList(virtualServiceList);
//            }
//            else {
                applyDetachedVirtualService();
//            }

            this.system.createOrReplaceCustomResource(
                    Utils.buildCustomResourceDefinitionContext(),
                    new FileInputStream(yamlFile),
                    this.params.getNamespace()
            );
        } catch (IOException | CampaignException e) {
            throw new CampaignException(e.getMessage());
        }
    }

    private boolean doesVirtualServiceExists(JSONObject virtualServiceList) {
        JSONArray itemList = virtualServiceList.getJSONArray("items");
        for(int i = 0; i < itemList.length(); i++) {
            JSONObject spec = itemList.getJSONObject(i).getJSONObject("spec");
            JSONArray http = spec.getJSONArray("http");
            for (int j = 0; j < http.length(); j++) {
                JSONArray route = http.getJSONObject(j).getJSONArray("route");
                for (int k = 0; k < route.length(); k++) {
                    JSONObject destination = route.getJSONObject(k).getJSONObject("destination");
                    String host = destination.getString("host");
                    if(host.equals(params.getHost())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void applyDetachedVirtualService() throws IOException {
        InputStream in = HttpAbortIjkPlug.class
                .getClassLoader()
                .getResourceAsStream(Utils.Strings.MANIFEST_NAME);

        this.yamlFile = Utils.stringBuilderToTempFile(
                Utils.changedYAMLManifest(in, this.params),
                Utils.Strings.PREFIX,
                Utils.Strings.SUFFIX
        );
    }


    private void addVirtualServiceToVSList(JSONObject virtualServiceList) throws CampaignException {
        JSONArray itemList = virtualServiceList.getJSONArray("items");
        System.out.println(itemList);
        for(int i = 0; i < itemList.length(); i++) {
            JSONObject spec = itemList.getJSONObject(i).getJSONObject("spec");
            JSONArray http = spec.getJSONArray("http");
            for (int j = 0; j < http.length(); j++) {
                JSONArray route = http.getJSONObject(j).getJSONArray("route");
                for (int k = 0; k < route.length(); k++) {
                    JSONObject destination = route.getJSONObject(k).getJSONObject("destination");
                    String host = destination.getString("host");
                    if(host.equals(params.getHost())) {
                        JSONObject percentage = new JSONObject();
                        percentage.put("value", Integer.valueOf(params.getFaultOccurrence()));

                        JSONObject abort = new JSONObject();
                        abort.put("httpStatus", Integer.valueOf(params.getHttpStatus()));
                        abort.put("percentage", percentage);

                        JSONObject fault = new JSONObject();
                        fault.put("abort", abort);

                        http.getJSONObject(j).put("fault", fault);

                        final File tempFile;
                        BufferedWriter writer;
                        try {
                            tempFile = File.createTempFile(Utils.Strings.PREFIX, Utils.Strings.SUFFIX);
                            tempFile.deleteOnExit();

                            writer = new BufferedWriter(new FileWriter(tempFile));
                            writer.write(itemList.getJSONObject(i).toString());
                            writer.close();

                            yamlFile = tempFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        System.out.println(itemList);
    }

    @Override
    public void stopInjection() throws CampaignException {
        this.system.deleteCustomResource(
                Utils.buildCustomResourceDefinitionContext(), params.getNamespace(), params.getService() + "-http-abort");
    }

    @Override
    public List<TargetType> getTargetTypes() {
        return new ArrayList<>() {
            {
                add(TargetType.SERVICE);
            }
        };
    }

    @Override
    public List<Target> getTargetInstancesByType(TargetType targetType) {
        return null;
    }

}
