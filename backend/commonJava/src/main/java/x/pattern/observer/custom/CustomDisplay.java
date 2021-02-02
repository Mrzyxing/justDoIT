package x.pattern.observer.custom;

/**
 * Created by zyxing on 2020/3/30.
 */
public class CustomDisplay implements CustomObserver {

    private String displayName;

    public CustomDisplay(String a) {
        this.displayName = a;
    }

    @Override
    public void update(String msg) {
        System.out.println(this.displayName + " update " + msg);
    }
}
