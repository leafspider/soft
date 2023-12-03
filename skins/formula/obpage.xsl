<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/tformula" xml:space="preserve">	
								
	<html>
	  <head>
		<title>Tformula</title>

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
					<!--xsl:apply-templates select="ingredients"/-->
				</table>			
			</div>
			
			<style>
				.ingredients {margin-top:50px;margin-right:50px;}
				.indent {width:50px;}
				.ingredient-col {}
				.ingredient-row {}
				.ingredient-cell {border:1px solid black;}
				.ingredient-link {cursor:hand;}
			</style>
			
			<script>	
				var xingredients = [<xsl:value-of select="ingredients" />];

				<![CDATA[		
					function openToffeeQuery( st ) {
						open( "/soft/toffee/jake/query/grid.htm?u=jake&query=" + st );
					}
					
					var yingredients = xingredients.map((x) => x);
					xingredients.reverse();
					
					var msg = '<table class="ingredients">';
					msg += '<tr>';
					msg += '<td class="indent"></td>';
					msg += '<td></td>';
					for ( xindex in xingredients ) {
						if ( xindex == xingredients.length-1 ) { continue; }
						xingredient = xingredients[xindex];
						msg += '<td class="ingredient-col">' + xingredient + '</td>';
					}
					msg += '</tr>';
					for ( yindex in yingredients ) {
						if ( yindex == yingredients.length-1 ) { continue; }
						yingredient = yingredients[yindex];
						msg += '<tr>';
						msg += '<td class="indent"></td>';
						msg += 	'<td class="ingredient-row">' + yingredient + '</td>';
												
						for ( xindex in xingredients ) {	
							if ( xindex == xingredients.length-1 ) { continue; }						
							if ( xindex > yingredients.length - yindex - 2 ) { continue; }
							xingredient = xingredients[xindex];
							query = xingredient + " " + yingredient;
							msg += 	'<td class="ingredient-cell"><div class="ingredient-link" onclick="javascript:openToffeeQuery( \'' + query + '\' );">' + xingredient + ' / ' + yingredient + '</div></td>';
						}
						
						msg += '</tr>';
					}
					msg += '</table>';
					document.write( msg );
				]]>
			</script>
							
	  </body>
	</html>

	</xsl:template>		
					
</xsl:stylesheet>