
import edu.duke.*;
import java.io.*;

public class MakeNegative {
    public ImageResource makeNegative(ImageResource inImage) {
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for (Pixel pixel: outImage.pixels()) {
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            
            pixel.setRed(255 - inPixel.getRed());
            pixel.setBlue(255 - inPixel.getBlue());
            pixel.setGreen(255 - inPixel.getGreen());
        }
        
        return outImage;
    }
    
    public void selectAndConvert () {
        DirectoryResource dr = new DirectoryResource();
        for (File file : dr.selectedFiles()) {
            ImageResource inImage = new ImageResource(file);
	    ImageResource negative = makeNegative(inImage);
            String fname = inImage.getFileName();
            String newName = "inverted-" + fname;
            negative.setFileName("./images/" + newName);
            negative.draw();
            negative.save();
        }
    }
}
