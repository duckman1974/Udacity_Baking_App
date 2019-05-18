package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.bakingapp.json.Ingredient;
import com.example.bakingapp.json.JsonData;
import com.example.bakingapp.widget.UpdateRecipeWidgetIntentService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BakingAppAdapter.RecipeSelectedListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    Context context;
    JsonData recipeForWidget;
    List<Ingredient> ingredList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        Log.d(TAG, "MainActivity: Checking network connection");
        NetworkConnectivity net = new NetworkConnectivity(context);
        net.onNetworkActive();

        Log.d(TAG, "MainActivity: Building Recipe Fragment");
        RecipeMainFragment mainFragment = new RecipeMainFragment();
        FragmentManager fragMgr = getSupportFragmentManager();
        fragMgr.beginTransaction()
                .add(R.id.placeholder, mainFragment)
                .commit();
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//
//    }

    @Override
    public void recipeSelected(int position) {
        Log.d(TAG, "Recipe selected for detail view to show");
        recipeForWidget = RecipeMainFragment.jd.get(position);
        Bundle bundle = new Bundle();
        Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
        bundle.putParcelable("recipeSelected", recipeForWidget);
        intent.putExtra("recipeIntent", bundle);
        Log.d(TAG, "recipeSelected: beforeBundleData: " + recipeForWidget.getName());

        if(intent.resolveActivity(getPackageManager()) != null) {
            Log.d(TAG, "recipeSelected: startingActivity for intent");
            startActivity(intent);
        } else {
            Log.d(TAG, "RecipeSelected: No activity to handle this intent");
        }

    }

    @Override
    public void recipeSelectedForWidget(int position) {
        Log.d(TAG, "recipeSelectedForWidget: ");
        recipeForWidget = RecipeMainFragment.jd.get(position);
        Log.d(TAG, "recipeSelectedForWidget: " + recipeForWidget.getId());
        Log.d(TAG, "recipeSelectedForWidget: " + recipeForWidget.getName());
        ingredList = recipeForWidget.getIngredients();
        String ingListString = getIngredientList(recipeForWidget.getIngredients());

        //save the ingredientListString in SharePreferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ingredient_list_string",ingListString);
        editor.putString("recipe_name", recipeForWidget.getName());
        Log.d(TAG, "onRecipeSelectedForWidget: "+ ingListString);
        editor.apply();

        UpdateRecipeWidgetIntentService.startActionUpdateRecipeWidget(context, recipeForWidget);
    }

    private String getIngredientList(List<Ingredient> ingListString) {
        Log.d(TAG, "getIngredientList: ");
        if(ingListString == null) {
            return null;
        }
        Gson gsonObj = new Gson();
        Type listType = new TypeToken<List<Ingredient>>() {
        }.getType();
        return gsonObj.toJson(ingListString, listType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy: called in main");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called!");
    }
}
