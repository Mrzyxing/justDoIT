package x.pattern.observer.custom;

/**
 * Created by zyxing on 2020/3/30.
 */
public class Main {

    public static void main(String[] args) {
        SomeData someData = new SomeData();
        CustomDisplay a = new CustomDisplay("A");
        CustomDisplay b = new CustomDisplay("B");
        someData.registerObserver(a);
        someData.registerObserver(b);

        someData.dataChanged("change 1");
        someData.removeObserver(b);
        someData.dataChanged("change b");
    }
}
