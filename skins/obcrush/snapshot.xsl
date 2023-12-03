<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/CrushRecord" xml:space="preserve">	

	<html>
	  <head>
	    <title>Crush</title>
	
	    <style>
	    #wrap {
	      width:700px;
	      padding:2em 2em 2em 172px;
	      margin:0 auto;
	    }
	    #menu {
	      width:150px;
	      position:fixed;
	      top:2em;
	      margin-left:-172px;
	    }
	    h2 {display:block;background:#ccc;padding:0.5em}
	    h3 {display:block;background:#eee;padding:0.5em}
	    h3+*{clear:left;}
	
	    pre {background:#eee;width:auto;padding:1em;overflow:hidden}
	    .example {
	      width:100%;
	      height:800px;
	    }
	    .sparkexample {
	      height:17px;
	      display:inline-block;
	      width:150px;
	    }
	    .butspan {
	    	font-weight:bold;
	    	color:blue;
	    	cursor:hand;	    	
	    	border:2px outdent;
	    	background-color:lightgrey;
	    	margin:3px;
	    	padding:2px;
	    }
	    </style>
	
	    <link href="{$skinUrl}sen.full.min.css" rel="stylesheet" type="text/css" />
	    <script src="{$skinUrl}prototype.js" type="text/javascript" charset="utf-8"></script>
	    <script src="{$skinUrl}raphael.js" type="text/javascript" charset="utf-8"></script>
	   	<script src="{$skinUrl}grafico.base.js" type="text/javascript" charset="utf-8"></script>
	    <script src="{$skinUrl}grafico.line.js" type="text/javascript" charset="utf-8"></script>
	    <script src="{$skinUrl}grafico.bar.js" type="text/javascript" charset="utf-8"></script>
	    <script src="{$skinUrl}grafico.spark.js" type="text/javascript" charset="utf-8"></script>
	  </head>
	
	<body>
		
		<!--
		press
		crush
		ticker
		close
		peel
		jam
		flow
		cherry
		plate
		startDate
		endDate		
		-->
		
		<br/>
		<form>
		
			<table border="0" width="100%">
				<tr>
					<td width="700" align="right" style="font-size:1.2em;">
						<nobr><b>Crush <input style="font-size:1.0em;" size="15" type="text" name="ticker" value="{ticker}"/></b></nobr>
					</td>
					<td colspan="2" align="left">
						<xsl:text>&#160;&#160;</xsl:text><input style="font-size:1.0em;" size="7" type="text" name="end" value="{end}"/><xsl:text>&#160;&#160;&#160;&#160;</xsl:text>
<!--						<span id="days=60" class="butspan" onclick="return onSubmit('days=60')"> 60d </span>-->
						<span id="months=4" class="butspan" onclick="return onSubmit('months=4')"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></span> 
						<span id="months=6" class="butspan" onclick="return onSubmit('months=6')"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></span> 
						<span id="months=8" class="butspan" onclick="return onSubmit('months=8')"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></span> 
						<span id="years=1" class="butspan" onclick="return onSubmit('years=1')"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></span> 
						<span id="monts=18" class="butspan" onclick="return onSubmit('months=18')"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></span> 
						<span id="years=2" class="butspan" onclick="return onSubmit('years=2')"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></span> 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a href="http://finance.yahoo.com/q/bc?s={ticker}" target="_blank"><img src="{$skinUrl}yahoo.ico"/></a><xsl:text>&#160;</xsl:text></span>
					</td>
					<!--
					<td width="" align="left">
						10 day: <b><xsl:value-of select="crush10" /></b><br/>
						20 day: <b><xsl:value-of select="crush20" /></b><br/>
						30 day: <b><xsl:value-of select="crush30" /></b>
					</td>
					-->
				</tr>
				<tr>
					<td colspan="2" align="center">
				  	<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
				  	<div id="linegraph1" class="example"></div>
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