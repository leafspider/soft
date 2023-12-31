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
			.stockList {min-width:20%;}
			.stockList th{border:1px dotted black;}
			.stock td{padding:4;text-align:left;}
			.stock th{padding:4;text-align:left;font-weight:normal;}
			.stockTotal td{padding:4;text-align:right;border-top:1px solid black;font-weight:bold;}
			.stockTotal th{padding:4;text-align:left;border-top:1px solid black;font-weight:bold;}
			.stockTitle td{padding:15px 4px 0px 4px;text-align:left;border-bottom:1px solid lightgrey;width:200px;font-size:1.3em;}
			td.divider {border-left:1px solid black;padding:0px 10px;border-right:1px solid black;}
			th.divider {border-left:1px solid black;padding:4px 10px;border-right:1px solid black;}
			td.cell {text-align:left;padding:1px 5px;white-space:nowrap;max-width:460px;overflow:hidden;text-overflow:ellipsis;}
			.divider a:link {padding:2px 4px;border-radius:3px;background-color:white;}
			.divider a:visited {color:black}
			.divider a:active {color:black}
			.divider a:hover {color:white;background-color:darkgrey;}
			.listtitle {padding-left:10px;}
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
			
				<td style="vertical-align:top;">
														
					<div class="toptail" style="margin-left:100px;margin-right:10px;min-width:290px;">						
						<table border="0" class="stockList performance">											
							<tr class="stockTitle">
								<td align="left" colspan="2" onclick="$('.exchangesRow').toggle();">
									<span class="butspan">Asset Lists</span>
								</td>
								<td style="text-align:right;">
									<span class="butspan" style="font-size:0.7em;background-color:#777777;padding:0 5px;"><a target="_blank" style="text-decoration:none;" href="/soft/newList.htm">New</a></span>
								</td>
							</tr>		
							<tr class="exchangesRow" style="display:;">		
								<td align="left" colspan="2" style="padding:10px 0 0 10px;"> 
									<nobr>
										All
									</nobr>
								</td>
								<td style="padding:10px 10px 0 0;">
									<a style="padding-right:15px;" target="_crush_all" href="/soft/assetlist/jake/all/crushpicks.htm">Crush</a>
									<a style="position:relative;left:-4px;padding-right:15px;" target="_porter_all" href="/soft/assetlist/jake/all/pearpicks.htm">Porter</a>
									<a style="position:relative;left:-8px;padding-right:15px;" target="_viper_all" href="/soft/assetlist/jake/all/viper.htm">Viper</a>
								</td>
							</tr>					
							<xsl:apply-templates select="assetlist"/>
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>
						</table>											
					</div>
						
				</td>
				
				<td valign="top">
					
					<div class="toptail" style="margin-right:100px;margin-bottom:10px;width:100%;">					
						<table border="0" class="stockList performance">
							<tr class="stockTitle">
								<td align="left" onclick="$('.snapshotRow').toggle();">
									<span class="butspan" style="margin:3px;">Snapshot</span>
								</td>
							</tr>
							<tr class="snapshotRow" style="display:;">
								<td class="listtitle" align="left" style="padding:10px;">
									<span>
										<a target="_snapshots" href="/{$app}/asset/jake.htm">History</a>
									</span>
									<br/>
									<span>
										<a target="_watchlist" href="/{$app}/asset/jake.htm?f=watched&amp;v=true">Watchlist</a>
									</span>
									<br/>
									<span>
										<a target="_performance" href="/{$app}/assetlist/jake/performance.htm">Performance</a>
									</span>
								</td>
							</tr>							
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>							
						</table>					
					</div>
											
					<div class="toptail" style="margin-right:100px;margin-bottom:10px;width:100%;">						
						<table border="0" class="stockList performance">															
							<tr class="stockTitle">
								<td align="left" onclick="$('.toffeeRow').toggle();">
									<span class="butspan">Toffee</span>
								</td>
							</tr>
							<xsl:apply-templates select="toffeelist"/>						
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>
						</table>											
					</div>
							
					<div class="toptail" style="margin-bottom:10px;width:100%;">						
						<table border="0" class="stockList performance">															
							<tr class="stockTitle">
								<td align="left" onclick="$('.formulaRow').toggle();">
									<span class="butspan">Formulas</span>
								</td>
							</tr>				
							<xsl:apply-templates select="formulalist"/>		
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>
						</table>											
					</div>
					
					<div class="toptail" style="margin-bottom:10px;width:100%;">						
						<table border="0" class="stockList performance">															
							<tr class="stockTitle">
								<td align="left" onclick="$('.lotusRow').toggle();">
									<span class="butspan">Rules</span>
								</td>
							</tr>				
							<tr class="snapshotRow" style="display:;">
								<td class="listtitle" align="left" style="padding:10px;">
									<span>
										<a target="_rules_all" href="/{$app}/assetlist/jake/all/rules.htm">All</a>
									</span>
								</td>
							</tr>			
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>
						</table>											
					</div>
													
					<div class="toptail" style="margin-bottom:10px;width:100%;">						
						<table border="0" class="stockList performance">															
							<tr class="stockTitle">
								<td align="left" onclick="$('.lotusRow').toggle();">
									<span class="butspan">Tsocial</span>
								</td>
							</tr>				
							<tr class="snapshotRow" style="display:;">
								<td class="listtitle" align="left" style="padding:10px;">
									<span>
										<a target="_tsocial_ju" href="/{$app}/tsocial/jake/justtradin/page.htm">justtradin</a>
									</span>
									<br/>
									<span>
										<a target="_tsocial_so" href="/{$app}/tsocial/jake/softaiinc1/page.htm">softaiinc1</a>
									</span>
									<br/>
									<span>
										<a target="_tsocial_soc" href="/{$app}/tsocial/jake/sociallyfked/page.htm">sociallyfked</a>
									</span>
									<br/>
									<span>
										<a target="_tsocial_il" href="/{$app}/tsocial/jake/ilikebets1/page.htm">ilikebets1</a>
									</span>
									<br/>
									<span>
										<a target="_tsocial_el" href="/{$app}/tsocial/jake/elonmusk/page.htm">elonmusk</a>
									</span>
									<br/>
									<span>
										<a target="_tsocial_be" href="/{$app}/tsocial/jake/bengoertzel/page.htm">bengoertzel</a>
									</span>
									<br/>
									<span>
										<a target="_tsocial_ed" href="/{$app}/tsocial/jake/eddiemac3356/page.htm">eddiemac3356</a>
									</span>
								</td>
							</tr>			
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>
						</table>											
					</div>
					
					<div class="toptail" style="margin-bottom:10px;width:100%;">						
						<table border="0" class="stockList performance">															
							<tr class="stockTitle">
								<td align="left" onclick="$('.lotusRow').toggle();">
									<span class="butspan">Fsocial</span>
								</td>
							</tr>				
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>
						</table>											
					</div>
																					
					<div class="toptail" style="margin-bottom:10px;width:100%;">					
						<table border="0" class="stockList performance">															
							<tr class="stockTitle">
								<td align="left" onclick="$('.coinRow').toggle();">
									<span class="butspan">softcoin.io</span>
								</td>
							</tr>		
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>
						</table>												
					</div>
					
					<div class="toptail" style="margin-bottom:10px;width:100%;">						
						<table border="0" class="stockList performance">															
							<tr class="stockTitle">
								<td align="left" onclick="$('.lotusRow').toggle();">
									<span class="butspan">lotusai.io</span>
								</td>
							</tr>				
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>
						</table>											
					</div>
					
					<div class="toptail" style="margin-bottom:10px;width:100%;">						
						<table border="0" class="stockList performance">															
							<tr class="stockTitle">
								<td align="left" onclick="$('.artRow').toggle();">
									<span class="butspan">artlotus.io</span>
								</td>
							</tr>				
							<tr>
								<td style="font-size:0.5em;"><br/></td>
							</tr>
						</table>											
					</div>
								
				</td>
		
			</tr>
						
			<tr>
				<td style="font-size:0.5em;"><br/></td>
			</tr>
					
			<tr>

				<!--td style="vertical-align:top;">
														
					<div class="toptail" style="margin-right:10px;min-width:290px;">
						
					<table border="0" class="stockList performance">
									
						<tr class="stockTitle">
							<td align="left" onclick="$('.reportsRow').toggle();">
								<span class="butspan">Viper</span>
							</td>
						</tr>
						<tr class="reportsRow" style="display:;">
							<td align="left" style="padding:10px;">
								<nobr>
									-
								</nobr>
								<br/>
								<xsl:apply-templates select="report"/>
							</td>
						</tr>	
																			
						<tr>
							<td style="font-size:0.5em;"><br/></td>
						</tr>
					</table>
											
					</div>
						
				</td-->

								
				<!--td style="vertical-align:top;">
														
					<div class="toptail" style="margin-right:10px;min-width:290px;">
						
					<table border="0" class="stockList performance">
																	
						<tr class="stockTitle">
							<td align="left" onclick="$('.heatRow').toggle();">
								<span class="butspan">Porter</span>
							</td>
						</tr>
						<tr class="heatRow" style="display:;">
							<td align="left" style="padding:10px;">
								<nobr>
									<a target="_porter_all" href="/soft/assetlist/jake/all/pearpicks.htm">
										All Porter
									</a>
								</nobr>
								<br/>
								<xsl:apply-templates select="porter"/>
							</td>
						</tr>
						
						<tr>
							<td style="font-size:0.5em;"><br/></td>
						</tr>
					</table>
											
					</div>
						
				</td-->
								
				<td style="vertical-align:top;">
														
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

	<xsl:template match="formulalist" >	
		<tr class="formulaRow" style="display:;">
			<xsl:choose>
				<xsl:when test="position() = 1">
					<td align="left" style="padding:10px 10px 0 10px;"><span><a target="_formula_{id}" href="/{$app}/formula/jake/{id}/grid.htm"><xsl:value-of select="id"/></a></span></td>
				</xsl:when>
				<xsl:otherwise>
					<td align="left" style="padding:0 10px;"><span><a target="_formula_{id}" href="/{$app}/formula/jake/{id}/grid.htm"><xsl:value-of select="id"/></a></span></td>
				</xsl:otherwise>
			</xsl:choose>
		</tr>
	</xsl:template>	
	
	<xsl:template match="toffeelist" >	
		<tr class="toffeeRow" style="display:;">
			<xsl:choose>
				<xsl:when test="position() = 1">
					<td align="left" style="padding:10px 10px 0 10px;"><span><a target="_toffee_{id}" href="/{$app}/toffee/jake/{id}/grid.htm"><xsl:value-of select="id"/></a></span></td>
				</xsl:when>
				<xsl:otherwise>
					<td align="left" style="padding:0 10px;"><span><a target="_toffee_{id}" href="/{$app}/toffee/jake/{id}/grid.htm"><xsl:value-of select="id"/></a></span></td>
				</xsl:otherwise>
			</xsl:choose>
		</tr>
	</xsl:template>	

	<xsl:template match="assetlist" >	
		<tr class="exchangesRow" style="display:;">		
			<td align="left" colspan="2" style="padding:0 10px;"> 
				<nobr>
					<a style="padding-right:15px;" target="_tickers_{id}" href="/soft/assetlist/jake/{id}/tickers.htm"><xsl:value-of select="title" /></a>
				</nobr>
			</td>
			<td>
				<a style="padding-right:15px;" target="_crushmap_{id}" href="/soft/assetlist/jake/{id}/crush.htm">Crush</a>
				<a style="padding-right:15px;" target="_portermap_{id}" href="/soft/assetlist/jake/{id}/pearpicks.htm">Porter</a>
				<a style="padding-right:15px;" target="_viper_{id}" href="/soft/assetlist/jake/{id}/viper.htm">Viper</a> 
				<a style="padding-right:15px;" target="_multi_{id}" href="/soft/assetlist/jake/{id}/multi.htm">Multi</a>
			</td>
		</tr>
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
	
	<!--xsl:template match="assetlist" >			
		<nobr>
			<a style="padding-right:15px;" target="_batch_{id}" href="/soft/assetlist/jake/{id}/multi.htm">
				<xsl:value-of select="title" />
			</a>
		</nobr>
		<br/>
	</xsl:template-->	
	
	<xsl:template match="report" >	
		<nobr>
			<a style="padding-right:15px;" target="_report_{id}" href="/soft/assetlist/jake/{id}/viper.htm">
				<xsl:value-of select="title" />
			</a>
		</nobr>
		<br/>
	</xsl:template>

	<xsl:template match="porter" >	
		<nobr>
			<a style="padding-right:15px;" target="_porter_{id}" href="/soft/assetlist/jake/{id}/pearpicks.htm">
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