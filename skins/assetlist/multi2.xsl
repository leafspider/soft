<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/multi" xml:space="preserve">	
								
	<html>
	  <head>
	    <title>Multi</title>
	
			<xsl:call-template name="head" />
		    			
	    <style>
	    .example {
	      width:700px;
	      height:370px;
	    }
	    </style>
	    
  </head>
	
	<body class="bod">
		
			
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
//				alert(sdisplay);
				function slider(val)
				{
//				alert(val);
					document.getElementById('sdisplay').innerText = val;
				}
				function openMulti()
				{
					var val = document.getElementById('slide').value;
					document.location = "/soft/assetlist/" + collection + "/<xsl:value-of select="resourceId" />/multi.htm?offset=" + val;
				}
			</script>
						
			<table border="0" width="100%" style="padding:0px 10px;">								
				<tr>
					<td/>
					<td align="left">
						<xsl:if test="offset &gt; 0">
							<span class="butspan"><xsl:text>&#160;</xsl:text><a style="text-decoration:none;font-size:1.3em;" href="/soft/assetlist/jake/{resourceId}/multi.htm?offset={$previous}">&lt; prev</a><xsl:text>&#160;</xsl:text></span>
						</xsl:if>
						<xsl:text>&#160;&#160;</xsl:text>
					</td>
					<td colspan="2">									
						<div id="slider" align="left" style="font-size:1.3em;padding-left:210px;font-weight:bold;">
							<xsl:value-of select="translate(resourceId,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')" />
							<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
							<input id="slide" type="range" min="0" max="{max}" step="1" value="{offset}" onchange="slider(this.value)" style="width:700px;cursor:hand;" /> 
							<xsl:text>&#160;&#160;</xsl:text>
							<span id="sdisplay" class="butspan" style="vertical-align:center;font-size:0.8em;padding:0px 5px;" onclick="openMulti()"><xsl:value-of select="offset" /></span>
						</div> 						
					</td>
					<td align="center">
						<xsl:text>&#160;&#160;</xsl:text>
						<xsl:if test="offset &lt; (max - limit)">
							<span class="butspan"><xsl:text>&#160;</xsl:text><a style="text-decoration:none;font-size:1.3em;" href="/soft/assetlist/jake/{resourceId}/multi.htm?offset={$next}">next &gt;</a><xsl:text>&#160;</xsl:text></span>
						</xsl:if>
					</td>	
					<td/>
			  </tr>
				<tr>
					<td style="padding-left:100px;"/>
					<xsl:apply-templates select="crush[1]"><xsl:with-param name="pos">1</xsl:with-param></xsl:apply-templates>
					<xsl:apply-templates select="crush[2]"><xsl:with-param name="pos">2</xsl:with-param></xsl:apply-templates>
					<td/>
			  </tr>	
				<tr>
					<td/>
					<xsl:apply-templates select="crush[3]"><xsl:with-param name="pos">1</xsl:with-param></xsl:apply-templates>
					<xsl:apply-templates select="crush[4]"><xsl:with-param name="pos">2</xsl:with-param></xsl:apply-templates>
					<td style="padding-right:100px;"/>
			  </tr>	
			</table>			
			
		</form>
		
	  </body>
	</html>

	</xsl:template>
	
	<xsl:template match="crush" xml:space="preserve">	
		<xsl:param name="pos" />
		<xsl:variable name="cid" select="generate-id()" />
							
			<xsl:if test="$pos = 2">
				<td width="45%" align="left">
			  	<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
			  	<div id="linegraph{$cid}" class="example" style="padding-left:0px;cursor:hand;"></div>
			  </td>
			</xsl:if>
			<td width="10" align="center" style="padding:0px 0px;">
				<!-- onclick="populateTickerField('{ticker}');fill{$cid}();" -->
				<!--<span id="populate{$cid}" class="butspan" style="color:black;font-size:1.3em" ><xsl:text>&#160;</xsl:text><xsl:value-of select="ticker" /><xsl:text>&#160;</xsl:text></span><br/>-->
				<!--<span id="populate{$cid}" style="color:black;font-size:0.5em"><xsl:text>&#160;</xsl:text><xsl:value-of select="ticker" /></span>-->
				<!--
				<span class="butspan"><xsl:text>&#160;</xsl:text><a style="border-radius:5px;text-decoration:none;" href="javascript:fill{$cid}();" target="_blank">data</a><xsl:text>&#160;</xsl:text></span><br/>
				<br/>
				-->
				<!--
				<xsl:text>&#160;</xsl:text><span style="font-weight:bold;color:#ff6600;">crush</span><br/>
				<xsl:text>&#160;</xsl:text><span style="font-weight:bold;color:black;">close</span><br/>
				<xsl:text>&#160;</xsl:text><span style="font-weight:bold;color:grey;">flow</span><br/>
				<xsl:text>&#160;</xsl:text><span style="font-weight:bold;color:blue;">plate</span><br/>
				<xsl:text>&#160;</xsl:text><span style="font-weight:bold;color:lightgreen;">pear</span><br/>
				<xsl:text>&#160;</xsl:text><span style="border-radius:5px;font-weight:bold;color:#efefff;background-color:{cherryColor}">cherry</span><br/>
				-->
				<!--
				<br/>
				<span class="butspan"><xsl:text>&#160;</xsl:text><a style="border-radius:5px;text-decoration:none;" href="/soft/asset/jake/{ticker}/snapshot.htm?end={../end}&amp;start={../start}" target="_blank">snapshot</a><xsl:text>&#160;</xsl:text></span>
				-->
			</td>
			<xsl:if test="$pos = 1">
				<td width="45%" align="right">
			  	<span style="text-style:bold;color:red;font-size:1.2em;"><xsl:value-of select="error" /></span>
			  	<div id="linegraph{$cid}" class="example" style="padding-left:0px;cursor:hand;"></div>
			  </td>
			</xsl:if>			
							
	  <script>	  	  
	  	
	  	var sta = '<xsl:value-of select="../start" />'.split('-');
//	                pointStart: Date.UTC(2006, 0, 01),
	  	
      $(function () {
        $('#linegraph<xsl:value-of select="$cid" />').highcharts({
            title: {
            		useHTML: true,
                text: '<span id="populate{$cid}"><xsl:value-of select="ticker" /></span>',
                style: { color: '#<xsl:value-of select="cherryColor" />' }                
            },
            series: [
            	{
	                name: 'crush',
	                pointStart: Date.UTC(sta[0], sta[1], sta[2]),
	                data: [<xsl:value-of select="crush" />]
	            },
	            {
	               	name: 'close',
	                pointStart: Date.UTC(sta[0], sta[1], sta[2]),
	                data: [<xsl:value-of select="close" />]
			        },
			        {
                	name: 'flow',
	                pointStart: Date.UTC(sta[0], sta[1], sta[2]),
	                data: [<xsl:value-of select="flow" />]
			        }]
			        

	        });
	        
			    $('#populate<xsl:value-of select="$cid" />').click(function() 
			    	{
			    		populateTickerField('<xsl:value-of select="$cid" />');
			    		
			        var chart = $('#linegraph<xsl:value-of select="$cid" />').highcharts();
			        if (chart.series.length == 3) {
			            chart.addSeries({
	                	name: 'plate',
	                	pointStart: Date.UTC(sta[0], sta[1], sta[2]),
		                data: [<xsl:value-of select="plate" />]
			            });
			            chart.addSeries({
	               		name: 'pear',
	                	pointStart: Date.UTC(sta[0], sta[1], sta[2]),
		                data: [<xsl:value-of select="pear" />]
			            });			            
			      }
			    });
  	  });  
  	  
		<!--
	  Event.observe(window, 'load', function() 
	  {
	    var linegraph<xsl:value-of select="$cid" /> = new Grafico.LineGraph($('linegraph<xsl:value-of select="$cid" />'),
		    {
		    	ghost:[1.0],
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
				document.forms[0].style.display = "";
	  });
	  
	  function fill<xsl:value-of select="$cid" />()
	  {
	  		var divid = 'linegraph<xsl:value-of select="$cid" />';
	  		var div = document.getElementById( divid );
	  		div.innerHTML = "";
	  			  		
  	    var linegraph = new Grafico.LineGraph($(divid),
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
		}
		-->
	  	  	 				
	  </script>
	  
	</xsl:template>
	
</xsl:stylesheet>