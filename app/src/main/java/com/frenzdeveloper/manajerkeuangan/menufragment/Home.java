package com.frenzdeveloper.manajerkeuangan.menufragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.frenzdeveloper.manajerkeuangan.R;
import com.frenzdeveloper.manajerkeuangan.adapter.ReportAdapter;
import com.frenzdeveloper.manajerkeuangan.database.AppDatabase;
import com.frenzdeveloper.manajerkeuangan.database.entity.Report;
import com.frenzdeveloper.manajerkeuangan.database.entity.User;
import com.frenzdeveloper.manajerkeuangan.sp_agent.SPAgent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tvBalance, tvUsername;
    ListView lv;
    AlertDialog.Builder dialog;
    AppDatabase database;
    List<Report> list = new ArrayList<>();
    List<User> listUser = new ArrayList<>();
    ReportAdapter adapter;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        // load element
        load(v);

        //Toast.makeText(getContext(), getUser_id()+"", Toast.LENGTH_SHORT).show();

        setUsernameLoggedIn();

        adapter = new ReportAdapter(getActivity(), list);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CharSequence[] option = {"Hapus"};
                dialog = new AlertDialog.Builder(getActivity());
                dialog.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            // Toast.makeText(getContext(), list.get(position).id+"", Toast.LENGTH_SHORT).show();
                            database.reportDao().deleteReportById(list.get(position).id);
                            reportGet();
                            Toast.makeText(getContext(), "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getContext(), e.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).show();
                return true;
            }
        });

        try {
            reportGet();
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage()+"", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    void reportGet(){
        list.clear();
        list.addAll(database.reportDao().getAllReportById(getUser_id()));
        adapter.notifyDataSetChanged();
    }

    void setUsernameLoggedIn(){
        tvUsername.setText("User : "+SPAgent.getUsernameLoggedIn(getContext()));
    }

    void load(View v){
        tvBalance = v.findViewById(R.id.tvBalance);
        tvUsername = v.findViewById(R.id.tvUsername);
        lv = v.findViewById(R.id.listUang);
        database = AppDatabase.getInstance(getContext());
    }

    void setTvBalance(){
        String balTotal = database.balanceDao().getOne(getUser_id())+"";
        tvBalance.setText("Rp. "+balTotal+",00");
    }

    int getUser_id(){
        return SPAgent.getIdLoggedIn(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        setTvBalance();
        reportGet();
    }
}