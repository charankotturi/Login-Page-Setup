package com.example.login_page;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class Adaptor extends RecyclerView.Adapter<Adaptor.ViewHolder> {

    private Context context;
    ArrayList<UserModel> userModels = new ArrayList<>();

    public Adaptor(Context mContext) {
        this.context = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String userNames = userModels.get(position).getFirstName() +" "+ userModels.get(position).getLastName();
        holder.UserName.setText(userNames);
        holder.email.setText(userModels.get(position).getEmail());

        Glide.with(context)
                .asBitmap()
                .load(userModels.get(position).getAvatar())
                .into(holder.imgData);

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Deleting the item");
                alertDialogBuilder.setMessage("Are you sure about this action?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Utils.deleteListItem(userModels.get(position).getId());
                        notifyDataSetChanged();
                    }
                });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Negative button", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialogBuilder.create().show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public void setUserModels(ArrayList<UserModel> userModels) {
        this.userModels = userModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private TextView UserName;
        private TextView email;
        private ImageView imgData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.DataCard);
            UserName = itemView.findViewById(R.id.txtDataName);
            email = itemView.findViewById(R.id.txtDataEmail);
            imgData = itemView.findViewById(R.id.imageViewData);
        }
    }
}
