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

import com.example.bakingapp.json.JsonData;
import com.example.bakingapp.json.RetrieveJsonData;
import java.util.ArrayList;

public class RecipeMainFragment extends Fragment {

    private static final String TAG = RecipeMainFragment.class.getSimpleName();

    Context context;
    BakingAppAdapter adapter;
    RecyclerView recyclerView;
    protected static ArrayList<JsonData> jd;

    public RecipeMainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getContext();

        View rootView = inflater.inflate(R.layout.main_recipe_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.listRecyclerView);
        BakingAppAdapter bakingAppAdapter = new BakingAppAdapter(getContext(), null);
        recyclerView.setAdapter(bakingAppAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RetrieveJsonData rj = new RetrieveJsonData(context);
        jd = rj.readJsonFile();
        for(JsonData d : jd) {
            Log.d(TAG, "IN LOOP:  " + d.getIngredients().get(0).getIngredient());
        }
        setAdapter(jd);
        return rootView;
    }

    private void setAdapter(ArrayList recipes) {
        adapter = new BakingAppAdapter(context, recipes);
        recyclerView.setAdapter(adapter);
    }
}
