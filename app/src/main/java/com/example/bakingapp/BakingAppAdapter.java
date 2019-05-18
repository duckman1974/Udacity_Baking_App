package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakingapp.json.Ingredient;
import com.example.bakingapp.json.JsonData;
import com.example.bakingapp.json.Step;
import com.example.bakingapp.widget.BakingAppWidget;
import com.example.bakingapp.widget.UpdateRecipeWidgetIntentService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class BakingAppAdapter extends RecyclerView.Adapter<BakingAppAdapter.ViewHolder> {

    Context context;
    int item;
    RecipeSelectedListener callback;
    final private ArrayList<JsonData> names;
    private static final String TAG = BakingAppAdapter.class.getSimpleName();

    public BakingAppAdapter(Context context, ArrayList<JsonData> names) {
        this.context = context;
        this.names = names;
    }

    public interface RecipeSelectedListener {
        void recipeSelectedForWidget(int position);
        void recipeSelected(int position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final List<Ingredient> dataList;
        final List<Step> stepList;
        if(names.get(position).getName().isEmpty()){
            Log.d(TAG, "Empty items in array");
        } else {
            final JsonData data = names.get(position);

            viewHolder.text_View.setText(data.getName());
            Log.d(TAG, "onBindViewHolder: " + data.getServings());
            viewHolder.servingsView.setText("Serves : " + data.getServings().toString());
            Log.d(TAG, "ViewHolder contains data");

            dataList = new ArrayList<>();
            Log.d(TAG, "onBindViewHolder: dataSize: " + data.getIngredients().size());
            List<Ingredient> listIng = names.get(position).getIngredients();
            Log.d(TAG, "onBindViewHolder: size: " + listIng.size());
            for(Ingredient t : listIng) {
                Ingredient ign = new Ingredient();
                ign.setIngredient(t.getIngredient());
                ign.setQuantity(t.getQuantity());
                ign.setMeasure(t.getMeasure());
               dataList.add(ign);
            }

            stepList = new ArrayList<>();
            List<Step> listStep = names.get(position).getSteps();
            Log.d(TAG, "onBindViewHolder: size: " + listStep.size());
            for(Step s : listStep) {
                Step step = new Step();
                step.setId(s.getId());
                step.setShortDescription(s.getShortDescription());
                step.setDescription(s.getDescription());
                step.setVideoURL(s.getVideoURL());
                step.setThumbnailURL(s.getThumbnailURL());

                stepList.add(step);
            }


            viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, RecipeDetailsActivity.class);
                    Bundle b = new Bundle();
                    Log.d(TAG, "onClick: ");
                    b.putParcelableArrayList("JsonData", (ArrayList)dataList);
                    b.putParcelableArrayList("Steps", (ArrayList)stepList);
                    intent.putExtra("ing", b);

                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView text_View;
        final TextView servingsView;
        final ImageView addRecipe;
        final CardView parentLayout;

        ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            text_View = view.findViewById(R.id.recipeNameText);
            servingsView = view.findViewById(R.id.servings);
            addRecipe = view.findViewById(R.id.add_ingredients_widget);
            parentLayout = view.findViewById(R.id.placeholder);
            addRecipe.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            callback = (RecipeSelectedListener) context;

            Log.d(TAG, "onClick: in ViewHolder " + view.getId());
            int adapterPost = getAdapterPosition();
            Log.d(TAG, "callback: " + callback);
            if(view.getId() == addRecipe.getId()) {
                Toast.makeText(context, "Recipe added to widget", Toast.LENGTH_SHORT).show();
                callback.recipeSelectedForWidget(adapterPost);
                callback.recipeSelected(adapterPost);
            }

        }
    }


}
