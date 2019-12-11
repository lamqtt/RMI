package teamviewer;

import teamviewer.controller.ClientController;
import teamviewer.controller.TeamviewerRemoteInterface;
import teamviewer.entity.*;
import teamviewer.entity.Action;

import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class homepage implements ActionListener {
    private static JTextField userText;
    private static TeamviewerRemoteInterface remote;
    private static ClientController clientController;
    private static String currentId = "";
    private static String currentPassword = "";
    private static String currentConnectPCId = "";
    private static JLabel label;
    private static byte[] imageData;
    private static float ratioXValue;
    private static float ratioYValue;
    static JDialog d, d1;
    private JPasswordField passwordText;

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                currentId = "";
                currentPassword = "";
                String host = "127.0.0.1";
                int port = 3030;
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    Registry registry = LocateRegistry.getRegistry(host, port);
                    remote = (TeamviewerRemoteInterface) registry.lookup("teamviewerRemote");
                    PCInfo pcInfo = remote.checkExistPCbyAddress(inetAddress.getHostAddress());
                    if (pcInfo == null) {
                        clientController = new ClientController(remote);
                        clientController.createIdAndPassword();
                        currentId = clientController.getId();
                        currentPassword = clientController.getPassword();
                        pcInfo = new PCInfo();
                        pcInfo.setAddress(inetAddress.getHostAddress());
                        pcInfo.setId(currentId);
                        pcInfo.setPassword(currentPassword);
                        remote.addNewPC(pcInfo);
                    }
                    else {
                        currentId = pcInfo.getId();
                        currentPassword = pcInfo.getPassword();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("My First Swing Example");
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                    }
                });
                // Setting the width and height of frame
                frame.setSize(850, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // create a panel
                JPanel p1 = new JPanel();
                JPanel p2 = new JPanel();

                JLabel l1, l2;
                l1 = new JLabel("ID: " + currentId);
                l1.setBounds(150, 50, 400, 60);

                l2 = new JLabel("Password: " + currentPassword);
                l2.setBounds(50, 500, 500, 90);
                l2.setAlignmentX(500);
                l2.setAlignmentY(200);

                p1.add(l1);
                p1.add(l2);

                // create a splitpane
                JSplitPane sl = new JSplitPane(SwingConstants.VERTICAL, p1, p2);
                sl.setResizeWeight(0.5);
                // set Orientation for slider
                sl.setOrientation(SwingConstants.VERTICAL);

                // add panel
                frame.add(sl);

                // set the size of frame


                // adding panel to frame

                /* calling user defined method for adding components
                 * to the panel.
                 */
                placeComponents(p2);

                // Setting the frame visibility to true
                frame.setVisible(true);
            }
        };

        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    while (currentId.equals("") || currentPassword.equals("")) {
                        System.out.println("Not created current id and current password");
                    }
                    try {
                        InetAddress inetAddress = InetAddress.getLocalHost();
                        PCInfo pc = new PCInfo(currentId, currentPassword, inetAddress.getHostAddress());
                        while (!remote.isSharedScreen(pc)) {

                        }
                        // đã có ng kết nối đến máy tính - > gửi ảnh lên sv liên tục
                        // kiểm tra có action mới thì thực hiện
                        while (remote.isSharedScreen(pc)){
                            List<Integer> listIndex = new ArrayList<>();
                            clientController.sendScreen();
                            Map<Integer,Action> listActionPC = remote.getListAction(pc);
                            for (Map.Entry<Integer, Action> action: listActionPC.entrySet()) {
                                System.out.println(action.getValue());
                                MouseAction mouseAction = null;
                                KeyAction keyAction = null;
                                try {
                                    mouseAction = (MouseAction) action.getValue();
                                } catch (Exception ex){
                                    System.out.println("Mouse erroe");
                                }

                                try {
                                    System.out.println("Key error");
                                    keyAction = (KeyAction) action.getValue();
                                }
                                catch (Exception ex){

                                }
                                if (mouseAction != null){
                                    if (mouseAction.getAction() == 0 ){
                                        System.out.println("Move mouse");
                                        clientController.moveMouse(mouseAction.getX(), mouseAction.getY());
                                    }
                                    else if (mouseAction.getAction() == 1){
                                        clientController.mousePress();
                                    }
                                    else if (mouseAction.getAction() == 2){
                                        clientController.mouseRelease();
                                    }
                                } else if (keyAction != null){
                                    if (keyAction.getAction() == 0){
                                        clientController.keyPress(keyAction.getKeycode());
                                    } else if (keyAction.getAction() == 1){
                                        clientController.keyRelease(keyAction.getKeycode());
                                    }
                                }
                                listIndex.add(action.getKey());
                            }
                            for (int i = listIndex.size()-1; i >= 0 ; i--){{
                                remote.deleteAction(i);
                            }}
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        PCRemoteAccessInfo pcConnectInfo = remote.getCurrentConnectionInfo(currentId);
                        if(pcConnectInfo != null){
                            imageData = remote.getImage(pcConnectInfo);
                        }
                        if (imageData != null){
                            update(imageData);
                        }
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }

            }
        };

        Thread threadView = new Thread(runnable);
        Thread threadShareScreen = new Thread(runnable1);
        Thread threadConnectorGetScreen = new Thread(runnable2);
        threadView.start();
        threadShareScreen.start();
        threadConnectorGetScreen.start();
        // Creating instance of JFrame

    }

    private static void placeComponents(JPanel panel) {


        panel.setLayout(null);


        JLabel userLabel = new JLabel("User");

        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        // Same process for password label and text field.


        homepage s = new homepage();

        JButton connectButton = new JButton("Connect");
        connectButton.setBounds(10, 80, 80, 25);
        connectButton.addActionListener(s);
        panel.add(connectButton);

    }


    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        String id = "";
        String password = "";
        if (s.equals("Connect")) {
            // create a dialog Box
            id = userText.getText();

            if (id.equals("") || id == null){
                //show dialog
            }
            else try {
                if (remote.isExistPC(id)){

                    JFrame f = new JFrame();
                    d = new JDialog(f, "Authentication");

                    // create a label
                    JLabel passwordLabel = new JLabel("Password");
                    passwordLabel.setBounds(10, 50, 80, 25);


                    passwordText = new JPasswordField(20);
                    passwordText.setBounds(100, 50, 165, 25);


                    // create a button
                    JButton b = new JButton("Authenticate");
                    b.setBounds(10, 80, 80, 20);
                    // add Action Listener
                    b.addActionListener(this);

                    // create a panel
                    JPanel p = new JPanel();

                    p.add(b);
                    p.add(passwordLabel);
                    p.add(passwordText);

                    // add panel to dialog
                    d.add(p);

                    // setsize of dialog
                    d.setSize(600, 300);

                    // set visibility of dialog
                    d.setVisible(true);
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }

        } else if (s.equals("Authenticate")) { // create a dialog Box
            d1 = new JDialog(d, "Window");
            password = String.valueOf(passwordText.getPassword());
            id = userText.getText();
            currentConnectPCId = id;
            try {
                if(remote.isCorrectPC(id, password)){
                    remote.connectToPC(currentId, id, password);
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
            System.out.println(password + " - " + id);
            // create a label


            ImageIcon image = new ImageIcon("D:\\screen_capture\\teamviewer-meo-hay.jpg");
            Image image2 = image.getImage(); // transform it

            int width  = image2.getWidth(null);
            int height = image2.getHeight(null);
            int scaleWidth = 1024;
            int scaleHeight = 768;


            String heightValue = (float)height*((float)1024/(float)width) + "";

            if(width > 1024 || height > 768) {
                Image newimg = null;
                if ((float)width/scaleWidth >= (float)height/scaleHeight) {
                    newimg = image2.getScaledInstance(1024, Integer.valueOf(heightValue.replace(".0", "")), java.awt.Image.SCALE_SMOOTH);
                }
                ImageIcon icon = new ImageIcon(newimg);
                label = new JLabel("", icon, JLabel.CENTER);
                JPanel panel = new JPanel(new BorderLayout());
                panel.add( label, BorderLayout.CENTER );
                d1.add(panel);
            } else {
                ImageIcon icon = new ImageIcon(image2);
                JLabel label = new JLabel("", icon, JLabel.CENTER);
                label.setSize(1024, Integer.valueOf(heightValue.replace(".0", "")));

                JPanel panel = new JPanel(new BorderLayout());

                panel.add( label, BorderLayout.CENTER );
                d1.add(panel);
            }

            // setsize of dialog
            d1.setSize(1024, Integer.valueOf(heightValue.replace(".0", "")));

            // set loaction of dialog
            d1.setLocation(200, 200);

            String finalId = id;
            d1.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent me)
                {
                    if (me.getClickCount() == 0) {
                        int x = me.getX();
                        int y = me.getY();
                        MouseAction action = new MouseAction(currentId, finalId, (int)((float)x*ratioXValue), (int)((float)y*ratioYValue), 0);
                        System.out.println("X: " + x + "\nY: " + y);
                        System.out.println("Screen x: " + (int)((float)x*ratioXValue) + "\nScreen y: "+ (int)((float)y*ratioYValue));
                        try {
                            remote.addMouseAction(action);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        int x = me.getX();
                        int y = me.getY();
                        for (int i = 0; i < me.getClickCount(); i++){
                            MouseAction action = new MouseAction(currentId, finalId, x, y,1);
                            MouseAction action1 = new MouseAction(currentId, finalId, x, y, 2);
                            try {
                                remote.addMouseAction(action);
                                remote.addMouseAction(action1);
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            });
            d1.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    KeyAction action = new KeyAction(currentId, currentConnectPCId, e.getKeyCode(),0);
                    try {
                        System.out.println(e.getKeyCode() + " - " + e.getKeyChar());
                        remote.addKeyAction(action);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    KeyAction action = new KeyAction(currentId, currentConnectPCId, e.getKeyCode(),1);
                    try {
                        System.out.println(e.getKeyCode() + " - " + e.getKeyChar());
                        remote.addKeyAction(action);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
            d1.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        PCInfo pcInfo = new PCInfo(currentId, currentPassword, InetAddress.getLocalHost().getHostAddress());
                        remote.removeConnection(pcInfo);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }

                }
            });

            d1.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

            // set visibility of dialog
            d1.setVisible(true);

        }

    }

    public static void update(byte[] imageData)
    {
        ImageIcon imageIcon = new ImageIcon(imageData);
        Image image = imageIcon.getImage();
        int width= image.getWidth(null);
        int height = image.getHeight(null);
        String heightValue = (float)height*((float)1024/(float)width) + "";

        if(width > 1024 || height > 768) {
            ratioXValue = (float)width/(float)1024;
            ratioYValue = (float)height/Float.valueOf(heightValue);
            image = image.getScaledInstance(1024, Integer.valueOf(heightValue.replace(".0", "")), java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
        } else {
            imageIcon = new ImageIcon(image);
        }

        label.setIcon(imageIcon);
        d1.repaint();
        d1.getContentPane().repaint();
    }
}