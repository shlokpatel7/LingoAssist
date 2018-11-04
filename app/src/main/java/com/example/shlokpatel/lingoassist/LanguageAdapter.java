package com.example.shlokpatel.lingoassist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class LanguageAdapter extends BaseAdapter {

    Activity activity;
    List<LanguageModel> list;

    public LanguageAdapter(Activity activity, List<LanguageModel> users) {
        this.activity = activity;
        this.list = users;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;

        if (view == null) {

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_item, viewGroup, false);

            holder = new ViewHolder();

            holder.tvUserName = (TextView) view.findViewById(R.id.user_lang);
            holder.ivCheckBox = (ImageView) view.findViewById(R.id.iv_check_box);

            view.setTag(holder);
        } else
            holder = (ViewHolder) view.getTag();

        LanguageModel model = list.get(i);

        holder.tvUserName.setText(model.getLangName());

        if (model.isSelected())
            holder.ivCheckBox.setBackgroundResource(R.drawable.checked);

        else
            holder.ivCheckBox.setBackgroundResource(R.drawable.check);

        return view;

    }

    public void updateRecords(List<LanguageModel> users) {
        this.list = users;

        notifyDataSetChanged();
    }

    class ViewHolder {

        TextView tvUserName;
        ImageView ivCheckBox;

    }
}