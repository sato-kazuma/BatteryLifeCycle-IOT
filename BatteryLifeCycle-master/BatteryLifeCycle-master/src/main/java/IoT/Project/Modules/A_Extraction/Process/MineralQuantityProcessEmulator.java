package IoT.Project.Modules.A_Extraction.Process;

import com.google.gson.Gson;
import IoT.Project.Modules.A_Extraction.MQTTConfigurationParameters;
import IoT.Project.Modules.A_Extraction.Models.MineralQuantitySensorDescriptor;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
/**
 * @author Paolo Castagnetti, 267731@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 23/02/2022 - 10:33
 */
public class MineralQuantityProcessEmulator {
    public static void main(String[] args) {

        System.out.println("MineralQuantityEmulator started ...");

        try{

            String sensorId = String.format("sensor-%s", MQTTConfigurationParameters.MQTT_NUMBER);

            MqttClientPersistence persistence = new MemoryPersistence();

            IMqttClient mqttClient = new MqttClient(
                    String.format("tcp://%s:%d", MQTTConfigurationParameters.BROKER_ADDRESS, MQTTConfigurationParameters.BROKER_PORT),
                    sensorId,
                    persistence);

            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(MQTTConfigurationParameters.MQTT_USERNAME);
            options.setPassword((MQTTConfigurationParameters.MQTT_PASSWORD).toCharArray());
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            //Connect to the target broker
            mqttClient.connect(options);

            System.out.println("Connected !");

            //Create Sensor Reference
            MineralQuantitySensorDescriptor MQSDescriptor = new MineralQuantitySensorDescriptor();
            MQSDescriptor.setUsid(sensorId);

            //Start to publish telemetry messages
            System.out.println("Publishing to:");
            while(true){
                MQSDescriptor.measureQuantityValue();
                publishTelemetryData(mqttClient, sensorId, MQSDescriptor);
                if(MQSDescriptor.getValue()==100){
                    System.out.println("The extraction has reached its maximum volume!");
                    break;
                }
                Thread.sleep(2000);
            }

            //Disconnect from the broker and close the connection
            mqttClient.disconnect();
            mqttClient.close();

            System.out.println("Disconnected !");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void publishTelemetryData(IMqttClient mqttClient, String sensorId, MineralQuantitySensorDescriptor MQS_TD) {
        try{
            Gson gson = new Gson();

            //Topic Structure: /iot/user/<user_id>/sensor/<sensor_id>/quantity/extracted
            String topic = String.format("%s/%s/%s/%s/%s",
                    MQTTConfigurationParameters.MQTT_BASIC_TOPIC,
                    MQTTConfigurationParameters.SENSOR_TOPIC,
                    sensorId,
                    MQTTConfigurationParameters.QUANTITY_VALUE_TOPIC,
                    MQTTConfigurationParameters.EXTRACTED_TOPIC);

            String payloadString = gson.toJson(MQS_TD);
            System.out.println("Topic: " + topic + "\nData: " + payloadString);

            if (mqttClient.isConnected() && payloadString != null && topic != null) {
                MqttMessage msg = new MqttMessage(payloadString.getBytes());
                if(MQS_TD.getValue()==100){
                    msg.setQos(2);
                }
                else
                    msg.setQos(0);
                msg.setRetained(false);
                mqttClient.publish(topic,msg);
                System.out.println("QoS defined: "+msg.getQos());
                System.out.println("Data Correctly Published !");
            }
            else{
                System.err.println("Error: Topic/Msg == Null OR MQTT Client is not Connected !");
            }
        }catch (Exception e){
            System.err.println("Error Publishing Information ! Error: " + e.getLocalizedMessage());
        }
    }
}

