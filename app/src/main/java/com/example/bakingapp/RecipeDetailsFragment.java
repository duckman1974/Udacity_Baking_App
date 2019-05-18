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

import com.example.bakingapp.adapter.IngredientsAdapter;
import com.example.bakingapp.adapter.StepsAdapter;
import com.example.bakingapp.json.Ingredient;
import com.example.bakingapp.json.JsonData;
import com.example.bakingapp.json.Step;

import java.util.ArrayList;
import java.util.List;


public class RecipeDetailsFragment extends Fragment {

    private static final String TAG = RecipeDetailsFragment.class.getSimpleName();

    Context context;
    List<Ingredient> ingList;
    ArrayList<Ingredient> ingArrayList;
    List<Step> stepList;
    ArrayList<Step> stepArrayList;
    IngredientsAdapter adapter;
    StepsAdapter stepAdpt;
    RecyclerView recyclerView;
    int viewType = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getContext();
        setHasOptionsMenu(true);
        Log.d(TAG, "onCreateView: In RecipeDetailsFrag");

        View rootView = inflater.inflate(R.layout.details_recipe_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.ingredientsRecyclerView);
        BakingAppAdapter bakingAppAdapter = new BakingAppAdapter(getContext(), null);
        recyclerView.setAdapter(bakingAppAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        if(getArguments() != null) {
            viewType = this.getArguments().getInt("type");
            //resetAdapter();
        }

        if(getActivity().getIntent().getBundleExtra("recipeIntent") != null) {
            Log.d(TAG, "if statement: recipeIntent");
            getWidgetIngredientsIncomingIntent();
        } else {
            Log.d(TAG, "else statement:");
            getIncomingIntent(viewType);
            Log.d(TAG, "VIEWTYPE = " + viewType);
        }

        return rootView;
    }

    public static RecipeDetailsFragment newInstance(JsonData recipe) {
        RecipeDetailsFragment mDetailsFrag = new RecipeDetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("recipe_selected", recipe);
        mDetailsFrag.setArguments(bundle);
        return mDetailsFrag;
    }

    private void getWidgetIngredientsIncomingIntent() {
        Bundle bl = getActivity().getIntent().getBundleExtra("recipeIntent");
        Log.d(TAG, "getWidgetIngredientsIncomingIntent: " + bl.size());
        Ingredient ingredient = null;
        if(bl != null) {
            Log.d(TAG, "getIncomingIntent: Bundle is NOT null");
            JsonData recipeWidgetData = bl.getParcelable("recipeSelected");
            Log.d(TAG, "getWidgetIngredients: " + recipeWidgetData.getName());
            ingArrayList = new ArrayList<>();

            for(Ingredient i : recipeWidgetData.getIngredients()) {
                ingredient = new Ingredient();
                Log.d(TAG, "Ingredient: " + i.getIngredient());
                ingredient.setIngredient(i.getIngredient());
                Log.d(TAG, "Qty: " + i.getQuantity());
                ingredient.setQuantity(i.getQuantity());
                Log.d(TAG, "Measure: " + i.getMeasure());
                ingredient.setMeasure(i.getMeasure());
                ingArrayList.add(ingredient);
            }
            setAdapter(ingArrayList);
        } else {
            Log.d(TAG, "getIncomingIntent: Bundle is NULL");
        }
    }

    private void getIncomingIntent(int viewType) {
        Log.d(TAG, "getIncomingIntent: checking for incoming intents");

        ingList = new ArrayList<Ingredient>();
        stepList = new ArrayList<Step>();
        Bundle bl = getActivity().getIntent().getBundleExtra("ing");

        if(bl != null) {
            Log.d(TAG, "getIncomingIntent: Bundle is NOT null");
        } else {
            Log.d(TAG, "getIncomingIntent: Bundle is NULL");
        }
        //Log.d(TAG, "bundle: " + bl.isEmpty());
        ingList = bl.getParcelableArrayList("JsonData");
        stepList = bl.getParcelableArrayList("Steps");
        int intSize = ingList.size();
        int stepSize = stepList.size();
        Log.d(TAG, "getIncomingIntent: size: " + intSize);
        Log.d(TAG, "getIncomingIntent: size: " + stepSize);
        Ingredient ig = null;
        Step step = null;
        JsonData jd = new JsonData();
        ingArrayList = new ArrayList<>();
        stepArrayList = new ArrayList<>();

        if(viewType == 0) {
            if(intSize > 0) {
                for(int s=0; s<intSize; s++) {
                    ig = new Ingredient();
                    ig.setIngredient(ingList.get(s).getIngredient());
                    ig.setQuantity(ingList.get(s).getQuantity());
                    ig.setMeasure(ingList.get(s).getMeasure());

                    Log.d(TAG, "getIncomingIntent: ingredient: " + ingList.get(s).getIngredient());
                    Log.d(TAG, "getIncomingIntent: qty: " + ingList.get(s).getQuantity());
                    Log.d(TAG, "getIncomingIntent: measure: " + ingList.get(s).getMeasure());

                    ingArrayList.add(ig);
                }
            }
            setAdapter(ingArrayList);
        }

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

    private void setAdapter(ArrayList recipes) {
        adapter = new IngredientsAdapter(context, recipes);
        recyclerView.setAdapter(adapter);
    }

    private void setStepAdapter(ArrayList recipes) {
        setAdapter(null);
        stepAdpt = new StepsAdapter(context, recipes);
        recyclerView.setAdapter(stepAdpt);
        stepAdpt.notifyDataSetChanged();
    }

    private void resetAdapter() {

        //ingArrayList.clear();
        adapter.notifyDataSetChanged();
        //stepAdpt.notifyDataSetChanged();
    }

    private void setIngredientsInWidget() {
        ArrayList<Ingredient> widgetIng;
        widgetIng = ingArrayList;
    }


}
