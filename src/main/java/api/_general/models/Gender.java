package api._general.models;

import com.google.gson.annotations.SerializedName;

public enum Gender {
    @SerializedName("male")MALE,
    @SerializedName("female")FEMALE,
    @SerializedName("nonexistent_gender")NONEXISTENT
}
