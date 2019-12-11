package teamviewer.entity;

public class MouseAction extends Action {
    private int x;
    private int y;
    // o - di chuyển chuột
    // 1 - nhấn
    // 2 - nhả chuột
    private int action;

    public MouseAction(String fromId, String toId, int x, int y, int action) {
        super(fromId, toId);
        this.x = x;
        this.y = y;
        this.action = action;
    }

    public MouseAction() {
    }

    public MouseAction(int x, int y, int action) {
        this.x = x;
        this.y = y;
        this.action = action;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
