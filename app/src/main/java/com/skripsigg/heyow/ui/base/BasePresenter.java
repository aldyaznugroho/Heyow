package com.skripsigg.heyow.ui.base;

import com.skripsigg.heyow.data.DataManager;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Aldyaz on 5/9/2017.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final DataManager dataManager;
    private final CompositeDisposable compositeDisposable;
    private V mvpView;

    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mvpView = mvpView;
    }

    @Override
    public void onDetach() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        mvpView = null;
    }

    public V getMvpView() {
        return mvpView;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public boolean isViewAttached() {
        return mvpView != null;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
