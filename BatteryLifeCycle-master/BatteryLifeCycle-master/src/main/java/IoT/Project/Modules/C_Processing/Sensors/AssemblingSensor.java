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

public class AssemblingSensor {
    private final static int VALUE_BOND = 20;

    private int value;
    private long i_Timestamp_assembling;
    private long f_Timestamp_assembling;
    private long i_timestamp_transforming;
    private long f_timestap_transforming;
    private String unit = "%";
    private String deviceId;
    private String location;
    private String code;
    private transient Cities city;
    private transient Random random;


    public AssemblingSensor(){
        this.i_Timestamp_assembling =0;
        this.deviceId= UUID.randomUUID().toString();
        this.value=20;
        this.random=new Random();
        this.city=new Cities();
    }


    public void update_assemble() throws InterruptedException {
        ProgressBar progressBar= generateAssemblingProgressBar();
        i_Timestamp_assembling = System.currentTimeMillis();
        System.out.printf("Starting periodic Update Task with on {%s}, setting initial timestamp at:%d,begin trasformation...\n", deviceId,i_Timestamp_assembling);
        System.out.println("Loading component on Conveyor  Line...\n");
        Thread.sleep(3000);
        while (value < 100) {
            value += VALUE_BOND;
            updateAssemblingProgressBar(progressBar,value);
            System.out.printf("Assembling percentage increased to:  %d, the current timestamp is %d, continue...\n",value,System.currentTimeMillis());
            Thread.sleep(1500);
            if(value==20){
                updateAssemblingProgressBar(progressBar,value);
                System.out.printf("System Begin:Casting Operation,operating state:1, current timestamp: %d, " +
                        "current state of line: succesfully operating!\n%n",System.currentTimeMillis());
                Thread.sleep(1500);
                System.out.printf("System Begin: Installing Thermal Management System,operating state:1, current timestamp: %d, " +
                        "current state of line: succesfully operating!\n%n",System.currentTimeMillis());
            }
            if(value==40){
                updateAssemblingProgressBar(progressBar,value);
                System.out.printf("System Begin: Fastening System, current timestamp: %d\n%n",System.currentTimeMillis());
                Thread.sleep(1500);
                System.out.printf("System Update: Fastening Installation Done...,operating state:1, " +
                        "current timestamp: %d, current state of line: succesfully operating!\n%n",System.currentTimeMillis());
            }
            if (value==60){
                updateAssemblingProgressBar(progressBar,value);
                System.out.printf("System Begin: Cable Installation...Current timestamp: %d\n%n",System.currentTimeMillis());
                Thread.sleep(1500);
                System.out.printf("System Begin: Pack Sealing...,operating state:1, " +
                        "current timestamp: %d, current state of line: succesfully operating!\n%n",System.currentTimeMillis());
            }
            if(value==80){
                updateAssemblingProgressBar(progressBar,value);
                System.out.printf("System Update: Inspection Operation, current timestamp: %d\n%n",System.currentTimeMillis());
                Thread.sleep(1500);
                System.out.printf("System Update: Testing Operation...,operating state:1, " +
                        "current timestamp: %d, current state of line: succesfully operating!\n%n",System.currentTimeMillis());
            }

        }
        f_Timestamp_assembling = System.currentTimeMillis();
        this.value = 100;
        updateAssemblingProgressBar(progressBar,value);
        Thread.sleep(1500);
        progressBar.dispose();
        setLocation(city.getCITY(random.nextInt(5)));
        System.out.println("All Test are OK, Assemblation Percentage is:"+value+"%");
        System.out.printf("""
                "The device number: %s is full Assembled!
                Setting final Timestamp to: %s
                Current location is %s
                %n""",deviceId,f_Timestamp_assembling,location);
    }

    @Override
    public String toString() {
        return "AssemblingSensor{" +
                "value=" + value +
                ", i_Timestamp_assembling=" + i_Timestamp_assembling +
                ", f_Timestamp_assembling=" + f_Timestamp_assembling +
                ", i_timestamp_transforming=" + i_timestamp_transforming +
                ", f_timestap_transforming=" + f_timestap_transforming +
                ", unit='" + unit + '\'' +
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

    public long getI_Timestamp_assembling() {
        return i_Timestamp_assembling;
    }

    public void setI_Timestamp_assembling(long i_Timestamp_assembling) {
        this.i_Timestamp_assembling = i_Timestamp_assembling;
    }

    public long getF_Timestamp_assembling() {
        return f_Timestamp_assembling;
    }

    public void setF_Timestamp_assembling(long f_Timestamp_assembling) {
        this.f_Timestamp_assembling = f_Timestamp_assembling;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public long getI_timestamp_transforming() {
        return i_timestamp_transforming;
    }

    public void setI_timestamp_transforming(long i_timestamp_transforming) {
        this.i_timestamp_transforming = i_timestamp_transforming;
    }

    public long getF_timestap_transforming() {
        return f_timestap_transforming;
    }

    public void setF_timestap_transforming(long f_timestap_transforming) {
        this.f_timestap_transforming = f_timestap_transforming;
    }

    public ProgressBar generateAssemblingProgressBar(){
        ProgressBar progressBar =new ProgressBar(0,100);
        progressBar.setVisible(true);
        progressBar.setTitle("Assembling Line");
        return progressBar;
    }
    public static void updateAssemblingProgressBar(ProgressBar progressBar, int value){
        progressBar.paint(progressBar.getGraphics());
        progressBar.jb.setValue(value);
    }
}
