package orionhealth.ingersoll.com.androidtask.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import orionhealth.ingersoll.com.androidtask.Adapters.ContactListAdapter;
import orionhealth.ingersoll.com.androidtask.Model.Address;
import orionhealth.ingersoll.com.androidtask.Model.Company;
import orionhealth.ingersoll.com.androidtask.Model.Contacts;
import orionhealth.ingersoll.com.androidtask.Model.Geo;
import orionhealth.ingersoll.com.androidtask.R;
import orionhealth.ingersoll.com.androidtask.Utils.Utility;

/**
 * Created by IngersollMohan on 30/6/16.
 */

public class ContactsListActivity extends AppCompatActivity{

    private int connTimeout = 100000;
    private ContactListAdapter contactAdapter;
    private ArrayList<Contacts> mContactsList;
    private ListView listView;
    private ProgressDialog proDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.label_contacts));
        setSupportActionBar(toolbar);

        listView = (ListView)findViewById(R.id.lv_contact_list);

        if(Utility.isConnected(ContactsListActivity.this) || Utility.isWifiConnected(ContactsListActivity.this)
                || Utility.isMobileConnected(ContactsListActivity.this)){

            proDialog = new ProgressDialog(ContactsListActivity.this);
            proDialog.setMessage("Loading Contacts...");
            proDialog.setCancelable(false);
            proDialog.show();
            new NetworkConnection().execute();
        }else{
            Utility.ShowDialogOneButton(ContactsListActivity.this,
                    getResources().getString(R.string.error_network),
                    getResources().getString(R.string.error_network_title));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent contactIntent = new Intent(ContactsListActivity.this,
                        ContactDetailActivity.class);
                Contacts cont = (Contacts) mContactsList.get(position);
                contactIntent.putExtra("conObje", cont);
                startActivity(contactIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_az) {
            if(mContactsList !=null) {
                Collections.sort(mContactsList, new SortAToZ());
                contactAdapter = new ContactListAdapter(ContactsListActivity.this, mContactsList);
                listView.setAdapter(contactAdapter);
            }
            return true;
        }

        if (id == R.id.action_sort_za) {
            if(mContactsList !=null) {
                Collections.sort(mContactsList, new SortZToA());
                contactAdapter = new ContactListAdapter(ContactsListActivity.this, mContactsList);
                listView.setAdapter(contactAdapter);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class NetworkConnection extends AsyncTask<String, Void, String>{

        String response = null;

        String contactUrl = "http://jsonplaceholder.typicode.com/users";

        @Override
        protected String doInBackground(String... params) {

            try{
                URL url = new URL(contactUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-length", "0");
                conn.setUseCaches(false);
                conn.setAllowUserInteraction(false);
                conn.setConnectTimeout(connTimeout);
                conn.setReadTimeout(connTimeout);
                conn.connect();
                int status = conn.getResponseCode();

                if(status == 200)
                {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        response = sb.toString();

                        mContactsList = new ArrayList<Contacts>();
                        JSONArray jsonArray = new JSONArray(response);

                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Contacts contacts = new Contacts();

                            contacts.setId(jsonObject.getInt("id"));
                            contacts.setName(jsonObject.getString("name"));
                            contacts.setUsername(jsonObject.getString("username"));
                            contacts.setEmail(jsonObject.getString("email"));
                            contacts.setPhone(jsonObject.getString("phone"));
                            contacts.setWebsite(jsonObject.getString("website"));


                            Address address = new Address();
                            JSONObject addObj = jsonObject.getJSONObject("address");
                            address.setStreet(addObj.getString("street"));
                            address.setCity(addObj.getString("city"));
                            address.setSuite(addObj.getString("suite"));
                            address.setZipcode(addObj.getString("zipcode"));
                            contacts.setAddress(address);

                            Geo geo = new Geo();
                            JSONObject geoObject = addObj.getJSONObject("geo");
                            geo.setLat(geoObject.getString("lat"));
                            geo.setLng(geoObject.getString("lng"));
                            address.setGeo(geo);

                            Company company = new Company();
                            JSONObject compObject = jsonObject.getJSONObject("company");
                            company.setName(compObject.getString("name"));
                            company.setCatchPhrase(compObject.getString("catchPhrase"));
                            company.setBs(compObject.getString("bs"));
                            contacts.setCompany(company);

                            mContactsList.add(contacts);

                        }

                }else{
                    Utility.ShowDialogOneButton(ContactsListActivity.this, getResources().getString(R.string.error_connection),
                            getResources().getString(R.string.error_connection_title));
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(proDialog.isShowing()){
                proDialog.dismiss();
            }
            contactAdapter = new ContactListAdapter(ContactsListActivity.this, mContactsList);
            listView.setAdapter(contactAdapter);
        }
    }

    public class SortAToZ implements Comparator<Contacts> {
        @Override
        public int compare(Contacts o1, Contacts o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    public class SortZToA implements Comparator<Contacts> {
        @Override
        public int compare(Contacts o1, Contacts o2) {
            return o2.getName().compareTo(o1.getName());
        }
    }
}
