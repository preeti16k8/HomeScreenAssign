package com.example.firebasecrud;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.firebasecrud.MainActivity.uid;

/**
 * Created by Belal on 2/26/2017.
 */

public class PostList extends ArrayAdapter<PostModel> {
    private Activity context;
    List<PostModel> postModels;

    public PostList(Activity context, List<PostModel> postModels) {
        super(context, R.layout.layout_post_list, postModels);
        this.context = context;
        this.postModels = postModels;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_post_list, null, true);
        TextView txtUsrid=(TextView)listViewItem.findViewById(R.id.textUserid);
        TextView textViewId = (TextView) listViewItem.findViewById(R.id.textViewPostId);
        TextView textViewPost = (TextView) listViewItem.findViewById(R.id.textViewText);

        PostModel post = postModels.get(position);
        txtUsrid.setText(uid);
        textViewId.setText(post.getPostid());
        textViewPost.setText(post.getText());

        return listViewItem;
    }
}