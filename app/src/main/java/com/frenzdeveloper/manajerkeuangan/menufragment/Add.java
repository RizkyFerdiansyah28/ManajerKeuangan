package com.frenzdeveloper.manajerkeuangan.menufragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.frenzdeveloper.manajerkeuangan.AddBalance;
import com.frenzdeveloper.manajerkeuangan.AddReportActivity;
import com.frenzdeveloper.manajerkeuangan.R;
import com.frenzdeveloper.manajerkeuangan.database.AppDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppDatabase database;

    Button btnAddIncome, btnAddOutcome, btnBalance, btnAddUid;

    public Add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add.
     */
    // TODO: Rename and change types and number of parameters
    public static Add newInstance(String param1, String param2) {
        Add fragment = new Add();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        database = AppDatabase.getInstance(getContext());

        Toolbar toolbar = v.findViewById(R.id.custom_actionbar_add);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Tambah");


        btnAddIncome = v.findViewById(R.id.btnAddIncome);
        btnAddIncome.setOnClickListener(v1 -> addIncomeOutcome(true, false));

        btnAddOutcome = v.findViewById(R.id.btnAddOutcome);
        btnAddOutcome.setOnClickListener(v12 -> addIncomeOutcome(false, true));

        btnBalance = v.findViewById(R.id.btnBalance);
        btnBalance.setOnClickListener(v13 -> {
            try {
                addBalanceIntent();
            }catch (Exception e){
                Toast.makeText(getContext(), e.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        String fullname = "Ahmadin";String email = "ahmadinlaisa04@gmail.com";

        btnAddUid = v.findViewById(R.id.generateUserId);
        btnAddUid.setVisibility(View.GONE);
//        btnAddUid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    int i = 0;
//                    if (database.userDao().insert(fullname, email) > 0){
//                        while (database.userDao().getAll().size() > 0){
//                            Toast.makeText(getContext(), database.userDao().getAll().get(i++).uid+"", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }catch (Exception e){
//                    Toast.makeText(getActivity(), e.getMessage()+"", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        return v;
    }

    private void addIncomeOutcome(boolean income, boolean outcome){
        Intent i = new Intent(getActivity(), AddReportActivity.class);
        i.putExtra("income", income);
        i.putExtra("outcome", outcome);
        startActivity(i);
    }

    void addBalanceIntent(){
        Intent in = new Intent(getActivity(), AddBalance.class);
        startActivity(in);

    }
}