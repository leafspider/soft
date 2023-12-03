package leafspider.util;
/**
 * @author Mark Hurst
 * @description .
 * @since KOS 1.00.
 */
public class UnyieldingProcessException extends Exception
{
	private String message;	
	public UnyieldingProcessException( String thisMessage )
	{
		super( thisMessage );
		message = thisMessage;
	}
	public String getMessage() { return message; }
}

