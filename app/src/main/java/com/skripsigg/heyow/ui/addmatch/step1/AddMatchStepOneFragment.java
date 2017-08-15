package com.skripsigg.heyow.ui.addmatch.step1;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.ui.base.BaseFragment;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.utils.photos.CameraPhoto;
import com.skripsigg.heyow.utils.photos.GalleryPhoto;
import com.skripsigg.heyow.utils.photos.ImageBase64;
import com.skripsigg.heyow.utils.photos.ImageLoader;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMatchStepOneFragment extends BaseFragment implements StepOneMvpView, BlockingStep {

    private final int CAMERA_REQUEST = 13323;
    private final int GALLERY_REQUEST = 22131;

    String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Inject
    StepOneMvpPresenter<StepOneMvpView> presenter;

    @BindView(R.id.match_image_upload_layout) View imageUploadLayout;
    @BindView(R.id.match_upload_preview_image) ImageView imagePreview;
    @BindView(R.id.match_add_image_text) TextView imagePreviewText;
    @BindView(R.id.add_match_title) EditText titleEditText;
    @BindView(R.id.add_match_category_spinner) Spinner categorySpinner;

    private CameraPhoto cameraPhoto;
    private GalleryPhoto galleryPhoto;

    private String selectedImage;

    public static AddMatchStepOneFragment getInstance() {
        return new AddMatchStepOneFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraPhoto = new CameraPhoto(getContext());
        galleryPhoto = new GalleryPhoto(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_match_step_one, container, false);

        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this, rootView));
        presenter.onAttach(this);
        init();

        return rootView;
    }

    private void init() {
        initCategorySpinner();
    }

    private void initCategorySpinner() {
        ArrayAdapter<CharSequence> categoryListAdapter = ArrayAdapter.createFromResource(
                getContext(), R.array.match_category, android.R.layout.simple_spinner_item);
        categoryListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryListAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                String photoPath = cameraPhoto.getPhotoPath();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).getBitmap();

                    // Test encode
                    Bitmap selectedBitmap = ImageLoader.init().from(photoPath).getBitmap();
                    String encodedBitmap = ImageBase64.encode(selectedBitmap);
                    Timber.v("Encoded : " + encodedBitmap);
                    selectedImage = encodedBitmap;

                    imagePreview.setImageBitmap(bitmap);
                    imagePreviewText.setVisibility(View.GONE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    showOnErrorToast("Error while loading photos");
                }
                Timber.v("Storage path: " + photoPath);
            }
        } else if (requestCode == GALLERY_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).getBitmap();

                    // Test encode
                    Bitmap selectedBitmap = ImageLoader.init().from(photoPath).getBitmap();
                    String encodedBitmap = ImageBase64.encode(selectedBitmap);
                    Timber.v("Encoded : " + encodedBitmap);
                    selectedImage = encodedBitmap;

                    imagePreview.setImageBitmap(bitmap);
                    imagePreviewText.setVisibility(View.GONE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    showOnErrorToast("Error while choosing photos");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PICK_PHOTO_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImagePickerDialog();
                } else {
                    showOnErrorToast("Error while pick an image. Please enabled permission in system app setting");
                }
        }
    }

    @Override
    public void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog dialog;

        builder.setMessage("Select upload");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public Observable<Object> ImageUploadLayoutClick() {
        return RxView.clicks(imageUploadLayout);
    }

    @Override
    public boolean checkIsHasPermission() {
        for (String permission : permissions) {
            if (getBaseActivity().hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void requestPermission() {
        requestPermissions(permissions, Constants.PICK_PHOTO_PERMISSION);
    }

    /**
     * Return null if the user can go to the next step, create a new VerificationError instance otherwise
     */
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    /**
     * Update UI when selected
     */
    @Override
    public void onSelected() {
    }

    /**
     * Handle error inside of the fragment, e.g. show error on EditText
     * @param error
     */
    @Override
    public void onError(@NonNull VerificationError error) {
    }

    /**
     * Handle when user clicking 'Next' button
     * @param callback
     */
    @Override
    public void onNextClicked(final StepperLayout.OnNextClickedCallback callback) {
        String matchTitle = titleEditText.getText().toString();
        Integer selectedCategory = categorySpinner.getSelectedItemPosition();

        if (selectedImage == null) {
            showOnErrorToast("Please choose an image");
        } else if (TextUtils.isEmpty(matchTitle)) {
            titleEditText.setError(getString(R.string.must_be_filled));
        } else if (selectedCategory == 0) {
            showOnErrorToast("Please choose category");
        } else {
            showLoading();
            presenter.saveFormToPreferences(selectedImage, matchTitle, selectedCategory);
            presenter.saveFormDelayProcessing(new Consumer<Long>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                    callback.goToNextStep();
                    hideLoading();
                }
            });
        }
    }

    /**
     * Handle when user clicking 'Complete' button
     * @param callback
     */
    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
    }

    /**
     * Handle when user clicking 'Back' button
     * @param callback
     */
    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
    }
}
