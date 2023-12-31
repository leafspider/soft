<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/assetlistList" xml:space="preserve">	

	<html>
	  <head>
	    <title>SOFTanalytics</title>
	
			<xsl:call-template name="head" />
	
	  </head>
	
	<body class="bod">
		
		<form class="back">
			
			<xsl:call-template name="menu" />
							
		<table border="0" width="100%">
			<tr>
				<td></td>
				<td align="right" width="30%" style="vertical-align:top;">
					
					<table border="0" width="100%">
						<!--
						<tr>
							<td width="10%" align="left" style="font-size:1.2em;">
								<nobr><b>Crush <input style="font-size:1.0em;" size="7" type="text" name="ticker" value=""/></b></nobr>
							</td>
							<td width="30%" align="left">
								<span id="months=4" class="butspan" onclick="return onSubmit('months=4')"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></span> 
								<span id="months=6" class="butspan" onclick="return onSubmit('months=6')"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></span> 
								<span id="months=8" class="butspan" onclick="return onSubmit('months=8')"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></span> 
								<span id="years=1" class="butspan" onclick="return onSubmit('years=1')"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></span> 
								<span id="months=18" class="butspan" onclick="return onSubmit('months=18')"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></span> 
								<span id="years=2" class="butspan" onclick="return onSubmit('years=2')"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></span> 
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<br/>
							</td>
						</tr>
						<tr>
							<td align="left" style="font-size:1.2em;">
								<b></b><br/>
							</td>
							<td align="left"><a class="butspan" target="_blank" href="/soft/asset/jake.htm"><xsl:text>&#160;</xsl:text>Snapshots<xsl:text>&#160;</xsl:text></a></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2">
								<br/><br/>
							</td>
						</tr>
						-->
						<!--
						<tr>
							<td align="left" style="font-size:1.2em;">
								<b>Portfolio</b><br/>
							</td>
							<td align="left"><span class="butspan"><a style="text-decoration:none;" target="_blank" href="/soft/assetlist/jake/milliondollar/portfolio.htm"><xsl:text>&#160;</xsl:text>2012 Million Dollar<xsl:text>&#160;</xsl:text></a></span></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2">
								<br/>
							</td>
						</tr>
						-->
						<tr>
							<td align="left" style="font-size:1.2em;">
								<b>Batch Lists</b><br/>
							</td>
							<td align="left"></td>
							<td></td>
						</tr>
						<xsl:apply-templates select="batchAssetlist"/>
						<!--
						<tr><td></td><td align="left"><span class="butspan"><xsl:text>&#160;</xsl:text><a target="_mlse" class="mlink" href="/{$app}/assetlist/jake/mlse/multi.htm">MLSE</a><xsl:text>&#160;</xsl:text></span></td></tr>
						<tr><td></td><td align="left"><span class="butspan"><xsl:text>&#160;</xsl:text><a target="_nasdaq" class="mlink" href="/{$app}/assetlist/jake/nasdaq/multi.htm">NASDAQ</a><xsl:text>&#160;</xsl:text></span></td></tr>
						<tr><td></td><td align="left"><span class="butspan"><xsl:text>&#160;</xsl:text><a target="_dax" class="mlink" href="/{$app}/assetlist/jake/dax/multi.htm">DAX</a><xsl:text>&#160;</xsl:text></span></td></tr>
						<tr><td></td><td align="left"><span class="butspan"><xsl:text>&#160;</xsl:text><a target="_ftse" class="mlink" href="/{$app}/assetlist/jake/ftse/multi.htm">FTSE</a><xsl:text>&#160;</xsl:text></span></td></tr>
						<tr><td></td><td align="left"><span class="butspan"><xsl:text>&#160;</xsl:text><a target="_bovespa" class="mlink" href="/{$app}/assetlist/jake/bovespa/multi.htm">Bovespa</a><xsl:text>&#160;</xsl:text></span></td></tr>
						<tr><td></td><td align="left"><span class="butspan"><xsl:text>&#160;</xsl:text><a target="_insider" class="mlink" href="/{$app}/assetlist/jake/insider/multi.htm">Insider</a><xsl:text>&#160;</xsl:text></span></td></tr>
						<tr><td></td><td align="left"><span class="butspan"><xsl:text>&#160;</xsl:text><a target="_finviz" class="mlink" href="/{$app}/assetlist/jake/finviz/multi.htm">Finviz</a><xsl:text>&#160;</xsl:text></span></td></tr>
						-->
						<tr>
							<td colspan="2">
								<br width="30%"/>
							</td>
						</tr>
						<tr>
							<td align="left" style="font-size:1.2em;">
								<b>Performance</b><br/>
							</td>
							<td align="left"></td>
							<td></td>
						</tr>
						<!--
						<tr>
							<td></td>
							<td align="left" style="padding-bottom:5px;"><span class="butspan"><a style="text-decoration:none;" target="_million" href="/soft/assetlist/jake/milliondollar/portfolio.htm"><xsl:text>&#160;</xsl:text>2012 Million Dollar Portfolio<xsl:text>&#160;</xsl:text></a></span></td>
							<td></td>
						</tr>
						-->
						<tr>
							<td></td>
							<td align="left" style="padding-bottom:5px;"><span class="butspan"><a style="text-decoration:none;" target="_trading" href="/soft/assetlist/jake/trading/performance.htm"><xsl:text>&#160;</xsl:text>Trading Alerts Since 2009<xsl:text>&#160;</xsl:text></a></span></td>
							<td></td>
						</tr>
						<!--
						<tr>
							<td></td>
							<td align="left" style="padding-bottom:5px;"><span class="butspan"><a style="text-decoration:none;" target="_assetman" href="/soft/assetlist/jake/assetmanager/performance.htm"><xsl:text>&#160;</xsl:text>Asset Manager Trading<xsl:text>&#160;</xsl:text></a></span></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td align="left" style="padding-bottom:5px;"><span class="butspan"><a style="text-decoration:none;" target="_hedge" href="/soft/assetlist/jake/hedge/performance.htm"><xsl:text>&#160;</xsl:text>Hedge Trading<xsl:text>&#160;</xsl:text></a></span></td>
							<td></td>
						</tr>
						-->
						<tr>
							<td colspan="2">
								<br width="30%"/>
							</td>
						</tr>
						<tr>
							<td align="left" style="font-size:1.2em;">
								<b>Variable Lists</b><br/>
							</td>
							<td align="left"></td>
							<td></td>
						</tr>
						<tr>
							<td align="right">Range Advances</td>
							<td align="left">						
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/rangeadvance/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barcharts.net/stocks/rangeadvance.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right">Gap Ups</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapup/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barcharts.net/stocks/gapup.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right">Gap Downs</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/gapdown/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barcharts.net/stocks/gapdown.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right">Percent Advances</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentadvance/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barchart.com/stocks/percentadvance.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right">Percent Declines</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/percentdecline/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barchart.com/stocks/percentdecline.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right">ETF Monitor</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/etfmonitor/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/etfmonitor/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/etfmonitor/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/etfmonitor/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/etfmonitor/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/etfmonitor/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barchart.com/etf/monitor.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							<td></td>
						</tr>
						<!--
						<tr>
							<td align="right"><nobr>Finviz</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/finviz/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://finviz.com" target="_blank"><img height="14" width="16" valign="middle" src="/soft/skins/resource/finviz.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							<td></td>
						</tr>
						-->
						<tr>
							<td align="right"><nobr>Today's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_today/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://biz.yahoo.com/research/earncal/today.html" target="_blank"><img valign="middle" src="/soft/skins/resource/yahoo.ico"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right"><nobr>Monday's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_mon/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right"><nobr>Tuesday's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_tue/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right"><nobr>Wednesday's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_wed/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right"><nobr>Thursday's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_thu/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
							</td>
							<td></td>
						</tr>
						<tr>
							<td align="right"><nobr>Friday's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/earnings_fri/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
							</td>
							<td></td>
						</tr>
					</table>
					
				</td>
				<td valign="top" align="left" width="40%">
									
					<table>				
						<tr>
							<td align="left" style="font-size:1.2em;">
								<nobr>
									<b>Fixed Lists</b>
									<!--<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>-->
								</nobr>
								<br/>
							</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/crushList.htm">New</a></span> 
							</td>
							<td></td>
						</tr>
						<xsl:apply-templates select="fixedAssetlist"/>
						<tr>
							<td colspan="2">
								<br width="30%"/>
							</td>
						</tr>
					</table>
		
				</td>
				<td></td>
			</tr>
		</table>
		
		</form>
	  
	  <script>	  	
	  	  
		<![CDATA[		
		
	  target = "_home";
	  
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
//			top.location.href = url;
			window.open(url);
	  }			
	  
		]]>
		
	  </script>
	
	  </body>
	</html>

	</xsl:template>	
	
	<xsl:template match="fixedAssetlist" >	
				<tr>
					<td align="right"><nobr><xsl:value-of select="title" /></nobr></td>
					<td align="left"> 
						<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;text-decoration:none;" href="/soft/assetlist/jake/{id}/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>
					<td></td>
				</tr>
	</xsl:template>	
	
	<xsl:template match="batchAssetlist" >	
				<tr>
					<td align="right"></td>
					<td align="left"> 
						<span>
							<a target="_{id}" style="margin:2px;text-decoration:none;font-weight:bold;" href="/soft/assetlist/jake/{id}/multi.htm">
								<xsl:text>&#160;</xsl:text><xsl:value-of select="title" /><xsl:text>&#160;</xsl:text>
							</a>
						</span> 
					</td>
					<td></td>
				</tr>
	</xsl:template>	
	
</xsl:stylesheet>