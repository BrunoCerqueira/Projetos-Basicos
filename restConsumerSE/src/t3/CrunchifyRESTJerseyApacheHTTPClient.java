package t3;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import com.tutorialacademy.http.Pessoa;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class CrunchifyRESTJerseyApacheHTTPClient {
	static String REST_SERVICE_URL = "http://localhost:8080/helloworld/rest/";
	public static void  main(String[] args) {
		if(Boolean.TRUE){
		try {
			 Pessoa p = new Pessoa(1,"José","M");
			javax.ws.rs.client.Client client = ClientBuilder.newClient();
			Invocation salvarPessoa = client.target(REST_SERVICE_URL+"cadastrar").request()
					.buildPost(Entity.entity(p, javax.ws.rs.core.MediaType.APPLICATION_JSON));
		
			Response response = salvarPessoa.invoke();
			Pessoa b1 = response.readEntity(Pessoa.class);
			Invocation getPessoas = client.target(REST_SERVICE_URL+"todasPessoas").request()
					.buildGet();
			response = getPessoas.invoke();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		}
			
		else{
			try {
				
				// create HTTP Client
				HttpClient httpClient = HttpClientBuilder.create().build();
	 
				// Create new getRequest with below mentioned URL
	//			HttpGet getRequest = new HttpGet("http://localhost:8080/helloworld/rest/");
				HttpPost post = new HttpPost("http://localhost:8080/helloworld/rest/cadastrar");
	 
				// Add additional header to getRequest which accepts application/xml data
				post.addHeader("accept", "text/html");
	 
				// Execute your request and catch response
				HttpResponse response = httpClient.execute(post);
	 
				// Check for HTTP response code: 200 = success
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}
	 
				// Get-Capture Complete application/xml body response
				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
				String output;
				System.out.println("============Output:============");
	 
				// Simply iterate through XML response and show on console.
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
	 
			} catch (ClientProtocolException e) {
				e.printStackTrace();
	 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}