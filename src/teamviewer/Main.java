package teamviewer;

import teamviewer.controller.ClientController;
import teamviewer.controller.TeamviewerRemoteInterface;
import teamviewer.controller.TeamviewerRemoteInterfaceImpl;
import teamviewer.entity.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 3030;
        ClientController clientController = new ClientController();


        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            Registry registry = LocateRegistry.getRegistry(host, port);
            TeamviewerRemoteInterface remote = (TeamviewerRemoteInterface) registry.lookup("teamviewerRemote");
            PCRemoteAccessInfo pcRemoteAccessInfo = new PCRemoteAccessInfo();
            pcRemoteAccessInfo.setToId("148");
            for (Action action : remote.getListAction()){

                try {
                    KeyAction keyAction = (KeyAction) action;
                    System.out.println(keyAction.getKeycode());
                    System.out.println(keyAction);
                } catch (Exception ex){

                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
