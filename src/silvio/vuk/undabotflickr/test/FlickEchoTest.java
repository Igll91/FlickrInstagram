package silvio.vuk.undabotflickr.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import silvio.vuk.undabotflickr.R;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class FlickEchoTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_flick_echo_test);
		
		new LongRunningGetIO().execute();
		
	}

	private class LongRunningGetIO extends AsyncTask <Void, Void, String> 
	{
		protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException 
		{
			InputStream in = entity.getContent();

			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n>0) 
			{
				byte[] b = new byte[4096];

				n =  in.read(b);

				if (n>0) out.append(new String(b, 0, n));

			}

			return out.toString();

		}

		@Override

		protected String doInBackground(Void... params) 
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			
			HttpGet httpGet = new HttpGet
					("http://api.flickr.com/services/rest/?method=flickr.test.echo&name=value&api_key=bbd62d8b5acf385e2303eae3a28a03d6&format=rest");
			String text = null;
			try {

				HttpResponse response = httpClient.execute(httpGet, localContext);

				HttpEntity entity = response.getEntity();

				text = getASCIIContentFromEntity(entity);

			} 
			catch (Exception e) {
				return e.getLocalizedMessage();

			}

			return text;

		}

		protected void onPostExecute(String results) 
		{
			if (results!=null) {

				TextView et = (TextView)findViewById(R.id.textView_flickr_test_echo);

				et.setText(results);

			}
		}

	}

}
