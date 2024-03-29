package com.example.mc_bitf17a040_a1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mc_bitf17a040_a1.classes.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter implements Filterable {
    private Context context;
    private ArrayList<Order> orders;
    private ArrayList<Order> filteredData;
    private ArrayList<Order> allOrders;
    private ArrayList<Order> selectedOrders;

    public ListAdapter(Context context, ArrayList<Order> orders, ArrayList<Order> selectedOrders, ArrayList<Order> allOrders) {
        super(context, R.layout.list_item, orders);
        this.context = context;
        this.orders = orders;
        this.selectedOrders = selectedOrders;
        this.allOrders = allOrders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View listItem = inflater.inflate(R.layout.list_item, null);

        Order order = orders.get(position);

        ((TextView)listItem.findViewById(R.id.lblItemName)).setText(order.getItemName());

        ((TextView)listItem.findViewById(R.id.lblFirstName)).setText(order.getPersonalDetails().getFirstName());
        ((TextView)listItem.findViewById(R.id.lblLastName)).setText(order.getPersonalDetails().getLastName());
        ((TextView)listItem.findViewById(R.id.lblEmail)).setText(order.getPersonalDetails().getEmail());
        ((TextView)listItem.findViewById(R.id.lblContact)).setText(order.getPersonalDetails().getContact());

        ((TextView)listItem.findViewById(R.id.lblCompanyName)).setText(order.getCompanyDetails().getCompanyName());
        ((TextView)listItem.findViewById(R.id.lblZip)).setText(order.getCompanyDetails().getZip());
        ((TextView)listItem.findViewById(R.id.lblState)).setText(order.getCompanyDetails().getState());
        ((TextView)listItem.findViewById(R.id.lblCity)).setText(order.getCompanyDetails().getCity());
        ((TextView)listItem.findViewById(R.id.lblBoxes)).setText(order.getCompanyDetails().getNoOfBoxes());

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        ((TextView)listItem.findViewById(R.id.lblOrderTime)).setText(format.format(order.getDateOfCreation()));

        if(selectedOrders.contains(order)) {
            listItem.setBackgroundColor(context.getResources().getColor(R.color.colorSelect));
        }
        else {
            listItem.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        }

        return listItem;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence nameSubStr) {

            FilterResults results = new FilterResults();

            if(filteredData == null)
                filteredData = new ArrayList<>();
            filteredData.clear();

            for(int i = 0; i < allOrders.size(); i++) {
                filteredData.add(allOrders.get(i));
            }

            if (nameSubStr != null && nameSubStr.length() > 0) {

                ArrayList<Order> filterList = new ArrayList<Order>();
                for (int i = 0; i < filteredData.size(); i++) {

                    String firstName = filteredData.get(i).getPersonalDetails().getFirstName().toUpperCase();
                    String lastName = filteredData.get(i).getPersonalDetails().getLastName().toUpperCase();
                    String state = filteredData.get(i).getCompanyDetails().getState().toUpperCase();
                    String city = filteredData.get(i).getCompanyDetails().getCity().toUpperCase();

                    nameSubStr = nameSubStr.toString().toUpperCase();

                    boolean expression = firstName.contains(nameSubStr)
                            || lastName.contains(nameSubStr)
                            || state.contains(nameSubStr)
                            || city.contains(nameSubStr);

                    if (expression) {

                        Order s = new Order(filteredData.get(i));
                        filterList.add(s);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            }
            else {
                results.count = allOrders.size();
                results.values = allOrders;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            orders = (ArrayList<Order>)results.values;
        }
    };

}

