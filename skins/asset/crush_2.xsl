<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/crush" xml:space="preserve">	

	<html>
	  <head>
	    <title>Crush</title>
	
			<xsl:call-template name="head" />
			
	    <style>
	    .example {
	      width:99%;
	      height:600px;
	    }
	    </style>
				
	  </head>
	
	<body class="bod">
		
		<form class="back">
		
			<xsl:call-template name="menu" />
			<!--
			<table border="0" width="100%">
				<tr>
					<td width="700" align="right" style="font-size:1.2em;">
						<nobr><b>Crush <input style="font-size:1.0em;" size="15" type="text" name="ticker" value="{ticker}"/></b></nobr>
					</td>
					<td colspan="2" align="left">
						<xsl:text>&#160;&#160;</xsl:text><input style="font-size:1.0em;" size="7" type="text" name="end" value="{end}"/><xsl:text>&#160;&#160;&#160;&#160;</xsl:text>
						<span id="months=4" class="butspan" onclick="return onSubmit('months=4')"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></span> 
						<span id="months=6" class="butspan" onclick="return onSubmit('months=6')"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></span> 
						<span id="months=8" class="butspan" onclick="return onSubmit('months=8')"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></span> 
						<span id="years=1" class="butspan" onclick="return onSubmit('years=1')"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></span> 
						<span id="monts=18" class="butspan" onclick="return onSubmit('months=18')"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></span> 
						<span id="years=2" class="butspan" onclick="return onSubmit('years=2')"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></span> 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a href="{chartUrl}" target="_blank" style="text-decoration:none;">chart</a><xsl:text>&#160;</xsl:text></span>
						<xsl:text>&#160;&#160;&#160;&#160;</xsl:text>
						<span class="butspan"><xsl:text>&#160;</xsl:text><a style="text-decoration:none;" href="/soft/asset/jake/{ticker}/snapshot.htm?end={end}&amp;start={start}" target="_blank">snapshot</a><xsl:text>&#160;</xsl:text></span>
					</td>
				</tr>
			</table>
			-->
			
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
			</table>
		
			<table border="0">
				<tr>
					<td width="20%"></td>
					<td class="symname">
						<xsl:value-of select="symname"/>
					</td>
					<td style="padding-left:100px;"></td>
					<td>Trading Strategy</td>
					<td style="padding-left:15px;"><input type="text" name="strategy" style="width:400px;"/></td>
				</tr>
				<tr>
					<td></td>
					<td style="text-align:left">
						<span style="font-size:1.3em;font-weight:bold"><xsl:value-of select="last"/></span><xsl:text>&#160;&#160;</xsl:text>
						<span style="font-size:1.1em;font-weight:bold;color:green"><xsl:value-of select="up"/></span>
						<span style="font-size:1.1em;font-weight:bold;color:red"><xsl:value-of select="down"/></span><xsl:text>&#160;&#160;</xsl:text>
						<span style="font-size:0.9em;color:grey"><xsl:value-of select="grey"/></span>
					</td>
					<td> </td>
					<td>Target Price</td>
					<td style="padding-left:15;"><input type="text" name="targetPrice" style="width:340px;"/> <span style="margin-left:10px;" id="snapshot" class="butspan" onclick="snapshot()"><xsl:text>&#160;</xsl:text>save<xsl:text>&#160;</xsl:text></span></td>
				</tr>
				<!--
				<tr>
					<td></td>
					<td>
						<table style="background-color:white;">
							<xsl:value-of select="dailyQuoteHtm" disable-output-escaping="yes"/>				
						</table>
					</td>
				</tr>
				-->
			</table>			
			
			<table border="0" width="100%">
				<tr>
					<td colspan="2" align="right">
				  	<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
				  	<div id="linegraph2" class="example" style="padding-left:35px;"></div>
				  </td>
					<td width="100" align="left">
						<span style="font-weight:bold;color:black;">volume</span>
					</td>
				</tr>
			</table>
				
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
				
		onload();
		function onload()
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