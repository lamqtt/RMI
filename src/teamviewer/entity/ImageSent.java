package teamviewer.entity;

import java.io.Serializable;

public class ImageSent implements Serializable {
    private String fromId;
    private byte[] data;
    private int height;
    private int width;

    public ImageSent() {
    }

    public ImageSent(String fromId, byte[] data) {
        this.fromId = fromId;
        this.data = data;
    }

    public ImageSent(String fromId, byte[] data, int height, int width) {
        this.fromId = fromId;
        this.data = data;
        this.height = height;
        this.width = width;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
