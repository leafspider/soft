<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:variable name="app">soft</xsl:variable>	
	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>	
	
	<xsl:template name="head" xml:space="preserve">			

    	<link rel="icon" href="/soft/skins/resource/leafspider.png"/>
    	
	    <link href="{$skinUrl}crush.css" rel="stylesheet" type="text/css" />

			<script src="{$skinUrl}jquery.min.js"></script>		
			<script src="{$skinUrl}highcharts.js"></script>
			<script src="{$skinUrl}gray.js"></script>

	</xsl:template>

	<xsl:template name="menu" xml:space="preserve">

			<script>

	  		var app = "<xsl:value-of select="$app" />";
	  		var collection = "<xsl:value-of select="collection" />";
			  var closearray = [<xsl:value-of select="crush/close" />];		
			  var labarray = [];	
			  var start = '<xsl:value-of select="start" />';		
			  var end = '<xsl:value-of select="end" />';		
  	
				<![CDATA[	
			  
				function onSubmit( periodVal )
			  {
					var tickerVal = document.forms[0].ticker.value;
					if ( tickerVal.length < 1 ) 
					{
						alert( "Please enter ticker." );
						return;
					}
					var endVal = document.forms[0].end.value;
					if ( endVal.length > 0 && isNaN( Date.parse( endVal ) ) ) 
					{
						alert( "Bad date format. Hint: leave the field empty to reset." );
						return;
					}
					var url = "/soft/asset/" + collection + "/" + tickerVal + "/crush.htm?end=" + endVal + "&" + periodVal;
//					top.location.href = url;
					open( url );
			  }						
			  
			  function populateTickerField( val )
			  {
			  	document.forms[0].ticker.value = val;
			  	document.forms[0].ticker.focus();
			  }  		 	
			  
			  function openChart()
			  {
					var tickerVal = document.forms[0].ticker.value;
					if ( tickerVal.length < 1 ) 
					{
						alert( "Please enter ticker." );
						return;
					}
			  	open( "http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + tickerVal );
			  }
			  
			  function snapshot()
			  {
					var tickerVal = document.forms[0].ticker.value;
					if ( tickerVal.length < 1 ) 
					{
						alert( "Please enter ticker." );
						return;
					}				
			  	open( "/" + app + "/asset/jake/" + tickerVal + "/snapshot.htm?end=" + end + "&start=" + start );
			  }
			  
			  ]]>
			  		
		  </script>							
						
			<br/>
			
			<table border="0" width="100%">
				<tr>
					<td width="400" align="left" style="font-size:1.3em;padding-left:110px;">
						<span class="butspan"><xsl:text>&#160;</xsl:text><a style="text-decoration:none;" href="/{$app}">home</a><xsl:text>&#160;</xsl:text></span>
						<!--<span class="butspan"><xsl:text>&#160;</xsl:text><a style="text-decoration:none;" href="/{$app}/asset/jake/0/multi.htm">multi</a><xsl:text>&#160;</xsl:text></span>-->
						<!--<span class="butspan"><a style="text-decoration:none;" href="/{$app}/assetlist/jake.htm">lists</a></span>-->
						<span class="butspan"><xsl:text>&#160;</xsl:text><a style="text-decoration:none;" href="/{$app}/asset/jake.htm">snapshots</a><xsl:text>&#160;</xsl:text></span>
					</td>
					<td width="300" align="right" style="font-size:1.2em;">
						<nobr><b>Crush<xsl:text>&#160;&#160;&#160;&#160;</xsl:text><input style="font-size:1.0em;" size="15" type="text" name="ticker" value="{ticker}"/></b></nobr>
					</td>
					<td colspan="2" align="left">
						<xsl:text></xsl:text><input style="font-size:1.1em;" size="7" type="text" name="end" value="{end}"/><xsl:text>&#160;&#160;</xsl:text>
						<span id="months=4" class="butspan" onclick="return onSubmit('months=4')"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></span> 
						<span id="months=6" class="butspan" onclick="return onSubmit('months=6')"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></span> 
						<span id="months=8" class="butspan" onclick="return onSubmit('months=8')"><xsl:text>&#160;</xsl:text>8m<xsl:text>&#160;</xsl:text></span> 
						<span id="years=1" class="butspan" onclick="return onSubmit('years=1')"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></span> 
						<span id="monts=18" class="butspan" onclick="return onSubmit('months=18')"><xsl:text>&#160;</xsl:text>18m<xsl:text>&#160;</xsl:text></span> 
						<span id="years=2" class="butspan" onclick="return onSubmit('years=2')"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></span> 
						<span id="chart" class="butspan" onclick="return openChart()"><xsl:text>&#160;</xsl:text>chart<xsl:text>&#160;</xsl:text></span> 
						<span id="snapshot" class="butspan" onclick="snapshot()"><xsl:text>&#160;</xsl:text>save<xsl:text>&#160;</xsl:text></span> 
					</td>
				</tr>
			</table>

  </xsl:template>
  
</xsl:stylesheet>