package IoT.Project.DCPM.Models;

/**
 * @author Francesco Lasalvia, 271719@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 03/03/2022 - 10:09
 */
public class ProcessingDescriptor {
    //Attributes
    private long timestamp_start_processing;
    private long timestamp_end_processing;
    private long timestamp_start_assembling_within_processing;
    private long timestamp_end_assembling_within_processing;
    private String code;
    private String location;


    //Constructor
    public ProcessingDescriptor(){
        this.timestamp_start_processing=0;
        this.timestamp_end_processing=0;
        this.timestamp_start_assembling_within_processing=0;
        this.timestamp_end_assembling_within_processing=0;
        this.code="";
        this.location="";
    }
    public ProcessingDescriptor(ProcessingDescriptor PD) {
        this.timestamp_start_processing = PD.timestamp_start_processing;
        this.timestamp_end_processing = PD.timestamp_end_processing;
        this.timestamp_start_assembling_within_processing = PD.timestamp_start_assembling_within_processing;
        this.timestamp_end_assembling_within_processing = PD.timestamp_end_assembling_within_processing;
        this.code = PD.code;
        this.location = PD.location;
    }

    public long getTimestamp_start_processing() {
        return timestamp_start_processing;
    }

    public void setTimestamp_start_processing(long timestamp_start_processing) {
        this.timestamp_start_processing = timestamp_start_processing;
    }

    public long getTimestamp_end_processing() {
        return timestamp_end_processing;
    }

    public void setTimestamp_end_processing(long timestamp_end_processing) {
        this.timestamp_end_processing = timestamp_end_processing;
    }

    public long getTimestamp_start_assembling_within_processing() {
        return timestamp_start_assembling_within_processing;
    }

    public void setTimestamp_start_assembling_within_processing(long timestamp_start_assembling_within_processing) {
        this.timestamp_start_assembling_within_processing = timestamp_start_assembling_within_processing;
    }

    public long getTimestamp_end_assembling_within_processing() {
        return timestamp_end_assembling_within_processing;
    }

    public void setTimestamp_end_assembling_within_processing(long timestamp_end_assembling_within_processing) {
        this.timestamp_end_assembling_within_processing = timestamp_end_assembling_within_processing;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "ProcessingDescriptor{" +
                "timestamp_start_processing=" + timestamp_start_processing +
                ", timestamp_end_processing=" + timestamp_end_processing +
                ", timestamp_start_assembling_within_processing=" + timestamp_start_assembling_within_processing +
                ", timestamp_end_assembling_within_processing=" + timestamp_end_assembling_within_processing +
                ", code='" + code + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
