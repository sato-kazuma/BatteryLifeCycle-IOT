package IoT.Project.Modules.E_Assembly.Process;

import IoT.Project.DCPM.Models.QrCodeDescriptor;
import IoT.Project.Modules.E_Assembly.MQTTConfigurationParameters;
import IoT.Project.Modules.E_Assembly.Models.Cars;
import IoT.Project.Modules.E_Assembly.Models.MountingBatteryonVehicleSensor;
import com.google.gson.Gson;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.Random;

/**
 * @author Marco Savarese, 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 31/03/2022 19:10
 */

public class MountingProcessEmulator {
    private final static String COAP_PREVIOUS_ENDPOINT = "coap://127.0.0.1:5683/QrCode";
    private static QrCodeDescriptor qrCodeDescriptor = new QrCodeDescriptor();
    private static Random random = new Random(System.currentTimeMillis());
    private static Cars cars = new Cars();


    public static void getQrObs() {
        Gson gson = new Gson();
        CoapClient coapClient = new CoapClient(COAP_PREVIOUS_ENDPOINT);

        Request request = Request.newGet().setURI(COAP_PREVIOUS_ENDPOINT).setObserve();
        request.setConfirmable(true);

        System.out.printf("Request Pretty Print: \n%s%n", Utils.prettyPrint(request));

        CoapObserveRelation relation = coapClient.observe(request, new CoapHandler() {

            public void onLoad(CoapResponse response) {
                String content = response.getResponseText();
                qrCodeDescriptor = gson.fromJson(content, QrCodeDescriptor.class);
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

    }

    public static void main(String[] args) {
        getQrObs();
        String currentCar = cars.CarGetter(random.nextInt(0, cars.CarsListLength()));
        String SensorID = String.format("sensor-%s", MQTTConfigurationParameters.MQTT_NUMBER);
        IMqttClient mqttClient;

        try {
            MqttClientPersistence persistence = new MemoryPersistence();

            mqttClient = new MqttClient(
                    String.format(
                            "tcp://%s:%d",
                            MQTTConfigurationParameters.BROKER_ADDRESS,
                            MQTTConfigurationParameters.BROKER_PORT
                    ),
                    SensorID,
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

            //Sensor
            MountingBatteryonVehicleSensor mountingBatteryonVehicleSensor = new MountingBatteryonVehicleSensor();
            mountingBatteryonVehicleSensor.setSensorID(SensorID);
            mountingBatteryonVehicleSensor.setFile(qrCodeDescriptor.getQrCode());
            mountingBatteryonVehicleSensor.setCar(currentCar);

            //Mounting Process
            while(true){
                if(mountingBatteryonVehicleSensor.getValue() == 100){
                    break;
                }else{
                    mountingBatteryonVehicleSensor.increasingProgressValue();
                }
                Mounting(mqttClient, mountingBatteryonVehicleSensor);
                Thread.sleep(1500);
            }


            mqttClient.disconnect();
            mqttClient.close();

            System.out.println("Disconnected !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Mounting(IMqttClient mqttClient,MountingBatteryonVehicleSensor mountingBatteryonVehicleSensor){
        try {
            Gson gsonData = new Gson();

            if(mqttClient.isConnected()){

                String topic = String.format("%s/%s/%s",
                        MQTTConfigurationParameters.MQTT_BASIC_TOPIC,
                        qrCodeDescriptor.getID(),
                        MQTTConfigurationParameters.MOUNTING_TOPIC
                );

                String payload = gsonData.toJson(mountingBatteryonVehicleSensor);

                if(mqttClient.isConnected() && payload != null && topic!= null){
                    MqttMessage msg = new MqttMessage(payload.getBytes());
                    msg.setQos(0);
                    msg.setRetained(false);
                    mqttClient.publish(topic, msg);
                    System.out.println("Device Data Correctly Published! Topic" + topic + "Payload: " + payload);
                }
            }else{
                System.err.println("Error: Topic or Msg = Null or MQTT Client is not Connected!");
            }
        }catch (Exception e){
            System.err.println("Error Publishing Mounting Information: " + e.getLocalizedMessage());
        }
    }

}
