package minaz.devcafe.devcafe;

import com.google.gson.annotations.SerializedName;

public class Idea {
    @SerializedName("id")
    public int id;
    @SerializedName("owner")
    public User owner;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("comments")
    public Comment[] comments;
}
