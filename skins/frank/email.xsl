<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/email" xml:space="preserve">	
	<html>
	  <head>
	    <title>Email</title>				
	  </head>	
		<body>		
			
			<script>				
				
				setCookies( "7D98AE474C0FCCFEF95B52756C10DAC8" );
				
				function setCookies( sessionId )
				{				
	        setCookie( "JSESSIONID", sessionId );
	        setCookie( "__utma", "13715124.1175791562.1370291949.1370291949.1370294345.2");
	        setCookie( "__utmb", "13715124.36.10.1370294345" );
	        setCookie( "__utmc", "13715124" );
	        setCookie( "__utmz", "13715124.1370294345.2.2.utmcsr=ruonline.ryerson.ca|utmccn=(referral)|utmcmd=referral|utmcct=/ccon/" );
				}
				
				function setCookie( cookieName, cookieValue ) 
				{
						var nDays = 7;
					 var today = new Date();
					 var expire = new Date();
					 if (nDays==null || nDays==0) nDays=1;
					 expire.setTime(today.getTime() + 3600000*24*nDays);
					 document.cookie = cookieName + "=" + escape(cookieValue) + ";expires=" + expire.toGMTString();
//					 document.cookie = cookieName + "=" + escape(cookieValue);
				}
				
				function showEmail(id)
				{
					document.forms[0].action="https://ruonline.ryerson.ca/ccon/online_dir_email.do?action=showEmail";
					document.getElementById("constId").value = id;
					document.forms[0].submit();
				}		   
				
			</script>

			<form class="back" style="margin:50px;">
				
				<input type="hidden" id="constId" name="constId"  value=""/>		
				<a href='#' onClick="showEmail('{id}');">
					script <xsl:value-of select="id" />
				</a>			
				
				<br/><br/>
				
				<a href='#' onClick="/soft/frank/server/{id}/email.htm">
					server <xsl:value-of select="id" />
				</a>			

			</form>		
			
	  </body>
	</html>
	</xsl:template>
	
</xsl:stylesheet>