<html>
	<head>
		<link rel="icon" href="/soft/skins/resource/leafspider.png">
		<title>Login</title>
		<link rel="stylesheet" type="text/css" href="/soft/skins/resource/resource.css" />
	</head>
	<body>
					
			
		<div class="contentHeader">
			<span class="title">
				Login
			</span>	
		</div>
		
		<br/>
						
		<!--You are logged in as <%=request.getRemoteUser()%>-->		
		<!--ref=<%= request.getHeader("referer") %>-->
		
		<div class="sentence-Content">
			<div class="summary">

			<form action='j_security_check' method='post'>
			<table>
			 <tr><td>Name:</td>
			   <td><input type='text' name='j_username'></td></tr>
			 <tr><td>Password:</td> 
			   <td><input type='password' name='j_password' size='8'></td>
			 </tr>
			 <!--
			 <tr><td>Referer:</td> 
			   <td><input type='text' name='j_referer' size='128' value='<%= request.getHeader("referer") %>'></td>
			 </tr>
			 -->
			</table>
			<br>
			  <input type='submit' value='login'> 
			</form>

			</div>
		</div>
		
	</body>
</html>