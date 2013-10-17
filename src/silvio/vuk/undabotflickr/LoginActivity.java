package silvio.vuk.undabotflickr;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		requestTokenReturn();
		authorize();
		accessToken();
	}
	
	
	private void requestTokenReturn()
	{
		String secret = "f5a6671614882042";
		String key = secret + "&";
		
		String BaseString = OauthSignatureCreator.requestTokenCreateBaseStringForFlickr("http://www.test.com");
		String Signature;
		
		try 
		{
			Signature = OauthSignatureCreator.hmacSha1(BaseString, key);
			
			String base = "http://www.flickr.com/services/oauth/request_token?";
			String nonce = "asdasdasqwhoihlash";
			
			String timestamp = OauthSignatureCreator.getTime();
			
			String URL = base + "oauth_callback=http://www.example.com" + "&" + "oauth_consumer_key=" + Flickr.API_KEY + "&" 
					+ "oauth_nonce=" +nonce + "&" + "oauth_signature=" + Signature + "&" + "oauth_signature_method=HMAC-SHA1" + "&"
					+ "oauth_timestamp=" +timestamp + "&" + "oauth_version=1.0";
		
			//URL = URLEncoder.encode(URL, OauthSignatureCreator.CHARSET_NAME);
			
			sendMessage(URL);
		} 
		catch (InvalidKeyException e) {
			Log.e(StaticCube.RESPONSE_TAG, e.getMessage() + e.getStackTrace() +"1");
			Toast.makeText(this, "Contact developers! err#4", Toast.LENGTH_LONG).show();
		} 
		catch (UnsupportedEncodingException e) {
			Log.e(StaticCube.RESPONSE_TAG, e.getMessage() + e.getStackTrace() +"2");
			Toast.makeText(this, "Contact developers! err#4", Toast.LENGTH_LONG).show();
		} 
		catch (NoSuchAlgorithmException e) {
			Log.e(StaticCube.RESPONSE_TAG, e.getMessage() + e.getStackTrace() +"3");
			Toast.makeText(this, "Contact developers! err#4", Toast.LENGTH_LONG).show();
		}
	}
	
	private void authorize()
	{
		
	}
	
	private void accessToken()
	{
		
	}
	
	private void sendMessage(String fullMethodULR)
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		
		HttpGet httpGet = new HttpGet(fullMethodULR);
		String text = null;
		
		try {

			HttpResponse response = httpClient.execute(httpGet, localContext);

			HttpEntity entity = response.getEntity();

			text = StaticCube.getASCIIContentFromEntity(entity);

			Log.i("sendMessage", text);
			
		} 
		catch (Exception e) 
		{
			Log.e(StaticCube.RESPONSE_TAG, e.getLocalizedMessage() + "\n" + e.getMessage());
			Toast.makeText(this, "Connection problem!", Toast.LENGTH_LONG).show(); // swap text with resource string !
			finish();
		}
	}
	

}
