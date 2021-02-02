package x.pattern.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by zyxing on 2020/3/30.
 */
public class SomeDisplay implements Observer {

    /**
     * 观察者有pull和push两种模式
     * push需要notifyObservers传递关心的数据
     * pull需要自己在update中主动去拉数据
     *
     * @param o the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof WeatherData){
            WeatherData weatherData = (WeatherData) o;
            // o.get some data
        }
    }
}
