package teamviewer.controller;

import teamviewer.entity.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface TeamviewerRemoteInterface extends Remote {
    void printMsg(String msg) throws RemoteException;
    Map<Integer,Action> getListAction(PCInfo pc) throws RemoteException;
    List<Action> getListAction() throws RemoteException;
    void deleteAction(int actionIndex) throws RemoteException;
    void addNewPC(PCInfo pc) throws RemoteException;
    void connectToPC(String currentId, String toId, String password) throws RemoteException;
    void removeConnection(PCInfo pc) throws RemoteException;
    PCInfo checkExistPCbyAddress(String ipAddress) throws RemoteException;
    boolean isExistPC(String id) throws RemoteException;
    boolean isCorrectPC(String id, String password) throws RemoteException;
    void addMouseAction(MouseAction action) throws RemoteException;
    void sendImage(ImageSent imageSent) throws RemoteException;
    byte[] getImage(PCRemoteAccessInfo pcRemoteAccessInfo) throws RemoteException;
    boolean isSharedScreen(PCInfo pc) throws RemoteException;
    PCRemoteAccessInfo getCurrentConnectionInfo(String id) throws RemoteException;
    void addKeyAction(KeyAction action) throws RemoteException;
}
