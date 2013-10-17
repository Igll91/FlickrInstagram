package silvio.vuk.undabotflickr;

public final class ImageSourceFactory {

	private ImageSourceFactory(){}
	
	public static ImageSource buildSource(ImageSourceName sourceName)
	{	
		switch (sourceName) 
		{
			case Flickr:
				return new Flickr();
			case Instagram:	
				return null;
				
			default:
				return null;
		}
	}
}
