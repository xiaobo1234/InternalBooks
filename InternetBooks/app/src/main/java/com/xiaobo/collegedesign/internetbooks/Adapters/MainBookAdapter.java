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
import com.xiaobo.collegedesign.internetbooks.R;
import com.xiaobo.collegedesign.internetbooks.Utils.DensityUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Xiaobo on 2015-04-09.
 */
public class MainBookAdapter extends BaseAdapter {

    private List<BookInfo> list;
    private Context context;

    public MainBookAdapter(Context context, List<BookInfo> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.main_book_adapter_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.getWindowWidth(context) / 9 * 4);
        layoutParams.gravity = Gravity.CENTER;
        holder.main_books_adapter_item_book_layout.setLayoutParams(layoutParams);
        holder.main_books_adapter_item_book_layout.setPadding(4, 8, 4, 8);

        holder.main_books_adapter_item_book_name.setText("" + list.get(position).getBook_name());

        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.main_books_adapter_item_book_name) TextView main_books_adapter_item_book_name;
        @InjectView(R.id.main_books_adapter_item_book_layout) LinearLayout main_books_adapter_item_book_layout;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
