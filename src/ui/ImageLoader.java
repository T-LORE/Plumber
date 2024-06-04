package ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
    public static BufferedImage loadImage(String path, int width, int height) {
        try {
            BufferedImage fullResImage = ImageIO.read(new File("Sprites/" + path));

            Image tmpImg = fullResImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(tmpImg, 0, 0, null);
            g.dispose();
            
            return bufferedImage;
        } catch (IOException e) {
            System.out.println("Error loading image: " + path);
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage shiftPicture(BufferedImage image, int x, int y) {
        AffineTransform tx = new AffineTransform();
        tx.translate(x, y);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage shifted = op.filter(image, null);
        return shifted;
    }

    public static BufferedImage rotateClockwisePicture(BufferedImage image, int times) {
        int width = image.getWidth();
        int height = image.getHeight();
        int targetWidth = width;
        int targetHeight = height;
        if (times % 4 == 1 || times % 4 == 3) {
            targetWidth = height;
            targetHeight = width;
        }

        BufferedImage rotatedImage = new BufferedImage(targetWidth, targetHeight, image.getType());

        Graphics2D g2d = rotatedImage.createGraphics();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(90 * times), width / 2, height / 2);
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }
}
