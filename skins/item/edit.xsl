<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/edit" xml:space="preserve">			
	<html>
		
	  <head>
	    <title>Create a Kube</title>			   
    	<link rel="icon" href="/scenario/skins/item/ManyCUES.ico"/> 
	    <style>
	    	th { text-align:right;vertical-align:top;font-weight:normal;}
				a{text-decoration:none;color:white;font-weight:bold;}
	    	.gobutton{font-size:0.9em;border:1 solid black;border-radius:8px;padding:0px 4px 2px 4px;color:black}
	    	body.waiting * { cursor: wait; }
    	</style>	
	  </head>		  	  
	  
		<body>	
			
			<form class="back" style="margin:50px;font-family:arial;" action="edit.htm" method="post" enctype="multipart/form-data">
				
				<input type="hidden" id="constId" name="constId"  value=""/>		
								
				<table>
					<tr><td colspan="4" style="font-weight:bold">Required</td></tr>
					
					<tr><th>Item Name</th>		<td colspan="3"><input type='text' size="100" name='name' value=''/></td></tr>
					<tr><th>Item URL</th>			<td colspan="3"><input type='text' size="100" name='url' value=''/></td></tr>
					<tr><th>Pavilion</th>					
						<td colspan="1">
							<!--<input readonly='true' type='text' size="30" name='pavilion' value='{pavilion}'/>-->
							<select name="pavilion" class="">
								<option value="{pavilion}"><xsl:value-of select="pavilion" /></option>
							</select>
						</td>
					
					<!--<tr><th>Category</th>					<td><input type='text' size="30" name='category' value='speaker'/></td></tr>-->
					
						<th style="width:5px">Category</th>					
						<td colspan="1">
							<select name="category" class="">
								<option value="exhibitor">exhibitor</option>
								<option value="company">company</option>
								<option value="keynote">keynote</option>
								<option value="speaker">speaker</option>
								<option value="expert">expert</option>
								<option value="anonymous">anonymous</option>
								<option value="product">product</option>
								<option value="stealth">stealth</option>
							</select>
						</td>
					</tr>		
		
					<!--<tr><th>Number</th>						<td><input type='text' size="30" name='place' value=''/></td></tr>-->
					<!--<tr><td colspan='2' style='border-bottom:solid black 1px;padding-top:6px;'></td></tr><tr><td colspan='2' style='padding-bottom:5px;'></td></tr>-->
					<!--<tr><th>Link to Pavilion</th>	<td><input type='text' size="30" name='pavlink' value=''/></td></tr>-->

					<tr><td colspan='4' style='border-bottom:solid black 1px;padding-top:6px;'></td></tr><tr><td colspan='4' style='padding-bottom:5px;'></td></tr>
					
					<tr><td colspan="4" style="font-weight:bold">Optional</td></tr>
					
					<tr><th>Hall</th>							
						<td colspan="1">					
							<select name="hall" class="">
								<option value=""></option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
								<option value="10">10</option>
								<option value="11">11</option>
								<option value="12">12</option>
								<option value="13">13</option>
								<option value="14">14</option>
								<option value="15">15</option>
								<option value="16">16</option>
							</select>
						</td>
						<th>Stand</th>							<td colspan="1"><input type='text' size="11" name='stand' value=''/></td>
					</tr>
					<tr><th>Cues URL</th>					<td colspan="3"><input type='text' size="100" name='cuesurl' value=''/></td></tr>
					<tr><th>Description</th>			<td colspan="3"><textarea cols='76' rows='10' name='description' value=''/></td></tr>
					<tr><th>File</th>							<td colspan="3"><input type="file" name="uploadedFile" /></td></tr>
					<tr><td colspan='4' style='border-bottom:solid black 1px;padding-top:6px;'></td></tr><tr><td colspan='4' style='padding-bottom:5px;'></td></tr>
				</table>
					
				<br/>					
				1. <a class="gobutton" href="javascript:submitForm()">Create Item Cues</a> 
				<br/><br/>							
				And once all Item Cues are created:
				<br/><br/>
				<!--2. <a class="gobutton" target="_blank" href="http://leafspider.com/{pavilion}/alerts.delete">Delete all Alerts</a>
				2. <a class="gobutton" href="javascript:processUrl('http://leafspider.com/{pavilion}/alerts.delete');">Delete all Alerts</a> -->
				2. <a class="gobutton" href="javascript:processUrl('http://localhost:8000/{pavilion}/alerts.delete');">Delete all Alerts</a> 
				<br/><br/>
				<!--3. <a class="gobutton" target="_blank" href="http://leafspider.com/{pavilion}/alerts.post">Create all Alerts</a>
				3. <a class="gobutton" href="javascript:processUrl('http://leafspider.com/{pavilion}/alerts.post')">Create all Alerts</a> -->
				3. <a class="gobutton" href="javascript:processUrl('http://localhost:8000/{pavilion}/alerts.post')">Create all Alerts</a>
				<br/><br/>
				4. <a class="gobutton" target="_blank" href="http://demo.marketstory.com/{pavilion}">Navigate</a>
				<br/><br/>				
				<span id="div_progress"/>
			</form>	
			
			<script language="javascript">
			
				<![CDATA[			
				
				function submitForm()
				{
					var msg = "";
					
					var url = document.forms[0].url.value.trim();
					if( document.forms[0].name.value.trim().length < 1 ) { msg = "A valid Item Name is required"; }
					else if( url.length < 7 ) { msg = "A valid Item URL is required, including protocol"; }
					else if( url.toLowerCase().indexOf( "http://" ) != 0 ) 
					{
						if( url.toLowerCase().indexOf( "https://" ) != 0 ) { msg = "A valid Item URL is required, including protocol"; }
					}
						
					if( msg == "" ) { document.forms[0].submit(); }
					else { alert(msg); }
				}
				
				var div_progress = document.getElementById("div_progress");																
				function processUrl(href)
				{	
//					document.body.className = "waiting";
//					div_progress.innerHTML = "Processing...";		
			  	doGet(href);			  	
				}						

				function doGet(href)
				{				
					var request;						
					if (window.XMLHttpRequest)
					{ 
						request = new XMLHttpRequest(); 
						request.onreadystatechange = function() { windowLoaded(request); }
						request.open("GET",href,false);
						request.send(null);
					}
				}

				function windowLoaded(request) 
				{							
					if(request.readyState == 4)
					{
						alert( "Done" );
//						document.body.className = "";
//						if(request.status == 200 || request.status == 304)
						{
//							div_progress.innerHTML = "";
						} 
//						else { alert("Error = " + request.status ); }
					}
				}		

				]]>
					
			</script>
						
			<iframe id="upload_target" name="upload_target" style="width:0;height:0;visibility:hidden;border:1px solid #888888;"></iframe> 
				
	  </body>	  
	</html>
	</xsl:template>
	
</xsl:stylesheet>