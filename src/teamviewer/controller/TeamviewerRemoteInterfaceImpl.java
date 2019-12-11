package teamviewer.controller;

import teamviewer.entity.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamviewerRemoteInterfaceImpl implements TeamviewerRemoteInterface {
    private List<PCRemoteAccessInfo> listPCRemoteAccessInfo = new ArrayList();
    private List<Action> listAction = new ArrayList<>();
    private List<PCInfo> listPCInfo = new ArrayList<>();
    private List<ImageSent> listImageShare = new ArrayList<>();

    @Override
    public void printMsg(String msg) throws RemoteException {
        System.out.println("Client said: " + msg);
    }

    @Override
    public List<Action> getListAction() throws RemoteException {
        return listAction;
    }

    @Override
    public Map<Integer,Action> getListAction(PCInfo pcInfo) throws RemoteException {
        Map<Integer,Action> listCurrentPCAction = new HashMap<>();
        int count = 0;
        for (Action action: listAction){
            System.out.println("to ID : " + action.getToId());
            System.out.println("pc ID : " + pcInfo.getId());
            if (action.getToId().equals(pcInfo.getId())){
                listCurrentPCAction.put(count, action);
                System.out.println("create new List");
            }
            count++;
        }
        return listCurrentPCAction;
    }

    @Override
    public void deleteAction(int actionIndex) throws RemoteException {
        listAction.remove(actionIndex);
    }

    public PCInfo checkExistPCbyAddress(String ipAddress) throws RemoteException{
        for (PCInfo pc : listPCInfo){
            if (pc.getAddress().equals(ipAddress)){
                System.out.println(pc.toString());
                return pc;
            }
        }
        return null;
    }

    @Override
    public void addNewPC(PCInfo pc) throws RemoteException {
        boolean exist = false;
        for (PCInfo pcFromList : listPCInfo){
            if (pcFromList.getAddress() == pc.getAddress()){
                exist = true;
            }
        }
        if (!exist) {
            listPCInfo.add(pc);
        }
    }

    @Override
    public void connectToPC(String currentId, String toId, String password) throws RemoteException {
        for (PCInfo pc : listPCInfo){
            if (pc.getId().equals(toId) && pc.getPassword().equals(password)){
                System.out.println("Success");
                PCRemoteAccessInfo pcRemoteAccessInfo = new PCRemoteAccessInfo(currentId, toId, password);
                listPCRemoteAccessInfo.add(pcRemoteAccessInfo);
            }
        }
    }

    @Override
    public void removeConnection(PCInfo pc) throws RemoteException {
        for (PCRemoteAccessInfo pcRemoteAccessInfo :listPCRemoteAccessInfo){
            if (pcRemoteAccessInfo.getFromId().equals(pc.getId())){
                listPCRemoteAccessInfo.remove(pcRemoteAccessInfo);
            }
        }
    }

    @Override
    public boolean isExistPC(String id) throws RemoteException {
        for (PCInfo pc : listPCInfo){
            if (pc.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCorrectPC(String id, String password) throws RemoteException {
        for (PCInfo pc : listPCInfo){
            if (pc.getId().equals(id) && pc.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void addMouseAction(MouseAction action) throws RemoteException {
        for (PCRemoteAccessInfo remoteInfo:listPCRemoteAccessInfo) {
            if ( remoteInfo.getFromId().equals(action.getFromId()) && remoteInfo.getToId().equals(action.getToId())){
                listAction.add(action);
            }
        }
    }

    @Override
    public void sendImage(ImageSent imageSent) throws RemoteException {
        listImageShare.add(imageSent);
    }

    @Override
    public byte[] getImage(PCRemoteAccessInfo pcRemoteAccessInfo) throws RemoteException {
        int count = 0;
        for (ImageSent image : listImageShare){
            if (image.getFromId().equals(pcRemoteAccessInfo.getToId())){
                byte[] imageData = image.getData();
                listImageShare.remove(count);
                return imageData;
            }
            count++;
        }
        return null;
    }

    @Override
    public boolean isSharedScreen(PCInfo pc) throws RemoteException {
        for (PCRemoteAccessInfo pcShareInfo: listPCRemoteAccessInfo){
            if (pcShareInfo.getToId().equals(pc.getId())){
                return true;
            }
        }
        return false;
    }

    @Override
    public PCRemoteAccessInfo getCurrentConnectionInfo(String currentId) throws RemoteException {
        for (PCRemoteAccessInfo pcRemoteAccessInfo: listPCRemoteAccessInfo) {
            if (pcRemoteAccessInfo.getFromId().equals(currentId)){
                return pcRemoteAccessInfo;
            }
        }
        return null;
    }

    @Override
    public void addKeyAction(KeyAction action) throws RemoteException {
        listAction.add(action);
    }
}
