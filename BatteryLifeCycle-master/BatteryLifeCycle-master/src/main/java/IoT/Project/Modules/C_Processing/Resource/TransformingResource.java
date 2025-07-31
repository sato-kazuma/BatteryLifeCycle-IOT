package IoT.Project.Modules.C_Processing.Resource;

import IoT.Project.Modules.C_Processing.Sensors.TransformingSensor;
import com.google.gson.Gson;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class TransformingResource extends CoapResource {
    private static final String OBJECT_TITLE = "ModCTransform";
    private static final long UPDATE_TIME_MS = 10000;

    private TransformingSensor transformingSensor;
    private Gson gson;

    public TransformingResource(String name){
        super(name);
        init();
    }

    private void init(){
        getAttributes().setTitle(OBJECT_TITLE);
        this.gson = new Gson();
        this.transformingSensor = new TransformingSensor();
    }

    //GET DA GUARDARE
    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.accept();
        exchange.setMaxAge(UPDATE_TIME_MS/1000);
        try{
            String responseBody = this.gson.toJson(this.transformingSensor);
            exchange.respond(CoAP.ResponseCode.CONTENT, responseBody, MediaTypeRegistry.APPLICATION_JSON);
        }catch (Exception e){
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public void handlePUT(CoapExchange exchange){
        try{
            System.out.println("Changing Transforming Resource");
            String receivedPayload = new String(exchange.getRequestPayload());
            TransformingSensor TS= gson.fromJson(receivedPayload, TransformingSensor.class);
            //If the request body is available
            if(TS.getCode() !=null && TS.getDeviceId() != null){
                this.transformingSensor.setCode(TS.getCode());
                this.transformingSensor.setLocation(TS.getLocation());
                this.transformingSensor.setDeviceId(TS.getDeviceId());
                this.transformingSensor.setValue(TS.getValue());
                this.transformingSensor.setF_Timestamp(TS.getF_Timestamp());
                this.transformingSensor.setI_Timestamp(TS.getI_Timestamp());
                exchange.respond(CoAP.ResponseCode.CHANGED);
                System.out.println("Transforming resource changed succesfully, the current timestamp is: "+ String.format("%s",System.currentTimeMillis()));
            }
            else {
                System.out.println("Couldn't change transforming resource...");
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            }
        }catch (Exception e){
            System.out.println("Error in coap exchange in transforming resource handlePut!!");
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }

    }


}
