package com.example.quixorder.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quixorder.R;
import com.example.quixorder.activity.TableActivity;
import com.example.quixorder.model.MenuItem;
import com.example.quixorder.model.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText cc;
    private EditText cvc;
    private Button pay;
    private Button cash;
    private double total;

    private Order order;
    private CollectionReference orders = FirebaseFirestore.getInstance().collection("orders");

    public PaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_payment, container, false);
        //getActivity().getActionBar().setTitle("Payment");
        cc = v.findViewById(R.id.ccNum);
        cvc = v.findViewById(R.id.cvc);
        pay = v.findViewById(R.id.btn_checkout);
        cash = v.findViewById(R.id.btn_cash);

        //order = new Order();

        Bundle x = getArguments();
        List<MenuItem> check = (ArrayList<MenuItem>)x.getSerializable("check");
        //List<MenuItem> quantities = (ArrayList<MenuItem>)x.getSerializable("qty");

        total = 0;
        for(MenuItem item : check)
        {
            total+=item.getPrice();
        }


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("Pay", "Paid with card");
                if(cc.getText().length() == 16 && cc.getText().toString().matches("-?\\d+(\\.\\d+)?"))
                {
                    if(cvc.getText().length() == 3 && cvc.getText().toString().matches("-?\\d+(\\.\\d+)?"))
                    {
                        Toast.makeText(getContext(), "Order "+/*TableActivity.newOrder.getDocumentId()*/1+" has been paid", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), TableActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        //startActivity(new Intent((TableActivity)getActivity(), TableActivity.class));
                        //getActivity().finish();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Invalid cvc number", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getContext(), "Invalid CC number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Cash", "Paid with Cash, send order with cash attribute");
                  /*  HashMap<String, Object> check = new HashMap<>();
                    check.put("OrderID", order.getDocumentId());
                    check.put("receivedTime", new Date());
                    check.put("total", total);
                    check.put("type", order.getTable());*/
                Intent intent = new Intent(getContext(), TableActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                    //startActivity(new Intent((TableActivity)getActivity(), TableActivity.class));
                    //FirebaseFirestore.getInstance().collection("order_delays").add(check);
            }
        });

        return v;
    }

    /*
    cookedTime April 18, 2020 at 11:02:56 PM UTC-7 (timestamp)
    documentID 1
    orderItems 0 /menu_items/9UjLSl36gN6qXEtOiEff
    servedTime null
    server "server1"
    startTime March 10, 2020 at 9:35:00 AM UTC-7
    status "ready to serve"
    table "table 1"

     */

    public void addOrder(Order order)
    {
        Log.d("Order", "Add Order");
        Map<String, Object> ord = new HashMap<>();
        ord.put("orderItems", order);
        ord.put("server", order.getServer());
        ord.put("startTime", new Date());
        ord.put("servedTime", null);
        ord.put("status", "cooking");
        ord.put("table", order.getTable());

        if(order != null)
        {
            orders.add(ord);
        }
        /*orders.whereEqualTo("table", curr)
                .get()
                .addOnSuccessListener(task -> {
                    if (task.getDocuments().size() != 0) {
                        Toast.makeText(this, "Server will help soon", Toast.LENGTH_SHORT).show();
                    } else
                    {
                        fb.collection("table_calls").add(call);
                        Toast.makeText(this, "Server has been called", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(error -> {
                    Log.e("QueryFailed", error.getMessage());
                });*/
    }
}
