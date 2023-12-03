<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
			
	<xsl:template match="/viper" xml:space="preserve">	

	<html>
		<head>
			<title>Viper</title>
			
			<xsl:call-template name="head_lite" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<style>
				table {width:100%;}
				th {text-align:left;}
				td {white-space:nowrap;padding:3px;}
				.report-title {padding:4px;color:white;}
				.asset table {width:100%;background-color:white;margin-right:50px;}
				.asset-title {padding-top:10px;font-weight:bold;}
				.asset td {width:10%;}
				.col-names {font-size:0.7em;}
				.data-col div {text-align:right;padding-right:25%;}
				
				.fourPlus {background-color:rgb(0, 150, 0, 0.8);color:white;}
				.onePlus {background-color:rgb(0, 255, 0, 0.7);}
				.plus {background-color:rgb(255, 165, 0, 0.6);}
				.zero {background-color:rgb(255, 255, 51, 0.5);}
				.minus {background-color:rgb(255, 165, 0, 0.6);}
				.oneMinus {background-color:rgb(255, 0, 0, 0.7);}
				.fourMinus {background-color:rgb(150, 0, 0, 0.8);color:white;}
				
				.vol {padding:0 10% !important}
				.spacer {height:20px;background-color:white;}			
				
				.volfourPlus {background-color:rgb(0, 150, 0, 0.8);color:white;}
				.volonePlus {background-color:rgb(0, 255, 0, 0.7);}
				.volplus {background-color:rgb(255, 165, 0, 0.6);}
				.volzero {background-color:rgb(255, 255, 51, 0.5);}
				.volminus {background-color:rgb(255, 165, 0, 0.6);}
				.voloneMinus {background-color:rgb(255, 0, 0, 0.7);}
				.volfourMinus {background-color:rgb(150, 0, 0, 0.8);color:white;}

				.signalCell {text-align:right;vertical-align:bottom;padding:0px;}
				.signal {text-align:center;font-size:0.8em;width:30px;display:inline-block;position:relative;top:16px;margin-right:3px;border-radius:40%}
				.rule {border:1px solid black;margin-right:20px;}
				.norule {border:1px solid white;margin-right:20px;}
				.ruleA1 {background-color:rgb(50, 255, 0, 0.5);border:1px solid black;}
				.ruleA2 {background-color:rgb(50, 222, 0, 0.6);border:1px solid black;}
				.ruleA3 {background-color:rgb(50, 189, 0, 0.7);color:white;border:1px solid black;}
				.ruleA4 {background-color:rgb(50, 156, 0, 0.7);color:white;border:1px solid black;}
				.ruleD1 {background-color:rgb(255, 0, 0, 0.7);border:1px solid black;}
				
				.example {
				  height:500px;
				  padding:10px 100px 25px 0;
				  margin-top:10px;
				  margin-left:100px;
				}
			</style>
		</head>	

	<body>
			
		<form>
				
			<xsl:call-template name="menu" />
						
			<table border="0" width="100%">
				<tr style="font-size:1.2em;">
					<th style="text-align:left;padding:30px 0px 0px 100px"><nobr><b><h1>Viper</h1></b></nobr>
					</th>						
					<td style="text-align:right;">	
						<div style="margin:25px 100px 20px 100px;">
							<table style="text-align:center;">
								<tr>
									<td class="volfourPlus"> &gt; 4% </td>
									<td class="volonePlus"> &gt; 1% </td>
									<td class="volplus"> &gt; 0.5% </td>
									<td class="volzero"> -0.5 - 0.5% </td>
									<td class="volminus"> &lt; -0.5% </td>
									<td class="voloneMinus"> &lt; -1% </td>
									<td class="volfourMinus"> &lt; -4% </td>						
								</tr>
								<tr><td></td></tr>
								<tr>
									<td colspan="5" style="text-align:left;font-size:1.0em;vertical-align:center">
										<div style="top:0px;color:white;background-color:ff8000;margin-right:0px" class="signal rule">C</div> Crush
										<div style="top:0px;color:white;background-color:0033ff;margin:10px 0 0 20px" class="signal rule">P</div> Porter
										<div style="top:0px;margin:10px 0 0 20px" class="signal ruleA1">A</div> Accumulation
										<div style="top:0px;margin:10px 0 0 20px" class="signal ruleD1">D</div> Distribution
									</td>								
								</tr>
							</table>
						</div>						
					</td>
				</tr>
				<tr>				
					<td colspan="2" valign="top">
						<div class="toptail" style="margin-right:100px;margin-left:100px;">										
							<xsl:apply-templates select="reports" />
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="left">
						<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
						<div id="linegraph1" class="example"></div>
					</td>
				</tr>
			</table>
		
		</form>
		
		<script>
			var project = '<xsl:value-of select="project" />';
			var ticker = '<xsl:value-of select="ticker" />';
			
			loadFullGraph('<xsl:value-of select="start" />', '<xsl:value-of select="end" />');
			 
			<![CDATA[	
			
				function loadFullGraph( startval, endval) {
					var url = '/soft/asset/' + project + '/' + ticker + '/crush.json?fill=false&end=' + endval + '&start=' + startval;
					console.log("url=" +url);
					$.getJSON(url, function(data) {
					
						//console.dir(data);									
						var xAx = {
							xAxis: {							        
								tickInterval: 10,
							}
						};			
						//console.log(JSON.stringify(data[0]));
						Highcharts.setOptions(xAx);    
						$('#linegraph1').highcharts(data[0]);
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

	<xsl:template match="reports">			
		<div class="reports">
			<xsl:apply-templates select="report" />
		</div>
	</xsl:template>

	<xsl:template match="report">			
		<div class="report">
			<div class="report-title"><xsl:value-of select="//ticker" /></div>			
			<!--table class="asset">
				<tr><td>Date</td><td>Open</td><td>High</td><td>Low</td><td>Close</td><td>Adjusted_close</td><td>Volume</td></tr>
			</table-->
			<xsl:apply-templates select="asset"/>
		</div>
		<div class="spacer"/>
	</xsl:template>			  
			  
	<xsl:template match="asset">
		<div class="asset">
			<table class="asset-table" border="0">
				<tr>
					<!--td colspan="3"><div class="asset-title"><xsl:value-of select="@ticker" /> <xsl:value-of select="@name" /></div></td-->
					<td colspan="3" style="padding-top:12px;"></td>
					<td colspan="5" class="signalCell" style="text-align:right;"><xsl:apply-templates select="signals"/></td>
				</tr>
				<tr class="col-names">
					<td>Date</td><td>Open</td><td>High</td><td>Low</td><td>Close</td><td>Change</td><td>%Change</td><td>Volume</td>
				</tr>
				<xsl:apply-templates select="row"/>
			</table>
		</div>
	</xsl:template>

	<xsl:template match="signals">		
		<xsl:apply-templates select="rule"/>
	</xsl:template>
	
	<xsl:template match="rule">		
		<div title="{.}" class='signal rule rule{.}'><xsl:value-of select='.'/></div>
	</xsl:template>

	<xsl:template match="row">		
		<xsl:choose>
			<xsl:when test="position() mod 2 = 0">
				<tr><xsl:apply-templates select="col"/></tr>
			</xsl:when>
			<xsl:otherwise>
				<tr style="background-color:#f7f7f7"><xsl:apply-templates select="col"/></tr>
			</xsl:otherwise>
		</xsl:choose>			
	</xsl:template>
				
	<xsl:template match="col">		
		<xsl:choose>
			<xsl:when test="position() = 1">
				<td><div class="data-col"><xsl:value-of select='.'/></div></td>
			</xsl:when>
			<xsl:when test="position() = 6 and .&gt;0">				
				<td><div class="data-col">+<xsl:value-of select='format-number(., "#0.00")'/></div></td>
			</xsl:when>
			<xsl:when test="position() = 7">				
				<xsl:if test=".&gt;4"><td><div class="data-col"><div class='fourPlus'>+<xsl:value-of select='format-number(., "#0.00")'/></div></div></td></xsl:if>
				<xsl:if test=".&lt;4 and .&gt;1"><td><div class="data-col"><div class='onePlus'>+<xsl:value-of select='format-number(., "#0.00")'/></div></div></td></xsl:if>						
				<xsl:if test=".&lt;1 and .&gt;0.5"><td><div class="data-col"><div class='plus'>+<xsl:value-of select='format-number(., "#0.00")'/></div></div></td></xsl:if>						
				<xsl:if test=".&lt;0.5 and .&gt;-0.5"><td><div class="data-col"><div class='zero'><xsl:value-of select='format-number(., "#0.00")'/></div></div></td></xsl:if>						
				<xsl:if test=".&lt;-0.5 and .&gt;-1"><td><div class="data-col"><div class='minus'><xsl:value-of select='format-number(., "#0.00")'/></div></div></td></xsl:if>	
				<xsl:if test=".&lt;-1 and .&gt;-4"><td><div class="data-col"><div class='oneMinus'><xsl:value-of select='format-number(., "#0.00")'/></div></div></td></xsl:if>
				<xsl:if test=".&lt;-4"><td><div class="data-col"><div class='fourMinus'><xsl:value-of select='format-number(., "#0.00")'/></div></div></td></xsl:if>
			</xsl:when>
			<xsl:when test="position() = 8">
				<xsl:if test=".&gt;4"><td><div class="data-col"><div title="{format-number(., '#0')}%" class='volfourPlus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></div></td></xsl:if>
				<xsl:if test=".&lt;4 and .&gt;1"><td><div class="data-col"><div title="{format-number(., '#0')}%" class='volonePlus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></div></td></xsl:if>
				<xsl:if test=".&lt;1 and .&gt;0.5"><td><div class="data-col"><div title="{format-number(., '#0')}%" class='volplus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></div></td></xsl:if>
				<xsl:if test=".&lt;0.5 and .&gt;-0.5"><td><div class="data-col"><div title="{format-number(., '#0')}%" class='volzero'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></div></td></xsl:if>
				<xsl:if test=".&lt;-0.5 and .&gt;-1"><td><div class="data-col"><div title="{format-number(., '#0')}%" class='volminus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></div></td></xsl:if>
				<xsl:if test=".&lt;-1 and .&gt;-4"><td><div class="data-col"><div title="{format-number(., '#0')}%" class='voloneMinus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></div></td></xsl:if>
				<xsl:if test=".&lt;-4"><td><div class="data-col"><div title="{format-number(., '#0')}%" class='volfourMinus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></div></td></xsl:if>
			</xsl:when>
			<xsl:when test="position() = 9">
			</xsl:when>				
			<!--xsl:when test="position() = 10">
				<td class="signalsCell"><div class="data-col"><div title="{.}" class='signals'><xsl:value-of select='.'/></div></div></td>
			</xsl:when-->				
			<xsl:otherwise>
				<td><div class="data-col"><xsl:value-of select="format-number(translate(., ',', ''), '#0.00')"/></div></td>
			</xsl:otherwise>
		</xsl:choose>			
	</xsl:template>
			  
</xsl:stylesheet>
