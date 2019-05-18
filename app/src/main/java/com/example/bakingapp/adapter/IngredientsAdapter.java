package com.example.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.json.Ingredient;
import java.util.ArrayList;
import java.util.List;


public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private static final String TAG = IngredientsAdapter.class.getSimpleName();

    Context context;
    List<Ingredient> ingList;
    ArrayList<Ingredient> ingArrayList;

    public IngredientsAdapter(Context context, ArrayList<Ingredient> ingArrayList) {
        this.context = context;
        this.ingArrayList = ingArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_grid_view, parent, false);
        //ButterKnife.bind(this, view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        final List<Ingredient> dataList;
        if(ingArrayList.get(position).getIngredient().isEmpty()){
            Log.d(TAG, "Empty Ingredient items");
        } else {
            final Ingredient ing = ingArrayList.get(position);
            viewHolder.ingredients.setText(ing.getIngredient());
            viewHolder.quantity.setText(String.valueOf(ing.getQuantity()));
            viewHolder.measure.setText(ing.getMeasure());
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + ingArrayList.size());
        return ingArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final CardView parentLayout;
        final TextView ingredients;
        final TextView quantity;
        final TextView measure;
        final TextView ingLabel;

        ViewHolder(View view) {
            super(view);

            ingLabel = view.findViewById(R.id.ingDetails);
            ingredients = view.findViewById(R.id.ingredient);
            quantity = view.findViewById(R.id.quantity);
            measure = view.findViewById(R.id.measure);
            parentLayout = view.findViewById(R.id.detail_placeHolder);
        }
    }
}
