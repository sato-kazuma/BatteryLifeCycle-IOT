package IoT.Project.Modules.E_Assembly.Models;


/**
 * @author Marco Savarese, 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 31/03/2022 18:51
 */

public class Cars {
    private String[] carsList = {"Tesla Model 3", "Tesla Model Y", "Tesla Model S", "Tesla Model X", "Audi Q8 E-Tron", "Dacia Spring"};

    public String[] getCarsList(){
        return carsList;
    }

    public String CarGetter(int i){
        return carsList[i];
    }

    public int CarsListLength(){
        return carsList.length;
    }
}
