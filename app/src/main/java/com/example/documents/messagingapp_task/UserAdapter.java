package com.example.documents.messagingapp_task;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.documents.messagingapp_task.R;

import java.util.Collections;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    public List<MsgsClass> data = Collections.emptyList();
    MsgsClass current;
    public String TAG = "taaaaaaaag";


    private UserClick lOnClickListener;
    public UserAdapter(UserClick listener) {
        lOnClickListener = listener;
    }
    public void setUsersData(List<MsgsClass> recipesIn, Context context) {
        data = recipesIn;
        mContext = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.msg_row_view;

        //View itemView = LayoutInflater.from(mContext).inflate(R.layout.foo);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{
            final ViewHolder holder1 = (ViewHolder) holder ;
            current = data.get(position);
            holder1.msg.setText(current.getMsg());
            Log.e(TAG , current.getMsg()+"");

            ((ViewHolder)holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "this" + holder1.msg, Toast.LENGTH_LONG).show();
                    Log.e("this is value of text" , String.valueOf(holder1.msg));
                }
            });

        }
        catch (NullPointerException e){

        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView msg;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            msg = (TextView)itemView.findViewById(R.id.msgText);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.lin);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {

            int clickedPosition = getAdapterPosition();
            lOnClickListener.asd(data.get(clickedPosition));
        }

        }
    }

