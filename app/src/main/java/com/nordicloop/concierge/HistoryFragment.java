/*
 * Copyright (c) 2017. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.nordicloop.concierge;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.plant(new Timber.DebugTree());

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
                Toast.makeText(getContext().getApplicationContext(), databaseError.getCode() + ": " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
