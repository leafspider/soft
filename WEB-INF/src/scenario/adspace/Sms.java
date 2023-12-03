package scenario.adspace;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jdom2.Attribute;
import org.jdom2.Element;

import leafspider.util.*;

public class Sms 
{
	// Git attempt
    /*
		<?xml version="1.0" encoding="utf-8" ?>
		<forwardSMS>
		  <orgCode orgId='SMSnet.ca' />
		  <orgShort shortcd='+767638'>		
		    <newMsg msgId='1'>
		      <msgPhone phonenum='+19995550001' />
		      <msgText type='text'>TDAB</msgText>
		      <msgCarrier type='text'>RG_PROD</msgCarrier>
		    </newMsg>
		  </orgShort>
		</forwardSMS>
    */
    
	public static void main(String[] args) 
	{
		try
		{		
			try
			{		
				Element root = new Element( "forwardSMS" );
				Element orgCode = new Element( "orgCode" );
				orgCode.setAttribute("orgId","SMSnet.ca");
				root.addContent(orgCode);
				
				Element orgShort = new Element( "orgShort" );
				orgShort.setAttribute("shortcd","+767638");			// Canada
//				orgShort.setAttribute("shortcd","+447860034996");	// Europe	
				root.addContent(orgShort);

				// msg1
				Element msg = new Element("newMsg");
				msg.setAttribute("msgId","1");
				
				Element phone = new Element("msgPhone");
				phone.setAttribute("phonenum","+16477042471");		// Mark
//				phone.setAttribute("phonenum","+14168232036");		// Nick
//				phone.setAttribute("phonenum","+447795835930");		// Mark
//				phone.setAttribute("phonenum","+447920487738");		// Tom
//				phone.setAttribute("phonenum","+447760117123");		// Greeba
				msg.addContent(phone);
				
				Element text = new Element("msgText");
				text.setAttribute("type","text");
//				text.addContent("OCE204");
//				text.addContent("DAVIS1");
//				text.addContent("MARS24");
//				text.addContent("DX 704");
//				text.addContent("DX 208 +14168232036");
//				text.addContent("TD 1A01");
//				text.addContent("TD 1A01 +14168232036");
//				text.addContent("S2135 4168232036");
//				text.addContent("S233");
//				text.addContent("S25 1F13");
//				text.addContent("S25 4A61hi");
//				text.addContent("S25");
//				text.addContent("S26 11G29");
//				text.addContent("S22 11");
//				text.addContent("S26 8B51a");
//				text.addContent("S26 100");
				text.addContent("S26");
				msg.addContent(text);
				
				Element carrier = new Element("msgCarrier");
				carrier.setAttribute("type","text");
//				carrier.addContent("RG_PROD");
				carrier.addContent("");
				msg.addContent(carrier);
								
				orgShort.addContent( msg );
				//--

				/* // msg2
				Element msg2 = new Element("newMsg");
				msg2.setAttribute("msgId","2");
				
				Element phone2 = new Element("msgPhone");
				phone2.setAttribute("phonenum","+16477042471");	// Mark
//				phone2.setAttribute("phonenum","+14168232036");	// Nick
				msg2.addContent(phone2);
				
				Element text2 = new Element("msgText");
				text2.setAttribute("type","text");
				text2.addContent("TD 5B46");
				msg2.addContent(text2);
				
				Element carrier2 = new Element("msgCarrier");
				carrier2.setAttribute("type","text");
				carrier2.addContent("RG_PROD");
				msg2.addContent(carrier2);
								
				orgShort.addContent( msg2 );
				// ------ */
				
				XmlJdomWriter jdomWriter = new XmlJdomWriter();	
				jdomWriter.setXmlEncodingType("UTF-8");					
				String xml = jdomWriter.writeToString( root );
				System.out.println( xml );

//		        URL url = new URL( "http://demo.marketstory.com/adspace/sms" );
//		        URL url = new URL( "http://208.75.75.79/adspace/sms" );
//		        URL url = new URL( "http://demo.leafspider.com/adspace/sms" );
//		        URL url = new URL( "http://localhost:8732/adspace/sms" );
		        URL url = new URL( "http://s2demo.cloudapp.net/adspace/sms" );
		        
		        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput( true );
				conn.setRequestProperty( "Content-Type", "application/xml" );
		        conn.setRequestMethod( "POST" );
		        
				OutputStreamWriter writer = new OutputStreamWriter( conn.getOutputStream() );
				writer.write( xml );
				writer.flush();
				writer.close();

		        BufferedReader reader = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
		        System.out.println( "code=" + conn.getResponseCode() );
		        String line;
		        while ( (line = reader.readLine()) != null ) { System.out.println( line ); }
		        reader.close();
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

}
