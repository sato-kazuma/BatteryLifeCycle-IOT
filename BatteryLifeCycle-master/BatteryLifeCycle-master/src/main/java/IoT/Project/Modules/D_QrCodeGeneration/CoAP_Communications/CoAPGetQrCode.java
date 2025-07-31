package IoT.Project.Modules.D_QrCodeGeneration.CoAP_Communications;

import IoT.Project.DCPM.Models.QrCodeDescriptor;
import com.google.gson.Gson;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;

public class CoAPGetQrCode {
    private static final String COAP_ENDPOINT = "coap://127.0.0.1:5683/QrCode";

    public static QrCodeDescriptor getQrCode(){
        String final_payload;
        QrCodeDescriptor qrCodeDescriptor = new QrCodeDescriptor();
        Gson gson = new Gson();
        //Initialize coapClient
        CoapClient coapClient = new CoapClient(COAP_ENDPOINT);

        //Request Class is a generic CoAP message: in this case we want a GET.
        //"Message ID", "Token" and other header's fields can be set
        Request getRequest = new Request(CoAP.Code.GET);
        System.out.println("Asking for QrCode information...\n");

        //Set Request as Confirmable
        getRequest.setConfirmable(true);

        System.out.printf("Request Pretty Print: \n%s%n", Utils.prettyPrint(getRequest));
        try{
            CoapResponse resp = coapClient.advanced(getRequest);
            byte[] payload = resp.getPayload();
            final_payload = new String(payload);
            qrCodeDescriptor = gson.fromJson(final_payload, QrCodeDescriptor.class);
            System.out.printf("Current Payload is:%s, current timestamp is: %d\n%n",final_payload,System.currentTimeMillis());

        }catch(ConnectorException | IOException e){
            System.out.println("Extraction information are wrong!\n");
            e.printStackTrace();
        }
        System.out.println("Got everything needed on QrCode!"+String.format("Current timestamp is: %d\n",System.currentTimeMillis()));
        return qrCodeDescriptor;
    }
}
