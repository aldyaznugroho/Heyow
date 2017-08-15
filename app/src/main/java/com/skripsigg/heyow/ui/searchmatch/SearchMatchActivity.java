package com.skripsigg.heyow.ui.searchmatch;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.skripsigg.heyow.R;
import com.devspark.robototextview.widget.RobotoEditText;
import com.skripsigg.heyow.models.SearchRequestModel;
import com.skripsigg.heyow.utils.others.Constants;
import com.skripsigg.heyow.ui.searchresults.SearchResultsActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchMatchActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    /**
     * UI widgets
     */
    private Toolbar searchMatchToolbar;
    private RobotoEditText searchVenueEditText, searchLocationEditText;
    private ListView searchCategoryListView;

    private SearchRequestModel searchRequestModel;
    protected String[] categoryValues;
    protected ArrayList<String> categoryLists;
    protected ArrayAdapter<String> categoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_match);

        searchMatchToolbar = (Toolbar) findViewById(R.id.search_match_toolbar);
        searchVenueEditText = (RobotoEditText) findViewById(R.id.search_venue_edit_text);
        searchLocationEditText = (RobotoEditText) findViewById(R.id.search_location_edit_text);
        searchCategoryListView = (ListView) findViewById(R.id.search_match_category_list_view);

        setSupportActionBar(searchMatchToolbar);

        searchRequestModel = new SearchRequestModel();

        initSearchEditText();
        initCategoryList();

        searchRequestModel.setMatchCategory("");
        searchRequestModel.setMatchLocation("");
        searchRequestModel.setMatchVenue("");
    }

    private void initSearchEditText() {
        searchVenueEditText.getCompoundDrawables()[0].setColorFilter(
                ContextCompat.getColor(this, R.color.colorPrimary),
                PorterDuff.Mode.SRC_ATOP);

        searchLocationEditText.getCompoundDrawables()[0].setColorFilter(
                ContextCompat.getColor(this, R.color.colorPrimary),
                PorterDuff.Mode.SRC_ATOP);

        searchVenueEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH)
                    if (!searchVenueEditText.getText().toString().trim().equalsIgnoreCase(""))
                        goToSearchResultsActivity();
                return true;
            }
        });

        searchLocationEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH)
                    if (!searchLocationEditText.getText().toString().trim().equalsIgnoreCase(""))
                        goToSearchResultsActivity();
                return true;
            }
        });
    }

    private void initCategoryList() {
        categoryValues = getResources().getStringArray(R.array.match_category);
        categoryLists = new ArrayList<>(Arrays.asList(categoryValues));
        categoryLists.remove(0);

        categoryListAdapter = new ArrayAdapter<>(
                getBaseContext(),
                android.R.layout.simple_list_item_1,
                categoryLists);

        searchCategoryListView.setAdapter(categoryListAdapter);

        searchCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchRequestModel.setMatchCategory(String.valueOf(i + 1));
                goToSearchResultsActivity();
            }
        });
    }

    private void goToSearchResultsActivity() {
        searchRequestModel.setMatchVenue(searchVenueEditText.getText().toString().trim());
        searchRequestModel.setMatchLocation(searchLocationEditText.getText().toString().trim());

        Bundle searchBundle = new Bundle();
        searchBundle.putParcelable(Constants.KEY_SEARCH_QUERY_PARCELABLE, searchRequestModel);

        Intent searchResultsIntent = new Intent(getApplicationContext(), SearchResultsActivity.class);
        searchResultsIntent.putExtras(searchBundle);

        startActivity(searchResultsIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchRequestModel.setMatchCategory("");
        searchRequestModel.setMatchLocation("");
        searchRequestModel.setMatchVenue("");
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity() {
        finish();
    }
}