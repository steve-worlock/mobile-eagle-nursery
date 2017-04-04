package com.capco.e1090384.eagleibank;



        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

/**
 * Created by Belal on 9/22/2015.
 */

public class CustomList extends ArrayAdapter<String> {
    private String[] ids;
    private String[] custrefs;
    private String[] trandates;
    private String[] tranamounts;
    private Activity context;


    public CustomList(Activity context, String[] ids, String[] custrefs, String[] trandates,String[] tranamounts) {
        super(context, R.layout.list_view_layout, ids);
        this.context = context;
        this.ids = ids;
        this.custrefs = custrefs;
        this.trandates = trandates;
        this.tranamounts = tranamounts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View thebalance = inflater.inflate(R.layout.list_view_layout, null, true);
        TextView textViewId = (TextView) thebalance.findViewById(R.id.textViewId);
        TextView textViewCustref = (TextView) thebalance.findViewById(R.id.textViewCustref);
        TextView textViewTrandate = (TextView) thebalance.findViewById(R.id.textViewTrandate);
        TextView textViewTranamount =(TextView) thebalance.findViewById(R.id.textViewTranamount);

        textViewId.setText(ids[position]);
        textViewCustref.setText(custrefs[position]);
        textViewTrandate.setText(trandates[position]);
        textViewTranamount.setText(tranamounts[position]);
        return thebalance;
    }
}