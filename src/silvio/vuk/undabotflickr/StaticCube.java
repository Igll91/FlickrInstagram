package silvio.vuk.undabotflickr;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.http.HttpEntity;

/**
 * Used to share Tag names for logging, methods all over application.
 * 
 * 
 * @author silvio vuk
 *
 */
public final class StaticCube {

	private StaticCube(){}
	
	public static final String RESPONSE_TAG = "responseTag";
	public static final String MAIN_APP_TAG = "mainAppTag";
	
	public static String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException 
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
	
	/**
	 * Returns a psuedo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimim value
	 * @param max Maximim value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
}
