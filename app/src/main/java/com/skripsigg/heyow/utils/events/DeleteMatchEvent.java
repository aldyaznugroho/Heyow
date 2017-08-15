package com.skripsigg.heyow.utils.events;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Aldyaz on 3/10/2017.
 */

public class DeleteMatchEvent {
    private static DeleteMatchEvent instance;

    private PublishSubject<String> subject = PublishSubject.create();

    public static DeleteMatchEvent instanceOf() {
        if (instance == null) {
            instance = new DeleteMatchEvent();
        }
        return instance;
    }

    public void setListener(String listener) {
        subject.onNext(listener);
    }

    public Observable<String> getStringObservable() {
        return subject;
    }
}