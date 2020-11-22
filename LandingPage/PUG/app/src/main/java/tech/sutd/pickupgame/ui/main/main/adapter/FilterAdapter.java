package tech.sutd.pickupgame.ui.main.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import tech.sutd.pickupgame.R;
import tech.sutd.pickupgame.databinding.ItemlistFilterBinding;

public class FilterAdapter extends BaseAdapter {

    private int[] images = {R.drawable.ic_calendar, R.drawable.ic_star, R.drawable.ic_location};
    private String[] desc = {"Date", "Sport", "Near Me"};

    private RequestManager requestManager;

    @Inject
    public FilterAdapter(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        @SuppressLint("ViewHolder") ItemlistFilterBinding binding = ItemlistFilterBinding.inflate(inflater, parent, false);

        requestManager.load(images[position])
                .into(binding.filterIcon);
        binding.filterText.setText(desc[position]);

        return binding.getRoot();
    }
}
