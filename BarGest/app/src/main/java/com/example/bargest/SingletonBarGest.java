package com.example.bargest;

import android.content.Context;

import com.example.bargest.Models.Requests;
import com.example.bargest.Models.Tables;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SingletonBarGest {

    private static SingletonBarGest INSTANCE = null;

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

        return arrayList;
    }
    public ArrayList<Requests> genereteFakeRequestList(){
        ArrayList<Requests> arrayList = new ArrayList<>();

        arrayList.add(new Requests(1,0));
        arrayList.add(new Requests(1,1));
        arrayList.add(new Requests(1,2));
        arrayList.add(new Requests(1,1));
        arrayList.add(new Requests(1,0));
        arrayList.add(new Requests(1,2));
        arrayList.add(new Requests(1,2));
        arrayList.add(new Requests(1,0));
        arrayList.add(new Requests(1,0));
        arrayList.add(new Requests(1,0));


        return arrayList;
    }
}
