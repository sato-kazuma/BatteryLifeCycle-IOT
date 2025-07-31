package IoT.Project.Modules.A_Extraction.Process;

import IoT.Project.Modules.A_Extraction.MQTTConfigurationParameters;
import IoT.Project.Modules.A_Extraction.Models.MineralQuantitySensorDescriptor;
import IoT.Project.Modules.A_Extraction.Models.UploadingActuatorDescriptor;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.UUID;
import static IoT.Project.Modules.A_Extraction.CoAP_Communications.ValidatingFirstStage.sendResource;

/**
 * @author Paolo Castagnetti, 267731@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 23/02/2022 - 12:13
 */
public class UploadingActuatorConsumer {
    static UploadingActuatorDescriptor UAD = new UploadingActuatorDescriptor();
    static Gson gson = new Gson();


    static void StartLoading() throws InterruptedException {
        System.out.println("Starting to load minerals into the truck.");
        Thread.sleep(3000);
        while(true){
            UAD.measureLoadingMaterial();
            String loading = gson.toJson(UAD);
            System.out.println("Loading: "+ loading);
            if(UAD.getValue()==100){
                UAD.setRtg(true);
                System.out.println("Ended loading on truck.\n");
                System.out.println("Sending Materials!");
                sendResource(UAD);
                break;
            }
            Thread.sleep(2000);
        }
    }

    public static void main(String [ ] args) {

        System.out.println("MQTT Auth Consumer Tester Started ...");

        try{

            String clientId = UUID.randomUUID().toString();

            MqttClientPersistence persistence = new MemoryPersistence();

            IMqttClient client = new MqttClient(
                    String.format("tcp://%s:%d", MQTTConfigurationParameters.BROKER_ADDRESS, MQTTConfigurationParameters.BROKER_PORT),
                    clientId,
                    persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MQTTConfigurationParameters.MQTT_USERNAME);
            options.setPassword((MQTTConfigurationParameters.MQTT_PASSWORD).toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            client.connect(options);
            System.out.println("Connected !");

            //Subscribe to Telemetry Data
            //Topic Structure: /iot/user/<user_id>/vehicle/<vehicle_id>/telemetry
            String sensorTelemetryTopic = String.format("%s/%s/+/%s/%s",
                    MQTTConfigurationParameters.MQTT_BASIC_TOPIC,
                    MQTTConfigurationParameters.SENSOR_TOPIC,
                    MQTTConfigurationParameters.QUANTITY_VALUE_TOPIC,
                    MQTTConfigurationParameters.EXTRACTED_TOPIC);

            client.subscribe(sensorTelemetryTopic, (topic, message) -> {
                byte[] payload = message.getPayload();
                String msg = new String(payload);
                System.out.println("Message Received on topic ("+topic+")\nContent: " + msg);
                MineralQuantitySensorDescriptor MQS = gson.fromJson(msg, MineralQuantitySensorDescriptor.class);
                if(MQS.getValue()==100){
                    System.out.println("Extraction has reach its maximum volume.\n");
                    UAD.setRtl(true);
                    UAD.setE_timestamp(MQS.getTimestamp());
                    StartLoading();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
