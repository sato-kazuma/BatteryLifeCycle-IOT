package IoT.Project.Modules.E_Assembly;

import IoT.Project.Modules.E_Assembly.Process.MountingProcessConsumer;
import IoT.Project.Modules.E_Assembly.Process.MountingProcessEmulator;

public class SimulationStageE {
    public static void main(String[] args) {
        try {
            System.out.println("STARTING SIMULATION STAGE E!");
            MountingProcessConsumer.main(args);
            Thread.sleep(2000);
            MountingProcessEmulator.main(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
