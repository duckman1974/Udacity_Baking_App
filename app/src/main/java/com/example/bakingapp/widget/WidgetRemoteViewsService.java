package com.example.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.json.Ingredient;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WidgetRemoteViewsService extends RemoteViewsService {

    private static final String TAG = WidgetRemoteViewsService.class.getSimpleName();


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext());
    }


    class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context context;
        private String recipeName;
        private String ingListString;
        private List<Ingredient> ignList;

        public WidgetRemoteViewsFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            ingListString = sharedPreferences.getString("ingredient_list_string", "");
            recipeName = sharedPreferences.getString("recipe_name", "");
            Log.d(TAG, "onDataSetChanged: "+ ingListString);

            ignList = getIngredientList(ingListString);
            Log.d(TAG, "onDataSetChanged: "+ ignList.size());

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ignList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if(ignList == null || ignList.size() == 0)  return null;

            float quantity = ignList.get(position).getQuantity();
            String measure = ignList.get(position).getMeasure();
            String ingredient = ignList.get(position).getIngredient();

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_item_list);

            views.setTextViewText(R.id.tv_quantity, String.valueOf(quantity));
            views.setTextViewText(R.id.tv_measure, measure);
            views.setTextViewText(R.id.tv_ingredient, ingredient);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

    // take ingredient List string and convert to List<Ingredients>
    private List<Ingredient> getIngredientList(String ingListString) {
        Gson gsonObj = new Gson();
        Type stepListType = new TypeToken<List<Ingredient>>() {
        }.getType();
        return gsonObj.fromJson(ingListString, stepListType);
    }


}
