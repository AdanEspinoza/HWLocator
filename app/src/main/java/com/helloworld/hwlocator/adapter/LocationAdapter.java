package com.helloworld.hwlocator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.helloworld.hwlocator.R;
import com.helloworld.hwlocator.model.LocationObject;

import java.util.List;

/**
 * Created by snakelogan on 2/6/16.
 */
public class LocationAdapter extends ArrayAdapter<LocationObject> {

    private Context mContext;
    private List<LocationObject> mLocationObjectList;

    public LocationAdapter(Context context, int resource, List<LocationObject> locationObjectList) {
        super(context, resource, locationObjectList);
        this.mContext = context;
        this.mLocationObjectList = locationObjectList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mName = (TextView) convertView.findViewById(R.id.office_tv_name);
            viewHolder.mAddress = (TextView) convertView.findViewById(R.id.office_tv_address);
            viewHolder.mDistance = (TextView) convertView.findViewById(R.id.office_tv_distance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LocationObject locationObject = mLocationObjectList.get(position);
        if (locationObject != null) {
            viewHolder.mName.setText(locationObject.getName());

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(locationObject.getAddress())
                    .append(", ")
                    .append(locationObject.getCity())
                    .append(", ")
                    .append(locationObject.getState())
                    .append(", ")
                    .append(locationObject.getZipPostalCode());
            viewHolder.mAddress.setText(stringBuilder.toString());

        }
        return convertView;
    }

    public static class ViewHolder {
        public TextView mName;
        public TextView mAddress;
        public TextView mDistance;
    }
}
