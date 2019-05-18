package com.example.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.bakingapp.R;
import com.example.bakingapp.json.JsonData;


public class UpdateRecipeWidgetIntentService extends IntentService {

    private static final String TAG = UpdateRecipeWidgetIntentService.class.getSimpleName();
    public static final String ACTION_UPDATE_RECIPE_WIDGET = "com.example.bakingapp.widget";

    public UpdateRecipeWidgetIntentService() {
        super("UpdateRecipeWidgetIntentService");
    }

    public static void startActionUpdateRecipeWidget(Context context, JsonData recipeSelected) {
       // Log.d(TAG, "IN startActionUpdateRecipeWidget: " + recipeSelected.getId());
       // Log.d(TAG, "IN startActionUpdateRecipeWidget: " + recipeSelected.getName());
        Intent intent = new Intent(context, UpdateRecipeWidgetIntentService.class);
        intent.setAction(ACTION_UPDATE_RECIPE_WIDGET);
        intent.putExtra("selectedRecipe", recipeSelected);
        Log.d(TAG, "STARTING Service for intent startActionUpdateRecipeWidget: " + intent);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "IN onHandleIntent: " + intent);

        if(intent != null) {
            final String action = intent.getAction();
            if(ACTION_UPDATE_RECIPE_WIDGET.equals(action)) {
                if(intent.getExtras().containsKey("selectedRecipe")) {
                    JsonData recipeSelected = intent.getParcelableExtra("selectedRecipe");
                    //Log.d(TAG, "onHandleIntent: selected recipe: " + recipeSelected.getName());
                    handleActionUpdateRecipeWidget(recipeSelected);

                } else {
                    Log.d(TAG, "onHandleIntent: No recipe data.");
                }
            }
        }

    }

    private void handleActionUpdateRecipeWidget(JsonData recipeSelected) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
        Log.d(TAG, "handleActionUpdateRecipeWidget: in this");

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.layout.recipe_widget_list_view);
        BakingAppWidget.updateRecipeWidget(this, appWidgetManager, appWidgetIds, recipeSelected);

    }
}
