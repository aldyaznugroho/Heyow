package com.skripsigg.heyow.ui.addmatch.step3;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.ui.base.BaseFragment;
import com.skripsigg.heyow.ui.matchdetail.MatchDetailActivity;
import com.skripsigg.heyow.utils.others.Constants;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMatchStepThreeFragment extends BaseFragment implements StepThreeMvpView, BlockingStep {

    @Inject
    StepThreeMvpPresenter<StepThreeMvpView> presenter;

    @BindView(R.id.add_match_desc_editor)
    EditText descEditText;

    public static AddMatchStepThreeFragment getInstance() {
        return new AddMatchStepThreeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_match_step_three, container, false);

        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this, rootView));
        presenter.onAttach(this);
        init();

        return rootView;
    }

    private void init() {
        initDescriptionText();
    }

    private void initDescriptionText() {
    }

    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        String matchDesc = descEditText.getText().toString();

        if (TextUtils.isEmpty(matchDesc)) {
            showOnErrorToast("Description must be filled");
        } else if (matchDesc.trim().length() < 50) {
            showOnErrorToast("Description must be at least 80 characters in length");
        } else {
            showLoading();

            Map<String, String> formFields = new HashMap<>();
            formFields.put(Constants.POST_MATCH_USER_ID, presenter.getUserIdPref());
            formFields.put(Constants.POST_MATCH_CATEGORY, presenter.getMatchFormPref().getMatchCategory());
            formFields.put(Constants.POST_MATCH_TITLE, presenter.getMatchFormPref().getMatchTitle());
            formFields.put(Constants.POST_MATCH_DETAIL, matchDesc);
            formFields.put(Constants.POST_MATCH_LOCATION, presenter.getMatchFormPref().getMatchLocation());
            formFields.put(Constants.POST_MATCH_VENUE, presenter.getMatchFormPref().getMatchVenue());
            formFields.put(Constants.POST_MATCH_DATE, presenter.getMatchFormPref().getMatchDate());
            formFields.put(Constants.POST_MATCH_TIME, presenter.getMatchFormPref().getMatchTime());
            formFields.put(Constants.POST_MATCH_LATITUDE, presenter.getMatchFormPref().getMatchLatitude());
            formFields.put(Constants.POST_MATCH_LONGITUDE, presenter.getMatchFormPref().getMatchLongitude());
            formFields.put(Constants.POST_MATCH_QUOTA_MAX, presenter.getMatchFormPref().getMatchQuota().getQuotaMax());
            formFields.put(Constants.POST_MATCH_IMAGE, presenter.getMatchFormPref().getMatchImage());

            presenter.loadCreateMatchApiService(formFields);
        }
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }

    @Override
    public void openMatchDetailActivity(String matchId) {
        Bundle matchDetailBundle = new Bundle();
        matchDetailBundle.putString(Constants.KEY_MATCH_ID, matchId);
        Intent matchDetailIntent = new Intent(getActivity(), MatchDetailActivity.class);
        matchDetailIntent.putExtras(matchDetailBundle);
        startActivity(matchDetailIntent);
        getActivity().finish();
    }
}
