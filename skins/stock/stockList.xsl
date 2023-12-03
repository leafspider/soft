<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:variable name="skinUrl">/soft/skins/resource/</xsl:variable>
		
	<xsl:output method="html" indent="yes" />
		
	<xsl:template match="/stockList" xml:space="preserve">	

	<html>
		<head>
			<title>Stocks</title>
			<meta http-equiv="expires" content="Fri, 26 Feb 2001 09:23:47 GMT"/>
			<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
			<style>
				.stockList td{padding:2;}
				.stock td{text-align:right;}
		    .butspan {
		    	font-weight:bold;
		    	color:blue;
		    	cursor:hand;	    	
		    	border:2px outdent;
		    	background-color:lightgrey;
		    	margin:3px;
		    	padding:2px;
		    }
	    </style>	    
		</head>	
		
		<body>
		<font face="arial">	
			
		<br/>
		
		<div class="contentHeader" style="margin-left:10px">
			<span class="title">
				Stocks: <xsl:apply-templates select="project"/> (<xsl:value-of select="count" />)
			</span>	
		</div>
		
		<br/>
			  
		<form>
			<input hidden="true" style="font-size:1.0em;" size="15" type="text" name="portfolio" value="Portfolio1"/>
			<div class="sentence-Content" style="margin-left:20px">
				<table border="1" class="stockList">
					<tr>
						<td>Ticker</td>
						<td>Price</td>
						<td>Target</td>
						<td>Projected Return</td>
						<td>... on $10k</td>
						<td>... on $20k</td>
						<td>... on $50k</td>
						<td>... on $100k</td>
						<td>... on $1M</td>
					</tr>
					<xsl:apply-templates select="stock"/>
					<tr class="stock">
						<td style="border:0;">
							<input style="font-size:1.0em;" size="3" type="text" name="ticker" value=""/>
						</td>
						<td style="border:0;">
							<input style="font-size:1.0em;" size="5" type="text" name="price" value=""/>
						</td>
						<td style="border:0;">
							<input style="font-size:1.0em;" size="5" type="text" name="target" value=""/>
						</td>
						<td style="border:0;text-align:left;">
							<span class="butspan"><a style="text-decoration:none;" href="javascript:onSubmit();"><xsl:text>&#160;</xsl:text>Save<xsl:text>&#160;</xsl:text></a></span> 
						</td>
					</tr>
						
				</table>
			</div>	
					
		  <script>	  	
			  var collection = "jake";
			  
				<![CDATA[		
				function onSubmit()
				{			
					var portfolio = document.forms[0].portfolio.value;
					var ticker = document.forms[0].ticker.value;
					var price = document.forms[0].price.value;
					var target = document.forms[0].target.value;
					var putUrl = "/soft/stock/" + collection + ".htm?portfolio=" + portfolio + "&ticker=" + ticker + "&price=" + price + "&target=" + target;
					location = putUrl;				
				}
							  
				]]>
		  </script>
		</form>
		
		</font>		
		</body>		
	</html>
					
	</xsl:template>
	
	<xsl:template match="stock" >		

			<tr class="stock">
				<td><xsl:value-of select="ticker" /></td>
				<td><xsl:value-of select="price" /></td>
				<td><xsl:value-of select="target" /></td>
				<td><xsl:value-of select='format-number(projectedReturnPercent, "###.##")' />%</td>				
				<td>$<xsl:value-of select='format-number(projectedReturn, "###,###")' /></td>				
				<td>$<xsl:value-of select='format-number(projectedReturn*2, "###,###")' /></td>			
				<td>$<xsl:value-of select='format-number(projectedReturn*5, "###,###")' /></td>			
				<td>$<xsl:value-of select='format-number(projectedReturn*10, "###,###")' /></td>			
				<td>$<xsl:value-of select='format-number(projectedReturn*100, "###,###")' /></td>			
			</tr>

	</xsl:template>

</xsl:stylesheet>
