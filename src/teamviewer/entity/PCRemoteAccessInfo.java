package teamviewer.entity;

import java.io.Serializable;

public class PCRemoteAccessInfo implements Serializable {
    // id máy điều khiển
    private String fromId;

    // máy share screen
    private String toId;
    private String password;

    public PCRemoteAccessInfo() {
    }

    public PCRemoteAccessInfo(String fromId, String toId, String password) {
        this.fromId = fromId;
        this.toId = toId;
        this.password = password;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
