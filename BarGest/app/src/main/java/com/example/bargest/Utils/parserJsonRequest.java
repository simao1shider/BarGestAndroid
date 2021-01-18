package com.example.bargest.Utils;



//For parse string to dateTime

import com.example.bargest.Models.ListRequests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

        import java.util.ArrayList;

public class parserJsonRequest {
    public static ArrayList<ListRequests> parserJsonListRequest(JSONArray json){
        ArrayList<ListRequests> requests = new ArrayList<>();
        try {
            for(int i=0; i < json.length(); i++){
                JSONObject request = (JSONObject) json.get(i);
                int id = request.getInt("id");
                int status = request.getInt("status");
                int number = request.getInt("table_number");
                String dateTime = request.getString("dateTime");
                requests.add(new ListRequests(id,status,number,dateTime));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        return requests;
    }
}
