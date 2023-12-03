<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/toffee" xml:space="preserve">	

	<html>
	  <head>
	    <title>Toffee Grid</title>	
	    
			<xsl:call-template name="head" />			
			
	    <style>
			table {text-align:center;}
			table.grid {margin:10px 180px;border:2px solid black;border-top:0px;border-left:0px;background-color:#e5f0ff;}
			.cell {border:1px dotted black;text-align:left;font-size:1.0em;padding:2px;}			
			.rtcell {border:2px solid black;text-align:center;}
			.cornercell {border:0px 2px 2px 0px solid black;text-align:left;font-weight:bold;font-size:3.0em;position:absolute;top:65px;left:100px;}
			.firstrow {border:0px 2px 2px 2px solid black;font-weight:bold;font-size:0.8em;}
			.firstcol {border:2px solid black;border-left:0px;font-weight:bold;font-size:1.0em;padding:5px 25px;}
			.firstrowcol {
				border-top:0px;
				border-left:2px solid black;
				border-right:2px solid black;
				border-bottom:2px solid black;
				max-width:10px;
				font-weight:bold;
				height:100px;
				font-size:1.3em;
				padding-left:0px;
				-ms-transform:rotate(270deg); /* IE 9 */
				-moz-transform:rotate(270deg); /* Firefox */
				-webkit-transform:rotate(270deg); /* Safari and Chrome */
				-o-transform:rotate(270deg); /* Opera */
			}
			.mention {background-color:#fed330;height:40px;width:60px;border-radius:3px;position:relative;}
			.nomention {background-color:transparent;height:40px;width:60px;border-radius:3px;}
			.rtmention {background-color:#dcfe40;height:40px;width:60px;border-radius:3px;position:relative;vertical-align:middle;}
			.gnature {height:20px;width:20px;position:absolute;color:white;font-size:0.8em;text-align:center;vertical-align:center;background-color:green;border-radius:3px;}
			.rnature {height:20px;width:20px;position:absolute;color:white;font-size:0.8em;text-align:center;vertical-align:center;background-color:red;border-radius:3px;}
			.signal {text-decoration:none;cursor:pointer;link:white;visited.color:white;hover.color:white;active.color:white;background-color:inherit;target:_blank;}
			.signal:link {color:white;background-color:inherit;}
			.signal:visited {color:white;background-color:inherit;}
			.signal:hover {color:#eec320;background-color:inherit;} 
			.signal:active {color:white;background-color:inherit;} 
			.tweetbox {border:1px solid #000;background:#FFFFE1;position:absolute;z-index:100;padding:10px;}
			.tweetboxText {width:400px;background:transparent;color:#000;font-size:1.2em;}
			a {color:blue;}
			</style>
		
	    <script>				
			function showSpan(spanid) { if (document.getElementById(spanid)) { document.getElementById( spanid ).style.display = "";} }			
			function hideSpan(spanid) { if (document.getElementById(spanid)) { document.getElementById( spanid ).style.display = "none"; } }							  
			function populateTickerField( val )
			{
				document.forms[0].ticker.value = val;
				document.forms[0].ticker.focus();
			} 
			</script>
			
	  </head>
	
	<body>
		
		<form>		
			<xsl:call-template name="menu" />			
			
			<!--
			<table style="border:0;margin:20px 20%;">
				<tr style="font-size:1.5em;text-align:center;">
					<th><nobr><b>Toffee</b></nobr></th>
				</tr>
			</table>		
			-->
			
			<br/><br/><br/><br/>

			<xsl:apply-templates select="grid"/>
			
			<xsl:apply-templates select="fundamentals"/>
			
			<br/><br/><br/><br/><br/><br/>
			
		</form>
	
	  </body>
	</html>

	</xsl:template>
	
	<xsl:template match="grid" >			
		<table class="grid">
			<xsl:apply-templates select="firstrow"/>
			<xsl:apply-templates select="rtrow"/>
			<xsl:apply-templates select="row"/>
		</table>		
	</xsl:template>
	
	<xsl:template match="firstrow" >	
		<tr class="firstrow">
			<xsl:apply-templates select="firstrowcol"/>
			<xsl:apply-templates select="col"/>
		</tr>
	</xsl:template>
	
	<xsl:template match="rtrow" >	
		<tr class="rtrow">
			<xsl:apply-templates select="rtrowcol"/>
		</tr>
	</xsl:template>

	<xsl:template match="row" >
		<xsl:choose>
			<xsl:when test="position() mod 2 = 1">
				<tr style="background-color:white;">
					<xsl:apply-templates select="firstcol"/>
					<xsl:apply-templates select="col"/>
				</tr>
			</xsl:when>				
			<xsl:otherwise>
				<tr>
					<xsl:apply-templates select="firstcol"/>
					<xsl:apply-templates select="col"/>
				</tr>				
			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>
	
	<xsl:template match="firstcol" >	
		<td class="cell firstcol">
			<a onclick="populateTickerField('{.}');">
				<xsl:value-of select="." />
			</a>
		</td>
	</xsl:template>

	<xsl:template match="firstrowcol" >	
		<xsl:choose>
			<xsl:when test=".=''">
				<td class="cornercell">
					<nobr>Toffee</nobr>
				</td>
			</xsl:when>
			<xsl:otherwise>
				<td class="cell firstrowcol">					
					<a style="padding:0px;border-radius:3px;" target="_blank" href="http://twitter.com/{.}"><xsl:value-of select="." /></a>
				</td>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
	
	<xsl:template match="rtrowcol" >	
		<xsl:choose>
			<xsl:when test=".=''">
				<td class="cell firstcol">
					<nobr>Top RT</nobr>
				</td>
			</xsl:when>
			<xsl:otherwise>
				<td class="cell rtcell">					
					<a  style="border-radius:3px;vertical-align:middle;" target="_blank" href="{url}" onmouseover="showSpan( 'rtsp{url}' )" onmouseout="hideSpan( 'rtsp{url}' )">
						<div class="rtmention"><div style="padding-top:7px;"><xsl:value-of select="count" /></div></div>
					</a>
					<span class="tweetbox" style="display:none;" id="rtsp{url}">
						<div class="tweetboxText"><xsl:value-of select="text" /></div>
					</span>
				</td>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>

	<xsl:template match="col" >	
		<td class="cell">
			<!--
			<xsl:if test=".!=''">
				<a class="signal" href="{url}" target="_blank" title="{text}">
					<div class="mention">
						<xsl:choose>
							<xsl:when test="nature=1"><div class="gnature" style="top:0;left:0;">Le</div></xsl:when>
							<xsl:when test="nature=2"><div class="gnature" style="top:0;left:20;">Lc</div></xsl:when>
							<xsl:when test="nature=3"><div class="gnature" style="top:0;left:40;">Sp</div></xsl:when>
							<xsl:when test="nature=4"><div class="rnature" style="top:20;left:0;">Se</div></xsl:when>
							<xsl:when test="nature=5"><div class="rnature" style="top:20;left:20;">Sc</div></xsl:when>
							<xsl:when test="nature=6"><div class="rnature" style="top:20;left:40;">Lp</div></xsl:when>
						</xsl:choose>	
					</div>
				</a>
			</xsl:if>
			-->

			<!--
			<xsl:if test=".!=''">
				<a class="signal" href="{url}" target="_blank" onmouseover="showSpan( 'sp{id}' )" onmouseout="hideSpan( 'sp{id}' )">
					<div class="mention">
						<xsl:choose>
							<xsl:when test="nature=1"><div class="gnature" style="top:0;left:0;">Le</div></xsl:when>
							<xsl:when test="nature=2"><div class="gnature" style="top:0;left:20;">Lc</div></xsl:when>
							<xsl:when test="nature=3"><div class="gnature" style="top:0;left:40;">Sp</div></xsl:when>
							<xsl:when test="nature=4"><div class="rnature" style="top:20;left:0;">Se</div></xsl:when>
							<xsl:when test="nature=5"><div class="rnature" style="top:20;left:20;">Sc</div></xsl:when>
							<xsl:when test="nature=6"><div class="rnature" style="top:20;left:40;">Lp</div></xsl:when>
						</xsl:choose>	
					</div>
				</a>
				<span class="tweetbox" style="display:none;" id="sp{id}">
					<div class="tweetboxText"><xsl:value-of select="text" /></div>
				</span>
			</xsl:if>
			-->
			
			<xsl:if test=".!=''">
				<a class="signal" href="{url}" target="_blank" onmouseover="showSpan( 'sp{id}' )" onmouseout="hideSpan( 'sp{id}' )">
					<div class="mention">
						<xsl:if test="longEquity &gt; 0"><div class="gnature" style="top:0;left:0;">Le</div></xsl:if>
						<xsl:if test="longCall &gt; 0"><div class="gnature" style="top:0;left:20;">Lc</div></xsl:if>
						<xsl:if test="shortPut &gt; 0"><div class="gnature" style="top:0;left:40;">Sp</div></xsl:if>
						<xsl:if test="shortEquity &gt; 0"><div class="rnature" style="top:20;left:0;">Se</div></xsl:if>
						<xsl:if test="shortCall &gt; 0"><div class="rnature" style="top:20;left:20;">Sc</div></xsl:if>
						<xsl:if test="longPut &gt; 0"><div class="rnature" style="top:20;left:40;">Lp</div></xsl:if>
					</div>
				</a>
				<span class="tweetbox" style="display:none;" id="sp{id}">
					<div class="tweetboxText"><xsl:value-of select="text" /></div>
				</span>
			</xsl:if>

			<xsl:if test=".=''">
				<div class="nomention"></div>
			</xsl:if>

		</td>
	</xsl:template>
	
</xsl:stylesheet>