package com.example.thebloomroom.Classes;

import java.util.ArrayList;
import java.util.List;


public class Cart {

    public static List<Cart> cart = new ArrayList<>();

    private Flower flower;
    private int quantity;

    public Cart(Flower flower, int quantity) {
        this.flower = flower;
        this.quantity = quantity;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static void addToCart(Flower flower) {
        boolean contains = false;
        Cart cartObject = null;
        for (Cart c : cart) {
            if (c.getFlower().equals(flower)) {
                contains = true;
                cartObject = c;
                break;
            }
        }

        if (contains) {
            int qty = cartObject.getQuantity()+1;
            cartObject.setQuantity(qty);
        } else {
            cart.add(new Cart(flower, 1));
        }
    }

    public static void removeFromCart(Flower flower) {
        Cart cartObject = null;
        for (Cart c : cart) {
            if (c.getFlower().equals(flower)) {
                cartObject = c;
                break;
            }
        }

        int qty = cartObject.getQuantity()-1;
        cartObject.setQuantity(qty);
        if (qty==0) {
            cart.remove(cartObject);
        }

    }

    public static double getCartTotal() {
        double total = 0;
        for (Cart c : cart) {
            total += c.getFlower().getPrice()*c.getQuantity();
        }
        return total;
    }



}
