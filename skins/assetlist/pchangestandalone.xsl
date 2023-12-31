<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/watchlist" xml:space="preserve">	

	<html>	
	  <head>
	    <title>SOFTanalytics.io</title>
		
		<xsl:call-template name="head_lite" />
		
		<script src="{$skinUrl}gray_lite.js"></script>
		
		<link rel="shortcut icon" href="{$skinUrl}Soft_ai_fav.png"/>
	
        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
				
	    <style>
			/*body {font-family:Arial, Helvetica, "DejaVu Sans", "Liberation sans", "Bitstream Vera Sans", sans-serif;background:#e5f0ff;}*/
			body {font-family:Arial, Helvetica, "DejaVu Sans", "Liberation sans", "Bitstream Vera Sans", sans-serif;background:#ffffff;}
			table {border-spacing:0;}
			table.stockList {background-color:white;}
			.performance {margin-left:0;width:auto;}
			.stockList th {border:1px dotted black;}
			.stockList td {border:1px dotted black;}
			.stock td {padding:4;text-align:left;}
			.stock th {padding:4;text-align:left;font-weight:normal;}
			.stockTotal td {padding:4;text-align:right;border-top:1px dotted black;font-weight:bold;}
			.stockTotal th {padding:4;text-align:left;border-top:1px dotted black;font-weight:bold;}
			.stockTitle td {padding:4;text-align:right;border-bottom:1px dotted black;}
			.stockTitle th {padding:10px 4px;text-align:center;font-weight:bold;font-size:0.6em;border-top:1px dotted black;border-bottom:1px dotted black;}
			th.divider {border-left:1px dotted black;padding:2px 2px;border-right:1px dotted black;font-weight:bold;font-size:0.8em}
			td.divider {border-left:1px dotted black;padding:2px 2px;border-right:1px dotted black;font-size:1.0em;}
			td.cell {text-align:left;padding:5px 5px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;font-size:1.0em;}
			td.pchange {text-align:right;}
			.legend {padding:3px;border:1px dotted black;font-size:0.7em;}
			.pcolumn {display:inline-block;width:auto;vertical-align:top;margin-right:10px;margin-bottom:40px}
			h1 {font-size:1.5em;margin-bottom:10px;}
			a:link {color:#0000aa;}
			a:visited {color:#0000aa;}
			#highcharts-0 {height:400px!important;}
			.lgraph {margin:0px 00px 10px 0px;height:170px;width:400px;}
			.empty-row {height:10px;border:0!important;}
	    </style>
	
	  </head>
	
	<body class="bod">
					
			<div class="back">
			
				<table border="0" style="margin:0 100px;">
						
					<tr>
						<td></td>
						<td colspan="3" style="font-size:1.7em;text-align:left;padding:25px 100px 10px 0px;">
							<nobr>
							<h1 style="font-family:Gelasio,Serif;">
								SOFTanalytics<span style="color:#0082d9;">.io</span>
							</h1>
							</nobr>
						</td>
					</tr>
					
					<tr>
						<td valign="top" align="right" width="20px"></td>
						<td valign="top">
							<div style="margin-right:10px;">
								<div id="graph1" class="lgraph"></div>
								<div id="graph2" class="lgraph"></div>
								<div id="graph3" class="lgraph"></div>
								<div id="graph4" class="lgraph"></div>
								<div id="graph5" class="lgraph"></div>
							</div>
						</td>
						<td valign="top" style="padding-right:20px">
							<div style="margin-right:10px;">
								<div id="graph6" class="lgraph"></div>
								<div id="graph7" class="lgraph"></div>
								<div id="graph8" class="lgraph"></div>
								<div id="graph9" class="lgraph"></div>
								<div id="graph10" class="lgraph"></div>
							</div>
						</td>
						<td valign="top" align="left">						
							<xsl:variable name="clen" select="42" />	
							<div style="margin:0px 10px 10px 0px;">
								<table style="text-align:center;width:100%;">
									<tr>
									  <td class="legend" style="background-color:#ff5555;"> &lt;-90% </td>
									  <td class="legend" style="background-color:#ff7777;"> &lt;-70% </td>
									  <td class="legend" style="background-color:#ff9999;"> &lt;-40% </td>
									  <td class="legend" style="background-color:#ffbbbb;"> &lt;-20% </td>
									  <td class="legend" style="background-color:#ffdddd;"> &lt;-10% </td>								  
									  <td class="legend" style="background-color:white;color:grey;"> &lt;0% </td>					  
									  <td class="legend" style="background-color:white;color:grey;"> &gt;0% </td>					  
									  <td class="legend" style="background-color:#ddffdd;"> &gt;10% </td>
									  <td class="legend" style="background-color:#bbffbb;"> &gt;20% </td>
									  <td class="legend" style="background-color:#99ff99;"> &gt;50% </td>
									  <td class="legend" style="background-color:#77ff77;"> &gt;100% </td>
									  <td class="legend" style="background-color:#55ff55;"> &gt;200% </td>
									  <td class="legend" style="background-color:#00ff00;"> &gt;400% </td>
									</tr>
								</table>
							</div>						
							<div id="results_div"/>								
						</td>
					</tr>
						
				</table>
									  	  	
			</div>
									
			<script>	 				
				var collection = 'jake';
				var resourceId = 'watchlist';			
				
				function clickTicker(data, i ) {
					<![CDATA[	
					var tag = data[i].title.text;
					pos1 = tag.indexOf('>');
					if( pos1 > -1 ) {
						pos2 = tag.indexOf('<', pos1);
						ticker = tag.substring(pos1+1, pos2);
					}	
					var url = "https://finance.yahoo.com/chart/" + ticker;
					console.log(url);
					var win = window.open(url, '_blank');
					win.focus();   
					]]>			
				}  		
				
				loadGraphs(0, 10, false);
				
				function loadGraphs( offset, limit, fill ) {
					<![CDATA[	
				    $.getJSON('/soft/assetlist/' + collection + '/' + resourceId + '/multi.data?offset=' + offset + '&limit=' + limit + '&fill=' + fill, function(data) {
							//console.dir(data);
							for ( i=0; i<limit-offset; i++) {
								$('#graph'+(i+1)).highcharts(data[i]); 
								$('#populate'+(i+1)).click(function() { clickTicker(data, i ); } );
							}
					});			    
					]]>			
				}
				
			</script>
			
			<script>
			
			/* <![CDATA[ */
			
			function heat(val) {
				
				if ( val < -90 ) { return "#ff5555"; }
				else if ( val < -70 ) { return "#ff7777"; }
				else if ( val < -40 ) { return "#ff9999"; }
				else if ( val < -20 ) { return "#ffbbbb"; }				
				else if ( val < -20 ) { return "#ffdddd"; }
				else if ( val < -10 ) { return "#ffdddd"; }
				else if ( val > 400 ) { return "#00ff00"; }
				else if ( val > 200 ) { return "#55ff55"; }
				else if ( val > 100 ) { return "#77ff77"; }
				else if ( val > 50 ) { return "#99ff99"; }
				else if ( val > 20 ) { return "#bbffbb"; }
				else if ( val > 10 ) { return "#ddffdd"; }
				else { return "white"; }				
			}
			
			var arrlen;
			
			function colmarkup(hits) {
				var res = "";
				for (i=0;i<hits.length;i++) { 
					res += '<tr class="stock">';
					res += hitmarkup(hits[i]);
					res += '</tr>'; 
				}
				return res;
			}

			function hitmarkup(hit) {
				console.dir(hit);
				pchange = parseFloat(hit['pchange']).toFixed(1);
				var hot = heat(pchange);
				var white = 'white';
				var res = 	'<td class="divider cell" style="background-color:' + white + '">' + hit['date'] + '</td>'; 
				var ticker = hit['ticker'];
				var url = "https://finance.yahoo.com/chart/" + ticker;				
				res += '<td class="divider cell" style="background-color:' + white + '">';
				res += 	'<a target="_blank" href="' + url + '">' + ticker.replace('-USD.CC','') + '</a>';
				res += '</td>'; 
				res += 	'<td class="divider cell" style="background-color:' + white + '">' + hit['days'] + '</td>'; 
				res += 	'<td class="divider cell pchange" style="background-color:' + hot + '">' + pchange + '</td>'; 
				return res;		
			}
					
			function loadData() {
			
				url = "pchangestandalone.json";
				
				axios.get(url)
				.then(function (response) {
					
					var results = response.data.snapshot;
					if ( !Array.isArray(results) ) { results = new Array(results); }
					//console.dir(results);
					arrlen = (results.length/2) + 1;
					
					var hits1 = results.slice(0,arrlen);
					var hits2 = results.slice(arrlen,results.length);
					hits2.reverse();

					//console.dir(hits1);
					//console.dir(hits2);
					
					var htm = '<div class="pcolumn" >';
					htm += '<table class="stockList performance">';
					htm += '<tr class="stockTitle">';
					htm += '<th class="divider">Date</th>';
					htm += '<th class="divider">Instr</th>';
					htm += '<th class="divider">Days</th>';
					htm += '<th class="divider">Max%Ch</th>';
					htm += '</tr>';
					htm += '<tr><td class="empty-row"/></tr>';
					htm += colmarkup(hits1);
					htm += '</table>';					
					htm += '</div>';

					htm += '<div class="pcolumn" >';
					htm += '<table class="stockList performance">';
					htm += '<tr class="stockTitle">';
					htm += '<th class="divider">Date</th>';
					htm += '<th class="divider">Instr</th>';
					htm += '<th class="divider">Days</th>';
					htm += '<th class="divider">Max%Ch</th>';
					htm += '</tr>';
					htm += '<tr><td class="empty-row"/></tr>';
					htm += colmarkup(hits2);
					htm += '</table>';					
					htm += '</div>';
					
					this.results_div.innerHTML = htm;

				})
				.catch(function (error) { console.log(error); })
			}
			
			loadData();			

			/* ]]> */

			</script>
				  	  	
		</body>
		
		</html>

	</xsl:template>	
		
</xsl:stylesheet>