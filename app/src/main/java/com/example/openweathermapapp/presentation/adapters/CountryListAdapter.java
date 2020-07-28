package com.example.openweathermapapp.presentation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.openweathermapapp.R;
import com.example.openweathermapapp.domain.datamodel.CountryModel;

import java.util.List;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListAdapter.CountryViewHolder> {

    class CountryViewHolder extends RecyclerView.ViewHolder {
        private final TextView countryItemView;

        private CountryViewHolder(View itemView) {
            super(itemView);
            countryItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<CountryModel> mCountries; // Cached copy of words

    public CountryListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.country_item_layout, parent, false);
        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        CountryModel current = mCountries.get(position);
        holder.countryItemView.setText(current.getName());
    }

    public void setCountries(List<CountryModel> countries){
        mCountries = countries;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCountries != null)
            return mCountries.size();
        else return 0;
    }
}

