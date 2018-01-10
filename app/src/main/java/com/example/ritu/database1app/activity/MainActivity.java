package com.example.ritu.database1app.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ritu.database1app.R;
import com.example.ritu.database1app.db.SQLiteHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private SQLiteHelper db;
    private ListView list;
    CustomAdapter adapter;
    public MainActivity mainActivity = null;
    public ArrayList<ContactModel> contactModels = new ArrayList<ContactModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mainActivity = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        db = new SQLiteHelper(this);
        list = (ListView) findViewById(R.id.mainListView);
        new fetchData().execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetInfoActivity.class);
                startActivity(intent);
                Log.e("MainActivity", "Qqqqqqq");
            }

        });
        Log.e("MainA", "hello");
        //ArrayList<ContactModel> contactModels = new ArrayList<ContactModel>();
//        contactModels = db.getAllRecords();

        //ArrayList<ContactModel> contactModels = db.getAllRecords();
        for (ContactModel n : contactModels) {
            Log.e("MainB", "bye" + n);
            // System.out.println(n);
        }

        Resources res = getResources();
        adapter = new CustomAdapter(mainActivity, contactModels, res);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private class fetchData extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            UIUtil.startProgressDialog(MainActivity.this, "Downloading data....");
        }

        @Override
        protected String doInBackground(Void... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try {

                URL url = new URL("https://api.myjson.com/bins/cj2br");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                int lengthOfFile = urlConnection.getContentLength();
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.e("Json1", forecastJsonStr);

                JSONArray jsonArray = new JSONArray(forecastJsonStr);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ContactModel contactModel = new ContactModel();
                    contactModel.setID(jsonObject.getString("id"));
                    contactModel.setName(jsonObject.getString("name"));
                    contactModel.setPhone(jsonObject.getString("mobile"));
                    contactModel.setImageURL(jsonObject.getString("profile_imge"));
                    contactModels.add(contactModel);
                }

                return forecastJsonStr;
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return forecastJsonStr;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.notifyDataSetChanged();
            UIUtil.stopProgressDialog(MainActivity.this);
        }
    }

    public void onItemClick(int mPosition) {
        ContactModel tempValues = (ContactModel) contactModels.get(mPosition);
        Toast.makeText(mainActivity, "" + tempValues.getName() + "email:" + tempValues.getEmail() + "phone:" + tempValues.getPhone(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
