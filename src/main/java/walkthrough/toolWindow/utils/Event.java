package walkthrough.toolWindow.utils;

public class Event {

    public final long timestamp;
    public final String msg;

    public Event(String msg) {
        this.timestamp = System.currentTimeMillis();
        this.msg = msg;
    }

}
