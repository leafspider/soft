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
      padding-left:20px;
      padding-right:160px;
    }
    .example {
      width:100%;
      height:570px;
      padding:10px 35px 10px 100px;
    }
    </style>
    
  </head>
	
	<body class="bod">
		
		<form class="back">
			
			<xsl:call-template name="menu" />
			
			<table border="0" width="100%">
				<tr>
					<td style="padding-left:100px;"></td>
					<td colspan="3" style="font-size:2.0em;padding-top:25px;width:0px;">
						<nobr>
							<b>Snapshot</b>
							<xsl:text>&#160;&#160;&#160;&#160;</xsl:text>
							<span style="font-weight:bold;"><xsl:value-of select="ticker" /></span>
							&#160;
							<!--
							<span style="font-weight:bold;color:orange;"><xsl:value-of select="start" /></span>
							<span style="color:grey;"> to </span>
							-->
							<span style="font-weight:bold;color:green;"><xsl:value-of select="end" /></span>
						</nobr>
					</td>
					<td rowspan="8" style="width:100%">
						<!-- <xsl:call-template name="timeSeries" /> -->
						<div id="linegraph2" class="timeSeries"></div>
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="padding-top:10px;width:0px;vertical-align:top;"><nobr>Strategy</nobr></td>
					<td style="padding-top:5px;padding-left:15px;vertical-align:top;">
						<!--
						<xsl:if test="strategy!='null'">
							<xsl:value-of select="strategy" /> 
						</xsl:if>
						<xsl:if test="strategy='null'">
						-->
							<input type="text" name="strategy" style="width:400px;" value="{strategy}"/>
						<!--</xsl:if>-->
					</td>
					<td>
						Portfolio
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;"><nobr>Target</nobr></td>
					<td style="padding-left:15;">
						<!--
						<xsl:if test="targetPrice!='null'">
							<xsl:value-of select="targetPrice" /> 
						</xsl:if>
						<xsl:if test="targetPrice='null'">
						-->
							<input type="text" name="targetPrice" style="width:400px;" value="{targetPrice}"/>
						<!--</xsl:if>-->
					</td>
					<td>
							<input type="text" name="portfolio" style="width:100px;" value="{portfolio}"/>
					</td>
				</tr>
				<tr>
					<td></td>
					<td></td>
					<td style="width:0px;text-align:center;">Exit
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						P&amp;L
					</td>
					<td>
					</td>						
				</tr>
				
				<tr>
					<td></td>
					<td style="width:0px;">1 Day</td>
					<td style="padding:0px 15px;text-align:center;">
						<!--
						<xsl:if test="pal!='null'">
							<xsl:value-of select="pal" />
						</xsl:if>
						<xsl:if test="pal='null'">
						-->
							<nobr>
								<input type="text" name="exit" style="width:195px;" value="{exit}"/>
								<xsl:text>&#160;&#160;</xsl:text>
								<input type="text" name="pal" style="width:195px;" value="{pal}"/> 
							</nobr>
						<!--</xsl:if>-->
					</td>
					<td>
						<script>
							function toggleComment() 
							{ 
								var commentArea = document.getElementById("commentArea");
								if( commentArea.style.display == "none" ) { commentArea.style.display = ""; }
								else { commentArea.style.display = "none"; }
							}
						</script>
						Comment <span style="padding:0px 3px;background-color:lightgrey" id="commentbut" class="butspan" onclick="toggleComment()">+/-</span>						
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;">5 Day</td>
					<td style="padding:0px 15px;text-align:center;">
						<!--
						<xsl:if test="pal!='null'">
							<xsl:value-of select="pal" />
						</xsl:if>
						<xsl:if test="pal='null'">
						-->
							<nobr>
								<input type="text" name="exit5" style="width:195px;" value="{exit5}"/>
								<xsl:text>&#160;&#160;</xsl:text>
								<input type="text" name="pal5" style="width:195px;" value="{pal5}"/> 
							</nobr>
						<!--</xsl:if>-->
					</td>
					<td>
						<textarea name="comment" id="commentArea" style="position:absolute;display:none;z-index:20;width:500px;height:200px;"><xsl:value-of select="comment" /></textarea>
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;">30 Day</td>
					<td style="padding:0px 15px;text-align:center;">
						<!--
						<xsl:if test="pal!='null'">
							<xsl:value-of select="pal" />
						</xsl:if>
						<xsl:if test="pal='null'">
						-->
							<nobr>
								<input type="text" name="exit30" style="width:195px;" value="{exit30}"/>
								<xsl:text>&#160;&#160;</xsl:text>
								<input type="text" name="pal30" style="width:195px;" value="{pal30}"/> 
							</nobr>
						<!--</xsl:if>-->
					</td>
					<td>
					</td>							
				</tr>
				
				<tr>
					<td></td>
					<td style="width:0px;">Saved</td>
					<td style="padding:5px 15px;">
						<nobr>
							<span style="font-size:0.8em;"><xsl:if test="lastModified!='null'"><xsl:value-of select="lastModified" /></xsl:if></span>
							<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
							<span>watch
								<input style="width:20px;height: 20px" type="checkbox" name="watched" value="true">
									<xsl:choose>
									      <xsl:when test="watched='true'">
										    <xsl:attribute name="checked"></xsl:attribute>     
									      </xsl:when>																	
									 </xsl:choose>
								</input>
							</span>
							<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
							<span style="margin-left:10px;" id="exitbut" class="butspan" onclick="saveExit()">
								<xsl:text>&#160;</xsl:text>save<xsl:text>&#160;</xsl:text>
							</span>
						</nobr>
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
							            value: datalen - 1,
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
							$('#linegraph2').highcharts(data[1]); 
				    });			    
		  	  ]]>			
				}	
			</script>
			
			<table border="0" width="100%">
				<tr>
					<td colspan="2" align="right">
				  	<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
				  	<div id="linegraph1" class="example"></div>
				  </td>
					<td width="200" align="left" style="padding:30px;">
						<span style="font-weight:bold;color:black;">close</span><br/>
						<span style="font-weight:bold;color:grey;">flow</span><br/>
						<span style="font-weight:bold;color:blue;">plate</span><br/>
						<span style="font-weight:bold;color:lightgreen;">pear</span><br/>
						<span style="font-weight:bold;color:#ff6600;">crush</span><br/>
						<span style="border-radius:5px;font-weight:bold;color:#efefff;background-color:{cherryColor}">cherry</span>
					</td>
				</tr>
			</table>
								
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