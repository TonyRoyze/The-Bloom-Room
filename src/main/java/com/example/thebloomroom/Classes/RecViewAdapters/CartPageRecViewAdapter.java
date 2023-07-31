package com.example.thebloomroom.Classes.RecViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thebloomroom.Classes.Cart;
import com.example.thebloomroom.Classes.Flower;
import com.example.thebloomroom.R;

public class CartPageRecViewAdapter extends RecyclerView.Adapter<CartPageRecViewAdapter.ViewHolder> {


    Context context;
    double total = 0;
    public CartPageRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        ViewHolder holder = new ViewHolder(view).linkAdapter(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Flower flower = Cart.cart.get(position).getFlower();
        int quantity = Cart.cart.get(position).getQuantity();
        holder.flowerName.setText(flower.getFlowerName());
        holder.flowerPrice.setText("$ " + String.valueOf(flower.getPrice()) + "0");
        holder.btnStepperText.setText(String.valueOf(quantity));
        holder.flower = flower;
        holder.count = quantity;

        double item_total = flower.getPrice()*quantity;
        holder.flowerTotal.setText("$ " + String.valueOf(item_total) + "0");

    }

    @Override
    public int getItemCount() {
        return Cart.cart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cartCard;
        ImageView btnStepperAdd, btnStepperRemove;
        TextView flowerName, flowerPrice, flowerTotal, btnStepperText;
        Flower flower;
        CartPageRecViewAdapter adapter;
        int count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartCard = itemView.findViewById(R.id.cart_card);
            flowerName = itemView.findViewById(R.id.flower_name);
            flowerTotal = itemView.findViewById(R.id.item_total);
            flowerPrice = itemView.findViewById(R.id.price);
            btnStepperText = itemView.findViewById(R.id.btn_stepper_text);
            btnStepperAdd = itemView.findViewById(R.id.btn_stepper_add);
            btnStepperRemove = itemView.findViewById(R.id.btn_stepper_remove);


            btnStepperAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStepperText.setText(String.valueOf(++count));
                    Cart.addToCart(flower);

                    double item_total = flower.getPrice()*count;
                    flowerTotal.setText("$ " + String.valueOf(item_total) + "0");
                    double cartTotal = Cart.getCartTotal();
//                    total.setText("$ " + cartTotal + "0");

                }
            });

            btnStepperRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (count != 0) {
                        btnStepperText.setText(String.valueOf(--count));
                        Cart.removeFromCart(flower);
                    }
                }
            });

        }

        public ViewHolder linkAdapter(CartPageRecViewAdapter adapter) {
            this.adapter = adapter;
            return this;
        }
    }

}
