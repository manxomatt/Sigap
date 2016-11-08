package com.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.sigap.R;
import com.app.sources.PolisiLog;

import java.util.List;

/**
 * Created by blue on 08/11/16.
 */

public class VolleyAdapter extends BaseAdapter {

    private Activity activity;

    private LayoutInflater inflater;

    private List<PolisiLog> items;

    public VolleyAdapter(Activity activity, List<PolisiLog> items) {
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
            convertView = inflater.inflate(R.layout.activity_bantuan_terdekat_polisi_list, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView keterangan = (TextView) convertView.findViewById(R.id.keterangan);

        PolisiLog data = items.get(position);

        id.setText(data.getId());
        nama.setText(data.getNama());
        keterangan.setText(data.getKeterangan());

        return convertView;
    }

}
