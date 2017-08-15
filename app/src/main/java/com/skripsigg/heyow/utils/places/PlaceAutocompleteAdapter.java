package com.skripsigg.heyow.utils.places;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;

import com.devspark.robototextview.widget.RobotoTextView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;
import com.skripsigg.heyow.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by Aldyaz on 12/23/2016.
 */

public class PlaceAutocompleteAdapter
        extends RecyclerView.Adapter<PlaceAutocompleteAdapter.PredictionHolder>
        implements Filterable {

    private final String TAG = getClass().getSimpleName();

    /**
     * Provide context
     */
    private Context context;

    /**
     * Layout resource for autocomplete list
     */
    private int layout;

    /**
     * Interface for clicking the item list
     */
    private OnListPlaceItemClickListener listPlaceItemClickListener;

    /**
     * Current results returned by this adapter.
     */
    private ArrayList<PlaceAutocompleteResult> autocompleteResultList;

    /**
     * Handles autocomplete requests.
     */
    private GoogleApiClient apiClient;

    /**
     * The bounds used for Places Geo Data autocomplete API requests.
     */
    private LatLngBounds bounds;

    /**
     * The autocomplete filter used to restrict queries to a specific set of place types.
     */
    private AutocompleteFilter placeFilter;

    public PlaceAutocompleteAdapter(Context context, int resource, GoogleApiClient googleApiClient,
                                    LatLngBounds bounds, AutocompleteFilter filter,
                                    OnListPlaceItemClickListener listPlaceItemClickListener) {
        this.context = context;
        this.layout = resource;
        this.apiClient = googleApiClient;
        this.bounds = bounds;
        this.placeFilter = filter;
        this.listPlaceItemClickListener = listPlaceItemClickListener;
    }

    public void setBounds(LatLngBounds bounds) {
        this.bounds = bounds;
    }

    /**
     * Returns the filter for the current set of autocomplete results.
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // Skip the autocomplete query if no constraints are given
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    autocompleteResultList = getAutoComplete(constraint);
                    if (autocompleteResultList != null) {
                        // The API successfully returned results.
                        results.values = autocompleteResultList;
                        results.count = autocompleteResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults != null && filterResults.count > 0) {
                    // The API returned at least one result, update the data
                    notifyDataSetChanged();
                }
            }
        };
    }

    @SuppressLint("LongLogTag")
    private ArrayList<PlaceAutocompleteResult> getAutoComplete(CharSequence constraint) {
        if (apiClient.isConnected()) {
            Log.i(TAG, "Starting autocomplete query for: " + constraint);

            // Submit the query to the autocomplete API and retrieve a PendingResult that will
            // contain the results when the query completes.
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi
                            .getAutocompletePredictions(apiClient, constraint.toString(),
                                    bounds, placeFilter);

            // This method should have been called off the main UI thread. Block and wait for at most 60s
            // for a result from the API.
            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);

            // Confirm that the query completed successfully, otherwise return null
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                Log.e(TAG, "Error getting autocomplete prediction API call : " + status.toString());
                Log.e(TAG, "Status code : " + status.getStatusCode());
                autocompletePredictions.release();
                return null;
            }

            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount() + " predictions.");

            // Copy the results into our own data structure, because we can't hold onto the buffer.
            // AutocompletePrediction objects encapsulate the API response (place ID and description).
            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList resultLists = new ArrayList(autocompletePredictions.getCount());
            while (iterator.hasNext()) {
                AutocompletePrediction prediction = iterator.next();
                // Get the details of this prediction and copy it into a new PlaceAutocomplete object.
                resultLists.add(new PlaceAutocompleteResult(
                        prediction.getPlaceId(), prediction.getFullText(null)));
            }

            // Release the buffer now that all data has been copied.
            autocompletePredictions.release();
            return resultLists;
        }
        Log.e(TAG, "Google API client is not connected for autocomplete query.");
        return null;
    }

    @Override
    public PredictionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(layout, parent, false);
        return new PredictionHolder(convertView);
    }

    @Override
    public void onBindViewHolder(PredictionHolder holder, int position) {
        holder.locationPredictionText.setText(autocompleteResultList.get(position).description);
        holder.setListPlaceItemClickListener(listPlaceItemClickListener);
    }

    public PlaceAutocompleteResult getItem(int position) {
        return autocompleteResultList.get(position);
    }

    @Override
    public int getItemCount() {
        if (autocompleteResultList != null) {
            return autocompleteResultList.size();
        } else {
            return 0;
        }
    }

    class PredictionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RobotoTextView locationPredictionText;
        LinearLayout locationRowLayout;
        OnListPlaceItemClickListener listPlaceItemClickListener;

        PredictionHolder(View itemView) {
            super(itemView);
            locationRowLayout = (LinearLayout) itemView.findViewById(R.id.location_item_row_layout);
            locationPredictionText = (RobotoTextView) itemView.findViewById(R.id.location_name_row_text);

            locationRowLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listPlaceItemClickListener.onItemClick(view, getAdapterPosition());
        }

        public void setListPlaceItemClickListener(OnListPlaceItemClickListener listPlaceItemClickListener) {
            this.listPlaceItemClickListener = listPlaceItemClickListener;
        }
    }

    /**
     * Holder for Places Geo Data Autocomplete API results.
     */
    public class PlaceAutocompleteResult {

        public CharSequence placeId;
        public CharSequence description;

        PlaceAutocompleteResult(CharSequence placeId, CharSequence description) {
            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString() {
            return description.toString();
        }
    }

    public interface OnListPlaceItemClickListener {
        void onItemClick(View view, int position);
    }
}
