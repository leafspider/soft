package leafspider.rest;

public class RepresentationException extends Exception
{
	private String message;

	public RepresentationException(String thisMessage)
	{
		message = thisMessage;
	}

	public String getMessage()
	{
		return message;
	}

}