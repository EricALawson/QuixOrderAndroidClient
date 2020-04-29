package com.example.quixorder.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quixorder.FormValidator;
import com.example.quixorder.R;
import com.example.quixorder.activity.TableActivity;
import com.example.quixorder.model.MenuItem;
import com.example.quixorder.model.Order;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
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
    private Button card;
    private Button cash;

    private double total;
    private String table;
    private String server;

    private List<MenuItem> check;
    private List<Integer> quantities;
    private List<DocumentReference> menuItemIDs;

    private Order order;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private CollectionReference orders = firestore.collection("orders");
    private CollectionReference payments = firestore.collection("Payment");

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
        card = v.findViewById(R.id.btn_checkout);
        cash = v.findViewById(R.id.btn_cash);

        //order = new Order();

        Bundle x = getArguments();
        check = (ArrayList<MenuItem>)x.getSerializable("check");
        quantities = (ArrayList<Integer>)x.getSerializable("qty");
        menuItemIDs = (ArrayList<DocumentReference>)x.getSerializable("ids");
        total = Double.valueOf(new DecimalFormat("#.##").format(x.getSerializable("total")));
        server = ((TableActivity)getActivity()).server;
        table = ((TableActivity)getActivity()).curr;

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClickListener", "card");

                if (FormValidator.validateCreditCard(cc.getText().toString())) {
                    if (FormValidator.validateCVC(cvc.getText().toString())) {
                        Toast.makeText(getContext(), "Order paid in credit", Toast.LENGTH_SHORT).show();
                        order = getOrder();
                        addOrder("Card");
                        
                        // Switch to menu fragment
                        Intent intent = new Intent(getActivity(), TableActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                        return;
                    }
                    Toast.makeText(getContext(), "Invalid cvc number", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "Invalid CC number", Toast.LENGTH_SHORT).show();
            }
        });

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("onClickListener", "cash");

                order = getOrder();
                addOrder("Cash");
                Toast.makeText(getContext(), "Order paid in cash", Toast.LENGTH_SHORT).show();

                // Switch to menu fragment
                Intent intent = new Intent(getActivity(), TableActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return v;
    }

    public Order getOrder() {
        ArrayList<DocumentReference> orderItems = new ArrayList<>();
        Date startTime = new Date();
        for (int i = 0; i < quantities.size(); i++) {
            for (int j = 0; j < quantities.get(i); j++) {
                orderItems.add(menuItemIDs.get(i));
            }
        }
        return new Order(orderItems, startTime, server, table);
    }

    public void addOrder(String type)
    {
        Log.d("Order", "Add Order");
        Map<String, Object> ord = new HashMap<>();
        ord.put("orderItems", order.getOrderItemIDs());
        ord.put("server", order.getServer());
        ord.put("table", order.getTable());
        ord.put("startTime", order.getStartTime());
        ord.put("servedTime", order.getServedTime());
        ord.put("cookedTime", order.getCookedTime());
        ord.put("status", order.getStatus());

        if(order != null)
        {
            orders.add(ord).addOnSuccessListener(task -> {
                addPayment(orders.document(task.getId()), type);
            })
            .addOnFailureListener(error -> {
                Log.e("error", error.getMessage());
            });
        }
    }

    public void addPayment(DocumentReference orderID, String type) {
        Log.d("Payment", "Add Payment");

        Map<String, Object> payment = new HashMap<>();
        payment.put("OrderID", orderID);
        payment.put("receivedTime", order.getStartTime());
        payment.put("total", total);
        payment.put("type", type);

        payments.add(payment);

    }
}