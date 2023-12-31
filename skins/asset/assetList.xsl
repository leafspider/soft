<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/CrushRecordList" xml:space="preserve">	

	<html>
	  <head>
	    <title>
		<xsl:choose>
		      <xsl:when test="filter='watched'">
				Watchlist
		      </xsl:when>
		      <xsl:otherwise>
				History
		      </xsl:otherwise>
		 </xsl:choose>
	    </title>

			<xsl:call-template name="head" />
				
	    <style>
			table{align:center;}
			table.stockList {background-color:white;}
			.performance{width:100%;}
			.stockList th{border:1px dotted black;}
			.stockList td{border:1px dotted black;}
			.stock td{padding:4;text-align:left;}
			.stock th{padding:4;text-align:left;font-weight:normal;}
			.stockTotal td{padding:4;text-align:right;border-top:1px solid black;font-weight:bold;}
			.stockTotal th{padding:4;text-align:left;border-top:1px solid black;font-weight:bold;}
			.stockTitle td{padding:4;text-align:right;border-bottom:1px solid black;}
			.stockTitle th{padding:10px 4px;text-align:center;border-top:1px solid black;border-bottom:1px solid black;font-weight:bold;}
			td.divider {border-left:1px solid black;padding:0px 10px;border-right:1px solid black;font-size:0.8em;}
			th.divider {border-left:1px solid black;padding:4px 10px;border-right:1px solid black;}
			td.cell {text-align:left;padding:1px 5px;overflow:hidden;text-overflow:ellipsis;font-size:0.8em;}
			//.divider a:link {padding:2px 4px;text-decoration:none;border-radius:3px;background-color:white;}
			//.divider a:visited {color:black}
			//.divider a:active {color:black}
			//.divider a:hover {color:white;background-color:darkgrey;}			
	    </style>
	
	  </head>
	
		<body class="bod">
			
			<form class="back">
			
				<xsl:call-template name="menu" />
				
				<table width="100%" border="0">
					<tr style="font-size:1.3em;text-align:center;">
						<th colspan="3" style="text-align:left;padding:25px 0px 15px 100px">
							<nobr><b>
							<h1>
								<xsl:choose>
								      <xsl:when test="filter='watched'">
										Watchlist
								      </xsl:when>
								      <xsl:otherwise>
										History
								      </xsl:otherwise>
								 </xsl:choose>								
							</h1>
							</b></nobr>
						</th>
					</tr>
						
				<xsl:if test="count &gt; 0">
						<tr>
							<td valign="top" align="right" width="5%"></td>
							<td valign="top" align="left">
								
								<div class="toptail">
												
								<table class="stockList performance">	
									<tr class="stockTitle">
										<xsl:choose>
										      <xsl:when test="filter='watched'">
												<xsl:attribute name="style">background-color:#ffffe0;</xsl:attribute>     
										      </xsl:when>
										      <xsl:otherwise>
												<xsl:attribute name="style">background-color:white;</xsl:attribute>     
										      </xsl:otherwise>
										 </xsl:choose>
										<th class="divider">#</th>
										<th class="divider">Time</th>
										<!--th class="divider">Entry</th>
										<th class="divider">Amount</th-->
										<th class="divider">Instrument</th>
										<th class="divider">Crush</th>
										<th class="divider">Strategy</th>
										<!--th class="divider"><nobr>Exit</nobr></th>
										<th class="divider">Commission</th>
										<th class="divider"><nobr>P&amp;L</nobr></th-->
										<!--th class="divider">Target</th>
										<th class="divider">Strategy</th>
										<th class="divider"><nobr>5d Exit</nobr></th>
										<th class="divider"><nobr>5d P&amp;L</nobr></th>
										<th class="divider"><nobr>30d Exit</nobr></th>
										<th class="divider"><nobr>30d P&amp;L</nobr></th-->
										<!--
										<th class="divider">cherry</th>
										<th class="divider">pear</th>
										<th class="divider">plate</th>
										<th>close</th>
										<th>flow</th>
										<th>peel</th>
										<th>jam</th>
										<th>press</th>
										-->
									</tr>
									<xsl:apply-templates select="CrushRecord"/>
								</table>
					
								</div>
								
							</td>
							<td width="5%"></td>
						</tr>
				</xsl:if>

				</table>
					  	  	
			</form>
				  	  	
		</body>
		
		</html>

	</xsl:template>	
	
	<xsl:template match="CrushRecord" >	
				<tr class="stock">
					<xsl:choose>
					      <xsl:when test="watched='true'">
							<xsl:attribute name="style">background-color:#ffffe0;</xsl:attribute>     
					      </xsl:when>
					      <xsl:otherwise>
							<xsl:attribute name="style">background-color:white;</xsl:attribute>     
					      </xsl:otherwise>
					 </xsl:choose>
					<td class="divider" style="text-align:right;padding:0px 10px 0px 10px;">				
						<nobr><xsl:value-of select="id" /></nobr>
					</td>
					<td class="divider cell" style="padding:4px 10px 2px 20px;">				
						<a target="_blank" href="/soft/asset/jake/{ticker}/snapshot.htm?end={endDate}&amp;start={startDate}">
							<nobr><xsl:value-of select="tradeTime" /></nobr>
						</a>
					</td>
					<!--td class="divider cell">
						<nobr><xsl:value-of select="entry" /></nobr>
					</td>
					<td class="divider cell">
						<nobr><xsl:value-of select="amount" /></nobr>
					</td-->
					<td class="divider cell" style="padding:4px 10px 2px 20px;">				
						<nobr><xsl:value-of select="instrument" /></nobr>
					</td>
					<td class="divider cell" style="text-align:right;padding-right:20px;width:0px;">
						<xsl:value-of select="format-number(crush*100, '#0.0')" />
					</td>
					<td class="divider cell">
						<xsl:value-of select="comment" />
					</td>
					<!--td class="divider cell">
						<nobr><xsl:value-of select="exit" /></nobr>
					</td>
					<td class="divider cell">
						<nobr><xsl:value-of select="commission" /></nobr>
					</td>
					<td class="divider cell">
						<xsl:value-of select="pal" />
					</td-->	
					<!--td class="divider cell">
						<nobr><xsl:value-of select="targetPrice" /></nobr>
					</td>
					<td class="divider cell">
						<xsl:value-of select="strategy" />
					</td>
					<td class="divider cell">
						<nobr><xsl:value-of select="exit5" /></nobr>
					</td>
					<td class="divider cell">
						<xsl:value-of select="pal5" />
					</td>					
					<td class="divider cell">
						<nobr><xsl:value-of select="exit30" /></nobr>
					</td>
					<td class="divider cell">
						<xsl:value-of select="pal30" />
					</td-->									
					<!--
					<td class="cell">
						<xsl:value-of select="format-number(cherry*100, '#0.0')" />
					</td>
					<td class="cell">
						<xsl:value-of select="format-number(pear*100, '#0.0')" />
					</td>
					<td class="cell">
						<xsl:value-of select="format-number(plate*100, '#0.0')" />
					</td>
					<td class="cell">
						<xsl:value-of select="format-number(close*100, '#0.0')" />
					</td>
					<td class="cell">
						<xsl:value-of select="format-number(flow*100, '#0.0')" />
					</td>
					<td class="cell">
						<xsl:value-of select="format-number(peel*100, '#0.0')" />
					</td>
					<td class="cell">
						<xsl:value-of select="format-number(jam*100, '#0.0')" />
					</td>
					<td class="cell">
						<xsl:value-of select="format-number(press*100, '#0.0')" />
					</td>
					-->
				</tr>
	</xsl:template>	
	
</xsl:stylesheet>