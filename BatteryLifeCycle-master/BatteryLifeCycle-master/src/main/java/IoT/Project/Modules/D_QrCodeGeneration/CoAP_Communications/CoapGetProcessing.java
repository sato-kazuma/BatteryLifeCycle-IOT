package IoT.Project.Modules.D_QrCodeGeneration.CoAP_Communications;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;
import java.io.IOException;

/**
 * @author Francesco Lasalvia, 271719@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 03/03/2022 - 10:09
 */

public class CoapGetProcessing {
    private static final String COAP_ENDPOINT_GET = "coap://127.0.0.1:5683/Processing";

    public static String getProcessingGson(){
        String final_payload = null;
        //Initialize coapClient
        CoapClient coapClient = new CoapClient(COAP_ENDPOINT_GET);

        //Request Class is a generic CoAP message: in this case we want a GET.
        //"Message ID", "Token" and other header's fields can be set
        System.out.println("Asking information to processing...\n");
        Request req = new Request(CoAP.Code.GET);


        //Set Request as Confirmable
        req.setConfirmable(true);

        System.out.printf("Request Pretty Print: \n%s%n", Utils.prettyPrint(req));
        try{
            CoapResponse resp = coapClient.advanced(req);
            byte[] payload = resp.getPayload();
            final_payload = new String(payload);
            System.out.println(String.format("Current Payload is:%s, " +
                    "current timestamp is: %d\n",final_payload,System.currentTimeMillis()));

        }catch(ConnectorException | IOException e){
            System.out.println("Processing information are wrong!\n");
            e.printStackTrace();
        }
        System.out.println("Got everything needed on Processing's information!\n"+
                String.format("Current timestamp is: %d\n",System.currentTimeMillis()));
        return final_payload;
    }
}
