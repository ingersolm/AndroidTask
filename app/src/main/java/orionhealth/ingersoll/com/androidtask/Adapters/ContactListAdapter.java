package orionhealth.ingersoll.com.androidtask.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import orionhealth.ingersoll.com.androidtask.Model.Contacts;
import orionhealth.ingersoll.com.androidtask.R;

/**
 * Created by Ingersoll Mohan on 30/6/16.
 */
public class ContactListAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Contacts> mContactsList;
    private LayoutInflater layoutInflater;

    public ContactListAdapter(Context context, ArrayList<Contacts> contactList){
        this.mContext = context;
        this.mContactsList = contactList;
    }

    @Override
    public int getCount() {
        return mContactsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mContactsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.contacts_list_item, null);
            viewHolder.rlContactItems = (RelativeLayout)convertView.findViewById(R.id.rl_contact_item);
            viewHolder.tvContactName = (TextView)convertView.findViewById(R.id.tv_contact_name);
            viewHolder.tvEmail = (TextView)convertView.findViewById(R.id.tv_contact_email);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.tvContactName.setText(mContactsList.get(position).getName().toString());
        viewHolder.tvEmail.setText(mContactsList.get(position).getEmail().toString());

        return convertView;
    }


    class ViewHolder{
        RelativeLayout rlContactItems;
        TextView tvContactName;
        TextView tvEmail;
    }
}
