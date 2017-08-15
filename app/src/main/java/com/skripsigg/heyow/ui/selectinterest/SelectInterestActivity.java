package com.skripsigg.heyow.ui.selectinterest;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anton46.collectionitempicker.CollectionPicker;
import com.anton46.collectionitempicker.Item;
import com.jakewharton.rxbinding2.view.RxMenuItem;
import com.skripsigg.heyow.R;
import com.skripsigg.heyow.ui.base.BaseActivity;
import com.skripsigg.heyow.ui.home.HomeActivity;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.utils.others.SharedPreferenceCustom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

public class SelectInterestActivity extends BaseActivity implements SelectInterestMvpView {

    @Inject
    SelectInterestMvpPresenter<SelectInterestMvpView> presenter;

    @BindView(R.id.select_interest_toolbar) Toolbar interestToolbar;
    @BindView(R.id.select_interest_category_picker) CollectionPicker categoryPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_interest);

        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        presenter.onAttach(this);

        setSupportActionBar(interestToolbar);
        setTitle("Select Interest");
        init();
    }

    private void init() {
        initToolbar();
        initCategoryPicker();
    }

    private void initToolbar() {
        interestToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Drawable navigationIcon = interestToolbar.getNavigationIcon();
        if (navigationIcon != null) {
            navigationIcon.mutate();
            navigationIcon.setColorFilter(
                    ContextCompat.getColor(this, R.color.color_white),
                    PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initCategoryPicker() {
        final String[] categoryStringArray = getResources().getStringArray(R.array.match_category);
        final HashMap<String, Object> categoryMap = presenter.getSharedPrefCategoryMap();
        List<Item> itemList = new ArrayList<>();
        for (int index = 0; index < categoryStringArray.length; index++) {
            itemList.add(new Item(String.valueOf(index), categoryStringArray[index]));
        }
        itemList.remove(0);
        categoryPicker.setItems(itemList);
        if (categoryMap.keySet().size() != 0) {
            categoryPicker.setCheckedItems(categoryMap);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_interest, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem doneItem = menu.findItem(R.id.action_done_interest);
        SpannableString spanString = new SpannableString(doneItem.getTitle());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 0, spanString.length(), 0);
        doneItem.setTitle(spanString);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_done_interest:
                presenter.saveSelectedInterest();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public HashMap<String, Object> getCollectionPickerCheckedItems() {
        return categoryPicker.getCheckedItems();
    }

    @Override
    public int getIntentInterestFlag() {
        return getIntent().getIntExtra(Constants.INTEREST_ACTIVITY_FLAG, 0);
    }

    @Override
    public void openHomeActivity() {
        Intent homeIntent = new Intent(getApplicationContext(), HomeActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        finishActivity();
    }

    @Override
    public void openIntentWithResultOk() {
        Intent homeIntent = new Intent();
        setResult(RESULT_OK, homeIntent);
        finish();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    /*profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (String key : categoryPicker.getCheckedItems().keySet()) {
                    Item item = new Item(key, key);
                    categoryMap.put(key, item.id);
                }
                Collection<Object> collection = categoryMap.values();
                String valuesFinal = Arrays.toString(collection.toArray());
                Log.e(TAG, "Choose (String no brackets): " + valuesFinal
                        .replace("[", "")
                        .replace("]", "")
                        .replace(" ", "")
                        .replace(" ", ""));
            }
        });*/
}
