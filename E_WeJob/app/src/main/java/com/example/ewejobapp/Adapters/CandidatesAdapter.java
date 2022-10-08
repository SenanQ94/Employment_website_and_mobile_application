package com.example.ewejobapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ewejobapp.Models.LoginCandidate;
import com.example.ewejobapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CandidatesAdapter extends RecyclerView.Adapter<CandidatesAdapter.ViewHolder>{
    private Context context;
    private List<LoginCandidate> list;


    public CandidatesAdapter(Context context,List<LoginCandidate> list) {
        this.context = context;
        this.list = list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,phone,field,level,years;
        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            phone = view.findViewById(R.id.phone);
            field = view.findViewById(R.id.field);
            level = view.findViewById(R.id.level);
            years = view.findViewById(R.id.years);


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
                .inflate(R.layout.item_candidate, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        LoginCandidate j = list.get(position);

        holder.name.setText(j.getFull_name());
        holder.phone.setText(j.getPhone());
        holder.field.setText(j.getEdu_field());
        holder.level.setText(j.getExp_lev());
        holder.years.setText(j.getExp_years()+ " Years");
    }
}
