package in.cocomo.firestoredsc.SSN;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import in.cocomo.firestoredsc.R;


public class CommentsAdapter extends ArrayAdapter<Comments> {

    public CommentsAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Comments comments=getItem(position);

        TextView tv_author,tv_msg;
        ListView lv_replies;


        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.comment_item,parent,false);

        tv_author=convertView.findViewById(R.id.tv_author);
        tv_msg=convertView.findViewById(R.id.tv_msg);
        lv_replies=convertView.findViewById(R.id.lv_replies);




        ArrayList<Reply> replyList=new ArrayList();
        for(HashMap<String,String> reply:comments.getReply()) {
            replyList.add(new Reply(reply.get("author"),reply.get("message")));
        }

        Float scale = getContext().getResources().getDisplayMetrics().density;
        /*
        OrderDetailAdapter adapter = new OrderDetailAdapter(context,0,orderDetails);
        int numberOfItems = orderDetails.size();
        ViewGroup.LayoutParams params = holder.listView.getLayoutParams();
        int pixels = (int) (80 * scale);
        int dpPixels = (int) (0.1 * scale);
        params.height =numberOfItems * pixels + (numberOfItems - 1) * dpPixels;
        holder.listView.setLayoutParams(params);
        holder.listView.setAdapter(adapter);



         */

        ReplyAdapter replyAdapter=new ReplyAdapter(getContext(),R.layout.reply_item);
        int numberOfItems=replyList.size();
        ViewGroup.LayoutParams params=lv_replies.getLayoutParams();

        int pixels=(int)(80 * scale);
        int dpPixels=(int)(0.1*scale);
        params.height =numberOfItems * pixels + (numberOfItems - 1) * dpPixels;
        lv_replies.setLayoutParams(params);
        replyAdapter.addAll(replyList);
        lv_replies.setAdapter(replyAdapter);


        tv_author.setText(comments.getAuthor());
        tv_msg.setText(comments.getMessage());


        return convertView;
    }
}
