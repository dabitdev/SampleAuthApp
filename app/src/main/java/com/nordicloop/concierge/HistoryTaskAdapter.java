package com.nordicloop.concierge;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nordicloop.concierge.model.ConciergeTask;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryTaskAdapter extends RecyclerView.Adapter<HistoryTaskAdapter.ViewHolder>{
  private List<ConciergeTask> tasksList;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView date, textMessage, approval, urgent;

    public ViewHolder(View view) {
      super(view);
      date = view.findViewById(R.id.date);
      textMessage = view.findViewById(R.id.textMessage);
      approval = view.findViewById(R.id.approval);
      urgent = view.findViewById(R.id.urgent);
    }
  }

  public HistoryTaskAdapter(List<ConciergeTask> tasksList) {
    this.tasksList = tasksList;
  }

  @NotNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.history_task_row, parent, false);

    return new ViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ConciergeTask task = tasksList.get(position);
    holder.date.setText(new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date(task.date)));
    holder.textMessage.setText(task.taskMessage);
    holder.approval.setText(task.preApprovalNeeded);
    holder.urgent.setText(task.urgent);
  }

  @Override
  public int getItemCount() {
    return tasksList.size();
  }
}
