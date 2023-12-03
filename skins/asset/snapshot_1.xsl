<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/snapshot" xml:space="preserve">	

	<html>
	  <head>
	    <title>Snapshot</title>
	
			<xsl:call-template name="head" />
			
	    <style>
	    .example {
	      width:99%;
	      height:650px;
	    }
	    </style>
	    
	  </head>
	
	<body class="bod">
		
		<form class="back">
			
			<xsl:call-template name="menu" />
			
			<table border="0" width="100%">
				<tr>
					<td style="padding-left:100px;"></td>
					<td style="font-size:1.2em;width:0px;"><b>Snapshot</b></td>
					<td style="font-size:1.2em;"><b><nobr><xsl:value-of select="ticker" /><xsl:text>&#160;&#160;&#160;</xsl:text><xsl:value-of select="start" /> ... <xsl:value-of select="end" /></nobr></b></td>
			</tr>
				<tr>
					<td></td>
					<td style="padding-top:10px;width:0px;">Trading Strategy</td>
					<td style="padding-top:10px;padding-left:15px;"><xsl:value-of select="strategy" /></td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;">Target Price</td>
					<td style="padding-left:15;"><xsl:value-of select="targetPrice" /></td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;">Exit</td>
					<td style="padding-left:15;">
						<xsl:if test="exit!='null'">
							<xsl:value-of select="exit" /> 
						</xsl:if>
						<xsl:if test="exit='null'">
							<input type="text" name="exit" style="width:400px;"/>
						</xsl:if>
					</td>
				</tr>
				<tr>
					<td></td>
					<td style="width:0px;">P&amp;L</td>
					<td style="padding-left:15;">
						<xsl:if test="pal!='null'">
							<xsl:value-of select="pal" />
						</xsl:if>
						<xsl:if test="pal='null'">
							<input type="text" name="pal" style="width:340px;"/> <span style="margin-left:10px;" id="exitbut" class="butspan" onclick="saveExit()"><xsl:text>&#160;</xsl:text>save<xsl:text>&#160;</xsl:text></span>
						</xsl:if>
					</td>
				</tr>
				<xsl:if test="exit!='null'">
					<tr>
						<td></td>
						<td style="width:0px;">Saved</td>
						<td style="padding-left:15;">
							<xsl:if test="lastModified!='null'">
								<xsl:value-of select="lastModified" />
							</xsl:if>
						</td>
					</tr>
				</xsl:if>
			</table>		
			
			<table border="0" width="100%">
				<tr>
					<td colspan="2" align="right">
				  	<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
				  	<div id="linegraph1" class="example" style="padding-left:35px;"></div>
				  </td>
					<td width="100" align="left">
						<span style="font-weight:bold;color:black;">close</span><br/>
						<span style="font-weight:bold;color:grey;">flow</span><br/>
						<span style="font-weight:bold;color:blue;">plate</span><br/>
						<span style="font-weight:bold;color:lightgreen;">pear</span><br/>
						<span style="font-weight:bold;color:#ff6600;">crush</span><br/>
						<span style="border-radius:5px;font-weight:bold;color:#efefff;background-color:{cherryColor}">cherry</span>
					</td>
				</tr>
				<!--
				<tr>
					<td colspan="2" align="right">
				  	<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
				  	<div id="linegraph2" class="example"></div>
				  </td>
					<td width="100" align="left">
						<span style="font-weight:bold;color:black;">volume</span><br/>
					</td>
				</tr>
				-->
			</table>
			
		
			<!--
			<table>
				<tr>
					<td width="25%"><br/></td>
					<td class="quoteTitle">
						Daily Quotes - Past 5 Weeks
					</td>
					<td width="20%"><br/></td>
				</tr>
				<tr>
					<td><br/></td>
					<td>
						<table>
							<xsl:value-of select="dailyQuoteHtm" disable-output-escaping="yes"/>				
						</table>
					</td>
					<td><br/></td>
				</tr>
			</table>
			-->
			
		</form>
	  
	  <script>	  	
	  	
	  var closearray = [<xsl:value-of select="close" />];
	  
		<![CDATA[	
	  var labarray = [];
	  for(i=0; i<closearray.length; i++)
  	{
  		if (i%5 == 0) { labarray[i] = "" + i; }
  		else { labarray[i] = ""; }
  	}
		]]>
		
	  Event.observe(window, 'load', function() 
	  {
	    var linegraph1 = new Grafico.LineGraph($('linegraph1'),
		    {
		    	close:[<xsl:value-of select="close" />],
					flow:[<xsl:value-of select="flow" />],
					plate:[<xsl:value-of select="plate" />],
					pear:[<xsl:value-of select="pear" />],
					crush:[<xsl:value-of select="crush" />]
				},
				{
//					show_horizontal_labels:true,
					show_ticks:true,
					grid:true,
					labels:labarray,
					label_rotation:-30,
					hide_empty_label_grid:true,
					grid_color:"#ddf"
				});
				
    	var linegraph2 = new Grafico.LineGraph($('linegraph2'),
		    {
		    	volume:[<xsl:value-of select="volume" />],
				},
				{
//					show_horizontal_labels:true,
					show_ticks:true,
					grid:true,
					labels:labarray,
					label_rotation:-30,
					hide_empty_label_grid:true,
					grid_color:"#ddf",
					vertical_label_unit:"M"
				});
	  });
	  
	  var collection = "<xsl:value-of select="project" />";
	  
		<![CDATA[		
				
		onloadSnapshot();
		function onloadSnapshot()
		{
  		var id = "years=1";	// Default
  		var href = window.location.href;
  		var pos = href.lastIndexOf( "&" );
  		if( pos < 0 )
  		{
  			pos = href.lastIndexOf( "?" );
	  	}
  		if( pos > -1 )
  		{
	  		id = href.substring( pos+1, href.length );
	  	}

	  	var span = document.getElementById( id );
//  		alert( span.id );
	  	span.style.color = "white";	  	
    	span.style.border = "2px indent";
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
			var url = "/soft/asset/" + collection + "/" + tickerVal + "/crush.htm?end=" + endVal + "&" + periodVal;
			top.location.href = url;
	  }			
	  
		]]>
				
	  </script>
	
	  </body>
	</html>

	</xsl:template>
	
</xsl:stylesheet>