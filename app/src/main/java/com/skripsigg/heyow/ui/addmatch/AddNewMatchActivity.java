package com.skripsigg.heyow.ui.addmatch;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.skripsigg.heyow.R;
import com.skripsigg.heyow.ui.addmatch.step1.AddMatchStepOneFragment;
import com.skripsigg.heyow.ui.addmatch.step2.AddMatchStepTwoFragment;
import com.skripsigg.heyow.ui.addmatch.step3.AddMatchStepThreeFragment;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.views.adapters.MatchFormStepperPagerAdapter;
import com.stepstone.stepper.StepperLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewMatchActivity extends BaseActivity implements AddMatchMvpView {

    @Inject
    AddMatchMvpPresenter<AddMatchMvpView> presenter;

    @BindView(R.id.add_match_toolbar) Toolbar toolbar;
    @BindView(R.id.add_match_stepper_layout) StepperLayout stepperLayout;

    MatchFormStepperPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_match);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(toolbar);
        init();
    }

    private void init() {
        initToolbar();
        initStepperLayout();
    }

    private void initToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDiscardFormDialog();
            }
        });
        Drawable navigationIcon = toolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initStepperLayout() {
        adapter = new MatchFormStepperPagerAdapter(getSupportFragmentManager(), this);
        adapter.addPage(AddMatchStepOneFragment.getInstance(), "Step One");
        adapter.addPage(AddMatchStepTwoFragment.getInstance(), "Step Two");
        adapter.addPage(AddMatchStepThreeFragment.getInstance(), "Step Three");

        stepperLayout.setAdapter(adapter);
        stepperLayout.setOffscreenPageLimit(3);
    }

    @Override
    public void onBackPressed() {
        if (stepperLayout.getCurrentStepPosition() == 0) {
            showDiscardFormDialog();
        } else {
            stepperLayout.onBackClicked();
        }
    }

    private void showDiscardFormDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog;

        builder.setMessage("Are you sure you'd like to discard the form?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                presenter.removeFormPreferences();
                finish();
            }
        });
        builder.setNegativeButton("No", null);
        dialog = builder.create();
        dialog.show();
    }
}
