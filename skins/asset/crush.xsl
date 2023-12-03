<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/crush" xml:space="preserve">	

	<html>
	  <head>
	    <title>Crush</title>
	
			<xsl:call-template name="head_lite" />
			
	    <style>
	    .timeSeries {
	      height:150px;
	      padding-left:20px;
	      padding-right:160px;
	    }
	    .example {
	      height:570px;
	      padding-left:50px;
	      padding-right:35px;
	      padding-top:10px;
	    }
	    </style>
				
	  </head>
	
	<body class="bod">
		
		<form class="back">
		
			<xsl:call-template name="menu" />
			
			<div class="main">
				
				<table border="0" width="100%">
					<tr>
						<td style="padding-left:100px;"></td>
						<td style="padding-top:25px;font-size:2.0em;width:0px;"><b>Crush</b>&#160;&#160;</td>
						<td style="font-size:2.0em;padding-top:25px;padding-left:15px;min-width:400px;width:0px">
							<nobr>
								<span style="font-weight:bold;"><xsl:value-of select="ticker" /></span>
								<xsl:text>&#160;&#160;&#160;</xsl:text>
								<span style="font-weight:bold;"><xsl:value-of select="end" /></span>
							</nobr>
						</td>
						<td rowspan="6" style="padding-top:15px;padding-bottom:15px;width:100%">
							<div id="linegraph2" class="timeSeries"></div>
						</td>
					</tr>
					<tr>
						<td></td>
						<td style="padding-top:10px;width:0px;vertical-align:top;"><nobr>Close Price</nobr></td>
						<td style="padding-top:10px;padding-left:15px;vertical-align:top;">
							<div id="closePrice" style="font-weight:bold;"/>
						</td>
					</tr>
					<tr>
						<td></td>
						<td style="padding-top:10px;width:0px;vertical-align:top;"><nobr>Trading Strategy</nobr></td>
						<td style="padding-top:10px;padding-left:15px;vertical-align:top;"><input type="text" name="strategy" style="width:400px;"/></td>
					</tr>
					<tr>
						<td></td>
						<td style="width:0px;"><nobr>Target Price</nobr></td>
						<td style="padding-left:15;"><nobr><input type="text" name="targetPrice" style="width:340px;"/> <span style="margin-left:10px;" id="snapshot" class="butspan" onclick="snapshot()"><xsl:text>&#160;</xsl:text>save<xsl:text>&#160;</xsl:text></span></nobr></td>
					</tr>
					<tr>
						<td></td>
						<td class="symname" colspan="2" style="font-size:1.1em;padding-top:15px;padding-bottom:0px;">
							<xsl:value-of select="symname"/>
						</td>
					</tr>
					<tr>
						<td></td>					
						<td colspan="2" style="text-align:left;padding-top:0px;padding-bottom:5px;">
							<span style="font-weight:bold"><xsl:value-of select="last"/></span><xsl:text>&#160;&#160;</xsl:text>
							<span style="font-weight:bold;color:green"><xsl:value-of select="up"/></span>
							<span style="font-weight:bold;color:red"><xsl:value-of select="down"/></span><xsl:text>&#160;&#160;</xsl:text>
							<span style="color:grey"><xsl:value-of select="grey"/></span>
						</td>
					</tr>
				</table>	
				
				<table border="0" width="100%">
					<tr>
						<td colspan="3" align="left">
						<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
						<div id="linegraph1" class="example"></div>
					  </td>
					</tr>
				</table>
			
			</div>
				
		</form>

		<script>
			var project = '<xsl:value-of select="project" />';
			var ticker = '<xsl:value-of select="ticker" />';
			
			loadFullGraph('<xsl:value-of select="start" />', '<xsl:value-of select="end" />');
			
			var vol = [<xsl:value-of select="volume" />];

			<![CDATA[	
			
				function loadFullGraph( startval, endval) {
					var url = '/soft/asset/' + project + '/' + ticker + '/crush.json?fill=false&end=' + endval + '&start=' + startval;
					console.log("url=" +url);
					$.getJSON(url, function(data) {
					
						// Crush chart
						var xAx = {
							xAxis: {							        
									tickInterval: 10,
							}
						};			
						//console.log(JSON.stringify(data[0]));
						Highcharts.setOptions(xAx);    
						$('#linegraph1').highcharts(data[0]);
						
						// Volume chart	alert(JSON.stringify(voldat));
						var yAx = {
								colors: ["grey"],
								title: {
									text: "Volume (millions)",
								},
							yAxis: {							        
									min: 0,
									max: null,
									tickInterval: null,
							}
						};								
						var voldat = {
							title: {text:"Volume"},
							series:[{name:"volume",data:vol}]
						}
						
						Highcharts.setOptions(pricesTheme);
						Highcharts.setOptions(yAx);
						$('#linegraph2').highcharts(data[1]); 
												
						var pricedat = data[2]['series'][0]['data'];
						var lastPrice = pricedat[pricedat.length-1].toFixed(2);
						$('#closePrice').text('$'+lastPrice);
					});			    
				}	
			  		
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