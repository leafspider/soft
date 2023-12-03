<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
			
	<xsl:template match="/pearpicks" xml:space="preserve">	

	<html>
		<head>
			<title>Porter</title>
			
			<xsl:call-template name="head" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<style> 
				table{align:center;}
				table.stockList {background-color:white;font-size:0.8em;}
				.performance{width:100%;}
				.stockList th{border:1px dotted black;}
				td {text-align:left;padding:0px 0px;overflow:hidden;text-overflow:ellipsis;}				
				.batch-title {font-size:0.9em;font-weight:bold;padding:4px 5px 0px 5px;min-width:60px;display:inline-block;}
				.batch-cell {vertical-align:top;text-transform: uppercase;}
				.pick a {color:white;}
				.pick-ticker {font-size:0.9em;margin:2px 0px;padding:2px 4px;color:white;border:solid 1px lightgrey;border-radius:3px;display:inline-block;}
			</style>
		</head>	
		
	<body>
			
		<form>
				
			<xsl:call-template name="menu" />
			 
			<table border="0" width="100%">
				<tr style="font-size:1.2em;text-align:center;">
					<th colspan="2" style="text-align:left;padding:30px 0px 0px 50px"><nobr><b><h1>Porter</h1></b></nobr></th>
				</tr>						
				<tr>				
					<td valign="top">
						<div class="toptail" style="margin-right:10px;margin-left:50px;">		
							<xsl:apply-templates select="batches" />										
						</div>							
					</td>				
				</tr>
			</table>
		
		</form>
		
		</body>		
	</html>
						
	</xsl:template>

	<xsl:template match="batches">			
		<table class="stockList performance">
			<tr><td><br/></td><td></td></tr>
			<xsl:apply-templates select="batch" />
			<tr><td><br/></td><td></td></tr>
		</table>
	</xsl:template>

	<xsl:template match="batch">	
		<tr>
			<td class="batch-cell">
				<div class="batch-title"><nobr><xsl:value-of select="@title" /></nobr></div>
			</td>
			<td>
				<xsl:apply-templates select="pick"/>
			</td>
		</tr>
	</xsl:template>			  
			  
	<xsl:template match="pick">		
			<a target="_blank" href="/soft/asset/jake/{@ticker}/crush.htm?years=1" title="{@ticker} {@pear}%">
				<div class="pick-ticker" style="border:1px solid #{@crushColor};color:black;background-color:{@pearColor}"><nobr>				
				<xsl:choose>
				  <xsl:when test="contains(@ticker, '-USD.CC')">
					<xsl:value-of select="substring-before(@ticker, '-USD.CC')"/>
				  </xsl:when>
				  <xsl:otherwise>
					<xsl:value-of select="@ticker" />
				  </xsl:otherwise>
				</xsl:choose>
				</nobr></div>
			</a>
	</xsl:template>
			  
</xsl:stylesheet>
