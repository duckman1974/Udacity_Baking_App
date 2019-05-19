package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.bakingapp.json.JsonData;


public class RecipeDetailsActivity extends AppCompatActivity {

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    private int columns = 0;
    private static final String SELECTED_RECIPE = "recipeSelected";
    JsonData recipeSelected;
    RecipeDetailsFragment mDetailsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        boolean tablet = getResources().getBoolean(R.bool.isTablet);
        if(tablet) {
            columns = 2;
            Log.d(TAG, "onCreate: tablet: " + tablet + " " + columns + " columns");
        } else {
            columns = 1;
            Log.d(TAG, "onCreate: tablet: " + tablet + " " + columns + " columns");
        }

//        if(savedInstanceState == null) {
//            Intent intent = getIntent();
//            Bundle extra = intent.getExtras();
//            if (extra == null) {
//                return;
//            } else {
//                if (extra.containsKey("recipeSelected")) {
//                    recipeSelected = intent.getParcelableExtra("recipeSelected");
//                    Log.d(TAG, "onCreate: Recipe name is " + recipeSelected.getName());
//                    mDetailsFragment = RecipeDetailsFragment.newInstance(recipeSelected);
//                }
//            }
//        } else {
//            recipeSelected = savedInstanceState.getParcelable(SELECTED_RECIPE);
////            mDetailsFragment = (RecipeDetailsFragment) getSupportFragmentManager().beginTransaction()
////                    .replace(R.id.ingredients_details, ingDetails)
//        }

        if(columns == 2) {
            Log.d(TAG, "onCreate: Detail Activity 2 columns");
            RecipeDetailsFragment ingDetailsFragment = new RecipeDetailsFragment();
            FragmentManager ingFragMgr = getSupportFragmentManager();
            ingFragMgr.beginTransaction()
                    .add(R.id.ingredients_details, ingDetailsFragment)
                    .commit();

            RecipeStepsFragment stepsDetailsFragment = new RecipeStepsFragment();
            FragmentManager stepsFragMgr = getSupportFragmentManager();
            stepsFragMgr.beginTransaction()
                    .add(R.id.steps_details, stepsDetailsFragment)
                    .commit();
        } else {
            RecipeDetailsFragment detailsFragment = new RecipeDetailsFragment();
            FragmentManager fragMgr = getSupportFragmentManager();
            fragMgr.beginTransaction()
                    .add(R.id.detail_placeHolder, detailsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(columns == 1) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {
            case R.id.baking_steps:
                Log.d(TAG, "Selected Baking Steps");
                Bundle bundle = new Bundle();
                bundle.putInt("type", 1);
                RecipeDetailsFragment df = new RecipeDetailsFragment();
                df.setArguments(bundle);
                FragmentManager fragMgr = getSupportFragmentManager();
                fragMgr.beginTransaction()
                        .replace(R.id.detail_placeHolder, df)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: called!");
        super.onSaveInstanceState(outState);
        Log.d(TAG, "closing and recipeSelected: " + recipeSelected);
        outState.putParcelable(SELECTED_RECIPE, recipeSelected);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called in detail");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called!");
    }
}
