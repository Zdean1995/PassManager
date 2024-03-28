package Pang_Dean_Project;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Passwords class provides the base for adding a password
 *
 * @author Timothy
 */

public class Password {
    
    private String linkName;
    private String userName;
    private String passcode;

    public Password(String linkName, String userName,String passcode) {
        //JSONObject  = new JSONObject();
        this.linkName = linkName;
        this.userName = userName;
        this.passcode = passcode;
    }

    public String getLinkName() {
        return linkName;
    }

    public String getPasscode() {
        return passcode;
    }
    
    public String getUserName() {
        return userName;
    }
    public JSONObject toJSONObject() {
    
        JSONObject object = new JSONObject();
        
        object.put("linkName", linkName);
        object.put("userName", userName);
        object.put("passcode", passcode);
        
        return object;
    
    }
    
    

    
}