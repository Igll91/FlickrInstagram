package silvio.vuk.undabotflickr;

public final class FlickrRest {

	private static final String BASE_API_REST_MESSAGE = "http://api.flickr.com/services/rest/";
	
	private static final String METHOD_TEST = "?method=flickr.test.echo";
	
	// drugi search ovaj nije dobar ! :... 
	private static final String METHOD_GET_POPULAR_IMAGES = "?method=flickr.photos.search";
	private static final String METHOD_GET_POPULAR_IMAGES_SORT = "sort=interestingness-asc";
	private static final String METHOD_GET_POPULAR_IMAGES_PER_PAGE = "per_page=20";
	
	private FlickrRest(){}
	
	public static String generateApiCall(SourceMethods methodName)
	{
		switch (methodName) 
		{
			case Test:
				return BASE_API_REST_MESSAGE + METHOD_TEST + "&api_key=" +Flickr.API_KEY;
			case GetPopularImages:
				return BASE_API_REST_MESSAGE + METHOD_GET_POPULAR_IMAGES + "&api_key=" +Flickr.API_KEY
						+"&" +METHOD_GET_POPULAR_IMAGES_SORT +"&" +METHOD_GET_POPULAR_IMAGES_PER_PAGE;
			
			default:
				return "";
		}
	}
}
