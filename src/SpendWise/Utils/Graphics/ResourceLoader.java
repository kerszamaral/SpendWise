package SpendWise.Utils.Graphics;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

import SpendWise.Utils.Paths;

public abstract class ResourceLoader implements Paths {
    public static final Toolkit toolkit = Toolkit.getDefaultToolkit();

    public static ImageIcon resourceToImage(String path) {
        URL place = ResourceLoader.class.getResource(path);
        Image image = toolkit.getImage(place);
        return new ImageIcon(image);
    }

    public static Font resourceToFont(String Path) {
        URL place = ResourceLoader.class.getResource(Path);
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(place.toURI()));
        } catch (Exception e) {
            System.out.println("Error loading font.");
        }
        return null;
    }
}
