package IoT.Project.Modules.B_Transport.Models;

/**
 * @author Marco Savarese - 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 25/02/2022 - 12:24
 */

public class GPSTrackerDescriptor {
    private double latitude;
    private double longitude;
    private double altitude;
    private String unit = "°";

    //Constructors
    public GPSTrackerDescriptor(){
        this.altitude = 0;
        this.latitude = 0;
        this.longitude = 0;
    }
    public GPSTrackerDescriptor(double latitude, double longitude, double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    //Getters and Setters
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getAltitude() {
        return altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    //toString
    @Override
    public String toString(){
        final StringBuffer GPSTrack = new StringBuffer("GPSLocation:{ ");
        GPSTrack.append("Latitude:' ").append(latitude).append(unit).append('\'');
        GPSTrack.append(" Longitude:' ").append(longitude).append(unit).append('\'');
        GPSTrack.append(" Altitude:' ").append(altitude).append('\'');
        GPSTrack.append("}");
        return GPSTrack.toString();
    }
}
