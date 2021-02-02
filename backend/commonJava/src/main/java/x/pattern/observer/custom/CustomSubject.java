package x.pattern.observer.custom;

/**
 * Created by zyxing on 2020/3/30.
 */
public interface CustomSubject {

    void registerObserver(CustomObserver o);

    void removeObserver(CustomObserver o);

    void notifyObservers(String msg);

}
