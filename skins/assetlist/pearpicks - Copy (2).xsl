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
				.batch-title {font-weight:bold;padding:0px 5px 5px 5px;min-height:50px;min-width:60px;}
				.batch-cell {vertical-align:top;text-transform: uppercase;}
				.pick a {color:white;}
				.pick-ticker {font-size:0.9em;margin:2px 2px;padding:2px 4px;color:white;border-radius:3px;}
			</style>
		</head>	
		
	<body>
			
		<form>
				
			<xsl:call-template name="menu" />
			 
			<table border="0" width="50%">
				<tr style="font-size:1.2em;text-align:center;">
					<th colspan="2" style="text-align:left;padding:30px 0px 0px 100px"><nobr><b><h1>Porter</h1></b></nobr></th>
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
			<tr>
				<td style="min-width:10px;"> <br/></td>
				<xsl:apply-templates select="batch" />
				<td style="width:100%"></td>
			</tr>
			<tr><td><br/></td><td></td></tr>
		</table>
	</xsl:template>

	<xsl:template match="batch">			
			<td class="batch-cell">
				<div class="batch-title"><xsl:value-of select="@title" /></div>
				<xsl:apply-templates select="pick"/>
			</td>
	</xsl:template>			  
			  
	<xsl:template match="pick">		
			<a target="_blank" href="/soft/asset/jake/{@ticker}/crush.htm?years=1" title="{@pear}">
				<div class="pick-ticker" style="color:black;background-color:{@pearColor}"><nobr><xsl:value-of select="@ticker" /></nobr></div>
			</a>
	</xsl:template>

			  
</xsl:stylesheet>
