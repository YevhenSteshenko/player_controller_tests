package api._general.models;

import com.google.gson.annotations.SerializedName;

public enum PlayerRole {
    @SerializedName("supervisor")SUPERVISOR,
    @SerializedName("admin")ADMIN,
    @SerializedName("user")USER,
    @SerializedName("nonexistent_player_role")NONEXISTENT,
    @SerializedName("") NONE,
}
