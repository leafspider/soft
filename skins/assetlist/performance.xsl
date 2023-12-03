<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
	
	<xsl:variable name="totalTradeAmount">
		<xsl:for-each select="stock">
			
	  </xsl:for-each>
	</xsl:variable>
		
	<xsl:template match="/performance" xml:space="preserve">	

	<html>
		<head>
			<title>Performance</title>
			
			<link href="/soft/skins/resource/leafspider.png" rel="icon"> 
			
			<xsl:call-template name="head" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<style>
				table.stockList {border:1px solid black;background-color:white;}
				.performance{width:50%;}
				.stockList th{border:1px dotted black;}
				.stockList td{border:1px dotted black;}
				.stock td{padding:4;text-align:right;}
				.stock th{padding:4;text-align:left;font-weight:normal;}
				.stockTotal td{padding:4;text-align:right;border-top:1px solid black;font-weight:bold;}
				.stockTotal th{padding:4;text-align:left;border-top:1px solid black;font-weight:bold;}
				.stockTitle td{padding:4;text-align:right;border-bottom:1px solid black;}
				.stockTitle th{padding:4;text-align:center;border-top:1px solid black;border-bottom:1px solid black;font-weight:bold;}
				td.divider {border-left:1px solid black;}
				th.divider {border-left:1px solid black;}
		    </style>
		</head>	
		
	<body>
			
		<form>
				
			<xsl:call-template name="menu" />
			 
		<font face="arial">	 
		  
		<div style="margin-left:100px;margin-right:30px;">
							
				<table width="100%">
					<tr>
						<th style="font-align:left;font-size:2.0em;text-align:left;padding:25px 0px 15px 0px"><nobr><b><xsl:value-of select='title' /></b></nobr></th>
					</tr>
				</table>
							
				<table width="10" align="left">
					<tr>
						<td>

				<div class="toptail">
									
				<table class="stockList">
					<tr class="stockTitle">
						<th class="divider" width="1"><nobr>Instrument</nobr></th>
						<th class="divider" width="1"><nobr>Entry Date</nobr></th>
						<th class="divider" width="1"><nobr>Entry Price</nobr></th>
						<!--
						<th class="divider" width="1"><nobr>Duration</nobr></th>
						-->
						<th class="divider" width="1"><nobr>Exit Date</nobr></th>
						<th class="divider" width="1"><nobr>Exit Price</nobr></th>
						<th class="divider" width="1"><nobr>% Return</nobr></th>
						<th class="divider" width="1"><nobr>Shares</nobr></th>
						<th class="divider" width="1"><nobr>Return</nobr></th>
					</tr>
					<xsl:apply-templates select="trade"/>
					<tr class="stockTitle">
						<th class="divider" width="1" style="text-align:left;"></th>
						<th class="divider" width="1"></th>
						<th class="divider" width="1"></th>
						<!--
						<th class="divider" width="1"></th>
						-->
						<th class="divider" width="1"></th>
						<th class="divider" width="1"></th>
						<th class="divider" width="1" style="text-align:right;"><xsl:value-of select='format-number((sum(//trade//percentageReturn) div 84), "###,##0.0")' /></th>	
						<th class="divider" width="1"></th>
						<th class="divider" width="1" style="text-align:right;"><xsl:value-of select='format-number((sum(//trade//return)), "$###,##0.0")' /></th>	
					</tr>
				</table>
			
				</div>	
				
						</td>
					</tr>
				</table>
					
		</div>	
									
		</font>		
		
		</form>
		
		</body>		
	</html>
						
	</xsl:template>
	
	<xsl:template match="trade" >		

			<tr class="stock">
				<th class="divider"><nobr><xsl:value-of select='instrument' /></nobr></th>
				<td class="divider"><xsl:value-of select='entryDate' /></td>
				<td class="divider"><xsl:value-of select='format-number(entryPrice, "$###,##0.00")' /></td>
				<!--<td class="divider"><xsl:value-of select='duration' /></td>				-->
				<td class="divider"><xsl:value-of select='exitDate' /></td>
				<td class="divider"><xsl:value-of select='format-number(exitPrice, "$###,##0.00")' /></td>
				<td class="divider"><xsl:value-of select='format-number(percentageReturn, "###,##0.0")' /></td>	
				<td class="divider"><xsl:value-of select='format-number(shares, "###,##0")' /></td>
				<td class="divider"><xsl:value-of select='format-number(return, "$###,##0.00")' /></td>
			</tr>

	</xsl:template>
		  
</xsl:stylesheet>
