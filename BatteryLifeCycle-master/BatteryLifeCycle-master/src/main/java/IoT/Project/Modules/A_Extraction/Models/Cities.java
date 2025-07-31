package IoT.Project.Modules.A_Extraction.Models;
/**
 * @author Paolo Castagnetti, 267731@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 10/03/2022 - 11:29
 */
public class Cities {
    private final String[] CITIES = {"Mantova", "Modena", "Reggio-Emilia", "Parma", "Milano", "Genova", "Venezia", "Roma"};
    public String[] getCITIES() {
        return CITIES;
    }
    public String getCITY(int rnd) {
        return CITIES[rnd];
    }
}
