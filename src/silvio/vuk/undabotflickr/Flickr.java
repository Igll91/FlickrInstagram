package silvio.vuk.undabotflickr;

public class Flickr extends ImageSource {

	public static final String API_KEY = "bbd62d8b5acf385e2303eae3a28a03d6";
	// if needed can be swapped for another method later in development.
	private static final FlickrRequestFormat REQUEST_FORMAT = FlickrRequestFormat.REST;
		
	@Override
	protected String getMethod(SourceMethods methodName) 
	{
		switch (REQUEST_FORMAT)
		{
			case REST:
				return FlickrRest.generateApiCall(methodName);
	
			default:
				return "";
		}
	}

}
