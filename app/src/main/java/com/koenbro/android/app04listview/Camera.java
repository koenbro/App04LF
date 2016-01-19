package com.koenbro.android.app04listview;

/**
 * Created by laszlo on 10/24/14.
 */
public class Camera {
    private long id;
    private String cameraName;
    private int bellowsMin;
    private int bellowsMax;

    public Camera() {}

    public Camera(String cameraName, int bellowsMin, int bellowsMax) {
        this.cameraName = cameraName;
        this.bellowsMin = bellowsMin;
        this.bellowsMax = bellowsMax;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public int getBellowsMin() {
        return bellowsMin;
    }

    public void setBellowsMin(int bellowsMin) {
        this.bellowsMin = bellowsMin;
    }

    public int getBellowsMax() {
        return bellowsMax;
    }

    public void setBellowsMax(int bellowsMax) {
        this.bellowsMax = bellowsMax;
    }

    @Override
    public String toString() {
        return "Camera ["+ cameraName  + "; id=" + id + "]";
    }
    public String toStringShort() {
        return cameraName  + " (id=" + id + ")";
    }
}
