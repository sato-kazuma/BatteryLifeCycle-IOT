package IoT.Project.Modules.C_Processing.Sensors;

import IoT.Project.Modules.A_Extraction.Models.Cities;
import IoT.Project.Modules.D_QrCodeGeneration.QrCodeMethod.LoadingBar.ProgressBar;

import java.util.Random;
import java.util.UUID;

/**
 * @author Francesco Lasalvia, 271719@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 03/03/2022 - 10:09
 */

public class TransformingSensor {
    private final static int VALUE_BOND = 20;

    private int value;
    private long i_Timestamp;
    private long f_Timestamp;
    private final String UNIT = "%";
    private String deviceId;
    private String location;
    private String code;
    private transient Cities city;
    private transient Random random;

    public TransformingSensor() {
        this.i_Timestamp = 0;
        this.deviceId = UUID.randomUUID().toString();
        this.value = 0;
        this.random=new Random();
        this.city=new Cities();
    }


    public void update_transform() throws InterruptedException {
        ProgressBar progressBar=generateTransformingingProgressBar();
        i_Timestamp = System.currentTimeMillis();
        System.out.printf("Starting periodic Update Task with on {%s}, setting initial timestamp at:%d,begin trasformation...\n", deviceId,i_Timestamp);
        System.out.println("Loading Raw Material on Trasnport Line...\n");
        Thread.sleep(3000);
        while (value < 100) {
            value += VALUE_BOND;
            updateTransformingingProgressBar(progressBar,value);
            System.out.printf("Trasforming percentage increased to:  %d, the current timestamp is %d, continue...\n",value,System.currentTimeMillis());
            Thread.sleep(1500);
            if(value==20){
                System.out.printf("System Begin: Crushing Raw Material and Creation of the Compound...,operating state:1, current timestamp: %d, " +
                        "current state of line: succesfully operating!\n%n",System.currentTimeMillis());
                Thread.sleep(1500);
            }
            if(value==40){
                updateTransformingingProgressBar(progressBar,value);
                System.out.printf("System Update: Compound is ready, current timestamp: %d\n%n",System.currentTimeMillis());
                Thread.sleep(1500);
                System.out.printf("System Begin: Creation Anode and Catode...,operating state:1, " +
                        "current timestamp: %d, current state of line: successfully operating!\n%n",System.currentTimeMillis());
                Thread.sleep(1500);
                System.out.printf("System Begin:Creation of Coating...,operating state:1, " +
                        "current timestamp: %d, current state of line: successfully operating!\n%n",System.currentTimeMillis());

            }
            if(value==60){
                updateTransformingingProgressBar(progressBar,value);
                System.out.printf("System Update: Ending Coating Operation, Current Timestamp: %d\n%n",System.currentTimeMillis());
                Thread.sleep(1500);
                System.out.printf("System Begin: Stacking,Drying and Welding Operation...,operating state:1, " +
                        "current timestamp: %d, current state of line: successfully operating!\n%n",System.currentTimeMillis());
            }
            if(value==80){
                updateTransformingingProgressBar(progressBar,value);
                System.out.printf("System Update: Ending Welding Operation, current timestamp: %d\n%n",System.currentTimeMillis());
                Thread.sleep(1500);
                System.out.printf("System Begin: Electrolyte Injection and Sealing Operation...,operating state:1, " +
                        "current timestamp: %d, current state of line: successfully operating!\n%n",System.currentTimeMillis());
            }

        }
        f_Timestamp = System.currentTimeMillis();
        this.value = 100;
        updateTransformingingProgressBar(progressBar,value);
        Thread.sleep(1500);
        progressBar.dispose();
        setLocation(city.getCITY(random.nextInt(5)));
        System.out.printf("Percentage reached: %d !!\n%n",value);
        System.out.printf("""
                "The device number: %s is full Transformed!
                Setting final Timestamp to: %s
                Current location is %s
                %n""",deviceId,f_Timestamp,location);
    }

    @Override
    public String toString() {
        return "TransformingSensor{" +
                "value=" + value +
                ", i_Timestamp=" + i_Timestamp +
                ", f_Timestamp=" + f_Timestamp +
                ", unit='" + UNIT + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", location='" + location + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getI_Timestamp() {
        return i_Timestamp;
    }

    public void setI_Timestamp(long i_Timestamp) {
        this.i_Timestamp = i_Timestamp;
    }

    public long getF_Timestamp() {
        return f_Timestamp;
    }

    public void setF_Timestamp(long f_Timestamp) {
        this.f_Timestamp = f_Timestamp;
    }

    public String getUNIT() {
        return UNIT;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProgressBar generateTransformingingProgressBar(){
        ProgressBar progressBar =new ProgressBar(0,100);
        progressBar.setVisible(true);
        progressBar.setTitle("Transforming Chain");
        return progressBar;
    }
    public static void updateTransformingingProgressBar(ProgressBar progressBar, int value){
        progressBar.paint(progressBar.getGraphics());
        progressBar.jb.setValue(value);
    }


}
