package IoT.Project.Modules.B_Transport.Models;

/**
 * @author Marco Savarese - 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 24/02/2022 - 12:48
 */
public class VehicleDesctiptor {
    private String ID;
    private String brand;
    private String model;
    private String driverId;

    //Constructors
    public VehicleDesctiptor() {
        this.ID = null;
        this.brand = null;
        this.model = null;
        this.driverId = null;
    }
    public VehicleDesctiptor(String ID, String brand, String model, String driverId) {
        this.ID = ID;
        this.brand = brand;
        this.model = model;
        this.driverId = driverId;
    }

    //Getters and Setters
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getDriverId() {
        return driverId;
    }
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    //toString
    @Override
    public String toString(){
       final StringBuffer VehicleInfo = new StringBuffer("Vehicle:{");
       VehicleInfo.append("ID: '").append(ID).append('\'');
       VehicleInfo.append(", Brand: '").append(brand).append('\'');
       VehicleInfo.append(", Model: '").append(model).append('\'');
       VehicleInfo.append(", DriverID: '").append(driverId).append('\'');
       VehicleInfo.append('}');
       return VehicleInfo.toString();
    }
}
