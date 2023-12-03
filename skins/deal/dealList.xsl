<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>
		
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/dealList" xml:space="preserve">	

	<html>
		<head>    	
			<link rel="icon" href="{$skinUrl}leafspider.png"/>
			<title>Deals</title>
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<link href="{$skinUrl}stores_style.css" type="text/css" rel="stylesheet" media="aural,braille,embossed,handheld,projection,screen,tty,tv"/>
		</head>	
		
		<body>
			
			<a class="unsel" href="/soft/city/Chad.htm">&lt; Cities</a>
			
		<br/><br/>
		
		<span class="toptitle">
			Deals 
			<!-- for 
			<xsl:apply-templates select="project"/>
			<xsl:text>&#160;</xsl:text>
			-->
			in 
			<xsl:apply-templates select="city"/> (<xsl:value-of select="count" />)
		</span>	
		
		<br/><br/>
		
		<table>
					<xsl:apply-templates select="deal"/>
		</table>
		
		</body>		
	</html>
					
	</xsl:template>
	
	<xsl:template match="deal" >		
		<tr>
			<td class="title" colspan="4">
				<a href="{url}" target="_blank"><xsl:value-of select="text" /></a>
			</td>
		</tr>
		<tr>
			<td class="weight">
					Price <xsl:value-of select="price" />
			</td>
			<td class="weight">
					Saving <xsl:value-of select="saving" />
			</td>
			<td class="weight">
					Value <xsl:value-of select="value" />
			</td>
			<td class="weight" width="75%">
					Discount <xsl:value-of select="discount" />
			</td>
		</tr>
		<tr>
			<td class="info">
					<xsl:value-of select="storeHost" />
			</td>
			<td class="info" colspan="3">
					<xsl:value-of select="nouns" />
			</td>
		</tr>
		<tr>
			<td>
				<br/>
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>
