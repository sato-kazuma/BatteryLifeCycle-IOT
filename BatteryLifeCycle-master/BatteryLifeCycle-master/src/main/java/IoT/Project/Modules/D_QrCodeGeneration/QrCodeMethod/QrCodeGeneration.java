package IoT.Project.Modules.D_QrCodeGeneration.QrCodeMethod;

import IoT.Project.DCPM.Models.ExtractionDescriptor;
import IoT.Project.DCPM.Models.QrCodeDescriptor;
import IoT.Project.Modules.D_QrCodeGeneration.CoAP_Communications.CoapGetExtraction;
import IoT.Project.Modules.D_QrCodeGeneration.CoAP_Communications.CoapGetProcessing;
import IoT.Project.Modules.D_QrCodeGeneration.CoAP_Communications.CoapGetTransport;
import IoT.Project.Modules.D_QrCodeGeneration.CoAP_Communications.CoapPutQrCode;
import IoT.Project.Modules.D_QrCodeGeneration.QrCodeMethod.LoadingBar.ProgressBar;
import com.google.gson.Gson;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.File;

import static IoT.Project.Modules.D_QrCodeGeneration.QrCodeMethod.LoadingBar.ProgressBar.updateProgressBar;

/**
 * @author Francesco Lasalvia, 271719@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 03/03/2022 - 10:09
 */

public class QrCodeGeneration {

    //dove vuoi mettere tutti i qrcode
    //private static String path="C:\\Users\\Marco\\Desktop\\Roba\\Università\\Terzo Anno\\IoT\\BatteryLifeCycle\\src\\main\\java\\IoT\\Project\\Modules\\D_QrCodeGeneration\\QrCodeImage";
    private static String path="C:\\Users\\lasal\\Desktop\\Unimore\\Iot\\Iot-code\\Prog-esame\\BatteryLifeCycle\\src\\main\\java\\IoT\\Project\\Modules\\D_QrCodeGeneration\\QrCodeImage";
    //private static String path="C:\\Users\\Paolo\\IdeaProjects\\BatteryLifeCycle\\src\\main\\java\\IoT\\Project\\Modules\\D_QrCodeGeneration\\QrCodeImage";
    private static int value=0;

    public static int getValue() {
        return value;
    }
    public static void setValue(int value) {
        QrCodeGeneration.value = value;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Generazione QR_Code..");
        ProgressBar progressBar =new ProgressBar(0,100);
        progressBar.setVisible(true);
        progressBar.setTitle("QrCodeGeneration");


        QrCodeDescriptor qrCodeDescriptor=new QrCodeDescriptor();
        Gson gson = new Gson();

        //text sono le info che vuoi mettere nel QRcode
        String A_Extraction = CoapGetExtraction.getExtractionGson();
        ExtractionDescriptor extractionDescriptor = gson.fromJson(A_Extraction, ExtractionDescriptor.class);
        updateProgressBar(progressBar,value+=10);
        Thread.sleep(1000);

        String B_Transport= CoapGetTransport.getTransportGson();
        updateProgressBar(progressBar,value+=10);
        Thread.sleep(1500);

        String C_Processing= CoapGetProcessing.getProcessingGson();
        updateProgressBar(progressBar,value+=10);
        Thread.sleep(1500);

        String final_payload=String.format("Extraction:\n%s\nTransport\n%s\nProcessing:\n%s\n",A_Extraction,B_Transport,C_Processing);
        updateProgressBar(progressBar,value+=10);
        Thread.sleep(1500);

        try{
            //generate QrCode
            QRCodeWriter writer=new QRCodeWriter();
            BitMatrix bitMatrix=writer.encode(final_payload, BarcodeFormat.QR_CODE,150,150);
            updateProgressBar(progressBar,value+=20);
            Thread.sleep(1500);

            //file generation: usa path/nome del qr code associato al codice univoco che prenderò da batteria!
            File qrCode = new File(String.format("%s/QR_Code_%s.png",path,extractionDescriptor.getLoad_code()));
            MatrixToImageWriter.writeToPath(bitMatrix,"PNG",qrCode.toPath());
            updateProgressBar(progressBar,value+=20);
            Thread.sleep(1500);

            qrCodeDescriptor.setID(extractionDescriptor.getLoad_code());
            qrCodeDescriptor.setTimestamp(System.currentTimeMillis());
            qrCodeDescriptor.setQrCode(bitMatrix);

            //System.out.println("ID: "+qrCodeDescriptor.getID());
            //System.out.println("Timestamp: "+ qrCodeDescriptor.getTimestamp());
            //System.out.println("bitMatrix: " + qrCodeDescriptor.getQrCode());
            updateProgressBar(progressBar,value+=20);
            Thread.sleep(1500);

            progressBar.dispose();
            System.out.println("QrCode Is Ready!!\n");
            System.out.printf("Current timestap is: %d\n%n",System.currentTimeMillis());

            CoapPutQrCode.putQrCode(qrCodeDescriptor);

            System.out.println("QrCode inserito sul DCPM\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
