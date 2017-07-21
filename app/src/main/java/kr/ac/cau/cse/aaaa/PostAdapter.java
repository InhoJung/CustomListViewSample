package kr.ac.cau.cse.aaaa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource,parent,false);
        }
        TextView tv_id,tv_userId,tv_title,tv_body;

        tv_id = (TextView)convertView.findViewById(R.id.tv_id);
        tv_id.setText(""+mItems.get(position).id);

        tv_userId = (TextView)convertView.findViewById(R.id.tv_userid);
        tv_userId.setText(""+mItems.get(position).userid);

        tv_title = (TextView)convertView.findViewById(R.id.tv_title);
        tv_title.setText(mItems.get(position).title);

        tv_body = (TextView)convertView.findViewById(R.id.tv_body);
        tv_body.setText(mItems.get(position).body);
        return convertView;
    }
}
