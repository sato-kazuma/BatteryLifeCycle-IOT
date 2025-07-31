package IoT.Project.DCPM.Models;
/**
 * @author Paolo Castagnetti, 267731@studenti.unimore.it - Marco Savarese, 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 03/03/2022 - 10:09
 */
public class TransportDescriptor{
    //Attributes
    private long transportTimestampStart; //Transport Timestamp Start
    private long transportTimestampEnd; //Transport Timestamp End
    private String vehicleID; //Vehicle ID
    private String startLocation; //Start Location
    private String endingLocation; //Ending Location

    //Constructor
    public TransportDescriptor(){
        this.transportTimestampStart = 0;
        this.transportTimestampEnd = 0;
        this.vehicleID = "";
        this.startLocation = "";
        this.endingLocation = "";
    }
    public TransportDescriptor(TransportDescriptor TD){
        this.transportTimestampStart = TD.transportTimestampStart;
        this.transportTimestampEnd = TD.transportTimestampEnd;
        this.vehicleID = TD.vehicleID;
        this.startLocation = TD.startLocation;
        this.endingLocation = TD.endingLocation;
    }

    //Getters and Setters
    public long getTransportTimestampStart() {
        return transportTimestampStart;
    }
    public void setTransportTimestampStart(long transportTimestampStart) {
        this.transportTimestampStart = transportTimestampStart;
    }

    public long getTransportTimestampEnd() {
        return transportTimestampEnd;
    }
    public void setTransportTimestampEnd(long transportTimestampEnd) {
        this.transportTimestampEnd = transportTimestampEnd;
    }

    public String getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getStartLocation() {
        return startLocation;
    }
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndingLocation() {
        return endingLocation;
    }
    public void setEndingLocation(String endingLocation) {
        this.endingLocation = endingLocation;
    }

    //toString
    @Override
    public String toString() {
        return "TransportDescriptor{" +
                "transportTimestampStart=" + transportTimestampStart +
                ", transportTimestampEnd=" + transportTimestampEnd +
                ", vehicleID='" + vehicleID + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", endingLocation='" + endingLocation + '\'' +
                '}';
    }
}
