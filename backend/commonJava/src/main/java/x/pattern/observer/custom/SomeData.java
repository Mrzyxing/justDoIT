package x.pattern.observer.custom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyxing on 2020/3/30.
 */
public class SomeData implements CustomSubject {

    private List<CustomObserver> observers = new ArrayList<>();

    public void dataChanged(String msg) {
        notifyObservers(msg);
    }


    @Override
    public void registerObserver(CustomObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(CustomObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String msg) {
        observers.forEach(o -> o.update(msg));
    }
}
