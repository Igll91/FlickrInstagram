package silvio.vuk.undabotflickr;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

public final class OauthSignatureCreator {

	private static final int MIN_NUM = 1000;
	private static final int MAX_NUM = 9999999;
	private static final String OAUTH_VERSION = "1.0";
	private static final String SIGNATURE_METHOD = "HMAC-SHA1";
	public static final String CHARSET_NAME = "UTF-8";
	
	private OauthSignatureCreator(){}
	
	public static String requestTokenCreateBaseStringForFlickr(String callbackURL)
	{
		String nonce = String.valueOf(StaticCube.randInt(MIN_NUM, MAX_NUM));
		String timestamp = getTime();
		
		String partOneHTTPMethod = "GET";	
		String partTwoURLEndpoint = "http://www.flickr.com/services/oauth/request_token";
		
		try 
		{
			String partTwoURLEndpointEncoded = URLEncoder.encode(partTwoURLEndpoint, CHARSET_NAME);
			
			String oauthCallback = URLEncoder.encode("oauth_callback", CHARSET_NAME) + "%3D" +
                    URLEncoder.encode(callbackURL, CHARSET_NAME) + "%26";
			String oauthConsumerKey = URLEncoder.encode("oauth_consumer_key", CHARSET_NAME) + "%3D" +
                    URLEncoder.encode(Flickr.API_KEY, CHARSET_NAME) + "%26";
			String oauthNonce = URLEncoder.encode("oauth_nonce", CHARSET_NAME) + "%3D" +
                    URLEncoder.encode(nonce, CHARSET_NAME) + "%26";
			String oauthSignatureMethod = URLEncoder.encode("oauth_signature_method", CHARSET_NAME) + "%3D" +
                    URLEncoder.encode(SIGNATURE_METHOD, CHARSET_NAME) + "%26";
			String oauthTimestamp = URLEncoder.encode("oauth_timestamp", CHARSET_NAME) + "%3D" +
                    URLEncoder.encode(timestamp, CHARSET_NAME) + "%26";
			String oauthVersion = URLEncoder.encode("oauth_version", CHARSET_NAME) + "%3D" +
                    URLEncoder.encode(OAUTH_VERSION, CHARSET_NAME);
			
			String baseString = partOneHTTPMethod + "&" + partTwoURLEndpointEncoded + "&" + oauthCallback
					+ oauthConsumerKey + oauthNonce + oauthSignatureMethod + oauthTimestamp + oauthVersion;
			
			return baseString;
		}
		catch (UnsupportedEncodingException e) {
			Log.e(StaticCube.RESPONSE_TAG, e.getMessage());
			return "";
		}
	}
	
	public static String getTime()
	{
		int millis = (int) System.currentTimeMillis() * -1;
        int time = (int) millis / 1000;
        
        return String.valueOf(time);
	}
	
	public static String hmacSha1(String value, String key) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException 
	{
	    String type = "HmacSHA1";
	    SecretKeySpec secret = new SecretKeySpec(key.getBytes(), type);
	    Mac mac = Mac.getInstance(type);
	    mac.init(secret);
	    byte[] bytes = mac.doFinal(value.getBytes());
	    return bytesToHex(bytes);
	}

	private final static char[] hexArray = "0123456789abcdef".toCharArray();

	private static String bytesToHex(byte[] bytes) 
	{
	    char[] hexChars = new char[bytes.length * 2];
	    int v;
	    for (int j = 0; j < bytes.length; j++) {
	        v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
}
