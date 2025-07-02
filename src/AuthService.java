import java.io.*;
import java.util.*;
import org.json.*;

public class AuthService {
    private static final String USERS_FILE = "users.json";
    //This function takes username as input, and checks the users.json file for any existing user. Then it creates a 6 digit login code.
    public boolean register(String username) throws IOException, JSONException {
        JSONObject users = loadUsers();
        if (users.has(username)) return false;

        String loginCode = UUID.randomUUID().toString().substring(0, 6);
        users.put(username, loginCode);
        saveUsers(users);

        System.out.println("Your login code (save this!): " + loginCode);
        return true;
    }
    //This function takes the username and login code as input and authenticates using the users.json file
    public boolean login(String username, String loginCode) throws IOException, JSONException {
        JSONObject users = loadUsers();
        return users.has(username) && users.getString(username).equals(loginCode);
    }
    //This function reads the users.json file and returns a JSON object containing all the entries
    private JSONObject loadUsers() throws IOException, JSONException {
        File file = new File(USERS_FILE);
        if (!file.exists()) return new JSONObject();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        reader.close();
        return new JSONObject(sb.toString());
    }
    //This function writes the users back to users.json file
    private void saveUsers(JSONObject users) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE));
        writer.write(users.toString(4));
        writer.close();
    }
}
