package IoT.Project.Modules.B_Transport.CoAP_Communication;

import IoT.Project.Modules.B_Transport.Models.TrackingActuatorDescriptor;
import com.google.gson.Gson;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;

/**
 * @author Marco Savarese - 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 20/03/2022 - 11:58
 */

public class ValidatingSecondStage {

    public static final String COAP_PREVIOUS_ENDPOINT = "coap://127.0.0.1:5683/Extraction";
    private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/Transport";
    private static Gson gson = new Gson();

    public static void TransportData(TrackingActuatorDescriptor TAD){
        CoapClient coapClient = new CoapClient(COAP_ENDPOINT);
        //PUT
        Request request = new Request(CoAP.Code.PUT);
        request.setConfirmable(true);
        String payload = gson.toJson(TAD);
        request.setPayload(payload.getBytes());

        System.out.printf("Request Pretty Print: \n%s%n", Utils.prettyPrint(request));
        try {
            CoapResponse coapResp = coapClient.advanced(request);
            System.out.printf("Response Pretty Print: \n%s%n", Utils.prettyPrint(coapResp));
        } catch (ConnectorException | IOException e) {
            e.printStackTrace();
        }

        //GET
        Request req = new Request(CoAP.Code.GET);
        req.setConfirmable(true);

        System.out.printf("Request Pretty Print: \n%s%n", Utils.prettyPrint(req));
        try{
            CoapResponse resp = coapClient.advanced(req);
            System.out.printf("Response Pretty Print: \n%s%n", Utils.prettyPrint(resp));
        }catch(ConnectorException | IOException e){
            e.printStackTrace();
        }
    }
}
