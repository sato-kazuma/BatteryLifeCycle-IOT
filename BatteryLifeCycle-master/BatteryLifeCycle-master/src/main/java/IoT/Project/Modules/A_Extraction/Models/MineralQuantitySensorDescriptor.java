package IoT.Project.Modules.A_Extraction.Models;

import java.util.Random;
/**
 * @author Paolo Castagnetti, 267731@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 06/02/2022 - 12:00
 */
public class MineralQuantitySensorDescriptor {

    //Attributes
    private int value;
    private final String UNIT = "%";
    private String usid = null;
    private long timestamp;

    private static int QUANTITY_START_VALUE = 0;
    private static final int QUANTITY_VALUE_BOUND = 10;

    //Utils
    private transient Random random;

    //Getter & Setter
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getUNIT() {
        return UNIT;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getUsid() {
        return usid;
    }
    public void setUsid(String usid) {
        this.usid = usid;
    }

    //Constructor
    public MineralQuantitySensorDescriptor() {
        this.random = new Random();
    }

    //Methods

    //This method simulates the quantity of the extracted materials
    public void measureQuantityValue(){
        QUANTITY_START_VALUE += this.random.nextInt(1, QUANTITY_VALUE_BOUND);
        this.value = QUANTITY_START_VALUE;
        if (this.value>100){
            int tmp = this.value - 100;
            this.value-=tmp;
        }
        this.timestamp = System.currentTimeMillis();
    }
}
