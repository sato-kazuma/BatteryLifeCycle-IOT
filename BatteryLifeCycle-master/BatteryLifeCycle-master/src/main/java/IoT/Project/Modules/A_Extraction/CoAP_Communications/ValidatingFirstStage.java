package IoT.Project.Modules.A_Extraction.CoAP_Communications;

import IoT.Project.Modules.A_Extraction.Models.UploadingActuatorDescriptor;
import com.google.gson.Gson;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;
/**
 * @author Paolo Castagnetti, 267731@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 03/03/2022 - 10:09
 */
public class ValidatingFirstStage {
    private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/Extraction";
    private static Gson gson = new Gson();

    public static void sendResource(UploadingActuatorDescriptor UAD){

        CoapClient coapClient = new CoapClient(COAP_ENDPOINT);
        UAD.simulateOriginOfMaterial();

        //PUT
        Request putRequest = new Request(CoAP.Code.PUT);
        putRequest.setConfirmable(true);
        String payload = gson.toJson(UAD);
        putRequest.setPayload(payload.getBytes());

        System.out.println("PUT of the resource on the DCPM.\n");

        System.out.printf("Request Pretty Print: \n%s%n\n", Utils.prettyPrint(putRequest));
        try {
            CoapResponse coapResp = coapClient.advanced(putRequest);
            System.out.println("Response DCPM: \n");
            System.out.printf("Pretty Print: \n%s%n\n", Utils.prettyPrint(coapResp));
        } catch (ConnectorException | IOException e) {
            e.printStackTrace();
        }

        //GET
        Request getRequest = new Request(CoAP.Code.GET);
        getRequest.setConfirmable(true);
        System.out.println("GET on the resource.\n");
        System.out.printf("Request Pretty Print: \n%s%n\n", Utils.prettyPrint(getRequest));
        try{
            CoapResponse resp = coapClient.advanced(getRequest);
            System.out.println("Response DCPM: \n");
            System.out.printf("Response Pretty Print: \n%s%n\n", Utils.prettyPrint(resp));
        }catch(ConnectorException | IOException e){
            e.printStackTrace();
        }
    }
}
