package IoT.Project.Modules.C_Processing;

import IoT.Project.Modules.C_Processing.Process.AssemblingCoapProcess;
import IoT.Project.Modules.C_Processing.Process.TransformingProcess;

public class SimulationStageC {
    public static void main(String[] args) {
        try {
            System.out.println("STARTING SIMULATION STAGE C!");
            TransformingProcess.main(args);
            Thread.sleep(30000);
            AssemblingCoapProcess.main(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
