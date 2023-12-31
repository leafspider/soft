<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/CrushRecordList" xml:space="preserve">	

	<html>
	  <head>
    	<link rel="icon" href="/soft/skins/resource/leafspider.png"/>
	    <title>Crush</title>
	
	    <style>
			table {
				font-family:arial;
			}
	    td.cell{
	    	padding-left:10px;
	    	text-align:right;
	    	width:40px;
	    }
	    </style>
	
	  </head>
	
	<body>
		
		<br/>
			
		<table width="90%">
			<tr style="font-size:1.5em;text-align:center;">
				<th><nobr><b>Crush Snapshots (<xsl:value-of select="count" />)</b></nobr></th>
			</tr>
		</table>
		
		<br/>
		
		<table width="100%">
			<tr>
				<td valign="top" align="right" width="30%"></td>
				<td valign="top" align="left" width="40%">
									
					<table>	
						<tr>
							<th align="left"></th>
							<th></th>
							<th>crush</th>
							<th>plate</th>
							<th>cherry</th>
							<th>close</th>
							<th>flow</th>
							<th>peel</th>
							<th>jam</th>
							<th>press</th>
						</tr>
						<xsl:apply-templates select="CrushRecord"/>
					</table>
		
				</td>
				<td></td>
			</tr>
		</table>
			  	  	
	  </body>
	</html>

	</xsl:template>	
	
	<xsl:template match="CrushRecord" >	
				<tr>
					<td align="left"> 						
						<a target="_blank" style="text-decoration:none;" href="/soft/asset/jake/{ticker}/crush.htm?end={endDate}&amp;start={startDate}">
							<nobr><xsl:value-of select="ticker" /> - </nobr>							
						</a>
					</td>
					<td>
						<nobr>
							<a target="_blank" style="text-decoration:none;" href="/soft/asset/jake/{ticker}/crush.htm?end={endDate}&amp;start={startDate}">
								<xsl:value-of select="startDate" /> to <xsl:value-of select="endDate" />
							</a>						
						</nobr>
					</td>
					<td class="cell">
						<xsl:value-of select="crush" />
					</td>
					<td class="cell">
						<xsl:value-of select="plate" />
					</td>
					<td class="cell">
						<xsl:value-of select="cherry" />
					</td>
					<td class="cell">
						<xsl:value-of select="close" />
					</td>
					<td class="cell">
						<xsl:value-of select="flow" />
					</td>
					<td class="cell">
						<xsl:value-of select="peel" />
					</td>
					<td class="cell">
						<xsl:value-of select="jam" />
					</td>
					<td class="cell">
						<xsl:value-of select="press" />
					</td>
				</tr>
	</xsl:template>	
	
</xsl:stylesheet>