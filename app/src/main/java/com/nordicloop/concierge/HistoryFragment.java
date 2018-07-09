package com.nordicloop.concierge;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nordicloop.concierge.model.ConciergeTask;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private Context appContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.plant(new Timber.DebugTree());
        appContext = inflater.getContext().getApplicationContext();
        addDbListener();
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        return view;
    }

    private void addDbListener(){
        FirebaseDatabase.getInstance().getReference().child("tasks").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot task : dataSnapshot.getChildren()) {
                        Timber.i(task.getValue(ConciergeTask.class).toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        FirebaseDatabase.getInstance().getReference().child("tasks").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ConciergeTask> taskList = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot task : dataSnapshot.getChildren()) {
                        ConciergeTask conciergeTask = task.getValue(ConciergeTask.class);
                        if (conciergeTask != null) {
                            Timber.e(conciergeTask.toString());
                            taskList.add(conciergeTask);
                        }
                    }
                }
                if (taskList.size() > 0) {
                    recyclerView.setAdapter(new HistoryTaskAdapter(taskList));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(appContext, databaseError.getCode() + ": " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
