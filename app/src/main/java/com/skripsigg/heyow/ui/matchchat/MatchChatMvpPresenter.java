package com.skripsigg.heyow.ui.matchchat;

import com.skripsigg.heyow.models.NotificationBodyPayload;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.MvpPresenter;

/**
 * Created by Aldyaz on 6/6/2017.
 */

public interface MatchChatMvpPresenter<V extends MatchChatMvpView> extends MvpPresenter<V> {
    UserModel getUserPreferences();
}
