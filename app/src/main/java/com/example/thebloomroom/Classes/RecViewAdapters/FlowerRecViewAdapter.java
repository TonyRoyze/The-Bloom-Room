package com.example.thebloomroom.Classes.RecViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thebloomroom.Activities.Admin.AddEditFlower;
import com.example.thebloomroom.Classes.Flower;
import com.example.thebloomroom.Database.DatabaseHelper;
import com.example.thebloomroom.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FlowerRecViewAdapter extends RecyclerView.Adapter<FlowerRecViewAdapter.ViewHolder> {

    List<Flower> flowers = new ArrayList<>();
    Context context;
    public FlowerRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_flower_item, parent, false);
        ViewHolder holder = new ViewHolder(view).linkAdapter(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Flower flower =  flowers.get(position);

        holder.flowerName.setText(flowers.get(position).getFlowerName());
        holder.flowerDesc.setText(flowers.get(position).getFlowerDesc());
        holder.flowerPrice.setText("$ " + flowers.get(position).getPrice() + "0");
        holder.flower = flower;
//        holder.flowerImg.setImageResource(R.mipmap.white_tulip);

        int imageResourceId = context.getResources().getIdentifier(flower.getImgName(), "mipmap", context.getPackageName());

        if (imageResourceId != 0) {
            holder.flowerImg.setImageResource(imageResourceId);
        } else {
            holder.flowerImg.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        return flowers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout flowerCard;
        FloatingActionButton btnEdit;
        ImageView flowerImg;
        TextView flowerName, flowerDesc, flowerPrice;
        Flower flower;
        FlowerRecViewAdapter adapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flowerCard = itemView.findViewById(R.id.flower_card);
            flowerImg = itemView.findViewById(R.id.item_img);
            flowerName = itemView.findViewById(R.id.flower_name);
            flowerDesc = itemView.findViewById(R.id.flower_desc);
            flowerPrice = itemView.findViewById(R.id.flower_price);
            btnEdit = itemView.findViewById(R.id.ic_edit);

            btnEdit.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                    boolean success = databaseHelper.deleteFlower(flower);
                    if (success){
                        Toast.makeText(context, flower.getFlowerName() + " Deleted Successfully", Toast.LENGTH_SHORT).show();
                        adapter.flowers.remove(getAdapterPosition());
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(context, flower.getFlowerName() + " Delete Failed", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AddEditFlower.class);
                    intent.putExtra("id", flower.getId());
                    context.startActivity(intent);
                }
            });

        }

        public ViewHolder linkAdapter(FlowerRecViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

    public void setFlowers(List<Flower> flowers) {
        this.flowers = flowers;
        notifyDataSetChanged();
    }
}
