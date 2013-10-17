package silvio.vuk.undabotflickr;

public enum ImageSourceName {
	
	Flickr, Instagram;
	
	public static ImageSourceName exists(String name)
	{
		for(ImageSourceName isn: ImageSourceName.values())
		{
			if(isn.name().equals(name))
				return isn;
		}
		return null;
	}
}
