package kr.ac.cau.cse.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by dhtpr on 2017-07-19.
 */

public class PostAdapter extends BaseAdapter {

    private Context mContext;
    private int mResource;
    private ArrayList<PostData> mItems = new ArrayList<>();

    public PostAdapter(Context context, int resource, ArrayList<PostData> datas){
        mContext = context;
        mResource= resource;
        mItems= datas;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
