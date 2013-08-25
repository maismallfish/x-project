package com.example.login.activity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.x_project.R;
 
public class ItemListBaseAdapter extends BaseAdapter {
 private static ArrayList<ItemDetails> itemDetailsrrayList;
  
 private Integer[] imgid = {
   R.drawable.p1,
   };
 
 //	对外接口
 public void SetHeadImgArray(String[] strlstHead)
 {
	 //未实现
 }
  
 private LayoutInflater l_Inflater;
 
 public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
  itemDetailsrrayList = results;
  l_Inflater = LayoutInflater.from(context);
 }
 
 public int getCount() {
  return itemDetailsrrayList.size();
 }
 
 public Object getItem(int position) {
  return itemDetailsrrayList.get(position);
 }
 
 public long getItemId(int position) {
  return position;
 }
 
 public View getView(int position, View convertView, ViewGroup parent) {
  ViewHolder holder;
  if (convertView == null) {
   convertView = l_Inflater.inflate(R.layout.activity_topic, null);
   holder = new ViewHolder();
//   holder.txt_playerCnt = (TextView) convertView.findViewById(R.id.playerCnt);
//   holder.txt_itemContent = (TextView) convertView.findViewById(R.id.content);
//   holder.txt_itemTime = (TextView) convertView.findViewById(R.id.time);
//   holder.itemHead = (ImageView) convertView.findViewById(R.id.head);
 
   convertView.setTag(holder);
  } else {
   holder = (ViewHolder) convertView.getTag();
  }
   
  holder.txt_playerCnt.setText(itemDetailsrrayList.get(position).getName());
  holder.txt_itemContent.setText(itemDetailsrrayList.get(position).getItemDescription());
  holder.txt_itemTime.setText(itemDetailsrrayList.get(position).getPrice());
  holder.itemHead.setImageResource(imgid[itemDetailsrrayList.get(position).getImageNumber() - 1]);
 
  return convertView;
 }
 
 static class ViewHolder {
  TextView txt_playerCnt;
  TextView txt_itemContent;
  TextView txt_itemTime;
  ImageView itemHead;
 }
}
