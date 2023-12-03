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
			height:570px;
			padding:10px 0px 10px 0px;
			margin-right:100px;
		}
		.main .firstRow {
			padding-bottom:20px;
		}
		.main td {
			border:0px green dotted;		
			white-space:nowrap;
		}
		.trade {
			margin-top:10px;
			margin-bottom:10px;
			border:0px black dotted;
		}
		.trade td {
			width:220px;
			padding:3px;
			border:0px black dotted;
			white-space:nowrap;
			text-align:center;
		}
	</style>
    
  </head>
	
	<body class="bod">
		
		<form class="back">
			
			<xsl:call-template name="menu" />
			
			<table class="main" border="0" width="100%">
				<tr>
					<td style="padding-left:100px;"></td>
					<td colspan="3"  class="firstRow" style="font-size:2.0em;padding-top:25px;width:0px;">
						<b>Snapshot</b>
						<xsl:text>&#160;&#160;&#160;&#160;</xsl:text>
						<span style="font-weight:bold;"><xsl:value-of select="ticker" /></span>
						&#160;
						<span style="font-weight:bold;color:green;"><xsl:value-of select="end" /></span>
					</td>
					<td rowspan="8" style="width:100%">
						<div id="linegraph2" class="timeSeries"></div>
					</td>
				</tr>
				<tr>
					<td class="firstRow" ></td>
					<td style="padding:5px;width:0px;"><nobr>Strategy</nobr></td>
					<td style="padding:5px;">
						<input type="text" name="strategy" style="width:400px;" value="{strategy}"/>
					</td>
					<td style="padding:5px">
						Portfolio
						<xsl:text>&#160;&#160;</xsl:text>
						<input type="text" name="portfolio" style="width:400px;" value="{portfolio}"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="padding:5px;width:0px;"><nobr>Target</nobr></td>
					<td style="padding:5px">
						<input type="text" name="targetPrice" style="width:400px;" value="{targetPrice}"/>
					</td>
					<td></td>
				</tr>
				
				<tr>
					<td></td>
					<td colspan="3" style="width:0px;">						
						<table class="trade">
							<tr>
								<td>Date</td>
								<td>Instrument</td>
								<td>Amount</td>
								<td>Exit</td>
								<td>Commission</td>
								<td>P&amp;L</td>
							</tr>
							<tr>
								<td><xsl:if test="lastModified!='null'"><xsl:value-of select="lastModified" /></xsl:if></td>
								<td><xsl:value-of select="ticker" /></td>
								<td><input type="text" name="amount" style="width:195px;" value="{amount}"/></td>
								<td><input type="text" name="exit" style="width:195px;" value="{exit}"/></td>
								<td><input type="text" name="commission" style="width:195px;" value="{commission}"/></td>
								<td><input type="text" name="pal" style="width:195px;" value="{pal}"/></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td>
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						Exit
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						P&amp;L
					</td>
					<td rowspan="4">
						<textarea name="comment" id="commentArea" style="margin:5px;width:787px;height:100px;"><xsl:value-of select="comment" /></textarea>
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;">5 Day</td>
					<td style="padding-left:15px;">
						<input type="text" name="exit5" style="width:195px;" value="{exit5}"/>
						<xsl:text>&#160;&#160;</xsl:text>
						<input type="text" name="pal5" style="width:195px;" value="{pal5}"/> 
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;">30 Day</td>
					<td style="padding-left:15px;">
						<input type="text" name="exit30" style="width:195px;" value="{exit30}"/>
						<xsl:text>&#160;&#160;</xsl:text>
						<input type="text" name="pal30" style="width:195px;" value="{pal30}"/> 
					</td>					
				</tr>
				
				<tr>
					<td></td>
					<td style="width:0px;"></td>
					<td style="padding:5px 15px;">
						<nobr>
							<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
							<span>watch
								<input style="width:20px;height: 20px" type="checkbox" name="watched" value="true">
									<xsl:choose>
									      <xsl:when test="watched='true'">
										    <xsl:attribute name="checked"></xsl:attribute>     
									      </xsl:when>																	
									 </xsl:choose>
								</input>
							</span>
							<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
							<span style="margin-left:10px;" id="exitbut" class="butspan" onclick="saveExit()">
								<xsl:text>&#160;</xsl:text>save<xsl:text>&#160;</xsl:text>
							</span>
						</nobr>
					</td>
				</tr>
				<tr>
					<td></td>
					<td colspan="4">
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
				    
					console.dir(data[0]);
					
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

					// Prices chart
					var snapshotMarker = {
						title: {
							text: "Price",
						},
					    xAxis: {
						plotLines: [{
						    value: 30,
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
					var priceSeries = data[1].series[0].data.slice(-31);
					data[1].series[0].data = priceSeries;
					$('#linegraph2').highcharts(data[1]); 
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