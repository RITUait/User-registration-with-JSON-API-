package com.example.ritu.database1app.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.ritu.database1app.R;
import com.example.ritu.database1app.db.SQLiteHelper;

public class GetInfoActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone;
    private SQLiteHelper db;
    private boolean isUpdate;
    private int contactId;
    private ContactModel contactModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_manipulation);
        etName = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPhone = (EditText) findViewById(R.id.et_phone);
        db = new SQLiteHelper(this);
        try{
            Intent intent = getIntent();
            isUpdate = intent.getBooleanExtra("update", false);
            contactId = intent.getIntExtra("contact_id", 0);
            contactModel = db.getContactFromId(contactId);
            contactModel.setName(etName.getText().toString());
            contactModel.setEmail(etEmail.getText().toString());
            contactModel.setPhone(etPhone.getText().toString());
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    public void saveDb(View view){
        ContactModel contactModel = new ContactModel();
        contactModel.setName(etName.getText().toString());
        contactModel.setEmail(etEmail.getText().toString());
        contactModel.setPhone(etPhone.getText().toString());
        db.insertRecord(contactModel);
        Toast.makeText(getApplicationContext()," Database saved ",Toast.LENGTH_LONG).show();
        Log.e("GetInfoActivity"," Q "+ "Data saved");
        finish();

    }

   /** public void delete(View view){
        ContactModel contactModel=new ContactModel();
        contactModel.getID();
        db.deleteRecord(contactModel);
        Log.e("GetInfoDelete","deleting"+"Yoo");
    }
    **/
}
