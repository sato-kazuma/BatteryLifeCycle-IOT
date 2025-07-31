package IoT.Project.Modules.E_Assembly.Models;

import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.util.Random;

/**
 * @author Marco Savarese, 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 31/03/2022 19:08
 */

public class MountingBatteryonVehicleSensor {

    //Constants

    private static int QUANTITY_START_VALUE = 0;
    private static final int QUANTITY_VALUE_BOUND = 10;
    private final java.lang.String UNIT = "%";

    //Attributes
    private int value;
    private BitMatrix file;
    private String sensorID;
    private long timestamp;
    private String car;

    private transient Random random = new Random(System.currentTimeMillis());

    //Constructors

    public MountingBatteryonVehicleSensor() {
        this.value = 0;
    }

    //Getters and Setters


    public BitMatrix getFile() {
        return file;
    }
    public void setFile(BitMatrix file) {
        this.file = file;
    }

    public java.lang.String getSensorID() {
        return sensorID;
    }
    public void setSensorID(java.lang.String sensorID) {
        this.sensorID = sensorID;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getCar() {
        return car;
    }
    public void setCar(String car) {
        this.car = car;
    }


    //Methods

    //This method simulates the mounting process progress
    public void increasingProgressValue(){
        QUANTITY_START_VALUE += this.random.nextInt(1, QUANTITY_VALUE_BOUND);
        this.value = QUANTITY_START_VALUE;
        if (this.value>90){
            setValue(100);
        }
        this.timestamp = System.currentTimeMillis();
    }


}
