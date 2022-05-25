package views.resources;

import controllers.FrameManager;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class SpiderMan implements Runnable {
    BufferedImage original=null;
    @Override
    public void run() {
        Random random = new Random();
        int compteurX = random.nextInt(700);
        int compteurY = random.nextInt(700);

        int incrementX = 10;
        int incrementY = 10;
        try {
            original = convertToARGB(ImageIO.read(new File("./src/views/resources/spiderman.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon normalIcon = new ImageIcon(original.getScaledInstance(256, 181, Image.SCALE_FAST));

        JLabel imageSpiderMan = new JLabel();
        imageSpiderMan.setIcon(normalIcon);
        FrameManager.container.add(imageSpiderMan);


        boolean check = false;
        while(true) {
            imageSpiderMan.setBounds(compteurX, compteurY, 256, 181);
            FrameManager.container.revalidate();
            FrameManager.container.repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            compteurX+=incrementX;
            compteurY+=incrementY;



            if(compteurX>FrameManager.container.getWidth()-imageSpiderMan.getWidth()+50){
                incrementX = -(random.nextInt(6)+5);
                check = true;
                imageSpiderMan.setIcon(getImage(check,0));
                playSound("../resources/web.wav");
            }
            if(compteurX<-30){
                incrementX = random.nextInt(6)+5;
                check = false;
                imageSpiderMan.setIcon(getImage(check,0));
                playSound("../resources/web.wav");
            }

            if(compteurY>FrameManager.container.getHeight()-imageSpiderMan.getHeight()+50){
                incrementY = -(random.nextInt(6)+5);
                imageSpiderMan.setIcon(getImage(check,0));
            }
            if(compteurY<-30){
                incrementY = random.nextInt(6)+5;
                imageSpiderMan.setIcon(getImage(check,0));
            }
        }
    }

    private static BufferedImage convertToARGB(BufferedImage image)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    private static BufferedImage createFlipped(BufferedImage image, int degre)
    {
        AffineTransform at = AffineTransform.getRotateInstance(
                Math.toRadians(degre), image.getWidth()/2, image.getHeight()/2.0);
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));

        return createTransformed(image, at);
    }
    private static BufferedImage createOriginal(BufferedImage image, int degre)
    {
        AffineTransform at = AffineTransform.getRotateInstance(
                Math.toRadians(degre), image.getWidth()/2, image.getHeight()/2.0);
        return createTransformed(image, at);
    }

    private static BufferedImage createTransformed(
            BufferedImage image, AffineTransform at)
    {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
    public ImageIcon getImage(boolean check, int degre){

        if(check){
            degre+=180;
            return new ImageIcon(createFlipped(original, degre).getScaledInstance(256, 181, Image.SCALE_FAST));
        }
        return new ImageIcon(createOriginal(original, degre).getScaledInstance(256, 181, Image.SCALE_FAST));

    }

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(SpiderMan.class.getResourceAsStream(url));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

}
