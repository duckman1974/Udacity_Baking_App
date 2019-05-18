package com.example.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.json.Step;
import com.example.bakingapp.mediaplayer.BakingVideo;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder>  {

    private static final String TAG = StepsAdapter.class.getSimpleName();

    Context context;
    List<Step> stepList;
    ArrayList<Step> stepArrayList;


    public StepsAdapter(Context context, ArrayList<Step> stepArrayList) {
        this.context = context;
        this.stepArrayList = stepArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_layout, parent, false);
        //ButterKnife.bind(this, view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        final List<Step> dataList;
        if(stepArrayList.get(position).getId() == null){
            Log.d(TAG, "Empty Step items");
        } else {
            final Step step = stepArrayList.get(position);
            if(step.getDescription().equals("Recipe Introduction")) {
                viewHolder.description.setText("");
            } else {
                viewHolder.description.setText(step.getDescription());
            }
            if(step.getShortDescription().equals("Recipe Introduction")){
                viewHolder.shortDescription.setText(step.getShortDescription());
                viewHolder.shortDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            } else {
                viewHolder.shortDescription.setText(step.getShortDescription());
            }

            viewHolder.shortDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String videoString = stepArrayList.get(position).getVideoURL();
                    String description = stepArrayList.get(position).getDescription();
                    Intent intent = new Intent(context, BakingVideo.class);
                    intent.putExtra("videoUrl", videoString);
                    intent.putExtra("description", description);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + stepArrayList.size());
        return stepArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final CardView parentLayout;
        final TextView shortDescription;
        final TextView description;

        ViewHolder(View view) {
            super(view);
            shortDescription = view.findViewById(R.id.short_description);
            description = view.findViewById(R.id.description);
            parentLayout = view.findViewById(R.id.detail_placeHolder);
        }
    }
}
