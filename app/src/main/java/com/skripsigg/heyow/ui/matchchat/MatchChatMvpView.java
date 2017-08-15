package com.skripsigg.heyow.ui.matchchat;

import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.MvpView;

/**
 * Created by Aldyaz on 6/6/2017.
 */

public interface MatchChatMvpView extends MvpView {
    MatchDetailResponse getMatchDetailExtra();
}
