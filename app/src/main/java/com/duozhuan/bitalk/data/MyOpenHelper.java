package com.duozhuan.bitalk.data;

import android.content.Context;

import com.duozhuan.bitalk.data.database.dao.DaoMaster;

public class MyOpenHelper extends DaoMaster.OpenHelper{

    public MyOpenHelper(Context context, String name){
        super(context,name);
    }
}