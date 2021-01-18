package com.example.bargest.Adaptars;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bargest.Models.ProductsToBePaid;
import com.example.bargest.R;

import java.util.List;

public class ListRequestProductAdapter extends RecyclerView.Adapter<ListRequestProductAdapter.ViewHolder> {

    private final List<ProductsToBePaid> products;

    public ListRequestProductAdapter(List<ProductsToBePaid> items) {
        products = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_request_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.quantity.setText(String.valueOf(products.get(position).getQuantity()));
        holder.name.setText(products.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView quantity;
        public final TextView name;

        public ViewHolder(View view) {
            super(view);
            quantity = (TextView) view.findViewById(R.id.item_requestproduct_quantity);
            name = (TextView) view.findViewById(R.id.item_requestproduct_name);
        }
    }
}