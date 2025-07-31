package IoT.Project.Modules.B_Transport.Process;

import IoT.Project.DCPM.Models.ExtractionDescriptor;
import IoT.Project.Modules.B_Transport.CoAP_Communication.ValidatingSecondStage;
import IoT.Project.Modules.B_Transport.MQTTConfigurationParameters;
import IoT.Project.Modules.B_Transport.Models.VehicleDesctiptor;
import IoT.Project.Modules.B_Transport.Models.VehicleTelemetryData;
import com.google.gson.Gson;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.IOException;

/**
 * @author Marco Savarese - 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 08/03/2022 11:04
 */
public class VehicleTrackingEmulator {
    static Gson gson = new Gson();
    static ExtractionDescriptor STC;

    public static String[] getExtractionObs() {
        String[] elements = new String[2];

        CoapClient coapClient = new CoapClient(ValidatingSecondStage.COAP_PREVIOUS_ENDPOINT);

        Request request = Request.newGet().setURI(ValidatingSecondStage.COAP_PREVIOUS_ENDPOINT).setObserve();
        request.setConfirmable(true);

        System.out.printf("Request Pretty Print: \n%s%n", Utils.prettyPrint(request));

        CoapObserveRelation relation = coapClient.observe(request, new CoapHandler() {

            public void onLoad(CoapResponse response) {
                String content = response.getResponseText();
                STC = gson.fromJson(content, ExtractionDescriptor.class);
                //pos 0 -> vehicle id & pos 1->ending location
                elements[0] = STC.getExtraction_location();
                elements[1] = STC.getLoad_code();
                System.out.printf("Response Pretty Print: \n%s%n", Utils.prettyPrint(response));
            }

            public void onError() {
                System.err.println("OBSERVING FAILED");
            }
        });

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        relation.proactiveCancel();
        return elements;
    }

    public static void main(String[] args) {
        String[] extractionObs = getExtractionObs();
        IMqttClient mqttClient;
        try {
            String ID = String.format("%s", extractionObs[1]);

            MqttClientPersistence persistence = new MemoryPersistence();

            mqttClient = new MqttClient(
                    String.format(
                            "tcp://%s:%d",
                            MQTTConfigurationParameters.BROKER_ADDRESS,
                            MQTTConfigurationParameters.BROKER_PORT
                    ),
                    ID,
                    persistence
            );

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MQTTConfigurationParameters.MQTT_USERNAME);
            options.setPassword((MQTTConfigurationParameters.MQTT_PASSWORD).toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            //Connection to the broker
            mqttClient.connect(options);
            System.out.println("Connected");

            //Vehicle
            VehicleDesctiptor vehicleDesctiptor = new VehicleDesctiptor();
            vehicleDesctiptor.setID(ID);
            vehicleDesctiptor.setDriverId(MQTTConfigurationParameters.MQTT_NUMBER);
            vehicleDesctiptor.setBrand("Ford");
            vehicleDesctiptor.setModel("E-Transit");

            //TelemetryData
            VehicleTelemetryData vehicleTelemetryData = new VehicleTelemetryData(ID);
            publishVehicleData(mqttClient, vehicleDesctiptor);
            String SC = STC.getExtraction_location();
            while(true){
                vehicleTelemetryData.updateMeasurments(SC);
                publishTelemetryData(mqttClient, vehicleDesctiptor.getID(), vehicleTelemetryData);
                if(vehicleTelemetryData.getBatteryLevel() == 0){
                    System.out.println("Destination reached");
                    break;
                }
                Thread.sleep(1000);
            }

            mqttClient.disconnect();
            mqttClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends message on topic /iot/user/<userid>/vehicle/<vehicleID>/info
     * @param mqttClient
     * @param vehicleDescriptor
     */
    public static void publishVehicleData(IMqttClient mqttClient, VehicleDesctiptor vehicleDescriptor){
        try{
            Gson gsonData = new Gson();

            if(mqttClient.isConnected()){

                String topic = String.format("%s/%s/%s/%s",
                        MQTTConfigurationParameters.MQTT_BASIC_TOPIC,
                        MQTTConfigurationParameters.VEHICLE_TOPIC,
                        vehicleDescriptor.getID(),
                        MQTTConfigurationParameters.VEHICLE_INFO_TOPIC
                );

                String payload = gsonData.toJson(vehicleDescriptor);

                MqttMessage msg = new MqttMessage(payload.getBytes());
                msg.setQos(0);
                msg.setRetained(false);
                mqttClient.publish(topic, msg);

                System.out.println("Device Data Correctly Published!");
            }else{
                System.err.println("Error: Topic or Msg = Null or MQTT Client is not Connected!");
            }
        }catch (Exception e){
            System.err.println("Error Publishing Vehicle Information: " + e.getLocalizedMessage());
        }
    }

    /**
     * Sends message on topic /iot/user/<userid>/vehicle/<vehicleID>/sensor/telemetry
     * @param mqttClient
     * @param vehicleID
     * @param telemetryData
     */
    public static void publishTelemetryData(IMqttClient mqttClient, String vehicleID, VehicleTelemetryData telemetryData){
        try{
            Gson gsonTel = new Gson();

            String topic = String.format("%s/%s/%s/%s/%s",
                    MQTTConfigurationParameters.MQTT_BASIC_TOPIC,
                    MQTTConfigurationParameters.VEHICLE_TOPIC,
                    vehicleID,
                    MQTTConfigurationParameters.SENSOR_TOPIC,
                    MQTTConfigurationParameters.VEHICLE_TELEMETRY_TOPIC
            );

            String payload = gsonTel.toJson(telemetryData);
            if(mqttClient.isConnected() && payload != null && topic != null){
                MqttMessage msg = new MqttMessage(payload.getBytes());
                msg.setQos(0);
                msg.setRetained(false);
                mqttClient.publish(topic, msg);
                System.out.println("Telemetry Data Correctly Published! Topic: " + topic + "\nPayload: " + payload);


            }
        }catch (Exception e){
            System.err.println("Error Publishing Telemetry Data: " + e.getLocalizedMessage());
        }
    }
}

