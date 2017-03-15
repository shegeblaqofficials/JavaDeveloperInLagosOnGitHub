package tech.damisa.developerlist;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by BLAQ on 3/13/2017.
 */
public class ListViewAdapter extends ArrayAdapter <ListItem> {

    //private final ColorMatrixColorFilter grayscaleFilter;
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<ListItem> mGridData = new ArrayList<ListItem>();

    public ListViewAdapter(Context mContext, int layoutResourceId, ArrayList<ListItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }


    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<ListItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.username = (TextView) row.findViewById(R.id.username);
            holder.score = (TextView) row.findViewById(R.id.score);
            holder.type = (TextView) row.findViewById(R.id.type);
            holder.imageView = (ImageView) row.findViewById(R.id.avatar);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ListItem item = mGridData.get(position);
        holder.username.setText(Html.fromHtml(item.getUsername()));
        holder.score.setText(Html.fromHtml("Score:"+item.getScore()));
        holder.type.setText("Account Type :"+item.getType());

        Picasso.with(mContext)
                .load(item.getImage())
                .placeholder(R.drawable.avatar)
                .into(holder.imageView);
        return row;
    }

    static class ViewHolder {
        TextView username,score,type;
        ImageView imageView;
    }
}
