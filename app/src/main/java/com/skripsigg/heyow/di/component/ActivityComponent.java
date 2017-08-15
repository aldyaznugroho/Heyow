package com.skripsigg.heyow.di.component;

import com.skripsigg.heyow.di.module.ActivityModule;
import com.skripsigg.heyow.di.module.GoogleAuthModule;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.ui.addmatch.AddNewMatchActivity;
import com.skripsigg.heyow.ui.addmatch.step1.AddMatchStepOneFragment;
import com.skripsigg.heyow.ui.addmatch.step2.AddMatchStepTwoFragment;
import com.skripsigg.heyow.ui.addmatch.step3.AddMatchStepThreeFragment;
import com.skripsigg.heyow.ui.auth.AuthActivity;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchActivity;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchFragment;
import com.skripsigg.heyow.ui.home.HomeActivity;
import com.skripsigg.heyow.ui.interest.InterestTabFragment;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchActivity;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchFragment;
import com.skripsigg.heyow.ui.matchchat.MatchChatActivity;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailActivity;
import com.skripsigg.heyow.ui.nearme.NearMeTabFragment;
import com.skripsigg.heyow.ui.otheruser.OtherUserActivity;
import com.skripsigg.heyow.ui.searchresults.SearchResultsActivity;
import com.skripsigg.heyow.ui.selectinterest.SelectInterestActivity;
import com.skripsigg.heyow.ui.selectradius.SelectRadiusActivity;
import com.skripsigg.heyow.ui.splash.SplashActivity;
import com.skripsigg.heyow.ui.userprofile.UserProfileActivity;

import dagger.Component;

/**
 * Created by Aldyaz on 5/8/2017.
 */

@ActivityScope
@Component(modules =
        {ActivityModule.class,
        GoogleAuthModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(HomeActivity homeActivity);
    void inject(SplashActivity splashActivity);
    void inject(AuthActivity authActivity);
    void inject(UserProfileActivity userProfileActivity);
    void inject(CreatedMatchActivity createdMatchActivity);
    void inject(JoinedMatchActivity joinedMatchActivity);
    void inject(MatchDetailActivity matchDetailActivity);
    void inject(OtherUserActivity otherUserActivity);
    void inject(SelectRadiusActivity selectRadiusActivity);
    void inject(SelectInterestActivity selectInterestActivity);
    void inject(AddNewMatchActivity addNewMatchActivity);
    void inject(MatchChatActivity matchChatActivity);
    void inject(SearchResultsActivity searchResultsActivity);

    void inject(NearMeTabFragment nearMeTabFragment);
    void inject(InterestTabFragment interestTabFragment);
    void inject(CreatedMatchFragment createdMatchFragment);
    void inject(JoinedMatchFragment joinedMatchFragment);
    void inject(AddMatchStepOneFragment stepOneFragment);
    void inject(AddMatchStepTwoFragment stepTwoFragment);
    void inject(AddMatchStepThreeFragment stepThreeFragment);
}
