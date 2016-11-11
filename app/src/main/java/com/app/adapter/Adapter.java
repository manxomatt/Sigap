package com.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.sigap.PolisiActivity;
import com.app.sigap.R;
import com.app.sources.Data;

import java.util.List;

/**
 * Created by blue on 08/11/16.
 */

public class Adapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Data> items;

    public Adapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_polisi, null);

        Typeface typeface_regular = Typeface.createFromAsset(
            inflater.inflate(R.layout.list_row_polisi, null).getContext().getApplicationContext().getAssets(),
            "fonts/titillium_regular_webfont.ttf"
        );
        Typeface typeface_semibold = Typeface.createFromAsset(
            inflater.inflate(R.layout.list_row_polisi, null).getContext().getApplicationContext().getAssets(),
            "fonts/titillium-semibold-webfont.ttf"
        );

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView keterangan = (TextView) convertView.findViewById(R.id.keterangan);

        nama.setTypeface(typeface_semibold);
        keterangan.setTypeface(typeface_regular);

        Data data = items.get(position);

        id.setText(data.getId());
        nama.setText(data.getNama());
        keterangan.setText(data.getAlamat());

        return convertView;
    }

}
