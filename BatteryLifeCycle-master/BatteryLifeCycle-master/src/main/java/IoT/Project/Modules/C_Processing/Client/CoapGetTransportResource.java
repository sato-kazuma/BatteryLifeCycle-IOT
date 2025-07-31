package IoT.Project.Modules.C_Processing.Client;

import IoT.Project.DCPM.Models.TransportDescriptor;
import com.google.gson.Gson;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;
import java.io.IOException;

/**
 * @author Francesco Lasalvia, 271719@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 03/03/2022 - 10:09
 */

public class CoapGetTransportResource {

    private static final String COAP_ENDPOINT_GET = "coap://127.0.0.1:5683/Transport";

    static TransportDescriptor transportDescriptor;
    static Gson gson = new Gson();

    public static String[] getTransport(){
        String[] elements =new String[2];

        //Initialize coapClient
        CoapClient coapClient = new CoapClient(COAP_ENDPOINT_GET);

        //Request Class is a generic CoAP message: in this case we want a GET.
        //"Message ID", "Token" and other header's fields can be set
        System.out.println("Asking information to Transport resource..\n");
        Request req = new Request(CoAP.Code.GET);

        //Set Request as Confirmable
        req.setConfirmable(true);

        //Synchronously send the GET message (blocking call)
        CoapResponse coapResp = null;

        System.out.printf("Request Pretty Print: \n%s%n", Utils.prettyPrint(req));
        try{
            CoapResponse resp = coapClient.advanced(req);
            byte[] payload = resp.getPayload();
            String final_payload = new String(payload);
            transportDescriptor= gson.fromJson(final_payload, TransportDescriptor.class);
            //pos 0 -> vehicle id & pos 1->ending location
            elements[0]=transportDescriptor.getVehicleID();
            elements[1]=transportDescriptor.getEndingLocation();
            System.out.println("Transport's information acquired succesfully:\n"+String.format("The current timestamp is:%d\n",System.currentTimeMillis()));
            System.out.printf("Response Pretty Print: \n%s%n", Utils.prettyPrint(resp));
            System.out.println("Ending Get on Transport Resource...\n");
        }catch(ConnectorException | IOException e){
            System.out.println("Transport information are wrong!\n");
            e.printStackTrace();
        }
        return elements;
    }

    //Questa funzione va utilizzata per una risorsa coap observable
    public static String[] getTransportObs(){
        String[] elements =new String[2];

        CoapClient coapClient = new CoapClient(COAP_ENDPOINT_GET);
        System.out.println("Asking information to Transport resource..\n");

        Request request = Request.newGet().setURI(COAP_ENDPOINT_GET).setObserve();
        request.setConfirmable(true);

        System.out.printf("Request Pretty Print: \n%s%n", Utils.prettyPrint(request));

        CoapObserveRelation relation = coapClient.observe(request, new CoapHandler() {

            public void onLoad(CoapResponse response) {
                String content = response.getResponseText();
                transportDescriptor= gson.fromJson(content, TransportDescriptor.class);
                //pos 0 -> vehicle id & pos 1->ending location
                elements[0]=transportDescriptor.getVehicleID();
                elements[1]=transportDescriptor.getEndingLocation();
                System.out.println("Transport's information acquired succesfully:\n"+String.format("The current timestamp is:%d\n",System.currentTimeMillis()));
                System.out.printf("Response Pretty Print: \n%s%n", Utils.prettyPrint(response));
                System.out.println("Ending Get on Transport Resource...\n");
            }

            public void onError() {
                System.err.println("OBSERVING FAILED");
            }
        });

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        relation.proactiveCancel();
        return elements;
    }

}
