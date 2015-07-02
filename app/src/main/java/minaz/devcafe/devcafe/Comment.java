package minaz.devcafe.devcafe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by minaz on 30/06/15.
 */
public class Comment {
    @SerializedName("id")
    public int id;
    @SerializedName("owner")
    public User owner;
    @SerializedName("timestamp")
    public String timestamp;
    @Expose
    @SerializedName("text")
    public String text;
}
