<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/watchlist" xml:space="preserve">	

	<html>
	  <head>
	    <title>
		SOFTanalytics Performance
	    </title>
		
		<link href="/soft/skins/resource/leafspider.png" rel="icon"></link>
		<link type="text/css" rel="stylesheet" href="/soft/skins/resource/crush.css"></link>
				
	    <style>
			body {font-family:Arial, Helvetica, "DejaVu Sans", "Liberation sans", "Bitstream Vera Sans", sans-serif;background:#e5f0ff;}
			table{align:center;border-spacing:0;}
			table.stockList {background-color:white;}
			.performance{margin-left:0;width:auto;}
			.stockList th{border:1px dotted black;}
			.stockList td{border:1px dotted black;}
			.stock td{padding:4;text-align:left;}
			.stock th{padding:4;text-align:left;font-weight:normal;}
			.stockTotal td{padding:4;text-align:right;border-top:1px solid black;font-weight:bold;}
			.stockTotal th{padding:4;text-align:left;border-top:1px solid black;font-weight:bold;}
			.stockTitle td{padding:4;text-align:right;border-bottom:1px solid black;}
			.stockTitle th{padding:10px 4px;text-align:center;border-top:1px solid black;border-bottom:1px solid black;font-weight:bold;font-size:0.6em;}
			th.divider {border-left:1px solid black;padding:2px 2px;border-right:1px solid black;font-weight:bold;}
			td.divider {border-left:1px solid black;padding:2px 2px;border-right:1px solid black;font-size:1.0em;}
			td.cell {text-align:left;padding:2px 2px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;font-size:0.75em;}
			td.pchange {text-align:right;}
			.legend {padding:3px;}
			.pcolumn {display:inline-block;width:auto;vertical-align:top;margin-right:10px;margin-bottom:40px}
			h1 {font-size:1.5em;margin-bottom:10px;}
	    </style>
	
	  </head>
	
		<body class="bod">
			
			<form class="back">
			
				<table border="0">
					<tr style="font-size:1.5em;text-align:center;">
						<th colspan="2" style="text-align:left;padding:15px 100px 10px 22px">
							<nobr><b>
							<h1>
								SOFTanalytics Performance
							</h1>
							</b></nobr>
						</th>						
						<td style="text-align:right;">	
							<div style="margin:20px 10px 20px 100px;">
								<table style="text-align:center;width:100%;">
									<tr>
									  <td class="legend" style="background-color:#ff5555;"> &lt; -200% </td>
									  <td class="legend" style="background-color:#ff7777;"> &lt; -100% </td>
									  <td class="legend" style="background-color:#ff9999;"> &lt; -50% </td>
									  <td class="legend" style="background-color:#ffbbbb;"> &lt; -20% </td>
									  <td class="legend" style="background-color:#ffdddd;"> &lt; -10% </td>								  
									  <td class="legend" style="background-color:white;color:grey;"> -10 - 10% </td>					  
									  <td class="legend" style="background-color:#ddffdd;"> &gt; 10% </td>
									  <td class="legend" style="background-color:#bbffbb;"> &gt; 20% </td>
									  <td class="legend" style="background-color:#99ff99;"> &gt; 50% </td>
									  <td class="legend" style="background-color:#77ff77;"> &gt; 100% </td>
									  <td class="legend" style="background-color:#55ff55;"> &gt; 200% </td>
									</tr>
								</table>
							</div>						
						</td>
					</tr>
						
						<tr>
							<td valign="top" align="right" width="20px"></td>
							<td colspan="2" valign="top" align="left">
								
								<xsl:variable name="clen" select="42" />
								
								<div class="">
									<div class="pcolumn">
										<table class="stockList performance">	
											<tr class="stockTitle">
												<th class="divider">Date</th>
												<th class="divider">Instr</th>
												<th class="divider">Days</th>
												<th class="divider">Max%Ch</th>
											</tr>
											<xsl:apply-templates select="snapshot[position() &lt; $clen+1]"/>
										</table>

									</div>
									<div class="pcolumn">
										<table class="stockList performance">	
											<tr class="stockTitle">
												<th class="divider">Date</th>
												<th class="divider">Instr</th>
												<th class="divider">Days</th>
												<th class="divider">Max%Ch</th>
											</tr>
											<xsl:apply-templates select="snapshot[position() &gt; $clen and position() &lt; 2*$clen+1]"/>
										</table>					
									</div>								
									<div class="pcolumn">
										<table class="stockList performance">	
											<tr class="stockTitle">
												<th class="divider">Date</th>
												<th class="divider">Instr</th>
												<th class="divider">Days</th>
												<th class="divider">Max%Ch</th>
											</tr>
											<xsl:apply-templates select="snapshot[position() &gt; 2*$clen and position() &lt; 3*$clen+1]"/>
										</table>					
									</div>	
									<xsl:if	test="count(snapshot) &gt; 3*$clen">						
										<div class="pcolumn">
											<table class="stockList performance">	
												<tr class="stockTitle">
													<th class="divider">Date</th>
													<th class="divider">Instr</th>
													<th class="divider">Days</th>
													<th class="divider">Max%Ch</th>
												</tr>
												<xsl:apply-templates select="snapshot[position() &gt; 3*$clen and position() &lt; 4*$clen+1]"/>
											</table>					
										</div>			
									</xsl:if>
									<xsl:if	test="count(snapshot) &gt; 4*$clen">
										<div class="pcolumn">
											<table class="stockList performance">	
												<tr class="stockTitle">
													<th class="divider">Date</th>
													<th class="divider">Instr</th>
													<th class="divider">Days</th>
													<th class="divider">Max%Ch</th>
												</tr>
												<xsl:apply-templates select="snapshot[position() &gt; 4*$clen and position() &lt; 5*$clen+1]"/>
											</table>					
										</div>		
									</xsl:if>
									<xsl:if	test="count(snapshot) &gt; 5*$clen">
										<div class="pcolumn">
											<table class="stockList performance">	
												<tr class="stockTitle">
													<th class="divider">Date</th>
													<th class="divider">Instr</th>
													<th class="divider">Days</th>
													<th class="divider">Max%Ch</th>
												</tr>
												<xsl:apply-templates select="snapshot[position() &gt; 5*$clen and position() &lt; 6*$clen+1]"/>
											</table>					
										</div>		
									</xsl:if>
									<xsl:if	test="count(snapshot) &gt; 6*$clen">
										<div class="pcolumn" >
											<table class="stockList performance">	
												<tr class="stockTitle">
													<th class="divider">Date</th>
													<th class="divider">Instr</th>
													<th class="divider">Days</th>
													<th class="divider">Max%Ch</th>
												</tr>
												<xsl:apply-templates select="snapshot[position() &gt; 6*$clen and position() &lt; 7*$clen+1]"/>
											</table>					
										</div>		
									</xsl:if>
									<xsl:if	test="count(snapshot) &gt; 7*$clen">
										<div class="pcolumn" >
											<table class="stockList performance">	
												<tr class="stockTitle">
													<th class="divider">Date</th>
													<th class="divider">Instr</th>
													<th class="divider">Days</th>
													<th class="divider">Max%Ch</th>
												</tr>
												<xsl:apply-templates select="snapshot[position() &gt; 7*$clen and position() &lt; 8*$clen+1]"/>
											</table>					
										</div>		
									</xsl:if>
									<xsl:if	test="count(snapshot) &gt; 8*$clen">
										<div class="pcolumn" >
											<table class="stockList performance">	
												<tr class="stockTitle">
													<th class="divider">Date</th>
													<th class="divider">Instr</th>
													<th class="divider">Days</th>
													<th class="divider">Max%Ch</th>
												</tr>
												<xsl:apply-templates select="snapshot[position() &gt; 8*$clen and position() &lt; 9*$clen+1]"/>
											</table>					
										</div>		
									</xsl:if>
									<xsl:if	test="count(snapshot) &gt; 9*$clen">
										<div class="pcolumn" >
											<table class="stockList performance">	
												<tr class="stockTitle">
													<th class="divider">Date</th>
													<th class="divider">Instr</th>
													<th class="divider">Days</th>
													<th class="divider">Max%Ch</th>
												</tr>
												<xsl:apply-templates select="snapshot[position() &gt; 9*$clen and position() &lt; 10*$clen+1]"/>
											</table>					
										</div>		
									</xsl:if>
								</div>
								
							</td>
						</tr>

				</table>
					  	  	
			</form>
				  	  	
		</body>
		
		</html>

	</xsl:template>	
	
	<xsl:template match="snapshot" >	
				<tr class="stock">
						<xsl:choose>
							  <xsl:when test="pchange &gt; 200"><xsl:attribute name="style">background-color:#55ff55;</xsl:attribute></xsl:when>
							  <xsl:when test="pchange &gt; 100"><xsl:attribute name="style">background-color:#77ff77;</xsl:attribute></xsl:when>
							  <xsl:when test="pchange &gt; 50"><xsl:attribute name="style">background-color:#99ff99;</xsl:attribute></xsl:when>
							  <xsl:when test="pchange &gt; 20"><xsl:attribute name="style">background-color:#bbffbb;</xsl:attribute></xsl:when>
							  <xsl:when test="pchange &gt; 10"><xsl:attribute name="style">background-color:#ddffdd;</xsl:attribute></xsl:when>
							  <xsl:when test="pchange &lt; -200"><xsl:attribute name="style">background-color:#ff5555;</xsl:attribute></xsl:when>
							  <xsl:when test="pchange &lt; -100"><xsl:attribute name="style">background-color:#ff7777;</xsl:attribute></xsl:when>
							  <xsl:when test="pchange &lt; -50"><xsl:attribute name="style">background-color:#ff9999;</xsl:attribute></xsl:when>
							  <xsl:when test="pchange &lt; -20"><xsl:attribute name="style">background-color:#ffbbbb;</xsl:attribute></xsl:when>
							  <xsl:when test="pchange &lt; -10"><xsl:attribute name="style">background-color:#ffdddd;</xsl:attribute></xsl:when>
							  <xsl:otherwise><xsl:attribute name="style">color:grey;</xsl:attribute></xsl:otherwise>
						 </xsl:choose>
					<td class="divider cell">
						<xsl:value-of select="date" />
					</td>
					<td class="divider cell">
						<a target="_blank" href="http://finance.yahoo.com/q/bc?t=1y&amp;l=on&amp;z=l&amp;q=l&amp;p=&amp;a=&amp;c=&amp;s={ticker}">
							<xsl:choose>
								<xsl:when test="contains(ticker, '-USD.CC')">
									<xsl:value-of select="substring-before(ticker, '-USD.CC')"/>
								  </xsl:when>
								  <xsl:otherwise>
									<xsl:value-of select="ticker" />
								  </xsl:otherwise>
							</xsl:choose>
						</a>
					</td>
					<td class="divider cell">
						<xsl:value-of select="days" />
					</td>
					
					<td class="divider cell pchange">
						<xsl:value-of select="format-number(pchange, '#0.0')" />
					</td>
				</tr>
												
	</xsl:template>	
	
</xsl:stylesheet>