package in.cocomo.firestoredsc.expandable;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.cocomo.firestoredsc.R;
import in.cocomo.firestoredsc.SSN.ClubPost;
import in.cocomo.firestoredsc.SSN.Comments;
import in.cocomo.firestoredsc.SSN.Reply;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Comments> commentArrayList;

    
    
    public CustomExpandableListAdapter(Context context, ArrayList<Comments> data) {
        this.context = context;
        this.commentArrayList=data;
    }

    public ArrayList<Comments> getCommentArrayList() {
        return commentArrayList;
    }

    public void setCommentArrayList(ArrayList<Comments> commentArrayList) {
        this.commentArrayList = commentArrayList;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        //return this.expandableListDetail.get(this.commentArrayList.get(listPosition)).get(expandedListPosition);
        return this.commentArrayList.get(listPosition).getReply().get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,boolean isLastChild, View convertView, ViewGroup parent) {
        
        final HashMap<String,String> expandedListText = (HashMap<String, String>) getChild(listPosition, expandedListPosition);
        
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        
        TextView commentListAuthor =  convertView.findViewById(R.id.commentListAuthor);
        TextView commentListDescription =  convertView.findViewById(R.id.commentListDescription);
        commentListAuthor.setText(expandedListText.get("author"));
        commentListDescription.setText(expandedListText.get("message"));
        
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.commentArrayList.get(listPosition).getReply().size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.commentArrayList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.commentArrayList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        
        Comments comment = (Comments) getGroup(listPosition);
        
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        
        TextView listTitleTextView = convertView.findViewById(R.id.listTitle);
        TextView listDescription = convertView.findViewById(R.id.listauthor);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(comment.getAuthor());
        listDescription.setText(comment.getMessage());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}
