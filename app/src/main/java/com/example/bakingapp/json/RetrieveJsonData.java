package com.example.bakingapp.json;

import android.content.Context;
import android.util.Log;

import com.example.bakingapp.R;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class RetrieveJsonData {

    private static final String TAG = RetrieveJsonData.class.getSimpleName();

    private ArrayList<JsonData> recipeName = new ArrayList<>();
    Context context;

    public RetrieveJsonData(Context context) {
        this.context = context;
    }

    public ArrayList<JsonData> readJsonFile() {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.bakingapp);
            Gson gson = new Gson();
            Reader reader = new InputStreamReader(is);
            JsonData[] gsonObj = gson.fromJson(reader, JsonData[].class);
            int size = gsonObj.length;
            List<Step> listData = null;
            ArrayList<Ingredient> iList = null;
            ArrayList<Step> stepList;
            for(int i=0; i<size; i++) {
                JsonData data = new JsonData();

                Log.d(TAG, "readJsonFile: id " + gsonObj[i].getId());
                Log.d(TAG, "readJsonFile: name " + gsonObj[i].getName());
                Log.d(TAG, "readJsonFile: serving " + gsonObj[i].getServings());
                data.setId(gsonObj[i].getId());
                data.setName(gsonObj[i].getName());
                data.setServings(gsonObj[i].getServings());
//                Log.d(TAG, "readJsonFile: servings = " + gsonObj[i].getServings());
//                Log.d(TAG, "readJsonFile: img = " + gsonObj[i].getImage());
//
                Step step = null;
                stepList = new ArrayList<>();
                for(int j=0; j<gsonObj[i].getSteps().size(); j++) {
                    step = new Step();
                    Log.d(TAG, "readJsonFile: id = " + gsonObj[i].getSteps().get(j).getId());
                    Log.d(TAG, "readJsonFile: videoUrl = " + gsonObj[i].getSteps().get(j).getVideoURL());
                    Log.d(TAG, "readJsonFile: thumbNail = " + gsonObj[i].getSteps().get(j).getThumbnailURL());
                    Log.d(TAG, "readJsonFile: description = " + gsonObj[i].getSteps().get(j).getDescription());
                    Log.d(TAG, "readJsonFile: shortDesp = " + gsonObj[i].getSteps().get(j).getShortDescription());
                    step.setId(gsonObj[i].getSteps().get(j).getId());
                    step.setVideoURL(gsonObj[i].getSteps().get(j).getVideoURL());
                    step.setThumbnailURL(gsonObj[i].getSteps().get(j).getThumbnailURL());
                    step.setDescription(gsonObj[i].getSteps().get(j).getDescription());
                    step.setShortDescription(gsonObj[i].getSteps().get(j).getShortDescription());
                    stepList.add(step);
                    data.setSteps(stepList);
                }
                Ingredient ing = null;
                iList = new ArrayList<>();
                for(int h=0; h<gsonObj[i].getIngredients().size(); h++) {
                     ing = new Ingredient();
                   // iList = new ArrayList<>();
                    Log.d(TAG, "readJsonFile: ingred = " + gsonObj[i].getIngredients().get(h).getIngredient());
                    Log.d(TAG, "readJsonFile: measure = " + gsonObj[i].getIngredients().get(h).getMeasure());
                    Log.d(TAG, "readJsonFile: quantity = " + gsonObj[i].getIngredients().get(h).getQuantity());
                    ing.setIngredient(gsonObj[i].getIngredients().get(h).getIngredient());
                    ing.setMeasure(gsonObj[i].getIngredients().get(h).getMeasure());
                    ing.setQuantity(gsonObj[i].getIngredients().get(h).getQuantity());
                    iList.add(ing);
                    data.setIngredients(iList);
                }

                recipeName.add(data);
            }
            Log.d(TAG, "total recipeName size: " + recipeName.size());
            //setAdapter(recipeName);
            return recipeName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
