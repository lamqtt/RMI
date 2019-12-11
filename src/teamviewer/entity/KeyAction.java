package teamviewer.entity;

public class KeyAction extends Action {
    private int keycode;
    // 0 - press
    // 1 - release
    private int action;

    public KeyAction(String fromId, String toId) {
        super(fromId, toId);
    }

    public KeyAction(String fromId, String toId, int keycode, int action) {
        super(fromId, toId);
        this.keycode = keycode;
        this.action = action;
    }

    public KeyAction() {
    }

    public KeyAction(int keycode, int action) {
        this.keycode = keycode;
        this.action = action;
    }

    public int getKeycode() {
        return keycode;
    }

    public void setKeycode(int keycode) {
        this.keycode = keycode;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

}
