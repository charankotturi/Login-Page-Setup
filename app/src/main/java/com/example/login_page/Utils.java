package com.example.login_page;

import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class Utils {
    
    public static ArrayList<UserModel> dataItems;
    
    public static void initArrayList(){
        //dataItems = Utils.getDataItems();
        if(dataItems == null){
            dataItems = new ArrayList<>();
        }
    }
    
    public static void deleteListItem(int ID){
        ArrayList<UserModel> allItems = Utils.getDataItems();
        Log.d(TAG, "deleteListItem: >>>>>>>>>>>");
        System.out.println(allItems);
        if(allItems != null){
            UserModel Item = new UserModel();
            for(UserModel m: allItems){
                if(m.getId() == ID){
                    Item = m;
                }
            }
            allItems.remove(Item);
            Log.d(TAG, "deleteListItem: this is next step>>>>>>>>>>>");
            Utils.setDataItems(allItems);
            System.out.println(allItems);
        }
    }

    public static ArrayList<UserModel> getDataItems() {
        return dataItems;
    }

    public static void setDataItems(ArrayList<UserModel> dataItems) {
        Utils.dataItems = dataItems;
    }
    
}
