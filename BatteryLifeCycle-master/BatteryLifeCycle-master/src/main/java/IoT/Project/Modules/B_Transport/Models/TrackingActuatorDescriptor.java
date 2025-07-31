package IoT.Project.Modules.B_Transport.Models;

import java.util.Random;

/**
 * @author Marco Savarese - 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 17/03/2022 - 14:57
 */

public class TrackingActuatorDescriptor {
    private String DID;
    private String startLocation;
    private String endLocation;
    private double batterylevel;
    private long stimestamp;
    private long timestamp;
    private long etimestamp;

    private transient Random random;

    public String getDID() {
        return DID;
    }
    public void setDID(String DID) {
        this.DID = DID;
    }

    public String getStartLocation() {
        return startLocation;
    }
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }
    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public double getBatterylevel() {
        return batterylevel;
    }
    public void setBatterylevel(double batterylevel) {
        this.batterylevel = batterylevel;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getStimestamp() {
        return stimestamp;
    }
    public void setStimestamp(long stimestamp) {
        this.stimestamp = stimestamp;
    }

    public long getEtimestamp() {
        return etimestamp;
    }
    public void setEtimestamp(long etimestamp) {
        this.etimestamp = etimestamp;
    }

    public TrackingActuatorDescriptor(){
        this.startLocation = "";
        this.batterylevel = 100.0;
        this.timestamp = 0;
        this.random = new Random(System.currentTimeMillis());
    }

    public void BatteryDrainingSimulator(){
        if(this.batterylevel <=10.0){
            this.setBatterylevel(0);
            this.setTimestamp(System.currentTimeMillis());
            this.setEtimestamp(System.currentTimeMillis());
        }
        else{
            if(this.batterylevel == 100.0){
                this.setStimestamp(System.currentTimeMillis());
            }
            this.setBatterylevel(this.batterylevel - this.random.nextDouble(0.0, 10.0));
            this.setTimestamp(System.currentTimeMillis());
        }
    }
}
