package IoT.Project.Modules.E_Assembly.Process;

import IoT.Project.Modules.E_Assembly.MQTTConfigurationParameters;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.UUID;

/**
 * @author Marco Savarese, 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 31/03/2022 19:12
 */

public class MountingProcessConsumer {
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
            //Topic: /iot/user/<userid>/<batteryID>/mounting
            String mountingBatteryTopic = String.format("%s/+/%s",
                    MQTTConfigurationParameters.MQTT_BASIC_TOPIC,
                    MQTTConfigurationParameters.MOUNTING_TOPIC
            );

            client.subscribe(mountingBatteryTopic, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                    byte[] payload = mqttMessage.getPayload();
                    System.out.println("Message Received (" + topic + ") Message Received" + new String(payload));
                }
            });


        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
