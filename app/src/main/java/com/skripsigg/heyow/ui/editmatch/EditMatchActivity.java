package com.skripsigg.heyow.ui.editmatch;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devspark.robototextview.widget.RobotoButton;
import com.devspark.robototextview.widget.RobotoEditText;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.models.MatchDetailResponse;
import com.skripsigg.heyow.models.UserModel;
import com.skripsigg.heyow.utils.apis.ApiAdapter;
import com.skripsigg.heyow.data.helper.heyow.HeyowApiService;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.utils.others.NetworkUtils;
import com.skripsigg.heyow.utils.others.SharedPreferenceCustom;
import com.skripsigg.heyow.utils.photos.CameraPhoto;
import com.skripsigg.heyow.utils.photos.GalleryPhoto;
import com.skripsigg.heyow.utils.photos.ImageBase64;
import com.skripsigg.heyow.utils.photos.ImageLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMatchActivity extends AppCompatActivity implements
        View.OnClickListener,
        Callback<MatchDetailResponse> {

    private final String TAG = getClass().getSimpleName();
    private final int CAMERA_REQUEST = 13323;
    private final int GALLERY_REQUEST = 22131;
    private final int PLACE_PICKER_REQUEST = 1;

    Toolbar toolbar;
    FrameLayout imageUploadLayout;
    LinearLayout placeHolderLayout;
    ImageView imagePreview;
    RobotoEditText matchTitleEditText,
            matchVenueNameEditText,
            matchVenueAddressEditText,
            matchDateEditText,
            matchTimeEditText,
            matchMaxPeopleEditText,
            matchDescriptionEditText;
    Spinner categorySpinner;
    RobotoButton editMatchButton;
    ProgressDialog progressDialog;

    private MatchDetailResponse matchDetailResult;
    private UserModel user;
    protected ArrayAdapter<CharSequence> categoryListAdapter;
    private CameraPhoto cameraPhoto;
    private GalleryPhoto galleryPhoto;

    private String matchSelectedImage, tempLat, tempLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_match);

        toolbar = (Toolbar) findViewById(R.id.edit_match_toolbar);
        imageUploadLayout = (FrameLayout) findViewById(R.id.match_image_upload_layout);
        placeHolderLayout = (LinearLayout) findViewById(R.id.image_upload_placeholder_layout);
        imagePreview = (ImageView) findViewById(R.id.match_upload_preview_image);
        categorySpinner = (Spinner) findViewById(R.id.edit_match_category_spinner);
        matchTitleEditText = (RobotoEditText) findViewById(R.id.edit_match_title);
        matchVenueNameEditText = (RobotoEditText) findViewById(R.id.edit_match_venue_name);
        matchVenueAddressEditText = (RobotoEditText) findViewById(R.id.edit_match_venue_address);
        matchDateEditText = (RobotoEditText) findViewById(R.id.edit_match_date_edit_text);
        matchTimeEditText = (RobotoEditText) findViewById(R.id.edit_match_time_edit_text);
        matchMaxPeopleEditText = (RobotoEditText) findViewById(R.id.edit_match_max_people);
        matchDescriptionEditText = (RobotoEditText) findViewById(R.id.edit_match_description);
        editMatchButton = (RobotoButton) findViewById(R.id.edit_match_button);

        setSupportActionBar(toolbar);

        user = SharedPreferenceCustom.getInstance(this).getSharedPrefUser();
        matchDetailResult = getIntent().getExtras().getParcelable(Constants.KEY_MATCH_RESPONSE_PARCELABLE);

        initImageUploader();
        initToolbar();
        initProgressDialog();
        initImageUploadLayout();
        initVenueAddressEditText();
        initCategorySpinner();
        initMatchDate();
        initMatchTime();
        initEditMatchButton();
        showForm();
    }

    private void initImageUploader() {
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.colorPrimary),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.dialog_please_wait));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }

    private void initImageUploadLayout() {
        imageUploadLayout.setOnClickListener(this);
    }

    private void initCategorySpinner() {
        categoryListAdapter = ArrayAdapter.createFromResource(this,
                R.array.match_category, android.R.layout.simple_spinner_item);
        categoryListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryListAdapter);
    }

    private void initVenueAddressEditText() {
        matchVenueAddressEditText.setFocusable(false);
        matchVenueAddressEditText.setClickable(true);
        matchVenueAddressEditText.setOnClickListener(this);
    }

    private void initMatchDate() {
        matchDateEditText.setFocusable(false);
        matchDateEditText.setClickable(true);
        matchDateEditText.setOnClickListener(this);
    }

    private void initMatchTime() {
        matchTimeEditText.setFocusable(false);
        matchTimeEditText.setClickable(true);
        matchTimeEditText.setOnClickListener(this);
    }

    private void initEditMatchButton() {
        editMatchButton.setOnClickListener(this);
    }

    private void showForm() {
        Glide.with(this)
                .load(matchDetailResult.getMatchImage())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .skipMemoryCache(true)
                .dontAnimate()
                .into(imagePreview);

        categorySpinner.setSelection(Integer.parseInt(matchDetailResult.getMatchCategory()));

        matchTitleEditText.setText(matchDetailResult.getMatchTitle());
        matchVenueNameEditText.setText(matchDetailResult.getMatchVenue());
        matchVenueAddressEditText.setText(matchDetailResult.getMatchLocation());
        matchDateEditText.setText(reformatDate(matchDetailResult.getMatchDate()));
        matchTimeEditText.setText(matchDetailResult.getMatchTime());
        matchMaxPeopleEditText.setText(matchDetailResult.getMatchQuota().getQuotaMax());
        matchDescriptionEditText.setText(matchDetailResult.getMatchDetail());

        matchSelectedImage = matchDetailResult.getMatchImage();

        tempLat = matchDetailResult.getMatchLatitude();
        tempLng = matchDetailResult.getMatchLongitude();
    }

    private String reformatDate(String matchDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = simpleDateFormat.parse(matchDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        simpleDateFormat = new SimpleDateFormat("yyyy-d-M", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {
                String photoPath = cameraPhoto.getPhotoPath();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).getBitmap();

                    // Test encode
                    Bitmap selectedBitmap = ImageLoader.init().from(photoPath).getBitmap();
                    String encodedBitmap = ImageBase64.encode(selectedBitmap);
                    Log.e(TAG, "Encoded : " + encodedBitmap);
                    matchSelectedImage = encodedBitmap;

                    imagePreview.setImageBitmap(bitmap);
                    placeHolderLayout.setVisibility(View.GONE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error while loading photos", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "Storage path: " + photoPath);
            }
        } else if (requestCode == GALLERY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).getBitmap();

                    // Test encode
                    Bitmap selectedBitmap = ImageLoader.init().from(photoPath).getBitmap();
                    String encodedBitmap = ImageBase64.encode(selectedBitmap);
                    Log.e(TAG, "Encoded : " + encodedBitmap);
                    matchSelectedImage = encodedBitmap;

                    imagePreview.setImageBitmap(bitmap);
                    placeHolderLayout.setVisibility(View.GONE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error while choosing photos", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place placeResults = PlacePicker.getPlace(getBaseContext(), data);
                String placeAddress = placeResults.getAddress().toString();
                tempLat = String.valueOf(placeResults.getLatLng().latitude);
                tempLng = String.valueOf(placeResults.getLatLng().longitude);
                matchVenueAddressEditText.setText(placeAddress);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == matchVenueAddressEditText) {
            openPlacePickerIntent();
        }

        // When image upload layout is clicking
        if (view == imageUploadLayout) {
            configureImageUploader();
        }

        // When match date edittext is clicking
        if (view == matchDateEditText) {
            showDatePickerDialog();
        }

        // When match time edittext is clicking
        if (view == matchTimeEditText) {
            showTimePickerDialog();
        }

        if (view == editMatchButton) {
            getMatchFormData();
        }
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    /**
     * Finish this activity
     */
    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    /**
     * Configure alert dialog for choosing camera or gallery upload
     */
    private void configureImageUploader() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog;

        builder.setMessage("Select upload");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openGalleryIntent();
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openCameraIntent();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    /**
     * Opening camera intent
     */
    private void openCameraIntent() {
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error while taking photos", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Opening gallery intent
     */
    private void openGalleryIntent() {
        startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
    }

    /**
     * Opening place picker intent
     */
    private void openPlacePickerIntent() {
        PlacePicker.IntentBuilder placePickerIntentBuilder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(placePickerIntentBuilder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Opening datePicker dialog
     */
    private void showDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Launch DatePicker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        matchDateEditText.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                    }
                },
                currentYear, currentMonth, currentDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);
        datePickerDialog.setTitle("Select Date");
        datePickerDialog.show();
    }

    /**
     * Opening timePicker dialog
     */
    private void showTimePickerDialog() {
        // Get current time
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        // Launch TimePicker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        matchTimeEditText.setText(
                                String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute));
                    }
                }, currentHour, currentMinute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    private void getMatchFormData() {
        String[] catList = getResources().getStringArray(R.array.match_category);

        String matchTitle = matchTitleEditText.getText().toString();
        String matchVenueName = matchVenueNameEditText.getText().toString();
        String matchVenueAddress = matchVenueAddressEditText.getText().toString();
        String matchDate = matchDateEditText.getText().toString();
        String matchTime = matchTimeEditText.getText().toString();
        String matchMaxPeople = matchMaxPeopleEditText.getText().toString();
        String matchDescription = matchDescriptionEditText.getText().toString();

        int categoryListPosition = categorySpinner.getSelectedItemPosition();

        if (matchSelectedImage == null) {
            Toast.makeText(this, "Please choose image", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(matchTitle)) {
            matchTitleEditText.setError(getString(R.string.must_be_filled));
        } else if (categoryListPosition == 0) {
            Toast.makeText(this, "Please choose category", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(matchVenueName)) {
            matchVenueNameEditText.setError(getString(R.string.must_be_filled));
        } else if (TextUtils.isEmpty(matchVenueAddress) && tempLat == null && tempLng == null) {
            matchVenueAddressEditText.setError(getString(R.string.must_be_filled));
        } else if (TextUtils.isEmpty(matchDate)) {
            matchDateEditText.setError(getString(R.string.must_be_filled));
        } else if (TextUtils.isEmpty(matchTime)) {
            matchTimeEditText.setError(getString(R.string.must_be_filled));
        } else if (TextUtils.isEmpty(matchMaxPeople)) {
            matchMaxPeopleEditText.setError(getString(R.string.must_be_filled));
        } else if(Integer.parseInt(matchMaxPeople) < 2) {
            Toast.makeText(this, "Minimal quota is 2", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(matchDescription)) {
            matchDescriptionEditText.setError(getString(R.string.must_be_filled));
        } else {
            // Process data here
            Map<String, String> formFields = new HashMap<>();
            formFields.put(Constants.PUT_MATCH_ID, matchDetailResult.getMatchId());
            formFields.put(Constants.POST_MATCH_CATEGORY, String.valueOf(categoryListPosition));
            formFields.put(Constants.POST_MATCH_TITLE, matchTitle);
            formFields.put(Constants.POST_MATCH_DETAIL, matchDescription);
            formFields.put(Constants.POST_MATCH_LOCATION, matchVenueAddress);
            formFields.put(Constants.POST_MATCH_VENUE, matchVenueName);
            formFields.put(Constants.POST_MATCH_DATE, matchDate);
            formFields.put(Constants.POST_MATCH_TIME, matchTime);
            formFields.put(Constants.POST_MATCH_LATITUDE, tempLat);
            formFields.put(Constants.POST_MATCH_LONGITUDE, tempLng);
            formFields.put(Constants.POST_MATCH_QUOTA_MAX, matchMaxPeople);
            formFields.put(Constants.POST_MATCH_IMAGE, matchSelectedImage);

            HeyowApiService apiService = ApiAdapter.getInstance()
                    .getRetrofit(Constants.HEYOW_BASE_URL)
                    .create(HeyowApiService.class);

            Call<MatchDetailResponse> editMatch = apiService.editMatch(formFields);

            if (!NetworkUtils.getInstance().isConnectingToInternet(this)) {
                Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            } else {
                showProgress();
                editMatch.enqueue(this);
            }
        }
    }

    @Override
    public void onResponse(Call<MatchDetailResponse> call, Response<MatchDetailResponse> response) {
        MatchDetailResponse resultResponse = response.body();

        if (response.isSuccessful()) {
            Toast.makeText(EditMatchActivity.this, "Success post!", Toast.LENGTH_SHORT).show();

            Intent matchDetailIntent = new Intent();
            matchDetailIntent.putExtra("MatchIdEdit", resultResponse.getMatchId());
            setResult(RESULT_OK, matchDetailIntent);
            finishActivity();
        } else {
            dismissProgress();
            Log.e(TAG, response.message());
            Log.e(TAG, String.valueOf(response.code()));
            Toast.makeText(getApplicationContext(), "Sorry, we couldn't complete your request. " +
                    "Please try again in a moment", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<MatchDetailResponse> call, Throwable t) {
        dismissProgress();
        Log.e(TAG, "Message : " + t.getMessage());
        Log.e(TAG, "Error : " + t.getLocalizedMessage());
        Log.e(TAG, "Cause : " + t.getCause());
        Toast.makeText(getApplicationContext(), "Sorry, we couldn't complete your request. " +
                "Please try again in a moment", Toast.LENGTH_SHORT).show();
    }

    /**
     * Showing progress dialog
     */
    public void showProgress() {
        progressDialog.show();
    }

    /**
     * Dismiss progress dialog
     */
    public void dismissProgress() {
        progressDialog.dismiss();
    }
}
