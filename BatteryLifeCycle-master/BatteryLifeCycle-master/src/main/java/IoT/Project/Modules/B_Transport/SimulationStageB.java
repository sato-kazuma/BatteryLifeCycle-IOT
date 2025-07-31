package IoT.Project.Modules.B_Transport;

import IoT.Project.Modules.B_Transport.Process.VehicleDataConsumer;
import IoT.Project.Modules.B_Transport.Process.VehicleTrackingEmulator;

/**
 * @author Marco Savarese - 271055@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 20/03/2022 - 11:58
 */

public class SimulationStageB {
        public static void main(String[] args){
            try {
                //DCPM.main(args);
                VehicleDataConsumer.main(args);
                Thread.sleep(2000);
                VehicleTrackingEmulator.main(args);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

}
