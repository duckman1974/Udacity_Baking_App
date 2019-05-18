package com.example.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.adapter.StepsAdapter;
import com.example.bakingapp.json.Ingredient;
import com.example.bakingapp.json.JsonData;
import com.example.bakingapp.json.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepsFragment extends Fragment {

    private static final String TAG = RecipeStepsFragment.class.getSimpleName();

    Context context;
    ArrayList<Step> stepArrayList;
    List<Step> stepList;
    StepsAdapter stepAdpt;
    RecyclerView recyclerView;
    int viewType = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreateView: In RecipeDetailsFrag");


        View rootView = inflater.inflate(R.layout.details_step_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.stepsRecyclerView);
        StepsAdapter stepsAppAdapter = new StepsAdapter(getContext(), null);
        recyclerView.setAdapter(stepsAppAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if(getArguments() != null) {
            viewType = this.getArguments().getInt("type");
            //resetAdapter();
        }

        getIncomingIntent(viewType);
        Log.d(TAG, "VIEWTYPE = " + viewType);
        return rootView;
    }

    private void getIncomingIntent(int viewType) {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");

        stepList = new ArrayList<Step>();
        Bundle bl = getActivity().getIntent().getBundleExtra("ing");
        stepList = bl.getParcelableArrayList("Steps");
        int stepSize = stepList.size();
        Log.d(TAG, "getIncomingIntent: size: " + stepSize);
        Ingredient ig = null;
        Step step = null;
        JsonData jd = new JsonData();
        stepArrayList = new ArrayList<>();

        if(viewType == 1) {
            if(stepSize > 0) {
                for(int st=0; st<stepSize; st++) {
                    step = new Step();
                    step.setId(stepList.get(st).getId());
                    step.setShortDescription(stepList.get(st).getShortDescription());
                    step.setDescription(stepList.get(st).getDescription());
                    step.setVideoURL(stepList.get(st).getVideoURL());
                    step.setThumbnailURL(stepList.get(st).getThumbnailURL());

                    Log.d(TAG, "getIncomingIntent: id: " + stepList.get(st).getId());
                    Log.d(TAG, "getIncomingIntent: shortDes: " + stepList.get(st).getShortDescription());
                    Log.d(TAG, "getIncomingIntent: Des: " + stepList.get(st).getDescription());
                    Log.d(TAG, "getIncomingIntent: videoUrl: " + stepList.get(st).getVideoURL());
                    Log.d(TAG, "getIncomingIntent: thumb: " + stepList.get(st).getThumbnailURL());

                    stepArrayList.add(step);
                }
            }
            setStepAdapter(stepArrayList);
        }
    }


    private void setStepAdapter(ArrayList recipes) {
        stepAdpt = new StepsAdapter(context, recipes);
        recyclerView.setAdapter(stepAdpt);
        stepAdpt.notifyDataSetChanged();
    }
}
