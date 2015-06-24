package minaz.devcafe.devcafe;

import com.google.gson.annotations.SerializedName;

/**
 * Created by minaz on 22/06/15.
 */
public class Idea {
    @SerializedName("owner")
    public String owner;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
}
