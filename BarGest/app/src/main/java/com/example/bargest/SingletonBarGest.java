package com.example.bargest;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bargest.Models.Bills;
import com.example.bargest.Models.Requests;
import com.example.bargest.Models.Tables;

import java.util.ArrayList;

public class SingletonBarGest {

    private static SingletonBarGest INSTANCE = null;

    ArrayList<Bills> bills;

    public static synchronized SingletonBarGest getInstance(Context context) {
        if(INSTANCE == null)
        {
            INSTANCE = new SingletonBarGest(context);
        }


        return INSTANCE;
    }

    private SingletonBarGest(Context context) {
    }

    public ArrayList<Tables> genereteFakeTableList(){
        ArrayList<Tables> arrayList = new ArrayList<>();

        arrayList.add(new Tables(1,false,0));
        arrayList.add(new Tables(2,false,0));
        arrayList.add(new Tables(3,false,0));
        arrayList.add(new Tables(4,true,0));
        arrayList.add(new Tables(5,false,0));
        arrayList.add(new Tables(6,false,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));
        arrayList.add(new Tables(7,true,0));

        return arrayList;
    }
    public ArrayList<Requests> genereteFakeRequestList(){
        ArrayList<Requests> arrayList = new ArrayList<>();

        arrayList.add(new Requests(1,0));
        arrayList.add(new Requests(2,1));
        arrayList.add(new Requests(3,2));
        arrayList.add(new Requests(4,1));
        arrayList.add(new Requests(5,0));
        arrayList.add(new Requests(6,2));
        arrayList.add(new Requests(7,2));
        arrayList.add(new Requests(8,0));
        arrayList.add(new Requests(9,0));
        arrayList.add(new Requests(10,0));


        return arrayList;
    }

    public ArrayList<Bills> generateFakeDetailsBills(){
        bills = new ArrayList<>();

        bills.add(new Bills("Asd",(float) 3.5,2));
        bills.add(new Bills("dd",(float) 2.5,2));
        bills.add(new Bills("oo",(float) 1.2,4));
        bills.add(new Bills("Asd",(float) 10,2));
        bills.add(new Bills("asdf",(float) 3.5,7));
        bills.add(new Bills("fdfff",(float) 3.5,8));
        bills.add(new Bills("vv",(float) 3.5,1));
        bills.add(new Bills("ser",(float) 3.5,2));
        bills.add(new Bills("dffv",(float) 3.5,6));
        bills.add(new Bills("ssa",(float) 3.5,5));
        bills.add(new Bills("ssd",(float) 3.5,9));
        bills.add(new Bills("ddd",(float) 3.5,3));
        bills.add(new Bills("Assasd",(float) 3.5,2));

        return bills;

    }

    public float getTotalBills(){
        float total = 0;
        for (Bills bill:bills) {
            total+=bill.getPrice()*bill.getQuantidade();
        }

        return total;
    }

}
