package orionhealth.ingersoll.com.androidtask.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import orionhealth.ingersoll.com.androidtask.Model.Address;
import orionhealth.ingersoll.com.androidtask.Model.Company;
import orionhealth.ingersoll.com.androidtask.Model.Contacts;
import orionhealth.ingersoll.com.androidtask.Model.Geo;
import orionhealth.ingersoll.com.androidtask.R;

/**
 * Created by Ingersoll mohan on 1/7/16.
 */
public class ContactDetailActivity extends AppCompatActivity {


    private TextView tvUserName;
    private TextView tvPhoneNum;
    private TextView tvAddress;
    private TextView tvWebsite;
    private TextView tvCompName;
    private TextView tvCatchPhrase;
    private TextView tvBs;

    private Contacts contacts;
    private Bundle bundle;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String street;
    private String suite;
    private String city;
    private String zipCode;
    private Geo geo;
    private String lat;
    private String lng;
    private String phone;
    private String website;
    private Company company;
    private String compName;
    private String compCatchPhrase;
    private String  compBs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_conatct_detail);

            bundle = getIntent().getExtras();
            if(bundle != null){
                contacts = (Contacts) bundle.getSerializable("conObje");

                name = contacts.getName().toString();
                username = contacts.getUsername().toString();
                email = contacts.getEmail().toString();

                address = contacts.getAddress();
                street = address.getStreet().toString();
                suite = address.getSuite().toString();
                city = address.getCity().toString();
                zipCode = address.getZipcode().toString();

                geo = address.getGeo();
                lat = geo.getLat().toString();
                lng = geo.getLng().toString();

                phone = contacts.getPhone().toString();
                website = contacts.getWebsite().toString();

                company = contacts.getCompany();

                compName = company.getName().toString();
                compCatchPhrase = company.getCatchPhrase().toString();
                compBs = company.getBs().toString();
            }

            tvUserName = (TextView)findViewById(R.id.tv_username);
            tvPhoneNum = (TextView)findViewById(R.id.tv_phonenumber);

            tvAddress = (TextView)findViewById(R.id.tv_address);
            tvWebsite = (TextView)findViewById(R.id.tv_website);

            tvCompName = (TextView)findViewById(R.id.tv_comp_name);
            tvCatchPhrase = (TextView)findViewById(R.id.tv_comp_catch);

            tvBs = (TextView)findViewById(R.id.tv_comp_bs);

            Toolbar toolbar = (Toolbar) findViewById(R.id.details_toolbar);
            toolbar.setTitle(name);
            setSupportActionBar(toolbar);

            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        initView();
    }

    public void initView(){
        if(username != null){
            tvUserName.setText(username);
        }
        if(phone != null) {
            tvPhoneNum.setText(phone);
        }
        if(suite != null || street != null || city != null || zipCode != null){
            tvAddress.setText(suite+", "+street+","+"\n"+city+", "+zipCode);
        }
        if(website != null) {
            tvWebsite.setText(website);
        }
        if(compName != null) {
            tvCompName.setText(compName);
        }
        if(compCatchPhrase != null){
            tvCatchPhrase.setText(compCatchPhrase);
        }
        if(compBs != null){
            tvBs.setText(compBs);
        }
    }
}