/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hc.client5.http.auth.AuthenticationException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.simple.JSONObject;

/**
 *
 * @author Manel
 */
public class RequestSender {

    final CloseableHttpClient client = HttpClients.createDefault();
    final String NO_AUTHENTICATION_ERROR_MESSAGE = "Trebuie sa apelezi authenticate inainte de a trimite un request pentru prima data";
    final static int NO_AUTH = 1;
    int identifier = -1;

    public RequestSender(int identifier) {
        this.identifier = identifier;
    }

    public RequestSender() {

    }

    public void sendPostRequest(String url, JSONObject bodyJson, RequestHandler callback) {
        HttpPost post = new HttpPost(url);
        post.setHeader("connection", "close");
        StringEntity entity = new StringEntity(bodyJson.toJSONString(), ContentType.APPLICATION_JSON);
        post.setEntity(entity);
        try {
            CloseableHttpResponse response = client.execute(post);
            try (InputStream instream = response.getEntity().getContent()) {
                BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(instream));
                String line;
                String raspuns = "";
                while ((line = inputStreamReader.readLine()) != null) {
                    raspuns += line + "\n";
                }
                callback.onSucces(raspuns, response);
            }
        } catch (IOException ex) {
            callback.onError(ex);
            Logger.getLogger(RequestSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendGetRequest(String url, String query, RequestHandler callback) {
        HttpGet getRequest = new HttpGet(url + "/" + query);
        getRequest.setHeader("connection", "close");
        try {
            CloseableHttpResponse response = client.execute(getRequest);
            try (InputStream instream = response.getEntity().getContent()) {
                BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(instream));
                String line;
                String raspuns = "";
                while ((line = inputStreamReader.readLine()) != null) {
                    raspuns += line + "\n";
                }
                callback.onSucces(raspuns, response);
            }
        } catch (IOException ex) {
            callback.onError(ex);
            Logger.getLogger(RequestSender.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public interface RequestHandler {

        public void onSucces(String body, CloseableHttpResponse response);

        public void onError(IOException ex);
    }
}
