package IoT.Project.Modules.E_Assembly;

/**
 * @author Paolo Castagnetti, 267731@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 06/02/2022 - 12:00
 */
public class MQTTConfigurationParameters {
    //Do not touch
    public static String BROKER_ADDRESS = "155.185.228.20";
    public static int BROKER_PORT = 7883;
    public static final String MQTT_USERNAME="271055@studenti.unimore.it";
    public static final String MQTT_PASSWORD="fvvnivmzjltosnks";
    public static final String MQTT_BASIC_TOPIC= String.format("/iot/user/%s", MQTT_USERNAME);
    //Editable
    public static final String MQTT_NUMBER="271055";
    public static final String MOUNTING_TOPIC="mounting";
}
