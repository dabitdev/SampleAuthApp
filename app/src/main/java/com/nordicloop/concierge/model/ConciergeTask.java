package com.nordicloop.concierge.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ConciergeTask {
    public Long date;
    public String taskMessage;
    public String uid;
    public String preApprovalNeeded = "false";
    public String urgent = "false";

    public ConciergeTask() {
        // Default constructor required for calls to DataSnapshot.getValue(ConciergeTask.class)
    }

    public ConciergeTask(Long date, String taskMessage, String uid, boolean preApprovalNeeded, boolean urgent) {
        this.date = date;
        this.taskMessage = taskMessage;
        this.uid = uid;
        this.preApprovalNeeded = preApprovalNeeded ? "true" : "false";
        this.urgent = urgent ? "true" : "false";
    }

    public static void writeNewTask(String uid, Long date, String taskMessage, Boolean preApprovalNeeded, Boolean urgent) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tasks");
        String taskId = reference.push().getKey();
        reference.child(taskId).setValue(new ConciergeTask(date, taskMessage, uid, preApprovalNeeded, urgent));
    }

    @Override
    public String toString() {
        return "ConciergeTask{" +
            "date=" + date + '\'' +
            "taskMessage='" + taskMessage + '\'' +
            ", uid='" + uid + '\'' +
            ", preApprovalNeeded='" + preApprovalNeeded + '\'' +
            '}';
    }
}