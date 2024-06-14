package com.example.gestiondetaches;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private final ArrayList<String> task_status;
    private final ArrayList<String> task_title;
    private final ArrayList<String> task_id;
    //private final ArrayList<String> task_description;

    CustomAdapter(Activity activity, Context context, ArrayList<String> task_id, ArrayList<String> task_status, ArrayList<String> task_title) {
        this.context = context;
        this.activity = activity;
        this.task_id = task_id;
        this.task_status = task_status;
        this.task_title = task_title;
        //this.task_description = task_description;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.task_title.setText(task_title.get(position));
        holder.task_id.setText(String.valueOf(task_id.get(position)));
        //holder.task_description.setText(task_description.get(position));

        String status = task_status.get(position);

        switch (status) {
            case "Done":
                holder.task_status.setBackgroundResource(R.drawable.circle_background_green);
                holder.constraintLayout.setBackgroundResource(R.drawable.border_background_green);
                break;
            case "In Progress":
                holder.task_status.setBackgroundResource(R.drawable.circle_background_blue);
                holder.constraintLayout.setBackgroundResource(R.drawable.border_background_blue);
                break;
            case "Bug":
                holder.task_status.setBackgroundResource(R.drawable.circle_background_red);
                holder.constraintLayout.setBackgroundResource(R.drawable.border_background_red);
                break;
            case "Todo":
                holder.task_status.setBackgroundResource(R.drawable.circle_background);
                holder.constraintLayout.setBackgroundResource(R.drawable.border_backgroud);
                break;
            default:
                holder.task_status.setBackgroundResource(R.drawable.circle_background);
                holder.constraintLayout.setBackgroundResource(R.drawable.border_backgroud);
                break;
        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("Nom", task_title.get(position));
                intent.putExtra("id", task_id.get(position));
                //intent.putExtra("Description", task_description.get(position));
                activity.startActivityForResult(intent, 1);

                Intent intent2 = new Intent(context, AddActivity.class);
                activity.startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return task_title.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mainLayout;
        ConstraintLayout constraintLayout;
        TextView task_title, task_id;
        ImageView task_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_status = itemView.findViewById(R.id.task_status);
            task_id = itemView.findViewById(R.id.task_id);
            task_title = itemView.findViewById(R.id.task_title);
            //task_description = itemView.findViewById(R.id.description_input);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            constraintLayout = itemView.findViewById(R.id.bloc_layout);

        }
    }
}
