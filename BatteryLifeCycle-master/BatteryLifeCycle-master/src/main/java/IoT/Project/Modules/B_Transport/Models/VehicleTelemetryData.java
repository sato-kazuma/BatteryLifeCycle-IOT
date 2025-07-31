package IoT.Project.Modules.B_Transport.Models;

/**
 * @author Marco Savarese - 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 24/02/2022 - 18:12
 */
import java.util.Random;
import IoT.Project.Modules.A_Extraction.Models.Cities;

public class VehicleTelemetryData {
    private GPSTrackerDescriptor gpsTrackerDescriptor;
    private String startingLocation;
    private String endingLocation;
    private double batteryLevel = 100.0;
    private double enginetemp;
    private long timestamp;
    private String ID;

    private transient Random random;
    private transient Cities City;


    //Constructors
    public VehicleTelemetryData(String ID){
        this.ID = ID;
        this.City = new Cities();
    }
    public VehicleTelemetryData(GPSTrackerDescriptor gpsTrackerDescriptor, double batterylevel, double enginetemp, long timestamp) {
        this.gpsTrackerDescriptor = gpsTrackerDescriptor;
        this.batteryLevel = batterylevel;
        this.enginetemp = enginetemp;
        this.timestamp = timestamp;
    }

    //Getters and Setters
    public GPSTrackerDescriptor getGpsTrackerDescriptor() {
        return gpsTrackerDescriptor;
    }
    public void setGpsTrackerDescriptor(GPSTrackerDescriptor gpsTrackerDescriptor) {
        this.gpsTrackerDescriptor = gpsTrackerDescriptor;
    }

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getStartingLocation() {
        return startingLocation;
    }
    public void setStartingLocation(String startingLocation) {
        this.startingLocation = startingLocation;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }
    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public double getEnginetemp() {
        return enginetemp;
    }
    public void setEnginetemp(double enginetemp) {
        this.enginetemp = enginetemp;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getEndingLocation() {
        return endingLocation;
    }
    public void setEndingLocation(String endingLocation) {
        this.endingLocation = endingLocation;
    }

    //Measurments updater via random numbers
    public void updateMeasurments(String CL){
        if(this.random == null){
            this.setStartingLocation(CL);
            this.random = new Random(System.currentTimeMillis());
            this.setEndingLocation("Unknown");
        }
        double randomlatitude = 40.85 + this.random.nextDouble() * 10.0;
        double randomlongitude = 15.27 + this.random.nextDouble() * 10.0;
        this.gpsTrackerDescriptor = new GPSTrackerDescriptor(randomlatitude, randomlongitude, 1232.0);
        this.enginetemp = 80 + this.random.nextDouble() * 10.0;
        if(this.batteryLevel <= 10.0){
            this.setBatteryLevel(0);
            String newCity = this.City.getCITY(this.random.nextInt(0, this.City.getCITIES().length));
            while(newCity.equals(getStartingLocation())){
                newCity = this.City.getCITY(this.random.nextInt(0, this.City.getCITIES().length));
            }
            this.setEndingLocation(newCity);
        }else {
            this.batteryLevel = this.batteryLevel - (this.random.nextDouble() * 10.0);
        }
        this.timestamp = System.currentTimeMillis();
    }

    //toString Method
    @Override
    public String toString(){
        StringBuffer VehicleTelemetry = new StringBuffer("VehicleTelemetry{");
        VehicleTelemetry.append("Geolocation: ").append(gpsTrackerDescriptor);
        VehicleTelemetry.append("Currennt Location: ").append(startingLocation);
        VehicleTelemetry.append(" Battery Level: ").append(batteryLevel).append('%');
        VehicleTelemetry.append(" Engine Temperature: ").append(enginetemp).append('°');
        VehicleTelemetry.append(" Timestamp: ").append(timestamp);
        VehicleTelemetry.append('}');
        return VehicleTelemetry.toString();
    }
}
