<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:variable name="app">soft</xsl:variable>	
	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>	
	
	<xsl:template name="head" xml:space="preserve">
		
		<link rel="icon" href="/soft/skins/resource/leafspider.png"/>    	
		<link href="{$skinUrl}crush.css" rel="stylesheet" type="text/css" />
		<script src="{$skinUrl}prototype.js" type="text/javascript" charset="utf-8"></script>
		<script src="{$skinUrl}raphael.js" type="text/javascript" charset="utf-8"></script>
		<script src="{$skinUrl}grafico.base.js" type="text/javascript" charset="utf-8"></script>
		<script src="{$skinUrl}grafico.line.js" type="text/javascript" charset="utf-8"></script>
		<script src="{$skinUrl}grafico.bar.js" type="text/javascript" charset="utf-8"></script>
		<script src="{$skinUrl}grafico.spark.js" type="text/javascript" charset="utf-8"></script>
		<script src="{$skinUrl}jquery.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="{$skinUrl}html2canvas.js" type="text/javascript" charset="utf-8"></script>
		<script src="{$skinUrl}html2canvas.svg.js" type="text/javascript" charset="utf-8"></script>
		
	</xsl:template>

	<xsl:template name="head_lite" xml:space="preserve">			

		<link rel="icon" href="/soft/skins/resource/leafspider.png"/>    	
		<link href="{$skinUrl}crush.css" rel="stylesheet" type="text/css" />
		<script src="{$skinUrl}jquery.min.js"></script>		
		<script src="{$skinUrl}highcharts.js"></script>
		<script src="{$skinUrl}gray.js"></script>
		<script src="{$skinUrl}html2canvas.js" type="text/javascript" charset="utf-8"></script>
		<script src="{$skinUrl}html2canvas.svg.js" type="text/javascript" charset="utf-8"></script>

	</xsl:template>
		
	<xsl:template name="menu" xml:space="preserve">
		
		<script>
			
	  		var app = "<xsl:value-of select="$app" />";
	  		var collection = "<xsl:value-of select="collection" />";
			if ( collection == "" ) { collection = "<xsl:value-of select="project" />"; }
			else { alert( collection ); }
			var resourceId = '<xsl:value-of select="resourceId" />';	
			if ( resourceId == "" ) { resourceId = "<xsl:value-of select="id" />"; }
			else { alert( resourceId ); }
			var start = '<xsl:value-of select="start" />';		
			var end = '<xsl:value-of select="end" />';	
			var snapshotTicker = '<xsl:value-of select="ticker" />';
						
			<![CDATA[	
				
				onloadMenu();
				function onloadMenu()
				{
					var id = "years-1";	// Default
					var href = window.location.href;
					var pos = href.lastIndexOf( "&" );
					if( pos < 0 )
					{
						pos = href.lastIndexOf( "?" );
					}
					if( pos > -1 )
					{
						id = href.substring( pos+1, href.length );
						id = id.replace("=", "-");
					}
//					console.log( id );
					
					$('#'+id).css( 'color', 'white');
					$('#'+id).css( 'border', '2px indent');
			
//					var span = document.getElementById( id );
//					span.style.color = "white";	  	
//					span.style.border = "2px indent";		    	
				}
			  
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
					var url = "/soft/asset/" + collection + "/" + tickerVal.trim() + "/crush.htm?end=" + endVal + "&" + periodVal;
//					top.location.href = url;
					open( url );
				}						
			  
			  /*
			  function populateTickerField( tag )
			  {
			  	pos1 = tag.indexOf('>');
			  	if( pos1 > -1 )
			  	{
			  		pos2 = tag.indexOf('<', pos1);
		  			val = tag.substring(pos1+1, pos2);
		  		}
			  	document.forms[0].ticker.value = val;
			  	document.forms[0].ticker.focus();
			  } 
			  */ 		 	

			  function openViper( periodVal )
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
				var url = "/soft/asset/" + collection + "/" + tickerVal.trim() + "/viper.htm?end=" + endVal + "&" + periodVal;
				//					top.location.href = url;
				open( url );
			  }
			  
			  function openChart()
			  {
				var tickerVal = document.forms[0].ticker.value;
				if ( tickerVal.length < 1 ) 
				{
					alert( "Please enter ticker." );
					return;
				}
			  	open( "http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + tickerVal.trim() );
			  }

			  function openToffee() {
			  
				var tickerVal = document.forms[0].ticker.value;
				if ( tickerVal.length < 1 ) {				
					alert( "Please enter query." );
					return;
				}
				q = tickerVal.trim();
				/*
				if ( q.indexOf( " " ) < 0 ) {
					q = "$" + q;
				}
				*/
			  	open( "/soft/toffee/jake/query/grid.htm?u=jake&query=" + q );
			  }
			  
			  function snapshot()
			  {
				var tickerVal = document.forms[0].ticker.value;
				if ( tickerVal.length < 1 ) 
				{
					alert( "Please enter ticker." );
					return;
				}				
				var targetPrice = encodeURIComponent(document.forms[0].targetPrice.value);
				var strategy = encodeURIComponent(document.forms[0].strategy.value);
			  	open( "/" + app + "/asset/jake/" + tickerVal + "/snapshot.htm?end=" + end + "&start=" + start + "&targetPrice=" + targetPrice + "&strategy=" + strategy );
			  }
			  			  
			  function saveExit()
			  {
				var tickerVal = snapshotTicker;
				var exitVal = document.forms[0].exit.value;
				/*
				if ( exitVal.length < 1 ) 
				{
					alert( "Please enter Exit." );
					return;
				}				
				var palVal = document.forms[0].pal.value;
				if ( palVal.length < 1 ) 
				{
					alert( "Please enter P&L." );
					return;
				}	
				*/			
				var exit = encodeURIComponent(document.forms[0].exit.value);
				var pal = encodeURIComponent(document.forms[0].pal.value);
				var amount = encodeURIComponent(document.forms[0].amount.value);
				var commission = encodeURIComponent(document.forms[0].commission.value);
				var exit5 = encodeURIComponent(document.forms[0].exit5.value);
				var pal5 = encodeURIComponent(document.forms[0].pal5.value);
				var exit30 = encodeURIComponent(document.forms[0].exit30.value);
				var pal30 = encodeURIComponent(document.forms[0].pal30.value);
				var strategy = encodeURIComponent(document.forms[0].strategy.value);
				var targetPrice = encodeURIComponent(document.forms[0].targetPrice.value);
				var portfolio = encodeURIComponent(document.forms[0].portfolio.value);
				var comment = encodeURIComponent(document.forms[0].comment.value);
				var watched = ""; if( document.forms[0].watched.checked == true ) { watched = encodeURIComponent(document.forms[0].watched.value); }
				var entry = encodeURIComponent(document.forms[0].entry.value);
				var instrument = encodeURIComponent(document.forms[0].instrument.value);
				
//					alert( "/" + app + "/asset/jake/" + tickerVal + "/snapshot.htm?end=" + end + "&start=" + start + "&exit=" + exit + "&pal=" + pal );	
				location.href = "/" + app + "/asset/jake/" + tickerVal + "/snapshot.htm?end=" + end + "&start=" + start + 
					"&exit=" + exit + "&pal=" + pal + 
					"&amount=" + amount + "&commission=" + commission + 
					"&exit5=" + exit5 + "&pal5=" + pal5 + 
					"&exit30=" + exit30 + "&pal30=" + pal30 + 
					"&strategy=" + strategy + "&targetPrice=" + targetPrice + 
					"&portfolio=" + portfolio + "&comment=" + comment +
					"&entry=" + entry + "&instrument=" + instrument +
					"&watched=" + watched;
			}
			  
			]]>
			  		
		</script>							
								
			<table border="0" style="width:100%;padding:5px 5px;background:black;">
				<tr>
					<td border="0" width="5" align="left" style="font-size:1.2em;padding:5px 30px 5px 100px;">
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_home" href="/{$app}">home</a><xsl:text>&#160;</xsl:text></span>
						<!-- 
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_snapshots" href="/{$app}/asset/jake.htm">Snapshots</a><xsl:text>&#160;</xsl:text></span>						
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_toffee" href="/{$app}/toffee/jake/today/grid.htm">Toffee</a><xsl:text>&#160;</xsl:text></span>						
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_mlse" class="mlink" href="/{$app}/assetlist/jake/mlse/multi.htm">MLSE</a><xsl:text>&#160;</xsl:text></span>
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_nasdaq" class="mlink" href="/{$app}/assetlist/jake/nasdaq/multi.htm">NASDAQ</a><xsl:text>&#160;</xsl:text></span>
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_dax" class="mlink" href="/{$app}/assetlist/jake/dax/multi.htm">DAX</a><xsl:text>&#160;</xsl:text></span>
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_ftse" class="mlink" href="/{$app}/assetlist/jake/ftse/multi.htm">FTSE</a><xsl:text>&#160;</xsl:text></span>
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_bovespa" class="mlink" href="/{$app}/assetlist/jake/bovespa/multi.htm">Bovespa</a><xsl:text>&#160;</xsl:text></span>
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_insider" class="mlink" href="/{$app}/assetlist/jake/insider/multi.htm">Insider</a><xsl:text>&#160;</xsl:text></span>
						<span class="butspan"><xsl:text>&#160;</xsl:text><a target="_finviz" class="mlink" href="/{$app}/assetlist/jake/finviz/multi.htm">Finviz</a><xsl:text>&#160;</xsl:text></span>
						-->
						<!--<span class="butspan"><xsl:text>&#160;</xsl:text><a style="text-decoration:none;" href="/{$app}/asset/jake/0/multi.htm">multi</a><xsl:text>&#160;</xsl:text></span>-->
						<!--<span class="butspan"><a style="text-decoration:none;" href="/{$app}/assetlist/jake.htm">lists</a></span>-->
					</td>
					<td width="30" align="right">
						<nobr>
							<b style="font-size:1.0em;color:white;padding-right:0px;">crush</b>
							<b><input style="border-radius:3px;text-align:center;font-size:0.9em;padding-left:0px;" size="8" type="text" name="ticker" value="{ticker}" placeholder="ticker"/></b>
						</nobr>
					</td>
					<td align="left">
						<nobr>
							<input placeholder="2015-04-23" style="border-radius:3px;text-align:center;margin-left:5px;font-size:0.9em;" size="8" type="text" name="end" value="{end}"/>
							<span id="months-4" class="butspan" onclick="return onSubmit('months=4')"><xsl:text>&#160;</xsl:text>4m<xsl:text>&#160;</xsl:text></span> 
							<span id="months-6" class="butspan" onclick="return onSubmit('months=6')"><xsl:text>&#160;</xsl:text>6m<xsl:text>&#160;</xsl:text></span> 
							<span id="years-1" class="butspan" onclick="return onSubmit('years=1')"><xsl:text>&#160;</xsl:text>1y<xsl:text>&#160;</xsl:text></span> 
							<span id="years-2" class="butspan" onclick="return onSubmit('years=2')"><xsl:text>&#160;</xsl:text>2y<xsl:text>&#160;</xsl:text></span> 
							<span id="viper" class="butspan" onclick="return openViper( 'years=1')"><xsl:text>&#160;</xsl:text>viper<xsl:text>&#160;</xsl:text></span> 
							<span id="chart" class="butspan" onclick="return openChart()"><xsl:text>&#160;</xsl:text>chart<xsl:text>&#160;</xsl:text></span> 
							<!--span id="copy" class="butspan"><xsl:text>&#160;</xsl:text>copy<xsl:text>&#160;</xsl:text></span-->
							<span id="toffee" class="butspan" onclick="return openToffee()"><xsl:text>&#160;</xsl:text>toffee<xsl:text>&#160;</xsl:text></span> 
							<!--<span id="snapshot" class="butspan" onclick="snapshot()"><xsl:text>&#160;</xsl:text>save<xsl:text>&#160;</xsl:text></span> -->
						</nobr>
						<div id="screenshot"></div>
						<script>
							$('#copy').click( function () {		
								var element = $('.main');						
								html2canvas(element, {
									onrendered: function(canvas) {
										$('#screenshot').append(canvas);
									}
								});
							});							
						</script>
					</td>
					<!--
					<td border="0" width="410" align="right" style="font-size:1.0em;padding-right:110px;">
					</td>
					-->
				</tr>
			</table>

  </xsl:template>
  
</xsl:stylesheet>