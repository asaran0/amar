package ak.amar_new.models;

import com.google.gson.annotations.Expose;

/**
 * Created by amar on 31/5/15.
 */
public class LoginModel {

    @Expose
    private String id;
    @Expose
    private String password;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
