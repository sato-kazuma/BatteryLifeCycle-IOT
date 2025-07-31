package IoT.Project.Modules.D_QrCodeGeneration;

import IoT.Project.Modules.D_QrCodeGeneration.QrCodeMethod.QrCodeGeneration;

public class SimulationStageD {
    public static void main(String[] args) {
        try {
            System.out.println("STARTING SIMULATION STAGE D!");
            QrCodeGeneration.main(args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
