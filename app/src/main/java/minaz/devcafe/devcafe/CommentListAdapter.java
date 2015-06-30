package minaz.devcafe.devcafe;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by minaz on 30/06/15.
 */
public class CommentListAdapter implements ListAdapter {

    public Activity activity;
    public List<Comment> comments;

    public CommentListAdapter(Activity activity, List<Comment> comments) {
        this.activity = activity;
        this.comments = comments;
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
    public Object getItem(int position) {
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
        Comment comment = (Comment) getItem(position);
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.list_item_comment, null);
        }
        TextView commentTimestamp = (TextView) convertView.findViewById(R.id.comment_timestamp);
        TextView commentOwner = (TextView) convertView.findViewById(R.id.comment_owner);
        TextView commentText = (TextView) convertView.findViewById(R.id.comment_text);

//        Time time = comment.timestamp.getTime();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(comment.timestamp);
//        String dateTime = simpleDateFormat.format("yyyy-MM-dd HH:mm:ssZ");

//        commentTimestamp.setText(String.valueOf(dateTime));
        commentTimestamp.setText(formatDateTime(comment.timestamp));
        commentOwner.setText("by: " + comment.owner.username);
        commentText.setText(comment.text);

        return convertView;
    }

    public String formatDateTime(String dt) {
        dt = dt.replace("T", " ");
        return dt.substring(0, dt.lastIndexOf("."));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return comments.size() == 0;
    }
}
