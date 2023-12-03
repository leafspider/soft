<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/multi" xml:space="preserve">	
								
	<html>
	  <head>
	    <title>Multi</title>
	    
			<xsl:call-template name="head" />
			
	  </head>
	
		<body class="bod">
	
			<script>
					
				document.location = "/soft/asset/jake/<xsl:value-of select="resourceId"/>/multi.htm";
					 
		  </script>
		  
		</body>
	
	</html>
	
	</xsl:template>
	
</xsl:stylesheet>