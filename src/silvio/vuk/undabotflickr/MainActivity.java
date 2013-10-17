package silvio.vuk.undabotflickr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{

	public static final String SOURCE_TAG = "sourceTag";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button startTest = (Button) findViewById(R.id.button1);
        startTest.setOnClickListener(this);
        
        // check if user is logined ...
        
    }

	@Override
	public void onClick(View v) 
	{
		Intent testIntent = new Intent(this, LoginActivity.class);
		String sourceTag = v.getTag().toString();
		testIntent.putExtra(SOURCE_TAG, sourceTag);
		startActivity(testIntent);
	}
    
}
