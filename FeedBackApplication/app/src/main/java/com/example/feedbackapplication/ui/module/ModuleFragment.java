package com.example.feedbackapplication.ui.module;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.feedbackapplication.Adapter.ModuleAdapter;
import com.example.feedbackapplication.LoginActivity;
import com.example.feedbackapplication.R;
import com.example.feedbackapplication.model.Module;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ModuleFragment extends Fragment implements ModuleAdapter.ClickListener {

    private ModuleViewModel myViewModel;
    private ModuleAdapter adapter;
    private RecyclerView rcvModule;
    private DatabaseReference database;
    private ArrayList<Module> arrayList;
    private FloatingActionButton btnInsert;
    private FirebaseRecyclerOptions<Module> options;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.module_fragment, container, false);

        database = FirebaseDatabase.getInstance().getReference().child("Module");
        rcvModule = root.findViewById(R.id.rcvModule);
        rcvModule.setHasFixedSize(true);
        rcvModule.setLayoutManager(new LinearLayoutManager(root.getContext()));
        //Retrieve data
        FirebaseRecyclerOptions<Module> options =
                new FirebaseRecyclerOptions.Builder<Module>()
                        .setQuery(database, Module.class)
                        .build();
        adapter = new ModuleAdapter(options,this);
        rcvModule.setAdapter(adapter);

        //Save data
        btnInsert = root.findViewById(R.id.btnNew);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_nav_module_to_nav_add);
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    public void updateClicked(Module module) {
        Bundle bundle = new Bundle();
        bundle.putString("name",module.getModuleName());
        bundle.putString("adminID",module.getAdminID());
        bundle.putInt("id",module.getModuleID());
        Navigation.findNavController(getView()).navigate(R.id.action_nav_module_to_nav_edit,bundle);
    }

    @Override
    public void deleteClicked(Module module) {
        String key = String.valueOf(module.getModuleID());
        FirebaseDatabase.getInstance().getReference()
                .child("Module")
                .child(key)
                .setValue(null)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(),"Update successfully" ,Toast.LENGTH_SHORT).show();
                    }
                });

    }
}