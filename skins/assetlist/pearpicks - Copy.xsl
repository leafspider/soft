<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
			
	<xsl:template match="/pearpicks" xml:space="preserve">	

	<html>
		<head>
			<title>Pearpicks</title>
			
			<xsl:call-template name="head" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		  <style> 
				table{align:center;}
				table.stockList {background-color:white;}
				.performance{width:100%;}
				.stockList th{border:1px dotted black;}
		    td {text-align:left;padding:2px 5px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;}
				.batch-title {font-weight:bold;padding:5px 5px 5px 15px;}
				.pick a {color:white;}
				.pick-ticker {width:90px;padding:2px 4px;display:inline-block;color:white;border-radius:3px;}
			</style>
		</head>	
		
	<body>
			
		<form>
				
			<xsl:call-template name="menu" />
			 
			<table border="0" width="50%">
				<tr style="font-size:1.2em;text-align:center;">
					<th colspan="2" style="text-align:left;padding:30px 0px 0px 100px"><nobr><b><h1>Pear Picks</h1></b></nobr></th>
				</tr>						
				<tr>				
					<td valign="top">						
						<div class="toptail" style="margin-right:10px;margin-left:100px;">				
							
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
			<td>
				<span class="batch-title"><xsl:value-of select="@title" /></span>
			</td>
			<td style="padding-right:20px;">
				<xsl:apply-templates select="pick"/>
			</td>
		</tr>
	</xsl:template>			  
			  
	<xsl:template match="pick">		
			<a target="_blank" href="/soft/asset/jake/{@ticker}/crush.htm?years=1" title="{@pear}">
				<div class="pick-ticker" style="color:black;background-color:{@pearColor}"><xsl:value-of select="@ticker" /></div>
			</a>
	</xsl:template>

			  
</xsl:stylesheet>
