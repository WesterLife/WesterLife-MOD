package fr.yan36.westerlife.common.objects;

public class Notification {
    public String title;
    public String message;
    public long time;
    public int popupColor;

    public Notification(String title, String message, int popupColor, long time) {
        this.title = title;
        this.message = message;
        this.popupColor = popupColor;
        this.time = time;
    }

    public Notification(String title, String message, long time) {
        this.title = title;
        this.message = message;
        this.popupColor = 0xF14902;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPopupColor() {
        return popupColor;
    }

    public void setPopupColor(int popupColor) {
        this.popupColor = popupColor;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
