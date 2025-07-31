package IoT.Project.DCPM.Resources;

import IoT.Project.DCPM.Models.QrCodeDescriptor;
import com.google.gson.Gson;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Francesco Lasalvia, 271719@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 31/03/2022 - 18:55
 */
public class QrCodeResource extends CoapResource{
    private static final String OBJECT_TITLE = "QrCode";
    private Gson gson;
    private QrCodeDescriptor QRD;
    private static final long UPDATE_TIME_MS = 10000;

    public QrCodeResource(String name) {
        super(name);
        init();
        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();
        getAttributes().setMaximumSizeEstimate(23876);

        Timer timer = new Timer();
        timer.schedule(new QrCodeResource.UpdateTask(),0,UPDATE_TIME_MS);
    }

    private void init(){
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();
        this.QRD = new QrCodeDescriptor();
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            changed();
        }
    }

    //GET
    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(UPDATE_TIME_MS/1000);
        try{
            String responseBody = this.gson.toJson(this.QRD);
            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }catch (Exception e){
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        try{
            String receivedPayload = new String(exchange.getRequestPayload());
            QrCodeDescriptor qrCodeDescriptor = gson.fromJson(receivedPayload, QrCodeDescriptor.class);
            if(qrCodeDescriptor.getID() != null && qrCodeDescriptor.getQrCode() != null && qrCodeDescriptor.getTimestamp() !=0){
                QRD.setQrCode(qrCodeDescriptor.getQrCode());
                QRD.setID(qrCodeDescriptor.getID());
                QRD.setTimestamp(qrCodeDescriptor.getTimestamp());
                exchange.respond(CoAP.ResponseCode.CHANGED);
                changed();
            }
            else{
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            }
        }catch(Exception e){
        exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
    }
    }
}
