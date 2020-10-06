import net.sourceforge.tess4j.TesseractException;
import services.Korea;
import services.NZ;
import services.Otc_USA;
import services.Singapur;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws IOException {

        NZ.scrapeData();


    }
}
