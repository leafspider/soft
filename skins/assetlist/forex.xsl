<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
			
	<xsl:template match="/forex" xml:space="preserve">	

	<html>
		<head>
			<title>Forex</title>
			
			<xsl:call-template name="head" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		  <style> 
		  	.qb_up { color:green; } 
		  	.qb_down { color:red; } 
		  	.qb_line { padding-right:10px; } 
		  	.qb_shad { padding-right:10px; }
 				.datatable {align:center;background-color:white;width:100%;} 
 				thead {align:left;} 
 				h3 {margin:0px;padding:5px;background-color:white;} 
 				.ds_name {border-left:dotted 1px;}
 				td[class*='ds_'] {border-top:dotted 1px;padding:5px;} 
 				th[class*='ds_'] {border-top:dotted 1px;padding:5px;} 
 				.qb_shad.noprint {border-top:dotted 1px;} 
 				.qb_line.noprint {border-top:dotted 1px;}
 				table {margin:0px;}
				.datatable a:link {color:blue;background-color:white;text-decoration:underline;border-radius:0px;}
				.datatable a:visited {color:blue;background-color:white;text-decoration:underline;border-radius:0px;}
				.datatable a:active {color:blue;background-color:white;text-decoration:underline;border-radius:0px;}
				.datatable a:hover {color:blue;background-color:white;text-decoration:underline;border-radius:0px;}
				.spacer {width:100px;}
				.pagetitle {text-align:left;}
			</style>
		</head>	
		
	<body>
			
		<form>
				
			<xsl:call-template name="menu" />
			 
			<br/>
			<br/>
			
			<xsl:value-of select='html' disable-output-escaping='yes'/>
		
		</form>
		
		</body>		
	</html>
						
	</xsl:template>
			  
</xsl:stylesheet>
