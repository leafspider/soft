<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
		
	<xsl:output method="html" indent="yes" />
			
	<xsl:template match="/report" xml:space="preserve">	

	<html>
		<head>
			<title><xsl:value-of select='reportTitle' /> Report</title>
			
			<xsl:call-template name="head" />
			
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		  <style> 
			table {margin:0px;}
		  	.qb_up { color:green; } 
		  	.qb_down { color:red; } 
		  	.qb_line { padding-right:10px; } 
		  	.qb_shad { padding-right:10px; }
			.datatable {align:left;background-color:white;width:100%;} 
			.datatable a:link {color:blue;background-color:white;text-decoration:underline;border-radius:0px;}
			.datatable a:visited {color:blue;background-color:white;text-decoration:underline;border-radius:0px;}
			.datatable a:active {color:blue;background-color:white;text-decoration:underline;border-radius:0px;}
			.datatable a:hover {color:blue;background-color:white;text-decoration:underline;border-radius:0px;}
			.toptail table tr td:nth-child(2) {max-width:180px;font-size:0.9em;}
			thead {align:left;} 
			h3 {margin:0px;padding:5px;background-color:white;} 
			.ds_name {border-left:dotted 1px;}
			td[class*='ds_'] {border-top:dotted 1px;padding:5px;} 
			th[class*='ds_'] {border-top:dotted 1px;padding:5px;} 
			.yfnc_tabledata1, .yfnc_tablehead1 {border-top:dotted 1px;padding:5px;}
			.qb_shad.noprint {border-top:dotted 1px;} 
			.qb_line.noprint {border-top:dotted 1px;}
			.spacer {width:100px;}
			.pagetitle {text-align:left;}
			.section_title {font-size:1.2em;border-top:1px solid !important;border-bottom:1px solid !important;}
			
			.red-down-arrow {background-color:red;}
			.red-down-green {background-color:green;}
			.hidden-md {display:none;}
			.datatable tr td {align:left;border-top:dotted 1px;padding:5px;}			
			.datatable tr td:nth-child(1) {font-size:0.9em;border-right:dotted 1px;}
			.datatable_simple tr td {font-size:0.9em;border-top:0px;padding:4px;border-right:0px !important;}			
			.datatable th {text-align:left;border-top:dotted 1px;padding:5px;} 
			th[class*='asx_sp_code'] {border-right:dotted 1px;}
			.datatable_simple th { font-size:0.4em;white-space:nowrap;}
			.yfnc_tabledata1 th {border:0px !important;}
			.yfnc_tabledata1 td:nth-child(1) {text-align:left;}
			</style>
		</head>	
		
	<body>
			
		<form>
				
			<xsl:call-template name="menu" />
			 
			<br/>
			<br/>
			
			<!--
			<table>
				<tr> 
					 <th class="spacer"></th> 
					 <th colspan="2" class="pagetitle"><h1><xsl:value-of select="reportTitle"/> - <xsl:value-of select="reportDate"/></h1></th> 
				</tr> 
				<tr>
					<td></td>
					<td>
						<div class="toptail">
							<table class="datatable">
							-->
								
								<xsl:value-of select="html" disable-output-escaping="yes"/>
								
							<!--
							</table>
						</div>
						<br/>
					</td>
				</tr>
			</table>
			-->
		
		</form>
		
		</body>		
	</html>
						
	</xsl:template>
			  
</xsl:stylesheet>
