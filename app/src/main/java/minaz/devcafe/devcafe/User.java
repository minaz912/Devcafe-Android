package minaz.devcafe.devcafe;

import com.google.gson.annotations.SerializedName;

/**
 * Created by minaz on 30/06/15.
 */
public class User {
    @SerializedName("id")
    public int id;
    @SerializedName("username")
    public String username;
    @SerializedName("picture")
    public String picture;
}
