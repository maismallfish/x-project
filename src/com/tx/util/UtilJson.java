package com.tx.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.example.login.database.DBHelper;

public class UtilJson {
	public static InputStream String2InputStream(String str){ 
		   ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes()); 
		   return stream; 
		} 
	public static void readJsonStream(String in, Context context) throws IOException {
		//InputStreamReader isr = new InputStreamReader(in, "UTF-8");
	     JSONArray jsonArr = null;
		
	     JSONObject oj = null;
	     try
		{
	    	 oj = new JSONObject(in);
		}
	     catch(Exception e)
	     {
	    	 
	     }
		
		String id = null;
	     String text = null;
	     
		 String value = oj.optString("id");
		 if(!value.isEmpty())
		 {
			 id = value;
		 }
		 value = oj.optString("nickname");
		 if(!value.isEmpty())
		 {
			 text = value;
		 }
	     
	   //这里值我们需要保存起来，下次登录的时候去取出来
	     String strPasswd = null;
	     DBHelper dbHelper = new DBHelper(context);
	     dbHelper.insertOrUpdate(text, id, strPasswd, 1);
	   }
}
