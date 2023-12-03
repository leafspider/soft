<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/multi" xml:space="preserve">	
								
	<html>
  <head>
    <title>Multi</title>

		<xsl:call-template name="head_lite" />
	    			
    <style>
    .example {
      width:625px;
      height:350px;
    }
    </style>
    		
  </head>
	
	<body>		
							
		<form class="back" style="display:;">
			
			<xsl:call-template name="menu" />			
							
			<xsl:variable name="previous">
				<xsl:choose>
					<xsl:when test="offset &gt; limit"><xsl:value-of select="offset - limit" /></xsl:when>
					<xsl:otherwise>0</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>			
			<xsl:variable name="next">
				<xsl:choose>
					<xsl:when test="offset &gt; max - limit - 2"><xsl:value-of select="max - limit" /></xsl:when>
					<xsl:otherwise><xsl:value-of select="offset + limit" /></xsl:otherwise>	
				</xsl:choose>
			</xsl:variable>
			
			<script>	  	
				var sdisplay = document.getElementById('sdisplay');
				function slider(val)
				{
					document.getElementById('sdisplay').innerText = val;
					if( val == <xsl:value-of select="offset" /> ) { document.getElementById('sdisplay').style.color = 'white'; }
					else { document.getElementById('sdisplay').style.color = 'grey'; }
				}
				function openMulti()
				{
					var val = document.getElementById('slide').value;
					document.location = "/soft/assetlist/" + collection + "/" + resourceId + "/multi.htm?offset=" + val;
				}
				
				var max = <xsl:value-of select="max" />;
				var limit = <xsl:value-of select="limit" />;
				var thisoffset = <xsl:value-of select="offset" />;
				function next() 
				{
					thisoffset += limit;
					thisoffset = Math.min(thisoffset, max);
					loadGraphs(thisoffset, false);
					slider(thisoffset);
					document.forms[0].slide.value = thisoffset;
				}
				function previous() 
				{
					thisoffset -= limit;
					thisoffset = Math.max(thisoffset, 0);
					loadGraphs(thisoffset, false);
					slider(thisoffset);
					document.forms[0].slide.value = thisoffset;
				}
				function fill()
				{
					loadGraphs(thisoffset, true);
				}				
			  function clickTicker(data, i )
			  {
				<![CDATA[	
			  	var tag = data[i].title.text;
			  	pos1 = tag.indexOf('>');
			  	if( pos1 > -1 )
			  	{
			  		pos2 = tag.indexOf('<', pos1);
		  			val = tag.substring(pos1+1, pos2);
		  		}
			  	document.forms[0].ticker.value = val;
			  	document.forms[0].ticker.focus();
			  	
			  	loadFullGraph(thisoffset, i); 	    
				]]>			
			  }  		 	
			  function openSnapshot( )
			  {
			  	var text = $(event.target).text();
			  	var vals = text.split(' ');			  	
			  	var url = '/soft/asset/' + collection + '/' + vals[0] + '/snapshot.htm?end=' + vals[1];
				  var win = window.open(url, '_blank');
				  win.focus();
			  }
				
				loadGraphs(<xsl:value-of select="offset" />, false);
				
				function loadGraphs( offset, fill )
				{
					<![CDATA[	
				    $.getJSON('/soft/assetlist/' + collection + '/' + resourceId + '/multi.data?offset=' + offset + '&fill=' + fill, function(data) {
							$('#linegraph1').highcharts(data[0]); 
							$('#linegraph2').highcharts(data[1]);
							$('#linegraph3').highcharts(data[2]);
							$('#linegraph4').highcharts(data[3]);
							$('#linegraph5').highcharts(data[4]);
							$('#linegraph6').highcharts(data[5]);														
					    $('#populate1').click(function() { clickTicker(data, 0 ); } );
					    $('#populate2').click(function() { clickTicker(data, 1 ); } );
					    $('#populate3').click(function() { clickTicker(data, 2 ); } );
					    $('#populate4').click(function() { clickTicker(data, 3 ); } );
					    $('#populate5').click(function() { clickTicker(data, 4 ); } );
					    $('#populate6').click(function() { clickTicker(data, 5 ); } );					    
					    $('tspan').click(function() { openSnapshot(); } );					    
				    });			    
					]]>			
				}

				function loadFullGraph( offset, i )
				{
					<![CDATA[	
				    $.getJSON('/soft/assetlist/' + collection + '/' + resourceId + '/multi.data?offset=' + offset + '&fill=true', function(data) {
							$('#linegraph' + (i+1)).highcharts(data[i]); 
				    });			    
					]]>			
				}
				
			</script>
												
			<div style="margin-left:10px;">
			<table border="0" width="100%" style="padding:5px 0px;">		
				<tr>
					<td border="0" align="left" style="width:33%;font-weight:bold;font-size:1.1em;padding-left:90px;padding-top:30px;padding-bottom:0px;">					
						<!--<h1><xsl:value-of select="translate(resourceId,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')" /></h1>-->
						<h1><xsl:value-of select="title" /></h1> <xsl:value-of select="end" />
					</td>
					<td align="center" style="width:33%;padding-top:15px;padding-bottom:10px;">
						<nobr>
							<input id="slide" type="range" min="0" max="{max}" step="{limit}" value="{offset}" onchange="slider(this.value)" style="width:480px;cursor:hand;" /> 
							<xsl:text>&#160;&#160;</xsl:text>
							<span id="sdisplay" class="butspan" style="color:white;vertical-align:center;font-size:1.2em;padding:3px 5px 0px;" onclick="openMulti()"><xsl:value-of select="offset" /></span>
							<span style="width:33%;font-weight:bold;font-size:1.3em;"> / <xsl:value-of select="max"/></span>
						</nobr>
					</td>
					<td style="width:33%;font-weight:bold;font-size:1.3em;padding-top:15px;padding-bottom:10px;">
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
<!--						<xsl:if test="offset &gt; -10">-->
							<span class="butspan" style="font-size:1.2em;">
								<!--<a style="text-decoration:none;padding:0px 10px;" href="/soft/assetlist/jake/{resourceId}/multi.htm?offset={$previous}">&lt;&lt;</a>-->
								<a style="text-decoration:none;padding:0px 10px;" href="javascript:previous()">&lt;&lt;</a>
							</span>
<!--						</xsl:if>
						<xsl:if test="offset = 0">
							<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						</xsl:if>-->						
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
<!--						<xsl:if test="offset &lt; (max - limit)">-->
							<span class="butspan" style="font-size:1.2em;">
								<!--<a style="text-decoration:none;padding:0px 10px;" href="/soft/assetlist/jake/{resourceId}/multi.htm?offset={$next}">&gt;&gt;</a>-->
								<a style="text-decoration:none;padding:0px 10px;" href="javascript:next()">&gt;&gt;</a>
							</span>
<!--						</xsl:if>-->
						<!--
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						<span class="butspan" style="font-size:1.2em;">
							<a style="text-decoration:none;padding:0px 10px;" href="javascript:fill()">fill</a>
						</span>
						-->
					</td>
				</tr>	
				<tr>
					<td style="padding-top:10px;">
				  	<span id="linegraph1" class="example"></span>
					</td>
					<td style="padding-top:10px;">
				  	<span id="linegraph2" class="example"></span>
					</td>
					<td style="padding-top:10px;">
				  	<span id="linegraph3" class="example"></span>
					</td>
			  </tr>	
				<tr>
					<td style="padding-top:10px;">
				  	<span id="linegraph4" class="example"></span>
					</td>
					<td style="padding-top:10px;">
				  	<span id="linegraph5" class="example"></span>
					</td>
					<td style="padding-top:10px;">
				  	<span id="linegraph6" class="example"></span>
					</td>
			  </tr>	
				<!--
				<tr>
					<td>
						<xsl:apply-templates select="crush[1]"><xsl:with-param name="pos">1</xsl:with-param></xsl:apply-templates>
					</td>
					<td>
						<xsl:apply-templates select="crush[2]"><xsl:with-param name="pos">2</xsl:with-param></xsl:apply-templates>
					</td>
					<td>
						<xsl:apply-templates select="crush[3]"><xsl:with-param name="pos">2</xsl:with-param></xsl:apply-templates>
					</td>
			  </tr>	
				<tr>
					<td>
						<xsl:apply-templates select="crush[4]"><xsl:with-param name="pos">1</xsl:with-param></xsl:apply-templates>
					</td>
					<td>
						<xsl:apply-templates select="crush[5]"><xsl:with-param name="pos">2</xsl:with-param></xsl:apply-templates>
					</td>
					<td>
						<xsl:apply-templates select="crush[6]"><xsl:with-param name="pos">2</xsl:with-param></xsl:apply-templates>
					</td>
			  </tr>	
			  -->
			</table>			
			</div>
			
		</form>
		
	  </body>
	</html>

	</xsl:template>
	
	<xsl:template match="crush" xml:space="preserve">	
		<!--<xsl:param name="pos" />-->
		<xsl:variable name="cid" select="generate-id()" />
		
  	<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
  	<span id="linegraph{$cid}" class="example" style="padding-left:0px;"></span>
							
	  <script>	  	  
	  	
  		var sta = '<xsl:value-of select="../start" />'.split('-');
	  		  	
      $(function () {
        $('#linegraph<xsl:value-of select="$cid" />').highcharts({
            title: {
            		useHTML: true,
                text: '<span id="populate{$cid}"><xsl:value-of select="ticker" /></span>',
                style: { color: '#<xsl:value-of select="cherryColor" />', cursor: 'hand' }                
            },
            series: [
            	{
	                name: 'crush',
//	                pointStart: Date.UTC(sta[0], sta[1], sta[2]),
	                data: [<xsl:value-of select="crush" />]
	            },
	            {
	               	name: 'close',
//	                pointStart: Date.UTC(sta[0], sta[1], sta[2]),
	                data: [<xsl:value-of select="close" />]
			        },
			        {
                	name: 'flow',
//	                pointStart: Date.UTC(sta[0], sta[1], sta[2]),
	                data: [<xsl:value-of select="flow" />]
			        }]
	        });
	        
			    $('#populate<xsl:value-of select="$cid" />').click(function() 
			    	{
			    		populateTickerField('<xsl:value-of select="ticker" />');
			    		
			        var chart = $('#linegraph<xsl:value-of select="$cid" />').highcharts();
			        if (chart.series.length == 3) {
			            chart.addSeries({
	                	name: 'plate',
//	                	pointStart: Date.UTC(sta[0], sta[1], sta[2]),
  									animation: true,
		                data: [<xsl:value-of select="plate" />]
			            });
			            chart.addSeries({
	               		name: 'pear',
//	                	pointStart: Date.UTC(sta[0], sta[1], sta[2]),
  									animation: true,
		                data: [<xsl:value-of select="pear" />]
			            });			            
			      }
			    });
  	  });  
  	  	  	  	 				
	  </script>
	  
	</xsl:template>
	
</xsl:stylesheet>