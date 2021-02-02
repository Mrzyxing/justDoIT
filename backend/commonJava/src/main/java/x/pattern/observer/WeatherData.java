package x.pattern.observer;

import java.util.Observable;

/**
 * Created by zyxing on 2020/3/30.
 */
public class WeatherData extends Observable {

    public void dataChanged() {
        setChanged();
        notifyObservers();
    }
}
