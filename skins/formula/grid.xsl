<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/toffee" xml:space="preserve">	

	<html>
	  <head>
	    <title>Formula Grid</title>	
	    
		<xsl:call-template name="head" />			
			
	    <style>
			table {}
			table.grid {margin:10px 15px;border:0px solid black;border-top:0px;border-left:0px;background-color:#e5f0ff;}
			.cell {border:0px dotted black;text-align:left;font-size:1.0em;padding:0px;}			
			.cell-filler {display:inline-block;}
			.rtcell {border:2px solid black;text-align:center;}
			.cornercell {border:0px 2px 2px 0px solid black;text-align:left;font-weight:bold;font-size:2.5em;position:absolute;top:65px;left:30px;}
			.firstrow {border:0px 2px 2px 2px solid black;font-weight:bold;font-size:0.8em;}
			.firstcol {border:0px solid black;border-left:0px;font-weight:bold;font-size:0.8em;padding:0px 20px;max-width:400px;}
			.firstrowcol {
				border-top:0px;
				border-left:2px solid black;
				border-right:2px solid black;
				border-bottom:2px solid black;
				max-width:10px;
				font-weight:bold;
				height:50px;
				font-size:1.2em;
				padding-left:0px;
				-ms-transform:rotate(270deg); /* IE 9 */
				-moz-transform:rotate(270deg); /* Firefox */
				-webkit-transform:rotate(270deg); /* Safari and Chrome */
				-o-transform:rotate(270deg); /* Opera */
			}
			.mention {background-color:#fed330;height:30px;width:30px;border-radius:3px;margin-top:4px;}
			.nomention {background-color:transparent;height:30px;width:30px;border-radius:3px;}
			.rtmention {background-color:#dcfe40;height:30px;width:30px;border-radius:3px;position:relative;vertical-align:middle;}
			.gnature {height:15px;width:20px;position:absolute;color:white;text-align:center;vertical-align:center;background-color:green;border-radius:3px;}
			.rnature {height:15px;width:20px;position:absolute;color:white;font-size:0.8em;text-align:center;vertical-align:center;background-color:red;border-radius:3px;}
			.signal {text-decoration:none;cursor:pointer;link:white;visited.color:white;hover.color:white;active.color:white;background-color:inherit;target:_blank;}
			.signal:link {color:white;background-color:inherit;}
			.signal:visited {color:white;background-color:inherit;}
			.signal:hover {color:#eec320;background-color:inherit;} 
			.signal:active {color:white;background-color:inherit;} 
			.tweetbox {border:1px solid #000;background:#FFFFE1;position:absolute;z-index:100;padding:10px;}
			.tweetboxText {width:400px;background:transparent;color:#000;font-size:1.2em;}
			a {color:blue;}
			
			.mention2 {background-color:#fed330;height:113px;width:80px;border-radius:3px;margin-top:10px;position:relative;left:-20px;top:80px;display:none;}
			.gruledesc {width:70px;vertical-align:top;font-size:0.25em;display:inline-block;padding-top:5px;padding-left:3px;margin-left:2px;text-align:left;}
			.rruledesc {width:70px;vertical-align:top;font-size:0.25em;display:inline-block;padding-top:5px;padding-left:3px;margin-left:2px;text-align:left;}
			.gnature2 {vertical-align:top;height:13px;width:15px;color:white;text-align:center;background-color:green;border-radius:3px;display:inline-block;}
			.rnature2 {vertical-align:top;height:13px;width:15px;color:white;text-align:center;background-color:red;border-radius:3px;display:inline-block;}
			.rulename {display:inline-block;}
		</style>
	    		
		<script>				
			function showSpan(spanid) { if (document.getElementById(spanid)) { document.getElementById( spanid ).style.display = "";} }			
			function hideSpan(spanid) { if (document.getElementById(spanid)) { document.getElementById( spanid ).style.display = "none"; } }							  
			function populateTickerField( val ) {
				document.forms[0].ticker.value = val;
				document.forms[0].ticker.focus();
			}

			<![CDATA[
			
				$(function () {
					var params = {};
					var ps = window.location.search.split(/\?|&/);
					for (var i = 0; i < ps.length; i++) {
					      if (ps[i]) {
						    var p = ps[i].split(/=/);
						    params[p[0]] = p[1];
					      }
					}				
					console.log(params.u);
					//if( params.u == 'jake' ) {
					if( true ) {
						$('.twitter-restricted').hide();
						$('.twitter-unrestricted').show();
					}
				});    
			]]>
			
		</script>
			
	  </head>
	
	<body>
		
		<form>		
			<xsl:call-template name="menu" />	
			
			<br/><br/>
			
			<xsl:apply-templates select="grid"/>
			
			<br/><br/><br/><br/><br/><br/><br/><br/>
			
		</form>
	
	  </body>
	</html>

	</xsl:template>

	<xsl:template match="grid" >			
		<table class="grid">
			<xsl:apply-templates select="firstrow"/>
			<xsl:apply-templates select="row"/>
		</table>		
	</xsl:template>
	
	<xsl:template match="firstrow" >	
		<tr class="firstrow">
			<xsl:apply-templates select="firstrowcol"/>
			<xsl:apply-templates select="col"/>
		</tr>
	</xsl:template>
	
	<xsl:template match="row" >
		<xsl:choose>
			<xsl:when test="position() mod 2 = 1">
				<tr style="background-color:white;">
					<xsl:apply-templates select="firstcol"/>
					<td class="cell"><div class="cell-filler"></div><xsl:apply-templates select="col"/><div class="cell-filler"></div></td>
				</tr>
			</xsl:when>				
			<xsl:otherwise>
				<tr>
					<xsl:apply-templates select="firstcol"/>
					<td class="cell"><div class="cell-filler"></div><xsl:apply-templates select="col"/><div class="cell-filler"></div></td>
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
					<nobr>Formula: <div style="display:inline-block;font-size:0.8em;position:relative;top:0px;left:20px;"><xsl:value-of select="/toffee/query" /></div>
					</nobr>	
				</td>
			</xsl:when>
			<xsl:otherwise>
				<td class="cell" style="height:50px;"></td>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>
	
	<xsl:template match="rtrowcol" >			
		
		<xsl:choose>
			<xsl:when test=".=''">				
				<td class="cell firstcol" style="text-align:right;color:darkgrey;">
					<nobr>Top RT</nobr>
				</td>
			</xsl:when>
			<xsl:otherwise>
				<td class="cell rtcell">		
					<div class="twitter-restricted">
						<div style="border-radius:3px;vertical-align:middle;" onmouseover="showSpan( 'rtsp{url}' )" onmouseout="hideSpan( 'rtsp{url}' )">
							<div class="rtmention"><div style="padding-top:7px;"><xsl:value-of select="count" /></div></div>
						</div>
					</div>
					<div class="twitter-unrestricted" style="display:none">
						<xsl:if test="count = 0">
							<a style="border-radius:3px;vertical-align:middle;">
								<div class="rtmention"><div style="padding-top:7px;"><xsl:value-of select="count" /></div></div>
							</a>
						</xsl:if>
						<xsl:if test="count &gt; 0">
							<a style="border-radius:3px;vertical-align:middle;" target="_blank" href="{url}" onmouseover="showSpan( 'rtsp{url}' )" onmouseout="hideSpan( 'rtsp{url}' )">
								<div class="rtmention"><div style="padding-top:7px;"><xsl:value-of select="count" /></div></div>
							</a>
						</xsl:if>
					</div>
					<span class="tweetbox" style="display:none;" id="rtsp{url}">
						<div class="tweetboxText"><xsl:value-of select="text" /></div>
					</span>
				</td>
			</xsl:otherwise>
		</xsl:choose>
		
	</xsl:template>

	<xsl:template match="col" >	
			
		<xsl:if test=".!=''">
			<div style="display:inline-block;">
				<a class="signal" href="{url}" target="_blank" onmouseover="showSpan( 'sp{id}' )" onmouseout="hideSpan( 'sp{id}' )">
					<div class="mention">
						<xsl:if test="longEquity &gt; 0"><div class="gnature" style="top:0;left:0;">Le</div></xsl:if>
						<xsl:if test="longCall &gt; 0"><div class="gnature" style="top:0;left:15;">Lc</div></xsl:if>
						<xsl:if test="shortPut &gt; 0"><div class="gnature" style="top:0;left:30;">Sp</div></xsl:if>
						<xsl:if test="shortEquity &gt; 0"><div class="rnature" style="top:15;left:0;">Se</div></xsl:if>
						<xsl:if test="shortCall &gt; 0"><div class="rnature" style="top:15;left:15;">Sc</div></xsl:if>
						<xsl:if test="longPut &gt; 0"><div class="rnature" style="top:15;left:30;">Lp</div></xsl:if>
					</div>
				</a>
				<span class="tweetbox" style="display:none;" id="sp{id}">
					<div class="tweetboxText">@<xsl:value-of select="handle" /> : <xsl:value-of select="text" /></div>
				</span>
			</div>
		</xsl:if>

	</xsl:template>
		
</xsl:stylesheet>