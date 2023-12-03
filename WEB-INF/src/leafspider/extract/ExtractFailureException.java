package leafspider.extract;

/** 
 * @since KOS 1.00.
 * @author Mark Hurst.
 */
public class ExtractFailureException extends Exception
{
	private String message;	
	public ExtractFailureException( String thisMessage )
	{
		super( thisMessage );
		message = thisMessage;
	}
	public String getMessage() { return message; }
}
