package t3;
 
import java.util.List;
import java.util.concurrent.Future;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tutorialacademy.http.Pessoa;
 
/**
 * @author Crunchify.com
 * 
 */
 
public class CrunchifyRESTJerseyApacheHTTPClient {
	static String REST_SERVICE_URL = "http://localhost:8080/helloworld/rest/";
	public static void  main(String[] args) {
		salvarPessoaHttp();
		SalvarPessoaJersey();
 		if(Boolean.TRUE){
		try {
//			 Pessoa p = new Pessoa(1,"José","M");
//			javax.ws.rs.client.Client client = ClientBuilder.newClient();
//			Invocation salvarPessoa = client.target(REST_SERVICE_URL+"cadastrar").request()
//					.buildPost(Entity.entity(p, javax.ws.rs.core.MediaType.APPLICATION_JSON));
//		
//			Response response = salvarPessoa.invoke();
//			Pessoa b1 = response.readEntity(Pessoa.class);
			javax.ws.rs.client.Client client = ClientBuilder.newClient();
			
//			Response response = getPessoas.invoke();
//			GenericType<List<Pessoa>> pessoaType = new GenericType<List<Pessoa>>() {
//			}; // generic type to wrap a generic list of books
			
			Invocation listarPessoas  = client.target(REST_SERVICE_URL+"todasPessoasAtualizado").request().buildGet();
			Future<List<Pessoa>> pessoaType  = listarPessoas.submit(new GenericType<List<Pessoa>>(){});
			pessoaType.get();
			System.out.println();
			 
			} catch (Exception e) {  
				e.printStackTrace();
			}
 		}
			
		else{
			System.out.println();
		}
//			try {
				
				// create HTTP Client
//				HttpClient httpClient = HttpClientBuilder.create().build();
	 
				// Create new getRequest with below mentioned URL
	//			HttpGet getRequest = new HttpGet("http://localhost:8080/helloworld/rest/");
//				HttpPost post = new HttpPost("http://localhost:8080/helloworld/rest/cadastrar");
	 
				// Add additional header to getRequest which accepts application/xml data
//				post.addHeader("accept", "text/html");
	 
				// Execute your request and catch response
//				HttpResponse response = httpClient.execute(post);
	 
				// Check for HTTP response code: 200 = success
//				if (response.getStatusLine().getStatusCode() != 200) {
//					throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
//				}
	 
				// Get-Capture Complete application/xml body response
//				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
//				String output;
//				System.out.println("============Output:============");
//	 
//				// Simply iterate through XML response and show on console.
//				while ((output = br.readLine()) != null) {
//					System.out.println(output);
//				}
//	 
//			} catch (ClientProtocolException e) {
//				e.printStackTrace();
//	 
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}
	public static void SalvarPessoaJersey(){
		Pessoa pessoaMock = new Pessoa("Djair" ,"M");
		Client client = ClientBuilder.newClient();
		Pessoa pessoaSalvaRest = client
				.target(REST_SERVICE_URL+"cadastrarAtualizado")
				.request()
				.post(Entity.entity(pessoaMock, MediaType.APPLICATION_JSON),
						Pessoa.class);
	 
	}
	public static void salvarPessoaHttp(){
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
			
	}

	
//	Client c = ClientBuilder.newClient();
//    WebTarget target = c.target("http://correiosapi.apphb.com/cep/76873274");
//    Invocation.Builder invocationBuilder = target.request(MediaType.TEXT_XML_TYPE);
//    Response response = invocationBuilder.get();
//    System.out.println(response.getStatus());
//    System.out.println(response.readEntity(String.class));
