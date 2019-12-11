package teamviewer.controller;

import teamviewer.entity.ImageSent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;

public class ClientController {
    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String NUMERIC_STRING = "0123456789";
    private String id;
    private String password;
    private Robot robot;
    private TeamviewerRemoteInterface remote;

    public ClientController() {
    }

    public ClientController(TeamviewerRemoteInterface remote) {
        try {
            robot = new Robot();
            this.remote = remote;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // chụp ảnh tạo màn hình điều khiển cho máy khác
    public void sendScreen(){
        try {
            Rectangle capture =
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            BufferedImage image = robot.createScreenCapture(capture);
            ByteArrayOutputStream imagebyte = new ByteArrayOutputStream();
            ImageIO.write(image, "png", imagebyte);
            byte[] imageData = imagebyte.toByteArray();
            ImageSent imageSent = new ImageSent(id, imageData,Toolkit.getDefaultToolkit().getScreenSize().height, Toolkit.getDefaultToolkit().getScreenSize().width);
            remote.sendImage(imageSent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Tạo id và password để có quyền điều khiển cho máy khác
    public void createIdAndPassword(){
        if (this.id == null && this.password == null){
            Random random = new Random();
            StringBuilder builderId = new StringBuilder();
            StringBuilder builderPassword = new StringBuilder();

            int countId = 3;
            while (countId-- != 0) {
                int character = (int)(Math.random()*NUMERIC_STRING.length());
                builderId.append(NUMERIC_STRING.charAt(character));
            }

            int countPassword = 6;
            while (countPassword-- != 0) {
                int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
                builderPassword.append(ALPHA_NUMERIC_STRING.charAt(character));
            }
            System.out.println(builderId);
            this.id = builderId.toString();
            System.out.println(builderPassword);
            this.password = builderPassword.toString();
        }
    }

    // Điều khiển ở máy khác
    public void moveMouse(int x, int y){
        robot.mouseMove(x, y);
    }

    public void mousePress(){
        try {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        } catch (Exception ex){

        }
    }

    public void mouseRelease(){
        try{
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (Exception ex){

        }
    }

    public void keyPress(int keycode){
        robot.keyPress(keycode);
    }

    public void keyRelease(int keycode){
        robot.keyRelease(keycode);
    }

}
