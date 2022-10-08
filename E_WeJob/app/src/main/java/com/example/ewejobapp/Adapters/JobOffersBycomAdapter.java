package com.example.ewejobapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ewejobapp.Activities.JobCandidatesActivity;
import com.example.ewejobapp.Models.JobOfferObject;
import com.example.ewejobapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JobOffersBycomAdapter extends RecyclerView.Adapter<JobOffersBycomAdapter.ViewHolder>{
    private Context context;
    private List<JobOfferObject> list;


    public JobOffersBycomAdapter(Context context,List<JobOfferObject> list) {
        this.context = context;
        this.list = list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title,company,field,level,years,salary;
        private LinearLayout layout;
        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            company = view.findViewById(R.id.company);
            field = view.findViewById(R.id.field);
            level = view.findViewById(R.id.level);
            years = view.findViewById(R.id.years);
            salary = view.findViewById(R.id.salary);
            layout = view.findViewById(R.id.layout);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_job_offer, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        JobOfferObject j = list.get(position);

        holder.title.setText(j.getTitle());
        holder.company.setVisibility(View.GONE);
        holder.field.setText(j.getField());
        holder.level.setText(j.getLevel());
        holder.years.setText(j.getYears()+ " Years");
        holder.salary.setText(j.getSalary()+"$");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobCandidatesActivity.class);
                intent.putExtra("id",j.getId());
                context.startActivity(intent);
            }
        });
    }
}
