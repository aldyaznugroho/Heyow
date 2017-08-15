package com.skripsigg.heyow.ui.matchchat;

import com.skripsigg.heyow.data.DataManager;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.models.NotificationBodyPayload;
import com.skripsigg.heyow.models.NotificationResponse;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Aldyaz on 6/6/2017.
 */

@ActivityScope
public class MatchChatPresenter<V extends MatchChatMvpView> extends BasePresenter<V>
        implements MatchChatMvpPresenter<V> {
    @Inject
    public MatchChatPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public UserModel getUserPreferences() {
        return getDataManager().getPreferenceHelper().getSharedPrefUser();
    }
}
