package IoT.Project.Modules.B_Transport.Process;

import IoT.Project.Modules.B_Transport.MQTTConfigurationParameters;
import IoT.Project.Modules.B_Transport.Models.TrackingActuatorDescriptor;
import IoT.Project.Modules.B_Transport.Models.VehicleTelemetryData;
import com.google.gson.Gson;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

import static IoT.Project.Modules.B_Transport.CoAP_Communication.ValidatingSecondStage.TransportData;

/**
 * @author Marco Savarese - 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 26/02/2022 14:46
 */

public class VehicleDataConsumer {
    private static TrackingActuatorDescriptor TAD = new TrackingActuatorDescriptor();
    static Gson gson = new Gson();

    static void StartCoAPCommunication() throws InterruptedException {
        System.out.println("Sending Location Data to Center");
        while (true){
            if(TAD.getBatterylevel() <= 0){
                TransportData(TAD);
                break;
            }
            TAD.BatteryDrainingSimulator();
            String Traveling = gson.toJson(TAD);
            System.out.println("Traveling data: " + Traveling);
            Thread.sleep(1000);
        }

    }
    public static void main(String[] args) {
        try{

            String clientID = UUID.randomUUID().toString();

            MqttClientPersistence persistence = new MemoryPersistence();

            IMqttClient client = new MqttClient(
                    String.format("tcp://%s:%d",
                            MQTTConfigurationParameters.BROKER_ADDRESS,
                            MQTTConfigurationParameters.BROKER_PORT),
                    clientID,
                    persistence
                    );

            //Options
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MQTTConfigurationParameters.MQTT_USERNAME);
            options.setPassword((MQTTConfigurationParameters.MQTT_PASSWORD).toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            //Connection
            client.connect(options);
            System.out.println("Connected!");
            //Subscription to the topic that gives the vehicle's information
            //Topic: /iot/user/<userid>/vehicle/+/info
            String vehicleInfoTopic = String.format("%s/%s/+/%s",
                    MQTTConfigurationParameters.MQTT_BASIC_TOPIC,
                    MQTTConfigurationParameters.VEHICLE_TOPIC,
                    MQTTConfigurationParameters.VEHICLE_INFO_TOPIC
                    );
            client.subscribe(vehicleInfoTopic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    byte[] payload = mqttMessage.getPayload();
                    System.out.println("Message Received (" + topic + ") Message Received" + new String(payload));
                }
            });

            //Subscription to the topic that gives telemetry data
            //Topic: /iot/user/<userid>/sensor/telemetry
            String vehicleTelemetryTopic = String.format("%s/%s/+/%s/%s",
                    MQTTConfigurationParameters.MQTT_BASIC_TOPIC,
                    MQTTConfigurationParameters.VEHICLE_TOPIC,
                    MQTTConfigurationParameters.SENSOR_TOPIC,
                    MQTTConfigurationParameters.VEHICLE_TELEMETRY_TOPIC
            );
            client.subscribe(vehicleTelemetryTopic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    byte[] payload = mqttMessage.getPayload();
                    String msg = new String(payload);
                    System.out.println("Message Received (" + topic + ") Message Received" + new String(payload));
                    VehicleTelemetryData VTD = gson.fromJson(msg, VehicleTelemetryData.class);
                    if(VTD.getBatteryLevel() == 0) {
                        System.out.println("Vehicle got to the destination: "+ VTD.getEndingLocation());
                        TAD.setDID(VTD.getID());
                        TAD.setStartLocation(VTD.getStartingLocation());
                        TAD.setEndLocation(VTD.getEndingLocation());
                        StartCoAPCommunication();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
