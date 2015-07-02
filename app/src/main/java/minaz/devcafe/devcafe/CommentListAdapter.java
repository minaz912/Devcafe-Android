package minaz.devcafe.devcafe;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by minaz on 30/06/15.
 */
public class CommentListAdapter extends ArrayAdapter<Comment> {

    private final Context context;
    public List<Comment> comments;
    private int layoutResourceId;

    public CommentListAdapter(Context context, int layoutResourceId, List<Comment> comments) {
        super(context, layoutResourceId, comments);
        this.context = context;
        this.comments = comments;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return comments.get(position).id;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ViewHolder();
            holder.commentTimestamp = (TextView) row.findViewById(R.id.comment_timestamp);
            holder.commentOwner = (TextView) row.findViewById(R.id.comment_owner);
            holder.commentOwnerPic = (ImageView) row.findViewById(R.id.comment_owner_pic);
            holder.commentText = (TextView) row.findViewById(R.id.comment_text);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Comment comment = comments.get(position);

        holder.commentTimestamp.setText(formatDateTime(comment.timestamp));
        holder.commentOwner.setText("by: " + comment.owner.username);
        holder.commentText.setText(comment.text);
        if (comment.owner.picture != null) {
            Picasso.with(this.context).load(comment.owner.picture).into(holder.commentOwnerPic);
        }

        return row;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    public String formatDateTime(String dt) {
        dt = dt.replace("T", " ");
        return dt.substring(0, dt.lastIndexOf("."));
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return comments.size() == 0;
    }

    static class ViewHolder {
        TextView commentTimestamp;
        TextView commentOwner;
        ImageView commentOwnerPic;
        TextView commentText;
    }
}
