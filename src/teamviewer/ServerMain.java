package teamviewer;

import teamviewer.controller.TeamviewerRemoteInterface;
import teamviewer.controller.TeamviewerRemoteInterfaceImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {
    public static void main(String[] args) {
        try {
            TeamviewerRemoteInterface teamviewerRemoteInterface = new TeamviewerRemoteInterfaceImpl();
            Registry registry = LocateRegistry.createRegistry(3030);

            TeamviewerRemoteInterface stub = (TeamviewerRemoteInterface) UnicastRemoteObject.exportObject(teamviewerRemoteInterface, 3030);
            registry.rebind("teamviewerRemote", stub);
            System.out.println("Server is ready to connect!");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
