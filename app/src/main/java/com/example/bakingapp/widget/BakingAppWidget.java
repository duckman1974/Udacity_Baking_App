package com.example.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.bakingapp.MainActivity;
import com.example.bakingapp.R;
import com.example.bakingapp.RecipeDetailsActivity;
import com.example.bakingapp.json.JsonData;

public class BakingAppWidget extends AppWidgetProvider {

    private static final String TAG = BakingAppWidget.class.getSimpleName();

    private static JsonData ingredientsSelectedForWidget;
    //private static Intent intent;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews views;
        if(width < 300) {

            Intent intent;
            views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

            if(ingredientsSelectedForWidget == null) {
                intent = new Intent(context, MainActivity.class);
                views.setTextViewText(R.id.tv_widget, "No Recipe Selected");
            } else {
                intent = new Intent(context, RecipeDetailsActivity.class);
                intent.putExtra("selectedRecipe", ingredientsSelectedForWidget);
                views.setTextViewText(R.id.tv_widget, ingredientsSelectedForWidget.getName());
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

        } else {
            views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_list_view);

            Intent intent = new Intent(context, WidgetRemoteViewsService.class);
            views.setRemoteAdapter(R.layout.recipe_widget_list_view, intent);
            views.setEmptyView(R.layout.recipe_widget_list_view, R.id.empty_view);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, JsonData recipeSelected) {
        ingredientsSelectedForWidget = recipeSelected;
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        UpdateRecipeWidgetIntentService.startActionUpdateRecipeWidget(context, ingredientsSelectedForWidget);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        UpdateRecipeWidgetIntentService.startActionUpdateRecipeWidget(context, ingredientsSelectedForWidget);
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }



    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action when one or more AppWidget instances have been deleted
    }

    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instance for this provider is deleted
    }
}
