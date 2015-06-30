package minaz.devcafe.devcafe;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;


public class IdeaListAdapter implements ListAdapter {

    public Activity activity;
    public List<Idea> ideaList;

    public IdeaListAdapter(Activity activity, List<Idea> ideaList) {
        this.activity = activity;
        this.ideaList = ideaList;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return ideaList.size();
    }

    @Override
    public Idea getItem(int position) {
        return ideaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) getItem(position).id;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.list_item_idea, null);

        TextView title =(TextView) convertView.findViewById(R.id.list_item_ideaTitle_textview);
        title.setText(getItem(position).title);
        TextView description = (TextView) convertView.findViewById(R.id.list_item_ideaDescription_textview);
        description.setText(getItem(position).description);
        TextView owner = (TextView) convertView.findViewById(R.id.list_item_ideaOwner_textview);
        owner.setText("by: " + getItem(position).owner.username);

        return convertView;
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
        return ideaList.size() == 0;
    }
}
