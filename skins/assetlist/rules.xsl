<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
			
	<xsl:template match="/rules" xml:space="preserve">	

	<html>
		<head>
			<title>Rules</title>
			
			<xsl:call-template name="head" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<style>
				table {align:center;width:100%;}
				th {text-align:left;}
				td {white-space:nowrap;padding:3px;}
				.report-title {padding:4px;color:white;}
				.asset table {width:100%;background-color:white;margin-right:50px;}
				.asset-title {display:inline-block;font-weight:bold;}
				.asset-name {display:inline-block;margin-left:10px;padding-top:10px;font-weight:bold;}
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
				.signal {text-align:center;display:inline-block;margin-right:3px;border-radius:3px}
				.rule {border:1px solid black;margin-right:20px;}
				.norule {border:1px solid white;margin-right:20px;}
				.ruleA1 {background-color:rgb(50, 255, 0, 0.5);border:1px solid black;}
				.ruleA2 {background-color:rgb(50, 222, 0, 0.6);border:1px solid black;}
				.ruleA3 {background-color:rgb(50, 189, 0, 0.7);color:white;border:1px solid black;}
				.ruleA4 {background-color:rgb(50, 156, 0, 0.7);color:white;border:1px solid black;}
				.ruleD1 {background-color:rgb(255, 0, 0, 0.7);border:1px solid black;}
			</style>
		</head>	

	<body>
			
		<form>
				
			<xsl:call-template name="menu" />
						
			<table border="0" width="100%">
				<tr style="font-size:1.2em;">
					<th style="text-align:left;padding:30px 0px 0px 100px"><nobr><b><h1>Rules</h1></b></nobr>
					</th>						
					<!--td style="text-align:right;">	
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
									<td colspan="5" style="text-align:left;font-size:0.8em;">
										<div style="color:white;background-color:ff8000;margin-right:0px" class="signal rule">C</div> Crush
										<div style="color:white;background-color:0033ff;margin:10px 0 0 20px" class="signal rule">P</div> Porter
										<div style="margin:10px 0 0 20px" class="signal ruleA1">A</div> Accumulation
										<div style="margin:10px 0 0 20px" class="signal ruleD1">D</div> Distribution
									</td>								
								</tr>
							</table>
						</div>						
					</td-->
				</tr>
				<tr>				
					<td colspan="2" valign="top">
						<div class="toptail" style="margin-right:100px;margin-left:100px;">										
							<xsl:apply-templates select="reports" />
						</div>
					</td>
				</tr>
			</table>
		
		</form>
		
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
			<div class="report-title"><xsl:value-of select="@title" /></div>	
			<xsl:apply-templates select="asset"/>
		</div>
		<div class="spacer"/>
	</xsl:template>			  
			  
	<xsl:template match="asset">		
		<div class="asset">
			<table class="asset-table">
				<tr class="col-names">
					<td style="width:80px;"></td><td style="width:30px;padding-left:3px;">Buy</td><td style="width:30px;padding-left:3px;">Sell</td><td style="padding-left:100px;">Why</td>
				</tr>
				<tr>
					<td>
						<div class="asset-title">
							<a href="#">
								<xsl:value-of select='@name'/>
							</a>
						</div>
					</td>
					<xsl:apply-templates select="signals"/>
				</tr>
			</table>
		</div>
	</xsl:template>
		
	<xsl:template match="signals">		
		<xsl:apply-templates select="signal"/>
	</xsl:template>
			
	<xsl:template match="signal">
		<xsl:choose>
			<xsl:when test="@action='buy'">
				<td>
					<div class="signal rule" style="background-color:{@color}">
						<div style="display:inline-block;font-size:0.9em;margin:2px 0px;padding:2px 4px;">
							<xsl:value-of select='format-number(@val, "#00.00")'/>
						</div> 
					</div>
				</td>		
				<td></td>
				<td style="padding-left:100px;font-size:0.8em;">
					<xsl:value-of select='why'/>
				</td>
			</xsl:when>			
		</xsl:choose>
		<xsl:choose>
			<xsl:when test="@action='sell'">
				<td></td>
			</xsl:when>			
		</xsl:choose>
	</xsl:template>
				  
</xsl:stylesheet>
