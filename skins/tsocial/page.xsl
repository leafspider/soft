<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/tsocial" xml:space="preserve">	
								
	<html>
  <head>
    <title>Tsocial</title>

		<xsl:call-template name="head_lite" />
	    			
    <style>
	.subtitle {
		font-weight:bold;
		font-size:1.7em;
	}
    </style>
    		
  </head>
	
	<body>		
										
			<xsl:call-template name="menu" />			
																			
			<div style="margin-left:10px;">
				<table border="0" width="50%" style="padding:5px 0px;">		
					<tr>
						<td border="0" align="left" style="width:33%;font-weight:bold;font-size:1.5em;padding-left:90px;padding-top:30px;padding-bottom:0px;">					
							<div class="subtitle"><xsl:value-of select="handle" /></div>
						</td>
					</tr>
					<tr>
						<td border="0" align="left" style="width:33%;font-weight:bold;font-size:1.1em;padding-left:90px;padding-top:30px;padding-bottom:0px;">					

							<a class="twitter-timeline" href="https://twitter.com/{handle}?ref_src=twsrc%5Etfw">Tweets</a> 
							<script async="true" src="https://platform.twitter.com/widgets.js" charset="utf-8"></script>
	
						</td>
					</tr>					
				</table>			
			</div>
							
	  </body>
	</html>

	</xsl:template>
					
</xsl:stylesheet>