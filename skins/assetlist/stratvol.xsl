<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
			
	<xsl:template match="/stratvol" xml:space="preserve">	

	<html>
		<head>
			<title>STRATvol</title>
			
			<xsl:call-template name="head" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<style> 
				.datatable {margin-left:50px;align:center;background-color:white;width:90%;font-size:0.9em;}
				.pagetitle {text-align:left;}
			</style>
		</head>	
		
	<body>
			
		<form>
				
			<xsl:call-template name="menu" />
			 
			<table border="0" width="50%">
				<tr style="font-size:1.2em;text-align:center;">
					<th colspan="2" style="text-align:left;padding:30px 0px 0px 100px"><nobr><b><h1>STRATvol</h1></b></nobr></th>
				</tr>						
				<tr>				
					<td valign="top">
						<div style="margin-right:10px;margin-left:100px;">											
							<a target="_csv" href="{csvUrl}" download="stratvol.csv">Download</a>						
						</div>							
					</td>				
				</tr>
			</table>
			
			<br/>

			<xsl:value-of select="html" disable-output-escaping="yes"/>
								
			<br/>
			
		</form>
		
		</body>		
	</html>
						
	</xsl:template>
			  
</xsl:stylesheet>
