<html>
	<head><title>Logout</title>
		<script type="text/javascript" src="/soft/skins/resource/utils.js"></script>		
		<link rel="stylesheet" type="text/css" href="/soft/skins/resource/resource.css" />
	</head>
	<body>
					
			<link rel="stylesheet" type="text/css" href="/soft/skins/resource/mainmenu.css" />
			<link rel="shortcut icon" href="/soft/skins/resource/leafspider.png" />
			
			<div class="emptymenu" id="mainMenu">
				<ul>
	
					<li class="emptymenu"><a href="/soft"><nobr>Home</nobr></a></li>								
	
				</ul>
				<br style="clear: left;" />
			</div>
			
		<div class="contentHeader">
			<span class="title">
				Logout
			</span>	
		</div>
		
		<br/>
		
		<div class="sentence-Content">
			<div class="summary">
				
				
				<%--<%@ page session="true"%>--%>

				<%=request.getRemoteUser()%> has logged out.
				
				<% session.invalidate(); %>
					
			</div>
		</div>
		
	</body>
</html>