package IoT.Project.Modules.A_Extraction;

import IoT.Project.Modules.A_Extraction.Process.MineralQuantityProcessEmulator;
import IoT.Project.Modules.A_Extraction.Process.UploadingActuatorConsumer;


public class SimulationStageA {
    public static void main(String[] args){
        try {
            System.out.println("STARTING SIMULATION STAGE A!");
            UploadingActuatorConsumer.main(args);
            Thread.sleep(2000);
            MineralQuantityProcessEmulator.main(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
