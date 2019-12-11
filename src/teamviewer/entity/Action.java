package teamviewer.entity;

import java.io.Serializable;

public class Action implements Serializable {

    private String fromId;
    private String toId;

    public Action() {
    }

    public Action(String fromId, String toId) {
        this.fromId = fromId;
        this.toId = toId;
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
}
