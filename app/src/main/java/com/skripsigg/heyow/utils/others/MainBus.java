package com.skripsigg.heyow.utils.others;

/**
 * Created by Aldyaz on 10/9/2016.
 */

public class MainBus extends RxEventBus {
    private static MainBus instance;

    public static MainBus getInstance() {
        if (instance == null) {
            instance = new MainBus();
        }
        return instance;
    }
}
