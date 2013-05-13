import static us.monoid.web.Resty.put;

import java.io.IOException;
import java.net.URI;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.json.JSONArray;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

public class RESTClient {
    private Resty resty;
    private URI baseURI;

    public RESTClient(URI uri) {
        resty = new Resty();
        baseURI = uri;
    }
    
    public Match requestMatch() {
        JSONResource response = null;
        try {
            response = resty.json(baseURI, Resty.form("token=ignored"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            return null;
        }
        try {
            JSONObject obj = response.toObject();
            if (obj.getInt("success") == 1) {
                int id = obj.getInt("match");
                
                JSONObject configuration = (JSONObject) obj.get("configuration");
                int width = configuration.getInt("width");
                int height = configuration.getInt("height");
                int numBots = configuration.getInt("num_bots");
                int numRounds = configuration.getInt("num_rounds");
                
                Match match = new Match(id, width, height, numRounds);
                
                JSONArray entries = obj.getJSONArray("entries");
                for (int i = 0; i < entries.length(); i++) {
                    JSONObject entry = entries.getJSONObject(i);
                    match.addEntry(new Entry(entry.getInt("id"), entry.getString("proper_name")));
                }
                return match;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void reportResults(MatchResults results) {
        JSONObject jsonData = null;
        try {
            jsonData = results.toJSON();
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("JSON EXCEPTION");
            return;
        }
        
        try {
            System.out.println("Posting results");
            resty.json(baseURI.toString() + results.match.id, put(Resty.content(jsonData)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
