package com.nordicloop.concierge.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Task {
    public String taskMessage;
    public String uid;
    public String preApprovalNeeded = "false";

    public Task() {
        // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    }

    public Task(String taskMessage, String uid, boolean preApprovalNeeded) {
        this.taskMessage = taskMessage;
        this.uid = uid;
        this.preApprovalNeeded = preApprovalNeeded ? "true" : "false";
    }

    public static void writeNewTask(String uid, String taskMessage, Boolean preApprovalNeeded) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tasks");
        String taskId = reference.push().getKey();
        reference.child(taskId).setValue(new Task(taskMessage, uid, preApprovalNeeded));
    }

    @Override
    public String toString() {
        return "Task{" +
            "taskMessage='" + taskMessage + '\'' +
            ", uid='" + uid + '\'' +
            ", preApprovalNeeded='" + preApprovalNeeded + '\'' +
            '}';
    }
}