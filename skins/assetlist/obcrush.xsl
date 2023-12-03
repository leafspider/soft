<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/crush" xml:space="preserve">	

	<html>
	  <head>
	    <title>Crush</title>
	
	    <link href="{$skinUrl}sen.full.min.css" rel="stylesheet" type="text/css" />
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
	
	  </head>
	
	<body>
		
		<br/>
		<table border="0" width="100%">
			<tr>
				<td width="700" align="right" style="font-size:1.2em;">
					<nobr><b>Crush <xsl:value-of select="listName" /></b></nobr>
				</td>
				<td colspan="2" align="left">
						<xsl:text>&#160;&#160;</xsl:text><input style="font-size:1.0em;" size="7" type="text" name="end" value="{end}"/><xsl:text>&#160;&#160;&#160;&#160;</xsl:text>
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
					<span style="font-weight:bold;color:green;">peel</span><br/>
					<span style="font-weight:bold;color:purple;">jam</span><br/>
					<span style="font-weight:bold;color:blue;">press</span><br/>
					<span style="font-weight:bold;color:#ff6600;">crush</span>
				</td>
			</tr>
		</table>
		
	  <script>	  	
	  	
	  Event.observe(window, 'load', function() 
	  {
	    var linegraph1 = new Grafico.LineGraph($('linegraph1'),
		    {
		    	close:[<xsl:value-of select="close" />],
					flow:[<xsl:value-of select="flow" />],
					peel:[<xsl:value-of select="peel" />],
					jam:[<xsl:value-of select="jam" />],
					press:[<xsl:value-of select="press" />],
					crush:[<xsl:value-of select="crush" />]
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