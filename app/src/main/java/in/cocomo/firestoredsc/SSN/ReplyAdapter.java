package in.cocomo.firestoredsc.SSN;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import in.cocomo.firestoredsc.R;

public class ReplyAdapter extends ArrayAdapter<Reply> {

    public ReplyAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Reply reply=getItem(position);


        TextView tv_reply_author,tv_reply_message;

        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.reply_item,parent,false);


        tv_reply_author=convertView.findViewById(R.id.tv_reply_author);
        tv_reply_message=convertView.findViewById(R.id.tv_reply_msg);

        tv_reply_author.setText(reply.name);
        tv_reply_message.setText(reply.message);

        return convertView;
    }
}
