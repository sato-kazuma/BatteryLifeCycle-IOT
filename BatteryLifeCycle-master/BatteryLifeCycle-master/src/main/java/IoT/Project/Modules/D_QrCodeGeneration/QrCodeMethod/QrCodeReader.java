package IoT.Project.Modules.D_QrCodeGeneration.QrCodeMethod;

import IoT.Project.DCPM.Models.QrCodeDescriptor;
import IoT.Project.Modules.D_QrCodeGeneration.CoAP_Communications.CoAPGetQrCode;
import com.google.gson.Gson;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * @author Francesco Lasalvia, 271719@studenti.unimore.it
 * @project IoT-BatteryLifeCycle
 * @created 03/03/2022 - 10:09
 */

public class QrCodeReader {
    //path è dove la funziona trova i QR_code da leggere
    //private static String path="C:\\Users\\Marco\\Desktop\\Roba\\Università\\Terzo Anno\\IoT\\BatteryLifeCycle\\src\\main\\java\\IoT\\Project\\Modules\\D_QrCodeGeneration\\QrCodeImage";
    private static String path="C:\\Users\\lasal\\Desktop\\Unimore\\Iot\\Iot-code\\Prog-esame\\BatteryLifeCycle\\src\\main\\java\\IoT\\Project\\Modules\\D_QrCodeGeneration\\QrCodeImage";
    //private static String path="C:\\Users\\Paolo\\IdeaProjects\\BatteryLifeCycle\\src\\main\\java\\IoT\\Project\\Modules\\D_QrCodeGeneration\\QrCodeImage";

    public static void main(String[] args) throws ChecksumException, NotFoundException, IOException, WriterException, FormatException {

        Gson gson =new Gson();
        QrCodeDescriptor qrCodeDescriptor = CoAPGetQrCode.getQrCode();


        String codeBattery=qrCodeDescriptor.getID();
        File qr_file=new File(String.format("%s/QR_Code_%s.png",path,codeBattery));

        System.out.println("IL contenuto del QrCode e' il seguente: \n");
        System.out.println(readQrCode(qr_file));
        System.out.println("\n");
        System.out.println("Done");
    }

    private static String readQrCode(File qrcodefile) {
        Result decode=null;
        try{
            QRCodeReader qrCodeReader=new QRCodeReader();
            decode=qrCodeReader.decode(new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(qrcodefile)))));
        }catch (Exception e){
            e.printStackTrace();
        }
        return decode.getText();
    }
}
