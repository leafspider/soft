<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/assetlistList" xml:space="preserve">	

	<html>
	  <head>
	    <title>SOFTanalytics</title>
	
			<xsl:call-template name="head" />
	
	    <style>
			table{align:center;}
			table.stockList {background-color:white;}
			.performance{width:100%;}
			.stockList {min-width:800px;}
			.stockList th{border:1px dotted black;}
			.stock td{padding:4;text-align:left;}
			.stock th{padding:4;text-align:left;font-weight:normal;}
			.stockTotal td{padding:4;text-align:right;border-top:1px solid black;font-weight:bold;}
			.stockTotal th{padding:4;text-align:left;border-top:1px solid black;font-weight:bold;}
			.stockTitle td{padding:15px 4px 0px 4px;text-align:left;border-bottom:1px solid lightgrey;width:200px;font-size:1.3em;cursor:hand;}
			td.divider {border-left:1px solid black;padding:0px 10px;border-right:1px solid black;}
			th.divider {border-left:1px solid black;padding:4px 10px;border-right:1px solid black;}
			td.cell {text-align:left;padding:1px 5px;white-space:nowrap;max-width:460px;overflow:hidden;text-overflow:ellipsis;}
			.divider a:link {padding:2px 4px;border-radius:3px;background-color:white;}
			.divider a:visited {color:black}
			.divider a:active {color:black}
			.divider a:hover {color:white;background-color:darkgrey;}
			.listtitle {padding-left:10px;min-width:460px}
	    </style>

	  </head>
	
	<body>

		<form>				

			<xsl:call-template name="menu" />
			
		<table border="0" width="50%">
			<tr style="font-size:1.2em;text-align:center;">
				<th colspan="2" style="text-align:left;padding:30px 0px 0px 100px"><nobr><b><h1>SOFTanalytics</h1></b></nobr></th>
			</tr>
					
			<tr>				
				<td valign="top">
					
					<div class="toptail" style="margin-right:10px;margin-left:100px;">
						
					<table border="0" class="stockList performance">
						
						<tr class="stockTitle">
							<td align="left" colspan="3" onclick="$('.mapsRow').toggle();">
								<span class="butspan">Heat Maps</span>
							</td>
						</tr>
						<tr class="mapsRow" style="display:none;">
							<td align="left" colspan="2" style="padding:10px;">
								<span>
									<a target="_toffee" href="/{$app}/toffee/jake/today/grid.htm">
										Toffee
									</a>
								</span>
								<br/>
								<span>
									<a target="_pearpicks" href="/soft/assetlist/jake/summary/pearpicks.htm">
										Pear Picks
									</a>
								</span>
								<br/>
								<span>
									<a target="_cherrypicks" href="/soft/assetlist/jake/summary/cherrypicks.htm">
										Cherry Picks
									</a>
								</span>
							</td>
						</tr>
						
						<tr class="stockTitle">
							<td align="left" colspan="3" onclick="$('.reportsRow').toggle();">
								<span class="butspan">Reports</span>
							</td>
						</tr>
						<tr class="reportsRow" style="display:none;">
							<td align="left" colspan="2" style="padding:10px;">
								<span>
									<a target="_futures" href="/soft/assetlist/jake/summary/futures.htm">
										Futures Daily Summary
									</a>
								</span>
								<br/>
								<span>
									<a target="_indices" href="/soft/assetlist/jake/summary/indices.htm">
										Indices Daily Summary
									</a>
								</span>
								<br/>
								<span>
									<a target="_forex" href="/soft/assetlist/jake/summary/forex.htm">
										Forex Daily Summary
									</a>
								</span>
								<!--
								<br/>
								<span>
									<a target="_trading" href="/soft/assetlist/jake/trading/performance.htm">
										Trading Alerts Since 2009
									</a>
								</span>
								-->
							</td>
						</tr>
						
						<tr class="stockTitle">
							<td align="left" colspan="2" onclick="$('.exchangesRow').toggle();">
								<span class="butspan">Exchanges &amp; Indices</span>
							</td>
							<td></td>
						</tr>		
						<tr class="exchangesRow" style="display:none;">
							<td align="left" colspan="2" style="padding:10px;"> 
								<xsl:apply-templates select="batchAssetlist"/>
							</td>
						</tr>
						
						<!--	
						<tr class="stockTitle">
							<td align="left" colspan="1">
								<b>Exchanges &amp; Indices</b>
							</td>
							<td></td>
						</tr>
		
						<tr>
							<td align="left" colspan="2" style="padding:10px;"> 
								<xsl:apply-templates select="batchAssetlist"/>
							</td>
						</tr>
						-->
								
						<tr>
							<td style="font-size:0.5em;"><br/></td>
						</tr>
					</table>
					
					</div>
																
				</td>

				<td style="vertical-align:top;">
														
					<div class="toptail">
						
					<table border="0" class="stockList performance">
										
						<tr class="stockTitle">
							<td align="left" colspan="2" onclick="$('.snapshotRow').toggle();">
								<span class="butspan" style="margin:3px;">Snapshots</span>
							</td>
						</tr>
						<tr class="snapshotRow" style="display:none;">
							<td class="listtitle" align="left" colspan="2" style="padding:10px;">
								<span>
									<a target="_snapshots" href="/{$app}/asset/jake.htm">History</a>
								</span>
								<br/>
								<span>
									<a target="_watchlist" href="/{$app}/asset/jake.htm?f=watched&amp;v=true">Watchlist</a>
								</span>
							</td>
						</tr>
						
						<tr class="stockTitle">
							<td align="left" colspan="2" onclick="$('.variableListRow').toggle();">
								<span class="butspan">Variable Lists</span>
							</td>							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle">Range Advances</td>
							<td align="left">						
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/rangeadvance/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/rangeadvance/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/rangeadvance/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barcharts.net/stocks/rangeadvance.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle">Gap Ups</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapup/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapup/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapup/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapup/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapup/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapup/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barcharts.net/stocks/gapup.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle">Gap Downs</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapdown/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapdown/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapdown/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapdown/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapdown/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/gapdown/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barcharts.net/stocks/gapdown.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle">Percent Advances</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentadvance/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentadvance/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentadvance/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barchart.com/stocks/percentadvance.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle">Percent Declines</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentdecline/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentdecline/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/percentdecline/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barchart.com/stocks/percentdecline.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							
						</tr>
						
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle"><nobr>Today's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_today/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_today/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_today/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://biz.yahoo.com/research/earncal/today.html" target="_blank"><img valign="middle" src="/soft/skins/resource/yahoo.ico"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle"><nobr>Monday's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_mon/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_mon/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_mon/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
							</td>
							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle"><nobr>Tuesday's Earnings</nobr></td>
							<td align="left">
								<!-- <span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_tue/multi.htm"><xsl:text>&#160;</xsl:text>12m<xsl:text>&#160;</xsl:text></a></span> -->
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_tue/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_tue/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_tue/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span>
							</td>
							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle"><nobr>Wednesday's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_wed/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_wed/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_wed/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
							</td>
							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle"><nobr>Thursday's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_thu/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_thu/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_thu/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
							</td>
							
						</tr>
						<tr class="variableListRow" style="display:none;">
							<td class="listtitle"><nobr>Friday's Earnings</nobr></td>
							<td align="left"> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_fri/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_fri/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/earnings_fri/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
							</td>
							
						</tr>
												
						<!--
						<tr>
							<td class="listtitle">ETF Monitor</td>
							<td align="left">
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/etfmonitor/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/etfmonitor/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/etfmonitor/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/etfmonitor/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/etfmonitor/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
								<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/etfmonitor/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
								<span><xsl:text>&#160;</xsl:text><a href="http://www.barchart.com/etf/monitor.php" target="_blank"><img width="16" valign="middle" src="/soft/skins/resource/barchart.gif"/></a><xsl:text>&#160;</xsl:text></span>
							</td>
							
						</tr>
						-->
						
						<tr class="stockTitle">
							<td align="left" class="listtitle" onclick="$('.fixedListRow').toggle();">
								<nobr>
									<span class="butspan">Fixed Lists</span>
								</nobr>
							</td>
							<td align="left">
								<span class="fixedListRow" style="display:none;font-size:0.8em;"><a target="_blank" style="margin:2px;" href="/soft/crushList.htm">New</a></span> 
							</td>							
						</tr>
						<xsl:apply-templates select="fixedAssetlist"/>
							
						<tr>
							<td style="font-size:0.5em;"><br/></td>
						</tr>
					</table>
											
					</div>
						
				</td>
				
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
				<tr class="fixedListRow" style="display:none;">
					<td class="listtitle"><nobr><xsl:value-of select="title" /></nobr></td>
					<td align="left"> 
						<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/{id}/crush.htm?months=4"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/{id}/crush.htm?months=6"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/{id}/crush.htm?months=8"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/{id}/crush.htm?years=1"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/{id}/crush.htm?months=18"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></a></span> 
						<span><a target="_blank" style="margin:2px;" href="/soft/assetlist/jake/{id}/crush.htm?years=2"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></a></span> 
					</td>					
				</tr>
	</xsl:template>	
	
	<xsl:template match="batchAssetlist" >			
		<nobr>
		<a style="padding-right:15px;" target="_{id}" href="/soft/assetlist/jake/{id}/multi.htm">
			<xsl:value-of select="title" />
		</a>
		</nobr>
		<br/>
	</xsl:template>	
	
	<xsl:template match="batchAssetlist1" >	
				<tr>
					<td class="listtitle"></td>
					<td align="left"> 
						<span>
							<a target="_{id}" style="margin:2px;" href="/soft/assetlist/jake/{id}/multi.htm">
								<xsl:text>&#160;</xsl:text><xsl:value-of select="title" /><xsl:text>&#160;</xsl:text>
							</a>
						</span> 
					</td>					
				</tr>
	</xsl:template>	
	
</xsl:stylesheet>