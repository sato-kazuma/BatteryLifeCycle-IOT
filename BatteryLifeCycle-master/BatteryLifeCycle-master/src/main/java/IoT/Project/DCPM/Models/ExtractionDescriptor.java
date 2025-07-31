package IoT.Project.DCPM.Models;
/**
 * @author Paolo Castagnetti, 267731@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 03/03/2022 - 10:09
 */
public class ExtractionDescriptor {

    //Attributes
    private long timestamp_end_extraction;
    private long timestamp_end_loading;
    private String extraction_location;
    private String load_code;
    private int mineral_quantity;

    private static final String UNIT = "Kg";

    //Constructor
    public ExtractionDescriptor(){
        this.timestamp_end_extraction=0;
        this.timestamp_end_loading=0;
        this.extraction_location="";
        this.load_code="";
        this.mineral_quantity=0;
    }
    public ExtractionDescriptor(ExtractionDescriptor ED){
        this.timestamp_end_extraction=ED.timestamp_end_extraction;
        this.timestamp_end_loading=ED.timestamp_end_loading;
        this.extraction_location=ED.extraction_location;
        this.load_code= ED.load_code;
        this.mineral_quantity=ED.mineral_quantity;
    }

    //Getter & Setter
    public String getExtraction_location() {
        return extraction_location;
    }
    public void setExtraction_location(String extraction_location) {
        this.extraction_location = extraction_location;
    }
    public String getLoad_code() {
        return load_code;
    }
    public void setLoad_code(String load_code) {
        this.load_code = load_code;
    }
    public int getMineral_quantity() {
        return mineral_quantity;
    }
    public void setMineral_quantity(int mineral_quantity) {
        this.mineral_quantity = mineral_quantity;
    }
    public void setLocation(String location) {
        extraction_location = location;
    }
    public long getTimestamp_end_extraction() {
        return timestamp_end_extraction;
    }
    public void setTimestamp_end_extraction(long timestamp_end_extraction) {
        this.timestamp_end_extraction = timestamp_end_extraction;
    }
    public long getTimestamp_end_loading() {
        return timestamp_end_loading;
    }
    public void setTimestamp_end_loading(long timestamp_end_loading) {
        this.timestamp_end_loading = timestamp_end_loading;
    }

    //Methods
    @Override
    public String toString() {
        return "ExtractionDescriptor{" +
                "timestamp_end_extraction=" + timestamp_end_extraction +
                ", timestamp_end_loading=" + timestamp_end_loading +
                ", extraction_location='" + extraction_location + '\'' +
                ", load_code='" + load_code + '\'' +
                ", mineral_quantity=" + mineral_quantity +
                '}';
    }
}