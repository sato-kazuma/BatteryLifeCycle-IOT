package IoT.Project;

import IoT.Project.DCPM.DCPM;
import IoT.Project.Modules.A_Extraction.SimulationStageA;
import IoT.Project.Modules.B_Transport.SimulationStageB;
import IoT.Project.Modules.C_Processing.SimulationStageC;
import IoT.Project.Modules.D_QrCodeGeneration.QrCodeMethod.QrCodeReader;
import IoT.Project.Modules.D_QrCodeGeneration.SimulationStageD;
import IoT.Project.Modules.E_Assembly.SimulationStageE;

public class GeneralSimulation {
    public static void main(String[] args) {
        System.out.println("STARTING SIMULATION OF BATTERY_LIFE_CYCLE!");
        try {
            DCPM.main(args);
            SimulationStageA.main(args);
            Thread.sleep(30000);
            SimulationStageB.main(args);
            Thread.sleep(30000);
            SimulationStageC.main(args);
            Thread.sleep(20000);
            SimulationStageD.main(args);
            Thread.sleep(20000);
            SimulationStageE.main(args);
            Thread.sleep(10000);
            QrCodeReader.main(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
