package com.xiaobo.collegedesign.internetbooks.Adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaobo.collegedesign.internetbooks.Model.Entity.BookInfo;
import com.xiaobo.collegedesign.internetbooks.Model.Entity.ReadInfo;
import com.xiaobo.collegedesign.internetbooks.R;
import com.xiaobo.collegedesign.internetbooks.Utils.DensityUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Xiaobo on 2015-04-09.
 */
public class ReadInfoAdapter extends BaseAdapter {

    private List<ReadInfo> list;
    private Context context;

    public ReadInfoAdapter(Context context, List<ReadInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(context).inflate(R.layout.read_info_adapter_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.read_info_adapter_item_bookmark_name.setText("" + list.get(position).getBookmark_name());

        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.read_info_adapter_item_bookmark_name) TextView read_info_adapter_item_bookmark_name;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
