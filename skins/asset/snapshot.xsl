<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/snapshot" xml:space="preserve">	

<html>
	<head>
	<title>Snapshot</title>

	<xsl:call-template name="head_lite" />
		
	<style>
		.timeSeries {
			height:200px;
			width:300px;
			padding-left:50px;
			margin-right:100px;			
		}
		.example {
			height:520px;
			padding:10px 0px 10px 0px;
			margin-right:100px;
		}
		.main .firstRow {
			padding-bottom:20px;
		}
		.main td {	
			border:0px darkblue solid;	
			white-space:nowrap;
		}
		.strategy td {
			border:0px black dotted;
		}
		.trade {
			margin-top:10px;
			margin-bottom:10px;
			border:0px black dotted;
			background-color:#d5e0ef;
		}
		.trade td {
			padding:5px;
			border:0px black dotted;
			white-space:nowrap;
			text-align:center;
		}
		input, textarea {
			font-size:1.1em;
		}
	</style>
    
  </head>
	
	<body class="bod">
		
		<form class="back">
			
			<xsl:call-template name="menu" />
			
			<table class="main" width="100%">
				<tr>
					<td style="padding-left:100px;"></td>
					<td colspan="3"  class="firstRow" style="font-size:2.0em;padding-top:25px;width:0px;">
						<b>Snapshot</b>
						<xsl:text>&#160;&#160;&#160;&#160;</xsl:text>
						<span style="font-weight:bold;"><xsl:value-of select="ticker" /></span>
						&#160;
						<span style="font-weight:bold;color:green;"><xsl:value-of select="end" /></span>
					</td>
					<td rowspan="4" style="width:100%">
						<div id="linegraph2" class="timeSeries"></div>
					</td>
				</tr>
				
				<tr>
					<td class="firstRow"></td>
					<td colspan="3" style="width:0px;">
						<table class="strategy">

							<tr>
								<td style="padding-top:10px;width:0px;vertical-align:top;">
									<nobr>
										Close Price<xsl:text>&#160;&#160;&#160;</xsl:text><div id="closePrice" style="font-weight:bold;display:inline-block"/>
									</nobr>
								</td>																	
							</tr>							
							<tr>
								<td style="padding-top:10px;padding-bottom:10px;width:0px;">
									Strategy
									<xsl:text>&#160;</xsl:text>
									<input type="text" name="strategy" style="width:340px;" value="{strategy}"/>
								</td>
								<td style="padding:5px;">
									<xsl:text>&#160;&#160;&#160;&#160;</xsl:text>
									Target
									<xsl:text>&#160;</xsl:text>
									<input type="text" name="targetPrice" style="width:340px;" value="{targetPrice}"/>
								</td>
								<td style="padding:5px">
									<xsl:text>&#160;&#160;&#160;&#160;</xsl:text>
									Portfolio
									<xsl:text>&#160;</xsl:text>
									<input type="text" name="portfolio" style="width:340px;" value="{portfolio}"/>
								</td>
							</tr>
						</table>
					</td>
					<td></td>
				</tr>
				
				<tr>
					<td></td>
					<td colspan="3" style="width:0px;">
						<table class="trade">
							<tr>
								<td>Time</td>
								<td>Entry</td>
								<td>Amount</td>
								<td>Instrument</td>
								<td>Exit</td>
								<td>Comm.</td>
								<td>P&amp;L</td>
							</tr>
							<tr>
								<td><xsl:value-of select="tradeTime" /></td>
								<td><input type="text" name="entry" value="{entry}" style="min-width:330px"/></td>
								<td><input type="text" name="amount" value="{amount}" style="width:80px"/></td>
								<td><input type="text" name="instrument" value="{instrument}" style="width:217px"/></td>
								<td><input type="text" name="exit" value="{exit}" style="width:330px"/></td>
								<td><input type="text" name="commission" value="{commission}" style="width:80px"/></td>
								<td><input type="text" name="pal" value="{pal}" style="width:80px"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td style="width:0px;">
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						Exit
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						P&amp;L
					</td>
					<td style="padding-left:0px;width:auto;" rowspan="3">
						<textarea name="comment" id="commentArea" style="margin:5px;width:786px;height:105px;"><xsl:value-of select="comment" /></textarea>
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;">5 Day</td>
					<td style="width:0px;">
						<input type="text" name="exit5" style="width:330px;" value="{exit5}"/>
						<xsl:text>&#160;&#160;</xsl:text>
						<input type="text" name="pal5" style="width:80px;" value="{pal5}"/> 
					</td>
					<td>
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						<xsl:if test="lastModified!='null'">Last modified <xsl:value-of select="lastModified" /></xsl:if>
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;">30 Day</td>
					<td style="width:0px;">
						<input type="text" name="exit30" style="width:330px;" value="{exit30}"/>
						<xsl:text>&#160;&#160;</xsl:text>
						<input type="text" name="pal30" style="width:80px;" value="{pal30}"/> 
					</td>
					<td>						
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						<span>watch
							<xsl:if test="watched='true'">
								<input style="width:25px;height: 25px" type="checkbox" name="watched" checked="checked" value="true"></input>
							</xsl:if>
							<xsl:if test="watched!='true'">
								<input style="width:25px;height: 25px" type="checkbox" name="watched" value="true"></input>
							</xsl:if>
						</span>
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						<span style="margin-left:10px;" id="exitbut" class="butspan" onclick="saveExit()">
							<xsl:text>&#160;</xsl:text>save<xsl:text>&#160;</xsl:text>
						</span>
					</td>				
				</tr>
				<tr>
					<td></td>
					<td colspan="4" style="width:0px;">
						<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
						<div id="linegraph1" class="example"></div>
					</td>
				</tr>
			</table>	
			
			<script>
				var project = '<xsl:value-of select="project" />';
				var ticker = '<xsl:value-of select="ticker" />';
				
				loadFullGraph('<xsl:value-of select="start" />', '<xsl:value-of select="end" />');
				function loadFullGraph( startval, endval)
				{
					<![CDATA[	
				    $.getJSON('/soft/asset/' + project + '/' + ticker + '/snapshot.json?end=' + endval + '&start=' + startval, function(data) {
				    
						console.dir(data);
						
						var datalen = data[0].series[0].data.length;

							// Crush chart alert(JSON.stringify(data[0]));				
						var crushAxis = {
							xAxis: {							        
							tickInterval: 10,
							plotLines: [{
								value: datalen - 1,
								width: 2,
								color: 'green'
							}]
							}
						};			
						Highcharts.setOptions(crushAxis);    
						$('#linegraph1').highcharts(data[0]);

	//					console.dir(data[1]);
						
						var priceslen = data[1].series[0].data.length;
						var offset = priceslen - datalen;
						var slicelen = 45;
						var snapshotPos = slicelen - offset - 1;
	//					console.log( slicelen +' '+ priceslen +' '+ datalen +' '+ offset +' '+ snapshotPos);
											
						// Prices chart
						var snapshotMarker = {
							title: {
								text: "Price",
							},
							xAxis: {
							plotLines: [{
								value: snapshotPos,
								width: 2,
								color: 'green',
								label: {
								text: endval,
								verticalAlign: 'bottom',
								x: -60,
								y: 27,										                				                
								rotation: 0,
								style: {
									color: 'green',
									fontWeight: 'bold'
								},
								}}]
							}
						};			
				
						Highcharts.setOptions(pricesTheme);
						Highcharts.setOptions(snapshotMarker);
						var priceSeries = data[1].series[0].data.slice(-slicelen);
						data[1].series[0].data = priceSeries;
						$('#linegraph2').highcharts(data[1]); 
						
						var pricedat = data[1]['series'][0]['data'];
						var lastPrice = pricedat[pricedat.length-1].toFixed(2);
						$('#closePrice').text('$'+lastPrice);
				    });			    
		  	  ]]>			
			}	
			</script>
								
		</form>
	  
	  <script>	  	
	  		  
		<![CDATA[		
				  	
	  function onSubmit( periodVal )
	  {
			var tickerVal = document.forms[0].ticker.value;
			var endVal = document.forms[0].end.value;
			if ( endVal.length > 0 && isNaN( Date.parse( endVal ) ) ) 
			{
				alert( "Bad date format. Hint: leave the field empty to reset." );
				return;
			}
			var url = "/soft/asset/" + project + "/" + tickerVal + "/crush.htm?end=" + endVal + "&" + periodVal;
			top.location.href = url;
	  }			
	  
		]]>
				
	  </script>
	
	  </body>
	</html>

	</xsl:template>
	
</xsl:stylesheet>