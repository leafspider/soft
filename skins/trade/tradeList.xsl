<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>
		
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/tradeList" xml:space="preserve">	

	<html>
		<head>
			<title>Trades</title>
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		</head>	
		
		<body>
			
		<br/>
		
		<div class="contentHeader" style="margin-left:10px">
			<span class="title">
				Trades: <xsl:apply-templates select="project"/> (<xsl:value-of select="count" />)
			</span>	
		</div>
		
		<br/>

		<form>			
			<div class="sentence-Content" style="margin-left:20px">
				<table>
					<tr>
						<td valign="top" style="font-size:15;">
							<xsl:apply-templates select="trade"/>
						</td>
					</tr>
				</table>
			</div>	
		</form>
		
		</body>		
	</html>
					
	</xsl:template>
	
	<xsl:template match="trade" >		
		
		<xsl:value-of select="ticker" /> <a href="report.htm"><xsl:value-of select="callDate" /></a><br/>
		
	</xsl:template>

</xsl:stylesheet>
