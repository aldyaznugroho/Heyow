package com.skripsigg.heyow.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.skripsigg.heyow.di.qualifier.ActivityContext;
import com.skripsigg.heyow.di.scope.ActivityScope;
import com.skripsigg.heyow.ui.addmatch.AddMatchMvpPresenter;
import com.skripsigg.heyow.ui.addmatch.AddMatchMvpView;
import com.skripsigg.heyow.ui.addmatch.AddMatchPresenter;
import com.skripsigg.heyow.ui.addmatch.step1.StepOneMvpPresenter;
import com.skripsigg.heyow.ui.addmatch.step1.StepOneMvpView;
import com.skripsigg.heyow.ui.addmatch.step1.StepOnePresenter;
import com.skripsigg.heyow.ui.addmatch.step2.StepTwoMvpPresenter;
import com.skripsigg.heyow.ui.addmatch.step2.StepTwoMvpView;
import com.skripsigg.heyow.ui.addmatch.step2.StepTwoPresenter;
import com.skripsigg.heyow.ui.addmatch.step3.StepThreeMvpPresenter;
import com.skripsigg.heyow.ui.addmatch.step3.StepThreeMvpView;
import com.skripsigg.heyow.ui.addmatch.step3.StepThreePresenter;
import com.skripsigg.heyow.ui.auth.AuthMvpPresenter;
import com.skripsigg.heyow.ui.auth.AuthMvpView;
import com.skripsigg.heyow.ui.auth.AuthPresenter;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchFragmentPresenter;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchMvpFragmentPresenter;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchMvpFragmentView;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchMvpPresenter;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchMvpView;
import com.skripsigg.heyow.ui.createdmatch.CreatedMatchPresenter;
import com.skripsigg.heyow.ui.home.HomeMvpPresenter;
import com.skripsigg.heyow.ui.home.HomeMvpView;
import com.skripsigg.heyow.ui.home.HomePresenter;
import com.skripsigg.heyow.ui.interest.InterestMvpPresenter;
import com.skripsigg.heyow.ui.interest.InterestMvpView;
import com.skripsigg.heyow.ui.interest.InterestPresenter;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchFragmentPresenter;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchMvpFragmentPresenter;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchMvpFragmentView;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchMvpPresenter;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchMvpView;
import com.skripsigg.heyow.ui.joinedmatch.JoinedMatchPresenter;
import com.skripsigg.heyow.ui.matchchat.MatchChatMvpPresenter;
import com.skripsigg.heyow.ui.matchchat.MatchChatMvpView;
import com.skripsigg.heyow.ui.matchchat.MatchChatPresenter;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailMvpPresenter;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailMvpView;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailPresenter;
import com.skripsigg.heyow.ui.nearme.NearMeMvpPresenter;
import com.skripsigg.heyow.ui.nearme.NearMeMvpView;
import com.skripsigg.heyow.ui.nearme.NearMePresenter;
import com.skripsigg.heyow.ui.otheruser.OtherUserMvpPresenter;
import com.skripsigg.heyow.ui.otheruser.OtherUserMvpView;
import com.skripsigg.heyow.ui.otheruser.OtherUserPresenter;
import com.skripsigg.heyow.ui.searchresults.SearchResultsMvpPresenter;
import com.skripsigg.heyow.ui.searchresults.SearchResultsMvpView;
import com.skripsigg.heyow.ui.searchresults.SearchResultsPresenter;
import com.skripsigg.heyow.ui.selectinterest.SelectInterestMvpPresenter;
import com.skripsigg.heyow.ui.selectinterest.SelectInterestMvpView;
import com.skripsigg.heyow.ui.selectinterest.SelectInterestPresenter;
import com.skripsigg.heyow.ui.selectradius.SelectRadiusMvpPresenter;
import com.skripsigg.heyow.ui.selectradius.SelectRadiusMvpView;
import com.skripsigg.heyow.ui.selectradius.SelectRadiusPresenter;
import com.skripsigg.heyow.ui.splash.SplashMvpPresenter;
import com.skripsigg.heyow.ui.splash.SplashMvpView;
import com.skripsigg.heyow.ui.splash.SplashPresenter;
import com.skripsigg.heyow.ui.userprofile.UserProfileMvpPresenter;
import com.skripsigg.heyow.ui.userprofile.UserProfileMvpView;
import com.skripsigg.heyow.ui.userprofile.UserProfilePresenter;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Aldyaz on 5/8/2017.
 */

@Module
public class ActivityModule {
    private AppCompatActivity appCompatActivity;

    public ActivityModule(AppCompatActivity activity) {
        this.appCompatActivity = activity;
    }

    @ActivityScope
    @ActivityContext
    @Provides
    Context provideContext() {
        return appCompatActivity;
    }

    @ActivityScope
    @ActivityContext
    @Provides
    Activity provideActivity() {
        return appCompatActivity;
    }

    @ActivityScope
    @ActivityContext
    @Provides
    AppCompatActivity provideAppCompatActivity() {
        return appCompatActivity;
    }

    @ActivityScope
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @ActivityScope
    @Provides
    HomeMvpPresenter<HomeMvpView> provideHomePresenter(HomePresenter<HomeMvpView> homePresenter) {
        return homePresenter;
    }

    @ActivityScope
    @Provides
    SplashMvpPresenter<SplashMvpView> provideSplashPresenter(
            SplashPresenter<SplashMvpView> splashPresenter) {
        return splashPresenter;
    }

    @ActivityScope
    @Provides
    AuthMvpPresenter<AuthMvpView> provideAuthPresenter(AuthPresenter<AuthMvpView> authPresenter) {
        return authPresenter;
    }

    @ActivityScope
    @Provides
    UserProfileMvpPresenter<UserProfileMvpView> provideUserProfilePresenter(
            UserProfilePresenter<UserProfileMvpView> userProfilePresenter) {
        return userProfilePresenter;
    }

    @ActivityScope
    @Provides
    CreatedMatchMvpPresenter<CreatedMatchMvpView> provideCreatedMatchPresenter(
            CreatedMatchPresenter<CreatedMatchMvpView> createdMatchPresenter) {
        return createdMatchPresenter;
    }

    @ActivityScope
    @Provides
    JoinedMatchMvpPresenter<JoinedMatchMvpView> provideJoinedMatchPresenter(
            JoinedMatchPresenter<JoinedMatchMvpView> joinedMatchPresenter) {
        return joinedMatchPresenter;
    }

    @ActivityScope
    @Provides
    MatchDetailMvpPresenter<MatchDetailMvpView> provideMatchDetailPresenter(
            MatchDetailPresenter<MatchDetailMvpView> matchDetailPresenter) {
        return matchDetailPresenter;
    }

    @ActivityScope
    @Provides
    OtherUserMvpPresenter<OtherUserMvpView> provideOtherUserPresenter(
            OtherUserPresenter<OtherUserMvpView> otherUserPresenter) {
        return otherUserPresenter;
    }

    @ActivityScope
    @Provides
    SelectRadiusMvpPresenter<SelectRadiusMvpView> provideSelectRadiusPresenter(
            SelectRadiusPresenter<SelectRadiusMvpView> selectRadiusPresenter) {
        return selectRadiusPresenter;
    }

    @ActivityScope
    @Provides
    SelectInterestMvpPresenter<SelectInterestMvpView> provideSelectInterestPresenter(
            SelectInterestPresenter<SelectInterestMvpView> selectInterestPresenter) {
        return selectInterestPresenter;
    }

    @ActivityScope
    @Provides
    CreatedMatchMvpFragmentPresenter<CreatedMatchMvpFragmentView> provideCreatedMatchFragmentPresenter(
            CreatedMatchFragmentPresenter<CreatedMatchMvpFragmentView> createdMatchFragmentPresenter) {
        return createdMatchFragmentPresenter;
    }

    @ActivityScope
    @Provides
    JoinedMatchMvpFragmentPresenter<JoinedMatchMvpFragmentView> provideJoinedMatchFragmentPresenter(
            JoinedMatchFragmentPresenter<JoinedMatchMvpFragmentView> joinedMatchFragmentPresenter) {
        return joinedMatchFragmentPresenter;
    }

    @ActivityScope
    @Provides
    AddMatchMvpPresenter<AddMatchMvpView> provideAddMatchPresenter(
            AddMatchPresenter<AddMatchMvpView> addMatchPresenter) {
        return addMatchPresenter;
    }

    @ActivityScope
    @Provides
    StepOneMvpPresenter<StepOneMvpView> provideStepOnePresenter(
            StepOnePresenter<StepOneMvpView> stepOnePresenter) {
        return stepOnePresenter;
    }

    @ActivityScope
    @Provides
    StepTwoMvpPresenter<StepTwoMvpView> provideStepTwoPresenter(
            StepTwoPresenter<StepTwoMvpView> stepTwoPresenter) {
        return stepTwoPresenter;
    }

    @ActivityScope
    @Provides
    StepThreeMvpPresenter<StepThreeMvpView> provideStepThreePresenter(
            StepThreePresenter<StepThreeMvpView> stepThreePresenter) {
        return stepThreePresenter;
    }

    @ActivityScope
    @Provides
    NearMeMvpPresenter<NearMeMvpView> provideNearMePresenter(
            NearMePresenter<NearMeMvpView> nearMePresenter) {
        return nearMePresenter;
    }

    @ActivityScope
    @Provides
    InterestMvpPresenter<InterestMvpView> provideInterestPresenter(
            InterestPresenter<InterestMvpView> interestPresenter) {
        return interestPresenter;
    }

    @ActivityScope
    @Provides
    MatchChatMvpPresenter<MatchChatMvpView> provideMatchChatPresenter(
            MatchChatPresenter<MatchChatMvpView> matchChatPresenter) {
        return matchChatPresenter;
    }

    @ActivityScope
    @Provides
    SearchResultsMvpPresenter<SearchResultsMvpView> provideSearchResultPresenter(
            SearchResultsPresenter<SearchResultsMvpView> searchResultsPresenter) {
        return searchResultsPresenter;
    }
}
