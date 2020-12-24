package com.example.bargest.Views.Fragments;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bargest.Adaptars.DivideAdaptar;
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Models.Products;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class DivideBillFragment extends Fragment implements ProductsListener {

    RecyclerView listOriginBill;
    RecyclerView listNewBill;
    Dialog dialog;
    TextView TVTotal;
    private DivideAdaptar adaptersOrigin;
    private DivideAdaptar adaptersNew;
    private ArrayList<Products> Originproducts;
    private ArrayList<Products> NewProducts;
    Products originProduct;
    Products newProduct;
    float Total;

    public DivideBillFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_divide_bill, container, false);

        dialog = new Dialog(getContext());
        listOriginBill = view.findViewById(R.id.listOriginBill);
        listNewBill = view.findViewById(R.id.listNewnBill);
        TVTotal = view.findViewById(R.id.TVtotalNewPrice);
        view.findViewById(R.id.BtnToolbarAdd).setVisibility(View.GONE);
        TVTotal.setText("0 €");
        listOriginBill.setLayoutManager(new LinearLayoutManager(getContext()));
        listNewBill.setLayoutManager(new LinearLayoutManager(getContext()));

        SingletonBarGest.getInstance(getContext()).getAccountProducts(getContext(), getArguments().getInt("account_id"));
        SingletonBarGest.getInstance(getContext()).setProductListener(this);
        NewProducts = new ArrayList<>();

        ItemTouchHelper itemTouchHelperNewBill = new ItemTouchHelper(callbackNew);

        itemTouchHelperNewBill.attachToRecyclerView(listNewBill);
        view.findViewById(R.id.BtnConfirmationNewBill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getFragmentManager().popBackStack("Bills",0);
            }
        });

        return view;
    }


    ItemTouchHelper.SimpleCallback callbackOrigin = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            openDialog(getArguments().getInt("account_id"), viewHolder, 1);
            adaptersOrigin.notifyDataSetChanged();
        }
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getContext(), R.color.swip_move_color))
                    .addSwipeRightActionIcon(R.drawable.ic_baseline_arrow_circle_down_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    ItemTouchHelper.SimpleCallback callbackNew = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            openDialog(getArguments().getInt("account_id"), viewHolder, 0);
            adaptersNew.notifyDataSetChanged();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.swip_move_color))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_arrow_circle_up_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void openDialog(final int account_id, final RecyclerView.ViewHolder viewHolder, final int setor) {
        dialog.setContentView(R.layout.dialog_split_quant);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = dialog.findViewById(R.id.BtnClosePayDialog);
        Button addNif = dialog.findViewById(R.id.BtnAddNif);
        final TextView error = dialog.findViewById(R.id.error);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addNif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setor == 1) {
                    EditText etquantity = (EditText)dialog.findViewById(R.id.quantity);
                    int quantity = Integer.parseInt(etquantity.getText().toString());
                    final int position = viewHolder.getAdapterPosition();
                    originProduct = Originproducts.get(position);

                    if(originProduct.getQuantity() == quantity) {
                        Originproducts.remove(position);
                        NewProducts.add(originProduct);
                        total();
                        TVTotal.setText(""+Total+"€");
                        dialog.dismiss();
                    }
                    else if(originProduct.getQuantity() > quantity){
                        originProduct.setQuantity(originProduct.getQuantity() - quantity);
                        Products pro = new Products(originProduct.getId(), originProduct.getName(), originProduct.getPrice(), quantity);
                        NewProducts.add(pro);
                        total();
                        TVTotal.setText(""+Total+"€");
                        dialog.dismiss();
                    }
                    else{
                        error.setText("Erro, insera um numero menor!");
                    }

                }
                else{
                    EditText etquantity = (EditText)dialog.findViewById(R.id.quantity);
                    int quantity = Integer.parseInt(etquantity.getText().toString());
                    final int position = viewHolder.getAdapterPosition();
                    newProduct = NewProducts.get(position);

                    if(newProduct.getQuantity() == quantity) {
                        NewProducts.remove(position);
                        Originproducts.add(newProduct);
                        total();
                        TVTotal.setText(""+Total+"€");
                        dialog.dismiss();
                    }
                    else if(newProduct.getQuantity() > quantity){
                        newProduct.setQuantity(newProduct.getQuantity() - quantity);
                        Products pro = new Products(newProduct.getId(), newProduct.getName(), newProduct.getPrice(), quantity);
                        Originproducts.add(pro);
                        total();
                        TVTotal.setText(""+Total+"€");
                        dialog.dismiss();
                    }
                    else{
                        error.setText("Erro, insera um numero menor!");
                    }
                }
            }
        });

        dialog.show();
    }

    public void total(){
        for (int i = 0; i < NewProducts.size(); i++){
            Total += NewProducts.get(i).getPrice()*NewProducts.get(i).getQuantity();
        }
    }

    @Override
    public void onRefreshListProducts(ArrayList<Products> products) {
        Originproducts = products;
        adaptersOrigin = new DivideAdaptar(getContext(), products);
        adaptersNew = new DivideAdaptar(getContext(), NewProducts);
        listOriginBill.setAdapter(adaptersOrigin);
        listNewBill.setAdapter(adaptersNew);
        ItemTouchHelper itemTouchHelperOriginBill = new ItemTouchHelper(callbackOrigin);
        itemTouchHelperOriginBill.attachToRecyclerView(listOriginBill);
        ItemTouchHelper itemTouchHelperNewBill = new ItemTouchHelper(callbackNew);
        itemTouchHelperNewBill.attachToRecyclerView(listNewBill);
    }

    @Override
    public void onRefreshArrayProducts(ArrayList<Products> products) {

    }
}