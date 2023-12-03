<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/assetlist" xml:space="preserve">	
								
	<html>
  <head>
    <title>Tickers</title>

		<xsl:call-template name="head_lite" />
	    			
    <style>
    .example {
      width:625px;
      height:350px;
    }
	.subtitle {
		font-weight:bold;
		font-size:1.7em;
	}
    </style>
    		
  </head>
	
	<body>		
							
		<form class="back" style="display:;">
			
			<xsl:call-template name="menu" />			
																			
			<div style="margin-left:10px;">
			<table border="0" width="100%" style="padding:5px 0px;">		
				<tr>
					<td border="0" align="left" style="width:33%;font-weight:bold;font-size:1.5em;padding-left:90px;padding-top:30px;padding-bottom:0px;">					
						<div class="subtitle"><xsl:value-of select="title" /> (<xsl:value-of select="max" />)</div>
					</td>
				</tr>
				<tr>
					<td border="0" align="left" style="width:33%;font-weight:bold;font-size:1.1em;padding-left:90px;padding-top:30px;padding-bottom:0px;">					
						<h1><xsl:value-of select="hierarchy" /></h1>
					</td>
				</tr>					
				<tr class="exchangesRow" style="">		
					<td>
						<a style="padding:0 15px 0 90px;" target="_crushmap_{id}" href="/soft/assetlist/jake/{id}/crush.htm">Crush</a>
						<a style="padding:0 15px 0 0px;" target="_portermap_{id}" href="/soft/assetlist/jake/{id}/pearpicks.htm">Porter</a>
						<a style="padding:0 15px 0 0px;" target="_viper_{id}" href="/soft/assetlist/jake/{id}/viper.htm">Viper</a> 
						<a style="padding:0 15px 0 0px;" target="_multi_{id}" href="/soft/assetlist/jake/{id}/multi.htm">Multi</a>
					</td>
				</tr>
				<tr>
					<td align="left" style="width:33%;padding:15px 90px 10px 90px;">
						<!--xsl:value-of select="tickers" /-->		
						<xsl:apply-templates select="tickerlist" />	
					</td>
				</tr>							
				<tr>
					<td align="left" style="width:33%;padding:15px 90px 10px 90px;">
						<xsl:apply-templates select="failures" />																
					</td>
				</tr>							
			</table>			
			</div>
			
		</form>
		
	  </body>
	</html>

	</xsl:template>

	<xsl:template match="tickerlist">
		<xsl:apply-templates select="ticker" />										
	</xsl:template>
	
	<xsl:template match="ticker">		
		<a target="_blank" href="/soft/asset/jake/{.}/crush.htm?years=1" title="{.}" style="margin-right:5px;">
			<div class="pick-ticker" style="display:inline-block;"><nobr>
				<xsl:choose>
				  <xsl:when test="contains(current(), '-USD.CC')">
					<xsl:value-of select="substring-before(current(), '-USD.CC')"/>
				  </xsl:when>
				  <xsl:otherwise>
					<xsl:value-of select="." />
				  </xsl:otherwise>
				</xsl:choose>
			</nobr></div>
		</a> 
	</xsl:template>

	<xsl:template match="failures">
		<b>Data Failures (<xsl:value-of select="count(failure)"/>)</b>
		<br/><br/>
		<xsl:apply-templates select="failure" />										
	</xsl:template>

	<xsl:template match="failure">		
		<a target="_blank" href="/soft/asset/jake/{@ticker}/crush.htm?years=1" title="{.}" style="margin-right:5px;font-size:0.7em;">
			<div class="pick-ticker" style="display:inline-block;"><nobr>
			<xsl:choose>
			  <xsl:when test="contains(@ticker, '-USD.CC')">
				<xsl:value-of select="substring-before(@ticker, '-USD.CC')"/>
			  </xsl:when>
			  <xsl:otherwise>
				<xsl:value-of select="@ticker" />
			  </xsl:otherwise>
			</xsl:choose>
			(<xsl:value-of select="@count" />)
			</nobr></div>
		</a>  
	</xsl:template>
		
</xsl:stylesheet>