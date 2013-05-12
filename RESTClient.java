import static us.monoid.web.Resty.data;
import static us.monoid.web.Resty.form;
import static us.monoid.web.Resty.put;

import java.io.IOException;
import java.net.URI;

import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
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
            response = resty.json(baseURI, form(data("token", "ignored")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null) {
            return null;
        }
        return null;
    }
    
    public void reportResults(MatchResults results) {
        JSONObject jsonData = null;
        try {
            jsonData = results.toJSON();
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        
        try {
            resty.json(baseURI.toString() + results.match.id, put(Resty.content(jsonData)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
