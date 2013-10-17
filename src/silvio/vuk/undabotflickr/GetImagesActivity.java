package silvio.vuk.undabotflickr;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class GetImagesActivity extends Activity {

	private static String fullMethodULR;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_images);
		
		String sourceTag = getIntent().getStringExtra(MainActivity.SOURCE_TAG);
		ImageSourceName imageSourceName;
		imageSourceName = ImageSourceName.exists(sourceTag);
		
		if(imageSourceName != null)
		{
			ImageSource imageSource = ImageSourceFactory.buildSource(imageSourceName);
			fullMethodULR = imageSource.getMethod(SourceMethods.GetPopularImages);
			
			new BackgroundImageReceiver().execute();
		}
		else
		{
			Toast.makeText(this, "Application err#1: No such source name! contact developers!", Toast.LENGTH_LONG).show();
			finish();
		}
	}

	private class BackgroundImageReceiver extends AsyncTask <Void, Void, String>
	{
	
		@Override
		protected String doInBackground(Void... params) 
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpContext localContext = new BasicHttpContext();
			
			HttpGet httpGet = new HttpGet(fullMethodULR);
			String text = null;
			
			try {

				HttpResponse response = httpClient.execute(httpGet, localContext);

				HttpEntity entity = response.getEntity();

				text = StaticCube.getASCIIContentFromEntity(entity);

			} 
			catch (Exception e) 
			{
				return e.getLocalizedMessage();
			}

			return text;
		}

		protected void onPostExecute(String results) 
		{
			if (results!=null) 
			{
				if(hasMethodSucceded(results))
				{
					TextView et = (TextView)findViewById(R.id.testStuff);
					et.setText(results);
				}
				else
				{
					Toast.makeText(GetImagesActivity.this, "Err#2 flickr response failed: contact developer", Toast.LENGTH_LONG).show();
					Log.e(StaticCube.RESPONSE_TAG, "Err#2 flickr response failed: contact developer");
					Log.e(StaticCube.RESPONSE_TAG, "\n" +results);
				}
			}
		}
		
		private boolean hasMethodSucceded(String string)
		{
			String[] lines = string.split(System.getProperty("line.separator"));
	
			if(lines[1].contains("rsp stat=\"ok\""))
				return true;
			else
				return false;
		}
	}
}
