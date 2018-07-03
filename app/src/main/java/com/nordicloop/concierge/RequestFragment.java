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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


public class RequestFragment extends Fragment {
    private EditText mEditText;
    private Switch mApproval;
    private Switch mUrgent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_service, container, false);
        Button button = view.findViewById(R.id.requestButton);

        mEditText = view.findViewById(R.id.message);
        mApproval = view.findViewById(R.id.switch1);
        mUrgent = view.findViewById(R.id.switch2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetForm();
                Toast.makeText(view.getContext(), "Request added successfuly!", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void resetForm() {
        mEditText.setText(null);
        mApproval.setChecked(false);
        mUrgent.setChecked(false);
    }

}
