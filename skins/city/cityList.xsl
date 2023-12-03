<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>
		
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/cityList" xml:space="preserve">	

	<html>
		<head>
    	<link rel="icon" href="{$skinUrl}leafspider.png"/>
			<!--<title>Resources for <xsl:value-of select="name"/></title>-->
			<title>Stores</title>
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<link href="{$skinUrl}stores_style.css" type="text/css" rel="stylesheet" media="aural,braille,embossed,handheld,projection,screen,tty,tv"/>
		</head>	
		
		<body>
			
		<br/><br/>
		
		<span class="toptitle">
			Cities 
			<!-- for 
			<xsl:apply-templates select="project"/>
			<xsl:text>&#160;</xsl:text>
			-->
			(<xsl:value-of select="count" />)
		</span>	
		
		<br/><br/>
		
		<table>
					<xsl:apply-templates select="city"/>
		</table>
				
		</body>		
	</html>
					
	</xsl:template>
	
	<xsl:template match="city" >	
					<tr>
						<td class="title">	
							<a href="/soft/deal/Chad.htm?city={city}"><xsl:value-of select="city" /></a><br/>
						</td>
					</tr>
	</xsl:template>

</xsl:stylesheet>
