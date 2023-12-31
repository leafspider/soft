<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/assetlistList" xml:space="preserve">	

	<html>
	  <head>
    	<link rel="icon" href="leafspider.png"/>
	    <title>Crush</title>
	
	    <style>
			table {
				font-family:arial;
			}
	    .butspan {
	    	font-weight:bold;
	    	color:blue;
	    	cursor:hand;	    	
	    	border:2px outdent;
	    	background-color:lightgrey;
	    	margin:3px;
	    	padding:2px;
	    }
	    </style>
	
	  </head>
	
	<body>
		
		<br/>
		<form>
		
			<table border="0" width="100%">
				<tr>
					<td width="30%" align="right" style="font-size:1.2em;">
						<nobr><b>Crush <input style="font-size:1.0em;" size="15" type="text" name="ticker" value=""/></b></nobr>
					</td>
					<td width="30%" align="left">
						<span id="months=4" class="butspan" onclick="return onSubmit('months=4')"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></span> 
						<span id="months=6" class="butspan" onclick="return onSubmit('months=6')"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></span> 
						<span id="months=8" class="butspan" onclick="return onSubmit('months=8')"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></span> 
						<span id="years=1" class="butspan" onclick="return onSubmit('years=1')"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></span> 
						<span id="months=18" class="butspan" onclick="return onSubmit('months=18')"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></span> 
						<span id="years=2" class="butspan" onclick="return onSubmit('years=2')"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></span> 
					</td>
					<td align="left" style="font-size:1.2em;">
						<nobr><b>Portfolios</b></nobr>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><br/></td>
					<td align="left" style="font-size:1.2em;padding-left:30px;">
						<a target=_blank" href="/soft/assetlist/jake/milliondollar/portfolio.htm">2012 Million Dollar</a>
					</td>
				</tr>
				<tr>
					<td align="right">
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/crushList.htm">New List</a></span> 
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<hr width="30%"/>
					</td>
				</tr>
				<tr>
					<td align="right">Range Advances</td>
					<td align="left">						
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a href="http://www.barcharts.net/stocks/rangeadvance.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"></a><xsl:text>&#160;</xsl:text></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right">Gap Ups</td>
					<td align="left">
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a href="http://www.barcharts.net/stocks/gapup.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"></a><xsl:text>&#160;</xsl:text></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right">Gap Downs</td>
					<td align="left">
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a href="http://www.barcharts.net/stocks/gapdown.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"></a><xsl:text>&#160;</xsl:text></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right">Percent Advances</td>
					<td align="left">
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a href="http://www.barchart.com/stocks/percentadvance.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"></a><xsl:text>&#160;</xsl:text></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right">Percent Declines</td>
					<td align="left">
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a href="http://www.barchart.com/stocks/percentdecline.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"></a><xsl:text>&#160;</xsl:text></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Finviz</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a href="http://finviz.com" target="_blank"><img valign="middle" src="/soft/skins/resource/finviz.gif"></a><xsl:text>&#160;</xsl:text></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Today's Earnings</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a href="http://biz.yahoo.com/research/earncal/today.html" target="_blank"><img valign="middle" src="/soft/skins/resource/yahoo.ico"></a><xsl:text>&#160;</xsl:text></span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<hr width="30%"/>
					</td>
				</tr>
				<tr>
					<td align="right"><nobr>Monday's Earnings</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Tuesday's Earnings</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Wednesday's Earnings</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Thursday's Earnings</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Friday's Earnings</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="2">
						<hr width="30%"/>
					</td>
				</tr>

				<xsl:apply-templates select="fixedAssetlist"/>

				<!--
				<tr>
					<td align="right"><nobr>Vulture Bargain Candidates</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/vulturebargaincandidates/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/vulturebargaincandidates/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/vulturebargaincandidates/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/vulturebargaincandidates/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/vulturebargaincandidates/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/vulturebargaincandidates/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Diamonds</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/diamonds/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/diamonds/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/diamonds/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/diamonds/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/diamonds/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/diamonds/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Sprott CDN Equity Fund</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottcdnequityfund/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottcdnequityfund/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottcdnequityfund/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottcdnequityfund/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottcdnequityfund/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottcdnequityfund/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Sprott Gold &amp; Precious Minerals</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottgoldpreciousminerals/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottgoldpreciousminerals/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottgoldpreciousminerals/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottgoldpreciousminerals/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottgoldpreciousminerals/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottgoldpreciousminerals/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Sprott Energy Fund</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottenergyfund/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottenergyfund/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottenergyfund/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottenergyfund/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottenergyfund/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/sprottenergyfund/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>FDA Approval Companies</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/fdaapproval/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/fdaapproval/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/fdaapproval/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/fdaapproval/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/fdaapproval/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/fdaapproval/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Biotech Breakout</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/biotechbreakout/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/biotechbreakout/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/biotechbreakout/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/biotechbreakout/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/biotechbreakout/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/biotechbreakout/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>TSX1</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx1/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx1/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx1/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx1/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx1/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx1/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>TSX2</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx2/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx2/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx2/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx2/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx2/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx2/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>TSX3</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx3/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx3/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx3/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx3/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx3/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsx3/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>TSXV</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsxv/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsxv/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsxv/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsxv/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsxv/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/tsxv/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Kingsmont</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/kingsmont/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/kingsmont/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/kingsmont/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/kingsmont/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/kingsmont/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/kingsmont/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>				
				<tr>
					<td align="right"><nobr>Bloom Burton</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/bloomburton/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/bloomburton/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/bloomburton/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/bloomburton/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/bloomburton/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/bloomburton/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>			
				<tr>
					<td align="right"><nobr>Front Street CDN Equity Fund Holdings</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcefh/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcefh/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcefh/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcefh/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcefh/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcefh/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>			
				<tr>
					<td align="right"><nobr>Front Street CDN Special Opportunities</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcso/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcso/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcso/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcso/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcso/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/frontstreetcso/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Small Cap Biotech</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/smallcapbio/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/smallcapbio/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/smallcapbio/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/smallcapbio/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/smallcapbio/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/smallcapbio/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Presenting Companies</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/presentingcompanies/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/presentingcompanies/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/presentingcompanies/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/presentingcompanies/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/presentingcompanies/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/presentingcompanies/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>		
				<tr>
					<td colspan="2">
						<hr width="30%"/>
					</td>
				</tr>	
 				<tr>
					<td align="right"><nobr>Earnings 2012-05-06</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120506/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120506/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120506/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120506/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120506/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120506/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-05-21</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120521/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120521/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120521/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120521/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120521/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120521/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-05-28</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120528/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120528/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120528/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120528/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120528/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120528/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-06-04</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120604/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120604/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120604/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120604/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120604/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120604/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-06-11</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120611/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120611/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120611/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120611/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120611/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120611/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-06-18</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120618/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120618/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120618/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120618/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120618/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120618/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>	
				<tr>
					<td align="right"><nobr>Earnings 2012-07-09</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120709/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120709/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120709/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120709/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120709/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120709/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-07-16</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120716/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120716/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120716/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120716/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120716/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120716/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-07-23</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120723/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120723/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120723/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120723/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120723/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120723/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-07-25</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120725/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120725/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120725/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120725/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120725/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120725/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-07-26</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120726/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120726/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120726/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120726/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120726/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120726/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-07-27</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120727/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120727/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120727/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120727/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120727/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120727/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right"><nobr>Earnings 2012-07-30</nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120730/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120730/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120730/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120730/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120730/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/earnings20120730/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
				-->
				<tr>
					<td colspan="2">
						<hr width="30%"/>
					</td>
				</tr>
			</table>
		
		</form>
	  
	  <script>	  	
	  
	  var collection = "jake";
	  
	  function onSubmit( periodVal )
	  {
			var tickerVal = document.forms[0].ticker.value;
			/*
			var endVal = document.forms[0].end.value;
			if ( endVal.length > 0 && isNaN( Date.parse( endVal ) ) ) 
			{
				alert( "Bad date format. Hint: leave the field empty to reset." );
				return;
			}
			var url = "/soft/asset/" + collection + "/" + tickerVal + "/crush.htm?end=" + endVal + "&" + periodVal;
			*/
			if ( tickerVal.length < 1 )
			{
				alert( "Enter a ticker" );
				return;
			}
			var url = "/soft/asset/" + collection + "/" + tickerVal + "/crush.htm?" + periodVal;
			top.location.href = url;
	  }			
	  
	  </script>
	
	  </body>
	</html>

	</xsl:template>	
	
	<xsl:template match="fixedAssetlist" >	
				<tr>
					<td align="right"><nobr><xsl:value-of select="title" /></nobr></td>
					<td align="left"> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span class="butspan"><a target="_blank" style="text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
	</xsl:template>	
	
</xsl:stylesheet>