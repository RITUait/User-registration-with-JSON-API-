package com.example.ritu.database1app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ritu.database1app.R;
import com.example.ritu.database1app.db.SQLiteHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ritu on 12/28/2017.
 */

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<ContactModel> contactModels;
    private static LayoutInflater inflator = null;
    public Resources res;
    ContactModel tempValues = null;
    int i = 0;

    // CustomAdapter constructor
    public CustomAdapter(Activity a, ArrayList<ContactModel> d, Resources resLocal) {
        activity = a;
        contactModels = d;
        res = resLocal;
        inflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        if (contactModels.size() <= 0)
            return 1;
        return contactModels.size();

    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*** create a holder class to hold the inflated xml file elements **/
    public static class ViewHolder {
        public TextView text;
        public TextView text1;
        public TextView textWide;
        public ImageView image;
        public ImageButton imgBtnDelete, imgBtnEdit;
    }


    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        LayoutInflater inflater1 = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //LayoutInflater inflater1 = getLayoutInflater();
        View row = inflater1.inflate(R.layout.inflate_record, parent, false);
        ImageButton deleteButton = (ImageButton) row.findViewById(R.id.deleteBn);
        Log.e("custom", "see you !");


        /**
         -------------------
         Button deleteButton=(Button)vi.findViewById(R.id.deleteBn);
         deleteButton.setTag(position);
         deleteButton.setOnClickListener(new Button.OnClickListener(){
        @Override public void OnClick(View v){
        Integer index=(Integer) v.getTag();
        contactModels.remove(index.intValue());
        notifyDataSetChanged();

        }

        });

         **/

        if (convertView == null) {
            vi = inflator.inflate(R.layout.inflate_record, null);
            holder = new ViewHolder();
            holder.text = (TextView) vi.findViewById(R.id.tv_name);
            holder.text1 = (TextView) vi.findViewById(R.id.tv_email);
            holder.textWide = (TextView) vi.findViewById(R.id.tv_phone);
            holder.image = (ImageView) vi.findViewById(R.id.list_image);
            holder.imgBtnDelete = (ImageButton) vi.findViewById(R.id.deleteBn);
            holder.imgBtnEdit = (ImageButton) vi.findViewById(R.id.edit);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }

        if (contactModels.size() <= 0) {
            holder.text.setText("NO DATA");
        } else {
            tempValues = null;
            tempValues = (ContactModel) contactModels.get(position);
            holder.text.setText(tempValues.getName());
            holder.text1.setText(tempValues.getEmail());
            holder.textWide.setText(tempValues.getPhone());
            Picasso.with(activity).load(tempValues.getImageURL()).into(holder.image);
        }


        holder.imgBtnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("Customdelete", "doing");
                ///
                ContactModel contactModel = contactModels.get(position);
                new SQLiteHelper(activity).deleteRecord(contactModel);
                contactModels.remove(position);
                notifyDataSetChanged();

            }
        });
        holder.imgBtnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("Customdelete", "doing");
                Intent intent = new Intent(activity, GetInfoActivity.class);
                intent.putExtra("update",true);
                intent.putExtra("contact_id", contactModels.get(position).getID());
                activity.startActivity(intent);
            }
        });


        return vi;
    }


}
