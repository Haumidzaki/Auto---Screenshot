import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThreadAutoScreenshot extends Thread {
    private static final String ACCESS_TOKEN = "your dropbox token";
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
    private DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial")
            .withAutoRetryEnabled().build();
    private DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);


    @Override
    public void run() {
        while (true) {
            try {
                Robot robot = new Robot();
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage image = robot.createScreenCapture(screenRect);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(image, "png", outputStream);
                InputStream stream = new ByteArrayInputStream(outputStream.toByteArray());
                Date date = new Date();
                String fileName = formatter.format(date);
                client.files().upload("/" + fileName + ".png").uploadAndFinish(stream);
                sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

