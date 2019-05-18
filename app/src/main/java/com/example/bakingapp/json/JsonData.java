package com.example.bakingapp.json;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonData implements Parcelable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    @NonNull
    private List<Ingredient> ingredients = null;
    @SerializedName("steps")
    @Expose
    @NonNull
    private List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;

    public JsonData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = new ArrayList<>();
        in.readTypedList(ingredients, Ingredient.CREATOR);
        steps = new ArrayList<>();
        in.readTypedList(steps, Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }




    public static final Creator<JsonData> CREATOR = new Creator<JsonData>() {
        @Override
        public JsonData createFromParcel(Parcel in) {

            return new JsonData(in);
        }

        @Override
        public JsonData[] newArray(int size) {

            return new JsonData[size];
        }
    };

    public JsonData() {

    }


    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public List<Ingredient> getIngredients() {

        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {

        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {

        return steps;
    }

    public void setSteps(List<Step> steps) {

        this.steps = steps;
    }

    public Integer getServings() {

        return servings;
    }

    public void setServings(Integer servings) {

        this.servings = servings;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);  // was writeList
        dest.writeTypedList(steps);  // was writeList
        dest.writeInt(servings);
        dest.writeString(image);

    }
}
