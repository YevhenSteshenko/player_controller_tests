package api.response.models.player.general;

import com.google.gson.annotations.SerializedName;

public enum Gender {
    @SerializedName("male")MALE,
    @SerializedName("female")FEMALE,
    @SerializedName("nonexistent_gender")NONEXISTENT
}
