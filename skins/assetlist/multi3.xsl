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
      width:620px;
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
			
<!--						<span id="slider" style="font-size:1.3em;font-weight:bold;padding-right:75px;">-->
						
			<table border="0" width="100%" style="padding:10px 5px;">		
				<tr>
					<td border="0" align="right" style="font-weight:bold;font-size:1.3em;padding-left:110px;padding-bottom:10px;">					
						<xsl:value-of select="translate(resourceId,'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')" />														
						<xsl:text>&#160;&#160;&#160;</xsl:text>
					</td>
					<td align="center" style="padding-bottom:10px;">
						<input id="slide" type="range" min="0" max="{max}" step="{limit}" value="{offset}" onchange="slider(this.value)" style="width:520px;cursor:hand;" /> 
						<xsl:text>&#160;&#160;</xsl:text>
						<span id="sdisplay" class="butspan" style="vertical-align:center;font-size:1.2em;padding:3px 5px 0px;" onclick="openMulti()"><xsl:value-of select="offset" /></span>
					</td>
					<td style="font-weight:bold;font-size:1.3em;padding-bottom:10px;">
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						<xsl:if test="offset &gt; 0">
							<span class="butspan" style="font-size:1.2em;">
								<a style="text-decoration:none;padding:0px 10px;" href="/soft/assetlist/jake/{resourceId}/multi.htm?offset={$previous}">&lt;&lt;</a>
							</span>
						</xsl:if>
						<xsl:if test="offset = 0">
							<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						</xsl:if>
						<xsl:text>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;</xsl:text>
						<xsl:if test="offset &lt; (max - limit)">
							<span class="butspan" style="font-size:1.2em;">
								<a style="text-decoration:none;padding:0px 10px;" href="/soft/assetlist/jake/{resourceId}/multi.htm?offset={$next}">&gt;&gt;</a>
							</span>
						</xsl:if>
					</td>
				</tr>	
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
			</table>			
			
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