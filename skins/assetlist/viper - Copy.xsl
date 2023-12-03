<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
			
	<xsl:template match="/viper" xml:space="preserve">	

	<html>
		<head>
			<title>Viper</title>
			
			<xsl:call-template name="head" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<style>
				table {align:center;width:100%;}
				th {text-align:left;}
				td {white-space:nowrap;padding:3px;}
				.report-title {padding:4px;color:white;}
				.asset table {width:100%;background-color:white;margin-right:50px;}
				.asset-title {padding-top:5px;font-weight:bold;}
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

				.prediction {width:120px;background-color:rgb(0, 255, 0, 0.7);text-align:center!important;padding-right:0!important}
			</style>
		</head>	

	<body>
			
		<!--form-->
				
			<xsl:call-template name="menu" />
						
			<table border="0" width="100%">
				<tr style="font-size:1.2em;text-align:center;">
					<th colspan="2" style="text-align:left;padding:30px 0px 0px 100px"><nobr><b><h1>Viper</h1></b></nobr>
					</th>
				</tr>						
				<tr>								
					<td>	
						<div style="margin-right:100px;margin-left:100px;margin-bottom:20px;">
							<table style="text-align:center;">
								<tr>
									<td class='volfourPlus'> &gt; 4% </td>
									<td class='volonePlus'> &gt; 1% </td>
									<td class='volplus'> &gt; 0.5% </td>
									<td class='volzero'> -0.5 - 0.5% </td>
									<td class='volminus'> &lt; -0.5% </td>
									<td class='voloneMinus'> &lt; -1% </td>
									<td class='volfourMinus'> &lt; -4% </td>						
								</tr>
							</table>
						</div>						
					</td>
				</tr>
				<tr>				
					<td valign="top">
						<div class="toptail" style="margin-right:100px;margin-left:100px;">										
							<xsl:apply-templates select="reports" />
						</div>
					</td>
				</tr>
			</table>
		
		<!--/form-->
		
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
			<!--table class="asset">
				<tr><td>Date</td><td>Open</td><td>High</td><td>Low</td><td>Close</td><td>Adjusted_close</td><td>Volume</td></tr>
			</table-->
			<xsl:apply-templates select="asset"/>
		</div>
		<div class="spacer"/>
	</xsl:template>			  
			  
	<xsl:template match="asset">
		<div class="asset">
			<table class="asset-table">
				<tr><td colspan="5"><div class="asset-title"><xsl:value-of select="@ticker" /> <xsl:value-of select="@name" /></div></td></tr>
				<tr class="col-names"><td>Date</td><td>Open</td><td>High</td><td>Low</td><td>Close</td><td>Change</td><td>%Change</td><td>Volume</td><td></td><td>Alerts</td></tr>
				<xsl:apply-templates select="row"/>
			</table>
		</div>
	</xsl:template>

	<xsl:template match="row">		
		<tr><xsl:apply-templates select="col"/></tr>
	</xsl:template>

	<xsl:template match="col">
		<td>
			<div class="data-col">
				<xsl:choose>
					<xsl:when test="position() = 1">
						<xsl:value-of select='.'/>
					</xsl:when>
					<xsl:when test="position() = 7">				
						<xsl:if test=".&gt;4"><div class='fourPlus'>+<xsl:value-of select='format-number(., "#0.00")'/></div></xsl:if>
						<xsl:if test=".&lt;4 and .&gt;1"><div class='onePlus'>+<xsl:value-of select='format-number(., "#0.00")'/></div></xsl:if>						
						<xsl:if test=".&lt;1 and .&gt;0.5"><div class='plus'>+<xsl:value-of select='format-number(., "#0.00")'/></div></xsl:if>						
						<xsl:if test=".&lt;0.5 and .&gt;-0.5"><div class='zero'><xsl:value-of select='format-number(., "#0.00")'/></div></xsl:if>						
						<xsl:if test=".&lt;-0.5 and .&gt;-1"><div class='minus'><xsl:value-of select='format-number(., "#0.00")'/></div></xsl:if>	
						<xsl:if test=".&lt;-1 and .&gt;-4"><div class='oneMinus'><xsl:value-of select='format-number(., "#0.00")'/></div></xsl:if>
						<xsl:if test=".&lt;-4"><div class='fourMinus'><xsl:value-of select='format-number(., "#0.00")'/></div></xsl:if>
					</xsl:when>
					<xsl:when test="position() = 8">
						<!--div class='vol'><xsl:value-of select='format-number(., "###,###,##0")'/></div-->
						<!--xsl:if test="@vpcc&gt;4"><div title="{@vpcc}%" class='volfourPlus'><xsl:value-of select='format-number(., "#0")'/></div></xsl:if>
						<xsl:if test="@vpcc&lt;4 and @vpcc&gt;1"><div title="{@vpcc}%" class='volonePlus'><xsl:value-of select='format-number(., "#0")'/></div></xsl:if>
						<xsl:if test="@vpcc&lt;1 and @vpcc&gt;0.5"><div title="{@vpcc}%" class='volplus'><xsl:value-of select='format-number(., "#0")'/></div></xsl:if>
						<xsl:if test="@vpcc&lt;0.5 and @vpcc&gt;-0.5"><div title="{@vpcc}%" class='volzero'><xsl:value-of select='format-number(., "#0")'/></div></xsl:if>
						<xsl:if test="@vpcc&lt;-0.5 and @vpcc&gt;-1"><div title="{@vpcc}%" class='volminus'><xsl:value-of select='format-number(., "#0")'/></div></xsl:if>
						<xsl:if test="@vpcc&lt;-1 and @vpcc&gt;-4"><div title="{@vpcc}%" class='voloneMinus'><xsl:value-of select='format-number(., "#0")'/></div></xsl:if-->
						<!--xsl:if test="@vpcc&lt;-4"><div title="{@vpcc}%" class='volfourMinus'><xsl:value-of select='format-number(., "#0")'/></div></xsl:if-->
						<!--xsl:value-of select='following-sibling::*[1]'/-->
						<xsl:if test=".&gt;4"><div title="{format-number(., '#0')}%" class='volfourPlus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></xsl:if>
						<xsl:if test=".&lt;4 and .&gt;1"><div title="{format-number(., '#0')}%" class='volonePlus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></xsl:if>
						<xsl:if test=".&lt;1 and .&gt;0.5"><div title="{format-number(., '#0')}%" class='volplus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></xsl:if>
						<xsl:if test=".&lt;0.5 and .&gt;-0.5"><div title="{format-number(., '#0')}%" class='volzero'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></xsl:if>
						<xsl:if test=".&lt;-0.5 and .&gt;-1"><div title="{format-number(., '#0')}%" class='volminus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></xsl:if>
						<xsl:if test=".&lt;-1 and .&gt;-4"><div title="{format-number(., '#0')}%" class='voloneMinus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></xsl:if>
						<xsl:if test=".&lt;-4"><div title="{format-number(., '#0')}%" class='volfourMinus'><xsl:value-of select='format-number(following-sibling::*[1], "#0")'/></div></xsl:if>
					</xsl:when>
					<xsl:when test="position() = 9">
					</xsl:when>				
					<xsl:when test="position() = 10">
						<div title="{.}" class='prediction'><xsl:value-of select='.'/></div>
					</xsl:when>				
					<xsl:otherwise>
						<xsl:value-of select='format-number(., "#0.00")'/>
					</xsl:otherwise>
				</xsl:choose>
			</div>
		</td>
	</xsl:template>
			  
</xsl:stylesheet>
