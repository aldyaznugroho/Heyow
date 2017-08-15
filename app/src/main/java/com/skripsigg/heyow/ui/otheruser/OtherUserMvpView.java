package com.skripsigg.heyow.ui.otheruser;

import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.ui.base.MvpView;

/**
 * Created by Aldyaz on 5/22/2017.
 */

public interface OtherUserMvpView extends MvpView {
    UserModel getJoinedUserParcelable();
}
