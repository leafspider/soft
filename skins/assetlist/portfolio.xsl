<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
	
	<xsl:variable name="totalTradeAmount">
		<xsl:for-each select="stock">
			
	  </xsl:for-each>
	</xsl:variable>
		
	<xsl:template match="/portfolio" xml:space="preserve">	

	<html>
		<head>
			<title>Portfolio</title>
			
			<xsl:call-template name="head" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<style>
				table{align:center;width:100%;}
				table.stockList {border:2px solid black;background-color:white;}
				.stockList th{border:1px dotted black;}
				.stockList td{border:1px dotted black;}
				.stock td{padding:4;text-align:right;}
				.stock th{padding:4;text-align:left;font-weight:normal;}
				.stockTotal td{padding:4;text-align:right;border-top:2px solid black;font-weight:bold;}
				.stockTotal th{padding:4;text-align:left;border-top:2px solid black;font-weight:bold;}
				.stockTitle td{padding:4;text-align:right;border-bottom:1px solid black;}
				.stockTitle th{padding:4;text-align:center;border-top:2px solid black;border-bottom:2px solid black;font-weight:bold;}
				td.divider {border-left:2px solid black;}
				th.divider {border-left:2px solid black;}
				
		    .example {
					width:100%;
		      height:350px;
		    }
		    </style>
		</head>	
		
	<body class="bod">	   
		
		<form class="back">
				
			<xsl:call-template name="menu" />
			
		<font face="arial">	 
		  
		<div style="margin-left:30px;margin-right:30px;">
			
			<table width="100%">
				<tr style="font-size:1.4em;">
					<th><nobr><b>2012 Million Dollar Portfolio</b></nobr></th>
				</tr>
			</table>
						
			<table width="100%" class="stockList">
				<tr class="stockTitle">
					<th>Company</th>
					<th width="1"><nobr>Ticker</nobr></th>
					<th width="1" class="divider"><nobr>Entry Date</nobr></th>
					<th width="1"><nobr>Entry Price</nobr></th>
					<th width="1" class="divider"><nobr>Shares</nobr></th>
					<th width="1"><nobr>Trade Amount</nobr></th>
					<th width="1" class="divider"><nobr>Target Price</nobr></th>
					<th width="1"><nobr>Projected Return</nobr></th>
					<th width="1"><nobr>%</nobr></th>
					<th width="1" class="divider"><nobr>Current Price</nobr></th>
					<th width="1"><nobr>Current Return</nobr></th>
					<th width="1"><nobr>%</nobr></th>
					<th width="1" class="divider"><nobr>Website</nobr></th>
				</tr>
				<xsl:apply-templates select="stock"/>
				<tr class="stockTotal">
					<th>Total</th>
					<td></td>
					<td class="divider"></td>
					<td></td>
					<td class="divider"></td>
					<td><xsl:value-of select='format-number(sum(//stock//tradeAmount), "$###,##0")'/></td>
					<td class="divider"></td>
					<td><xsl:value-of select='format-number(sum(//stock//projectedReturn), "$###,##0")'/></td>
					<td><xsl:value-of select='format-number(100 * sum(//stock//projectedReturn) div sum(//stock//tradeAmount), "###,##0.0")'/></td>
					<td class="divider"></td>
					<td><xsl:value-of select='format-number(sum(//stock//currentReturn), "$###,##0")'/></td>
					<td><xsl:value-of select='format-number(100 * sum(//stock//currentReturn) div sum(//stock//tradeAmount), "###,##0.0")'/></td>
					<td class="divider"></td>
				</tr>
			</table>
		
			<table width="100%">
				<tr>
					<th>Projected Return $1000s</th>
					<th>Current Return $1000s</th>
				</tr>
				<tr>
					<td width="50%">
						<div id="bargraph1" class="example"></div>
					</td>
					<td>
						<div id="bargraph2" class="example"></div>
					</td>
				</tr>
			</table>

		</div>	
				
	  <script>	  	
	  		  
		  var tickers = '<xsl:for-each select="stock"><xsl:value-of select="ticker" />,</xsl:for-each>';
		  tickers = tickers.substring(0,tickers.length-1);
		  var tickersArray = tickers.split(',');
		  
  	  var prorets = '<xsl:for-each select="stock"><xsl:value-of select='projectedReturn*0.001' />,</xsl:for-each>';
		  prorets = prorets.substring(0,prorets.length-1);
		  var proretsArray = prorets.split(',');

		  var currets = '<xsl:for-each select="stock"><xsl:value-of select='currentReturn*0.001' />,</xsl:for-each>';
		  currets = currets.substring(0,currets.length-1);
		  var curretsArray = currets.split(',');
		  		
		  Event.observe(window, 'load', function() 
		  {
//				/*
				var bargraph1 = new Grafico.BarGraph($('bargraph1'), proretsArray,
				{
				  labels :              tickersArray,
				  color :               '#4b80b6',
//				  meanline :            false,
//				  label_rotation :      -30,
//				  vertical_label_unit : "$",
//				  bargraph_lastcolor :  "#666666",
				  bargraph_negativecolor : "#660000",
				  hover_color :         "#006677",
				  datalabels :          {one: tickersArray}
				});
//				*/
	
//				/*			
				var bargraph2 = new Grafico.BarGraph($('bargraph2'), curretsArray,
				{
				  labels :              tickersArray,
				  color :               '#4b80b6',
//				  meanline :            false,
//				  label_rotation :      -30,
//				  vertical_label_unit : "$",
//				  bargraph_lastcolor :  "#666666",
				  bargraph_negativecolor : "#660000",
				  hover_color :         "#006677",
				  draw_axis :             true,
				  datalabels :          {one: tickersArray}
				});
//				*/
		  });
		  		  	  
	  </script>
					
		</font>		
		
		</form>
		
		</body>		
	</html>
						
	</xsl:template>
	
	<xsl:template match="stock" >		

			<tr class="stock">
				<th><xsl:value-of select='company' /></th>
				<th><xsl:value-of select='ticker' /></th>
				<td class="divider"><xsl:value-of select='entryDate' /></td>
				<td><xsl:value-of select='format-number(entryPrice, "$###,##0.00")' /></td>
				<td class="divider"><xsl:value-of select='format-number(shares, "###,##0")' /></td>
				<td><xsl:value-of select='format-number(tradeAmount, "$###,##0")' /></td>
				<td class="divider"><xsl:value-of select='format-number(targetPrice, "$###,##0.00")' /></td>
				<td><xsl:value-of select='format-number(projectedReturn, "$###,##0")' /></td>	
				<td><xsl:value-of select='format-number(projectedReturnPercent, "###,##0.0")' /></td>	
				<td class="divider"><xsl:value-of select='format-number(currentPrice, "$###,##0.00")' /></td>
				<td><xsl:value-of select='format-number(currentReturn, "$###,##0")' /></td>	
				<td><xsl:value-of select='format-number(currentReturnPercent, "###,##0.0")' /></td>	
				<th class="divider"><a href="{website}" target="_blank"><xsl:value-of select='website' /></a></th>	
			</tr>

	</xsl:template>
		  
</xsl:stylesheet>
