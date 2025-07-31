package IoT.Project.DCPM.Resources;
import IoT.Project.DCPM.Models.TransportDescriptor;
import IoT.Project.Modules.B_Transport.Models.TrackingActuatorDescriptor;
import com.google.gson.Gson;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Marco Savarese, 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 25/03/2022 10:37
 */

public class TransportResource extends CoapResource {
    private static final String OBJECT_TITLE = "Transport";
    private Gson gson;
    private TransportDescriptor TD;
    private static final long UPDATE_TIME_MS = 10000;

    public TransportResource(String name) {
        super(name);
        init();
        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();

        Timer timer = new Timer();
        timer.schedule(new UpdateTask(),0,UPDATE_TIME_MS);
    }

    private void init(){
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();
        this.TD = new TransportDescriptor();
    }

    private class UpdateTask extends TimerTask{
        @Override
        public void run() {
            changed();
        }
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(UPDATE_TIME_MS/1000);
        try{
            String responseBody = this.gson.toJson(this.TD);
            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }catch (Exception e){
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        try{
            String receivedPayload = new String(exchange.getRequestPayload());
            TrackingActuatorDescriptor TAD = this.gson.fromJson(receivedPayload, TrackingActuatorDescriptor.class);

            if(TAD != null && TAD.getBatterylevel() == 0){
                this.TD.setTransportTimestampStart(TAD.getStimestamp());
                this.TD.setTransportTimestampEnd(TAD.getEtimestamp());
                this.TD.setVehicleID(TAD.getDID());
                this.TD.setStartLocation(TAD.getStartLocation());
                this.TD.setEndingLocation(TAD.getEndLocation());
                exchange.respond(CoAP.ResponseCode.CHANGED);
                changed();
            }else{
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            }
        }catch(Exception e){
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
