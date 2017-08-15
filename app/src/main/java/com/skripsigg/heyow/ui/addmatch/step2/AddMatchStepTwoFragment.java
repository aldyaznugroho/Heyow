package com.skripsigg.heyow.ui.addmatch.step2;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.ui.base.BaseFragment;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMatchStepTwoFragment extends BaseFragment implements StepTwoMvpView, BlockingStep {

    private final int PLACE_PICKER_REQUEST = 225;

    @Inject
    StepTwoMvpPresenter<StepTwoMvpView> presenter;

    @BindView(R.id.create_match_venue_name) EditText venueNameEditText;
    @BindView(R.id.create_match_venue_address) EditText venueAddressEditText;
    @BindView(R.id.create_match_date_edit_text) EditText dateEditText;
    @BindView(R.id.create_match_time_edit_text) EditText timeEditText;
    @BindView(R.id.create_match_max_people) EditText maxPeopleEditText;

    private String tempLat, tempLng;

    public static AddMatchStepTwoFragment getInstance() {
        return new AddMatchStepTwoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_match_step_two, container, false);

        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this, rootView));
        presenter.onAttach(this);
        init();

        return rootView;
    }

    private void init() {
        initVenueAddressEditText();
        initDateEditText();
        initTimeEditText();
    }

    private void initVenueAddressEditText() {
        venueAddressEditText.setFocusable(false);
        venueAddressEditText.setClickable(true);
    }

    private void initDateEditText() {
        dateEditText.setFocusable(false);
        dateEditText.setClickable(true);
    }

    private void initTimeEditText() {
        timeEditText.setFocusable(false);
        timeEditText.setClickable(true);
    }

    @OnClick(R.id.create_match_venue_address)
    public void venueAddressEditTextClick() {
        openPlacePickerIntent();
    }

    @OnClick(R.id.create_match_date_edit_text)
    public void dateEditTextClick() {
        showDatePickerDialog();
    }

    @OnClick(R.id.create_match_time_edit_text)
    public void timeEditTextClick() {
        showTimePickerDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place placeResults = PlacePicker.getPlace(getContext(), data);
                String placeAddress = placeResults.getAddress().toString();
                tempLat = String.valueOf(placeResults.getLatLng().latitude);
                tempLng = String.valueOf(placeResults.getLatLng().longitude);
                venueAddressEditText.setText(placeAddress);
            }
        }
    }

    private void openPlacePickerIntent() {
        PlacePicker.IntentBuilder placePickerIntentBuilder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(placePickerIntentBuilder.build(getActivity()), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void showDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Launch DatePicker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        dateEditText.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                    }
                },
                currentYear, currentMonth, currentDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        // Get current time
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Launch TimePicker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeEditText.setText(
                                String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
                    }
                }, currentHour, currentMinute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
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
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        String matchVenueName = venueNameEditText.getText().toString();
        String matchVenueAddress = venueAddressEditText.getText().toString();
        String matchDate = dateEditText.getText().toString();
        String matchTime = timeEditText.getText().toString();
        String matchMaxPeople = maxPeopleEditText.getText().toString();

        if (TextUtils.isEmpty(matchVenueName)) {
            venueNameEditText.setError(getString(R.string.must_be_filled));
        } else if (TextUtils.isEmpty(matchVenueAddress) && tempLat == null && tempLng == null) {
            venueAddressEditText.setError(getString(R.string.must_be_filled));
        } else if (TextUtils.isEmpty(matchDate)) {
            dateEditText.setError(getString(R.string.must_be_filled));
        } else if (TextUtils.isEmpty(matchTime)) {
            timeEditText.setError(getString(R.string.must_be_filled));
        } else if (TextUtils.isEmpty(matchMaxPeople)) {
            maxPeopleEditText.setError(getString(R.string.must_be_filled));
        } else if (Integer.parseInt(matchMaxPeople) < 2 && !TextUtils.isEmpty(matchMaxPeople)){
            showOnErrorToast("Minimum quota must be 2 people");
        } else {
            showLoading();
            presenter.saveFormToPreferences(
                    matchVenueName,
                    matchVenueAddress,
                    tempLat,
                    tempLng,
                    matchDate,
                    matchTime,
                    matchMaxPeople);
            presenter.saveFormDelayProcessing(new Consumer<Long>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                    callback.goToNextStep();
                    hideLoading();
                }
            });
        }
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        callback.goToPrevStep();
    }
}
