package eu.verdelhan.acr122urw.client;

/**
 * Created by edalmai on 3/10/16.
 */
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestClient {

    Client client = Client.create();
    //Set the appropriate URL for POST request
    String postUrl = "http://localhost:8080/JAXRS-JSON/rest/student/data/post";

    public void postRequest(String value){
        WebResource webResource = client.resource(postUrl);
        String inputData = "{\"signum\":\"" + value + "\"}";
        System.out.println(inputData);
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class,inputData);
        if(response.getStatus()!=201){
            throw new RuntimeException("HTTP Error: "+ response.getStatus());
        }

        String result = response.getEntity(String.class);
        System.out.println("Response from the Server: ");
        System.out.println(result);
    }
}
