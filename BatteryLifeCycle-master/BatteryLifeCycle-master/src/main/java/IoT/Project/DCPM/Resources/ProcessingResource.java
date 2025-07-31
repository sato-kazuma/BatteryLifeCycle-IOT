package IoT.Project.DCPM.Resources;

import IoT.Project.DCPM.Models.ProcessingDescriptor;
import IoT.Project.Modules.C_Processing.Sensors.AssemblingSensor;
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
 * @created 03/03/2022 - 10:09
 */
public class ProcessingResource extends CoapResource {
    private static final String OBJECT_TITLE = "Processing";
    private Gson gson;
    private ProcessingDescriptor PD;
    private static final long UPDATE_TIME_MS = 10000;

    public ProcessingResource(String name) {
        super(name);
        init();
        setObservable(true);
        setObserveType(CoAP.Type.CON);
        getAttributes().setObservable();

        Timer timer = new Timer();
        timer.schedule(new ProcessingResource.UpdateTask(),0,UPDATE_TIME_MS);
    }

    private void init(){
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();
        this.PD = new ProcessingDescriptor();
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            changed();
        }
    }

    //GET DA VEDERE
    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.setMaxAge(UPDATE_TIME_MS/1000);
        try{
            String responseBody = this.gson.toJson(this.PD);
            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }catch (Exception e){
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        try{
            String receivedPayload = new String(exchange.getRequestPayload());
            AssemblingSensor assemblingSensor=gson.fromJson(receivedPayload,AssemblingSensor.class);
            if(assemblingSensor.getF_Timestamp_assembling()!=0 && assemblingSensor.getCode()!=null && assemblingSensor.getLocation()!=null) {
                PD.setCode(assemblingSensor.getCode());
                PD.setLocation(assemblingSensor.getLocation());
                PD.setTimestamp_end_assembling_within_processing(assemblingSensor.getF_Timestamp_assembling());
                PD.setTimestamp_start_assembling_within_processing(assemblingSensor.getI_Timestamp_assembling());
                PD.setTimestamp_start_processing(assemblingSensor.getI_timestamp_transforming());
                PD.setTimestamp_end_processing(assemblingSensor.getF_timestap_transforming());
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
