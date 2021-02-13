package com.example.bargest.Views.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.example.bargest.Adaptars.DivideAdaptar;
import com.example.bargest.Listeners.ProductsListener;
import com.example.bargest.Models.Products;
import com.example.bargest.R;
import com.example.bargest.SingletonBarGest;
import com.example.bargest.Views.LoginActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.content.Context.MODE_PRIVATE;

public class DivideBillFragment extends Fragment implements ProductsListener {

    RecyclerView listOriginBill;
    RecyclerView listNewBill;
    Dialog dialog;
    TextView TVTotal;
    TextView TVTollbarTitleview;
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
        SharedPreferences prefs = getActivity().getSharedPreferences("Pref", MODE_PRIVATE);
        if(prefs.getString("token","")==""){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
        dialog = new Dialog(getContext());
        listOriginBill = view.findViewById(R.id.listOriginBill);
        listNewBill = view.findViewById(R.id.listNewnBill);
        TVTotal = view.findViewById(R.id.TVtotalNewPrice);
        TVTollbarTitleview = view.findViewById(R.id.TVTollbarTitle);
        view.findViewById(R.id.BtnToolbarAdd).setVisibility(View.GONE);
        TVTotal.setText("0 €");
        listOriginBill.setLayoutManager(new LinearLayoutManager(getContext()));
        listNewBill.setLayoutManager(new LinearLayoutManager(getContext()));

        SingletonBarGest.getInstance(getContext()).getAccountProductsDivBill(getContext(), getArguments().getInt("account_id"));
        SingletonBarGest.getInstance(getContext()).setProductListener(this);
        NewProducts = new ArrayList<>();

        ItemTouchHelper itemTouchHelperNewBill = new ItemTouchHelper(callbackNew);

        itemTouchHelperNewBill.attachToRecyclerView(listNewBill);
        view.findViewById(R.id.BtnConfirmationNewBill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogPay(getArguments().getInt("account_id"));
            }
        });
        TVTollbarTitleview.setText("Conta: " + getArguments().getString("account_name"));
        view.findViewById(R.id.IMGBackFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("account_id",getArguments().getInt("account_id"));
                bundle.putString("account_name",getArguments().getString("account_name"));
                BillsDetailsFragment billsDetailsFragment = new BillsDetailsFragment();
                billsDetailsFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container, billsDetailsFragment).addToBackStack("BillsDetails").commit();
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
            adaptersNew.notifyDataSetChanged();
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
            adaptersOrigin.notifyDataSetChanged();
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

                        for(Products prod : NewProducts){
                            if(prod.getId() == originProduct.getId()){
                                Originproducts.remove(position);

                                prod.setQuantity(prod.getQuantity()+quantity);
                                total();
                                adaptersOrigin.notifyDataSetChanged();
                                adaptersNew.notifyDataSetChanged();
                                dismissdialog();
                                return;
                            }
                        }
                        Originproducts.remove(position);

                        NewProducts.add(originProduct);
                        total();
                        dismissdialog();
                    }
                    else if(originProduct.getQuantity() > quantity){

                        for(Products prod : NewProducts){
                            if(prod.getId() == originProduct.getId()){

                                originProduct.setQuantity(originProduct.getQuantity() - quantity);
                                Products pro = new Products(originProduct.getId(), originProduct.getName(), originProduct.getPrice(), quantity, originProduct.getCategory_id());
                                prod.setQuantity(prod.getQuantity()+quantity);
                                total();
                                TVTotal.setText(""+Total+"€");
                                dismissdialog();
                                return;
                            }
                        }
                        originProduct.setQuantity(originProduct.getQuantity() - quantity);
                        Products pro = new Products(originProduct.getId(), originProduct.getName(), originProduct.getPrice(), quantity, originProduct.getCategory_id());
                        NewProducts.add(pro);
                        total();
                        dismissdialog();
                    }
                    else{
                        Toast.makeText(getContext(), "Erro! Quantidade inserida maior que a quantidade do produto.", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    EditText etquantity = (EditText)dialog.findViewById(R.id.quantity);
                    int quantity = Integer.parseInt(etquantity.getText().toString());
                    final int position = viewHolder.getAdapterPosition();
                    newProduct = NewProducts.get(position);

                    if(newProduct.getQuantity() == quantity) {

                        for(Products prod : Originproducts){
                            if(prod.getId() == newProduct.getId()){
                                NewProducts.remove(position);

                                prod.setQuantity(prod.getQuantity()+quantity);
                                total();
                                dismissdialog();
                                return;
                            }
                        }
                        NewProducts.remove(position);
                        Originproducts.add(newProduct);
                        total();
                        dismissdialog();
                    }
                    else if(newProduct.getQuantity() > quantity){
                        for(Products prod : Originproducts){
                            if(prod.getId() == newProduct.getId()){
                                newProduct.setQuantity(newProduct.getQuantity() - quantity);
                                prod.setQuantity(prod.getQuantity()+quantity);
                                total();
                                dismissdialog();
                                return;
                            }
                        }
                        newProduct.setQuantity(newProduct.getQuantity() - quantity);
                        Products pro = new Products(newProduct.getId(), newProduct.getName(), newProduct.getPrice(), quantity, newProduct.getCategory_id());
                        Originproducts.add(pro);
                        total();
                        dismissdialog();
                    }
                    else{
                        Toast.makeText(getContext(), "Erro! Quantidade inserida maior que a quantidade do produto.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        dialog.show();
    }

    private void openDialogPay(final int account_id) {
        if(NewProducts.isEmpty()){
            Toast.makeText(getContext(), "Não existem produtos na lista de produtos a pagar!", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setContentView(R.layout.dialog_pay);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView close = dialog.findViewById(R.id.BtnClosePayDialog);
        Button addNif = dialog.findViewById(R.id.BtnAddNif);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addNif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nif = dialog.findViewById(R.id.quantity);
                SingletonBarGest.getInstance(getContext()).paywithsplit(getContext(), account_id, Integer.parseInt(nif.getText().toString()), NewProducts, getFragmentManager());
                dismissdialog();
            }
        });

        dialog.show();
    }

    public void total(){
        Total = 0;
        for (int i = 0; i < NewProducts.size(); i++){
            Total += NewProducts.get(i).getPrice()*NewProducts.get(i).getQuantity();
        }
        BigDecimal bigDecimal = new BigDecimal(Float.toString(Total));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        TVTotal.setText(bigDecimal.floatValue()+"€");
    }

    public void dismissdialog(){
        adaptersOrigin.notifyDataSetChanged();
        adaptersNew.notifyDataSetChanged();
        dialog.dismiss();
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