<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:include href="../resource/global.xsl" />
	
	<xsl:output method="html" indent="yes" />
	
	<xsl:template match="/watchlist" xml:space="preserve">	

	<html>
	  <head>
	  	
		<link href="/soft/skins/resource/leafspider.png" rel="icon"></link>
		<link type="text/css" rel="stylesheet" href="/soft/skins/resource/crush.css"></link>
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
				
	    <style>
			/*body {font-family:Arial, Helvetica, "DejaVu Sans", "Liberation sans", "Bitstream Vera Sans", sans-serif;background:#e5f0ff;}*/
			body {font-family:Arial, Helvetica, "DejaVu Sans", "Liberation sans", "Bitstream Vera Sans", sans-serif;background:#ffffff;}
			table {border-spacing:0;}
			table.stockList {background-color:white;}
			.performance {margin-left:0;width:auto;}
			.stockList th {border:1px dotted black;}
			.stockList td {border:1px dotted black;}
			.stock td {padding:4;text-align:left;}
			.stock th {padding:4;text-align:left;font-weight:normal;}
			.stockTotal td {padding:4;text-align:right;border-top:1px solid black;font-weight:bold;}
			.stockTotal th {padding:4;text-align:left;border-top:1px solid black;font-weight:bold;}
			.stockTitle td {padding:4;text-align:right;border-bottom:1px solid black;}
			.stockTitle th {padding:10px 4px;text-align:center;border-top:1px solid black;border-bottom:1px solid black;font-weight:bold;font-size:0.6em;}
			th.divider {border-left:1px solid black;padding:2px 2px;border-right:1px solid black;font-weight:bold;font-size:0.8em}
			td.divider {border-left:1px solid black;padding:2px 2px;border-right:1px solid black;font-size:1.0em;}
			td.cell {text-align:left;padding:5px 5px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;font-size:1.4em;}
			td.pchange {text-align:right;}
			.legend {padding:3px;border:1px dotted black;}
			.pcolumn {display:inline-block;width:auto;vertical-align:top;margin-right:10px;margin-bottom:40px}
			h1 {font-size:1.5em;margin-bottom:10px;}
			a:link {color:#0000aa;}
			a:visited {color:#0000aa;}
	    </style>

	<meta charset="UTF-8" />

	<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
	<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
	<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
	<!--[if gte IE 9 ]><html class="no-js ie9" lang="en"> <![endif]-->

	<title>SOFTanalytics</title>

	<meta name="description" content="The fastest and most lucrative financial analytics/AI trading platform" />

	<!--[if lt IE 9]>
	<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />

    <link rel="shortcut icon" href="https://softai.io/wp-content/uploads/2021/01/Soft_ai_fav.png" />
	<link rel='dns-prefetch' href='//s.w.org' />
		<script type="text/javascript">
			/* <![CDATA[ */
			window._wpemojiSettings = {"baseUrl":"https:\/\/s.w.org\/images\/core\/emoji\/12.0.0-1\/72x72\/","ext":".png","svgUrl":"https:\/\/s.w.org\/images\/core\/emoji\/12.0.0-1\/svg\/","svgExt":".svg","source":{"concatemoji":"https:\/\/softai.io\/wp-includes\/js\/wp-emoji-release.min.js?ver=5.4.2"}};
			/*! This file is auto-generated */
			!function(e,a,t){var r,n,o,i,p=a.createElement("canvas"),s=p.getContext&&p.getContext("2d");function c(e,t){var a=String.fromCharCode;s.clearRect(0,0,p.width,p.height),s.fillText(a.apply(this,e),0,0);var r=p.toDataURL();return s.clearRect(0,0,p.width,p.height),s.fillText(a.apply(this,t),0,0),r===p.toDataURL()}function l(e){if(!s||!s.fillText)return!1;switch(s.textBaseline="top",s.font="600 32px Arial",e){case"flag":return!c([127987,65039,8205,9895,65039],[127987,65039,8203,9895,65039])&&(!c([55356,56826,55356,56819],[55356,56826,8203,55356,56819])&&!c([55356,57332,56128,56423,56128,56418,56128,56421,56128,56430,56128,56423,56128,56447],[55356,57332,8203,56128,56423,8203,56128,56418,8203,56128,56421,8203,56128,56430,8203,56128,56423,8203,56128,56447]));case"emoji":return!c([55357,56424,55356,57342,8205,55358,56605,8205,55357,56424,55356,57340],[55357,56424,55356,57342,8203,55358,56605,8203,55357,56424,55356,57340])}return!1}function d(e){var t=a.createElement("script");t.src=e,t.defer=t.type="text/javascript",a.getElementsByTagName("head")[0].appendChild(t)}for(i=Array("flag","emoji"),t.supports={everything:!0,everythingExceptFlag:!0},o=0;o<i.length;o++)t.supports[i[o]]=l(i[o]),t.supports.everything=t.supports.everything&&t.supports[i[o]],"flag"!==i[o]&&(t.supports.everythingExceptFlag=t.supports.everythingExceptFlag&&t.supports[i[o]]);t.supports.everythingExceptFlag=t.supports.everythingExceptFlag&&!t.supports.flag,t.DOMReady=!1,t.readyCallback=function(){t.DOMReady=!0},t.supports.everything||(n=function(){t.readyCallback()},a.addEventListener?(a.addEventListener("DOMContentLoaded",n,!1),e.addEventListener("load",n,!1)):(e.attachEvent("onload",n),a.attachEvent("onreadystatechange",function(){"complete"===a.readyState&&t.readyCallback()})),(r=t.source||{}).concatemoji?d(r.concatemoji):r.wpemoji&&r.twemoji&&(d(r.twemoji),d(r.wpemoji)))}(window,document,window._wpemojiSettings);
			/* ]]> */
		</script>
		<style type="text/css">
img.wp-smiley,
img.emoji {
	display: inline !important;
	border: none !important;
	box-shadow: none !important;
	height: 1em !important;
	width: 1em !important;
	margin: 0 .07em !important;
	vertical-align: -0.1em !important;
	background: none !important;
	padding: 0 !important;
}
</style>

</head>



<body class="home page-template page-template-p-elementor-page page-template-p-elementor-page-php page page-id-138 elementor-default elementor-kit-1960 elementor-page elementor-page-138"><!-- the Body  -->
<div id="global-wrapper" class="w-100 _vh-100 m-auto position-relative amin-width overflow-hidden">

	<!-- HIDDEN SIDEBAR -->
	<div id="hidden-sidebar" class="position-fixed anim-all shadow-wrap opens-right">

			<!-- global row: HIDDEN SIDEBAR HEADER -->
			<div class="hidden-sidebar-header container">
				<div class="row b-b-grey-10">
					<div class="col py-2 d-flex align-items-center anim-pad flex-grow-1">        <div class="logo-link">        <a href="https://softai.io">
            <img src="https://softai.io/wp-content/uploads/2021/01/Soft_ai_logo.png" class="attachment-full size-full " alt="Soft_ai_logo" /></a></div></div>
					<div class="col d-flex align-items-center justify-content-end hidden-sidebar-close-btn">
						<div class="icon--close float-right d-inline-block cursor-pointer"></div>
					</div>
				</div>
			</div>

			<!-- global row: HIDDEN SIDEBAR CONTENT -->
			<div class="hidden-sidebar-content _fill-height">
				<div class="hidden-sidebar-content-inner"><div class="accordion-menu"><ul id="menu-main-menu" class="menu"><li id="menu-item-1970" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1970 accordion"><a href="https://softai.io/strategy/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Strategy"><p class="label">Strategy</p><p class="accordion-header"><span></span></p></a></li>
<li id="menu-item-1971" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1971 accordion"><a href="https://softai.io/team/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Team"><p class="label">Team</p><p class="accordion-header"><span></span></p></a></li>
<li id="menu-item-1969" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1969 accordion"><a href="https://softai.io/technology/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Technology"><p class="label">Technology</p><p class="accordion-header"><span></span></p></a></li>
<li id="menu-item-1972" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-1972 accordion"><a href="https://softai.io/engagement/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Engagement"><p class="label">Engagement</p><p class="accordion-header"><span></span></p></a></li>
</ul></div><button type="button" class="btn btn-primary btn-lg d-inline-block ml-3 mt-4" data-toggle="modal" data-target="#contact-form">
        Contact Us
    </button></div>
			</div>
		</div>

	<!-- VISIBLE CONTENT -->
	<div class="global-inner w-100 _vh-100 m-auto position-relative anim-all">

		<!-- GLOBAL HEADER -->
		<nav id="layout-header" class="sticky-header w-100">
		        <div id="header-content" class="header-content">
        <div class="shadow-wrap w-100 amin-width px-3">
            <div class="container amin-width px-0">
                <div class="row">

                    <div class="col col-lg-3 d-flex align-items-center py-2 anim-pad">        <div class="logo-link">        <a href="https://softai.io">
            <img src="https://softai.io/wp-content/uploads/2021/01/Soft_ai_logo.png" class="attachment-full size-full " alt="Soft_ai_logo" /></a></div></div>



                    <div class="col d-none d-md-flex align-items-center justify-content-end">

                        <div id="main-menu" class="d-none d-lg-inline-block">
                            <!--  the Menu -->
                            <div class="menu-main-menu-container"><ul id="menu-main-menu-1" class="menu"><li id="menu-item-1970" class="menu-item menu-item-type-post_type menu-item-object-page"><a href="https://softai.io/strategy/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Strategy">Strategy</a></li>
<li id="menu-item-1971" class="menu-item menu-item-type-post_type menu-item-object-page"><a href="https://softai.io/team/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Team">Team</a></li>
<li id="menu-item-1969" class="menu-item menu-item-type-post_type menu-item-object-page"><a href="https://softai.io/technology/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Technology">Technology</a></li>
<li id="menu-item-1972" class="menu-item menu-item-type-post_type menu-item-object-page"><a href="https://softai.io/engagement/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Engagement">Engagement</a></li>
</ul></div>                        </div>
                        <button type="button" class="btn btn-primary btn-lg d-none d-md-inline-block mx-4 mr-md-0" data-toggle="modal" data-target="#contact-form">
                            Contact Us
                        </button>
                    </div>



                    <div class="col d-flex align-items-center justify-content-end max-w-50  d-lg-none">
                        <div class="icon--menu hidden-sidebar-trigger float-right d-inline-block cursor-pointer"></div>
                    </div>
                </div>
            </div>
        </div>
        </div></nav>

		<!-- PAGE CONTENT -->
		<div id="layout-content" class="sf-content">



					<div data-elementor-type="wp-page" data-elementor-id="138" class="elementor elementor-138" data-elementor-settings="[]">
						<div class="elementor-inner">
							<div class="elementor-section-wrap">

				<section class="elementor-section elementor-top-section elementor-element elementor-element-38821f6 fw-inner elementor-section-content-middle elementor-section-boxed elementor-section-height-default elementor-section-height-default" data-id="38821f6" data-element_type="section" data-settings="">
						<div class="elementor-container elementor-column-gap-default">
							<div class="elementor-row">
					<div class="elementor-column elementor-col-50 elementor-top-column elementor-element elementor-element-94de868 no-padding el-justify-col-content-md-end" data-id="94de868" data-element_type="column" data-settings="">
			<div class="elementor-column-wrap elementor-element-populated">
							<div class="elementor-widget-wrap">
						<section class="elementor-section elementor-inner-section elementor-element elementor-element-85564e5 elementor-section-content-middle container-2 left px-3 pr-md-0 pl-md-4 pl-lg-5 mr-md-4 mr-lg-5  elementor-section-boxed elementor-section-height-default elementor-section-height-default" data-id="85564e5" data-element_type="section" data-settings="">
						<div class="elementor-container elementor-column-gap-default">
							<div class="elementor-row">
					<div class="elementor-column elementor-col-100 elementor-inner-column elementor-element elementor-element-69380cf py-4 py-sm-5 py-md-6" data-id="69380cf" data-element_type="column">
			<div class="elementor-column-wrap elementor-element-populated">
							<div class="elementor-widget-wrap">
						<div class="elementor-element elementor-element-18e51be text-white h-light elementor-widget elementor-widget-text-editor" data-id="18e51be" data-element_type="widget" data-widget_type="text-editor.default">
				<div class="elementor-widget-container">
					<div class="elementor-text-editor elementor-clearfix"><h2 class="h-display h-display-v2 d-inline-block" style="text-align: left;">Engagement</h2><p>Data is an exponentially increasing in volume, velocity and variety. In fact, data is becoming a new natural resource. It promises to be for the 21st century what steam power was for the 18th, electricity for the 19th and hydrocarbons for the 20th. Our current engagement model is focused on working with Family Wealth Practices to maximize.</p></div>
				</div>
				</div>
				<div class="elementor-element elementor-element-fb2ad31 elementor-widget elementor-widget-button" data-id="fb2ad31" data-element_type="widget" data-widget_type="button.default">
				<div class="elementor-widget-container">
					<div class="elementor-button-wrapper">
			<a href="https://softai.io/engagement/" class="elementor-button-link elementor-button elementor-size-sm" role="button">
						<span class="elementor-button-content-wrapper">
						<span class="elementor-button-text">Learn More</span>
		</span>
					</a>
		</div>
				</div>
				</div>
						</div>
					</div>
		</div>
								</div>
					</div>
</section>
						</div>
					</div>
		</div>
				<div class="elementor-column elementor-col-50 elementor-top-column elementor-element elementor-element-3c96b9b elementor-hidden-phone" data-id="3c96b9b" data-element_type="column">
			<div class="elementor-column-wrap">
							<div class="elementor-widget-wrap">
							
							
										
			<div class="back">
			
				<table border="0" style="margin:0 100px;">
					<tr style="font-size:1.5em;text-align:center;">
						<th colspan="2" style="text-align:left;padding:25px 100px 0px 22px">
							<nobr><b>
							<h1>
								SOFTanalytics Performance
							</h1>
							</b></nobr>
						</th>		
					</tr>
						
					<tr>						
						<td colspan="2" style="text-align:right;">	
							<div style="margin:20px 10px 20px 22px;">
								<table style="text-align:center;width:100%;">
									<tr>
									  <td class="legend" style="background-color:#ff5555;"> &lt;-90% </td>
									  <td class="legend" style="background-color:#ff7777;"> &lt;-70% </td>
									  <td class="legend" style="background-color:#ff9999;"> &lt;-50% </td>
									  <td class="legend" style="background-color:#ffbbbb;"> &lt;-20% </td>
									  <td class="legend" style="background-color:#ffdddd;"> &lt;-10% </td>								  
									  <td class="legend" style="background-color:white;color:grey;"> -10% - 10% </td>					  
									  <td class="legend" style="background-color:#ddffdd;"> &gt;10% </td>
									  <td class="legend" style="background-color:#bbffbb;"> &gt;20% </td>
									  <td class="legend" style="background-color:#99ff99;"> &gt;50% </td>
									  <td class="legend" style="background-color:#77ff77;"> &gt;100% </td>
									  <td class="legend" style="background-color:#55ff55;"> &gt;200% </td>
									  <td class="legend" style="background-color:#00ff00;"> &gt;400% </td>
									</tr>
								</table>
							</div>						
						</td>
					</tr>
					
					<tr>
						<td valign="top" align="right" width="20px"></td>
						<td colspan="2" valign="top" align="left">						
							<xsl:variable name="clen" select="42" />
							<div id="results_div"/>								
						</td>
					</tr>
						
				</table>
									  	  	
			</div>
			
			<script>
			
			/* <![CDATA[ */
			
			function heat(val) {
				
				if ( val < -90 ) { return "#ff5555"; }
				else if ( val < -70 ) { return "#ff7777"; }
				else if ( val < -50 ) { return "#ff9999"; }
				else if ( val < -20 ) { return "#ffbbbb"; }				
				else if ( val < -20 ) { return "#ffdddd"; }
				else if ( val < -10 ) { return "#ffdddd"; }
				else if ( val > 400 ) { return "#00ff00"; }
				else if ( val > 200 ) { return "#55ff55"; }
				else if ( val > 100 ) { return "#77ff77"; }
				else if ( val > 50 ) { return "#99ff99"; }
				else if ( val > 20 ) { return "#bbffbb"; }
				else if ( val > 10 ) { return "#ddffdd"; }
				else { return "white"; }				
			}
			
			var arrlen;
			
			function colmarkup(hits) {
				var res = "";
				for (i=0;i<arrlen;i++) { 
					res += '<tr class="stock">';
					res += hitmarkup(hits[i]);
					res += '</tr>'; 
				}
				return res;
			}

			function hitmarkup(hit) {
				pchange = parseFloat(hit['pchange']).toFixed(1);
				var res = 	'<td class="divider cell" style="background-color:' + heat(pchange) + '">' + hit['date'] + '</td>'; 
				var ticker = hit['ticker'];			
				res += '<td class="divider cell" style="background-color:' + heat(pchange) + '">';
				res += 	'<a target="_blank" href="http://finance.yahoo.com/q/bc?t=1y&amp;l=on&amp;z=l&amp;q=l&amp;p=&amp;a=&amp;c=&amp;s=' + ticker + '">' + ticker.replace('-USD.CC','') + '</a>';
				res += '</td>'; 
				res += 	'<td class="divider cell" style="background-color:' + heat(pchange) + '">' + hit['days'] + '</td>'; 
				res += 	'<td class="divider cell pchange" style="background-color:' + heat(pchange) + '">' + pchange + '</td>'; 
				return res;				
			}
					
			function loadData() {
			
				url = "pchangestandalone.json";
				
				axios.get(url)
				.then(function (response) {
					
					var results = response.data.snapshot;
					arrlen = results.length/2;
					
					var hits1 = results.slice(0,arrlen);
					var hits2 = results.slice(arrlen,results.length);
					hits2.reverse();

					console.dir(hits1);
					
					var htm = '<div class="pcolumn" >';
					htm += '<table class="stockList performance">';
					htm += '<tr class="stockTitle">';
					htm += '<th class="divider">Date</th>';
					htm += '<th class="divider">Instr</th>';
					htm += '<th class="divider">Days</th>';
					htm += '<th class="divider">Max%Ch</th>';
					htm += '</tr>';
					htm += colmarkup(hits1);
					htm += '</table>';					
					htm += '</div>';

					htm += '<div class="pcolumn" >';
					htm += '<table class="stockList performance">';
					htm += '<tr class="stockTitle">';
					htm += '<th class="divider">Date</th>';
					htm += '<th class="divider">Instr</th>';
					htm += '<th class="divider">Days</th>';
					htm += '<th class="divider">Max%Ch</th>';
					htm += '</tr>';
					htm += colmarkup(hits2);
					htm += '</table>';					
					htm += '</div>';
					
					this.results_div.innerHTML = htm;

				})
				.catch(function (error) { console.log(error); })
			}
			
			loadData();			

			/* ]]> */

			</script>							
							
							
								</div>
					</div>
		</div>
								</div>
					</div>
		</section>
		
						</div>
						</div>
					</div>
				</div>


		<!-- GLOBAL FOOTER -->
		<div id="layout-footer" class="row sf-footer mx-0">
				<div id="footer-content" class="col px-0 text-white bg-grey-90">

			<div id="footer-main" class="w-100 px-3">
				<div class="container px-0">
					<div class="row text-center text-md-left py-5 mx-0 mx-md-n4">
						<div class="col-12 col-md-4 px-4">
							        <div class="logo-link">        <a href="https://softai.io">
            <img src="https://softai.io/wp-content/uploads/2021/01/Soft_ai_logo.png" class="attachment-full size-full " alt="Soft_ai_logo" /></a></div>							<p><br/><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#contact-form">
								Get In Touch
							</button></p>
						</div>


						<div class="col-12 col-md-4 my-4 my-md-0 px-3 px-md-4 py-4 py-md-0 border-dark b-solid by-1 bx-0 bx-md-1 by-md-0">
							<h4>Contact Details</h4>
							<p>360 Bay St, Suite 999,
								<br/>Toronto, Canada, M5H 2V6
								<br/>Tel: (+1) 416 342 5622</p>
						</div>


						<div class="col-12 col-md-4 px-3 px-md-4">
							<h4>Site Map</h4>
							<div class="menu-site-map-container"><ul id="menu-site-map" class="menu"><li id="menu-item-146" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-home current-menu-item page_item page-item-138 current_page_item"><a href="https://softai.io/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Home">Home</a></li>
<li id="menu-item-2048" class="menu-item menu-item-type-post_type menu-item-object-page"><a href="https://softai.io/strategy/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Strategy">Strategy</a></li>
<li id="menu-item-2049" class="menu-item menu-item-type-post_type menu-item-object-page"><a href="https://softai.io/team/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Team">Team</a></li>
<li id="menu-item-2047" class="menu-item menu-item-type-post_type menu-item-object-page"><a href="https://softai.io/technology/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Technology">Technology</a></li>
<li id="menu-item-2050" class="menu-item menu-item-type-post_type menu-item-object-page"><a href="https://softai.io/engagement/" class="cw-cnm-item" data-event="true" data-event-val="wp-nav: Engagement">Engagement</a></li>
</ul></div>						</div>
					</div>
				</div>
			</div>



			<div id="sub-footer" class="w-100 px-3 p-2 bg-grey-95">
				<div class="container px-0">
					<div class="row">

						<div class="col-xs-12 col-sm text-center text-sm-left"><div class="menu-sub-footer-container"><ul id="menu-sub-footer" class="menu"><li id="menu-item-2262" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-2262"><a href="https://softai.io/legal/">Legal</a></li>
<li id="menu-item-2263" class="menu-item menu-item-type-post_type menu-item-object-page menu-item-2263"><a href="https://softai.io/privacy-policy/">Privacy</a></li>
</ul></div></div>
						<div class="col-xs-12 col-sm-7">
							<div id="copyright" class="w-100 text-center text-sm-right">
								<p class="sub-footer-text">© Softai Inc. 2021</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div></div>	</div>
</div>
<div class="modal " id="contact-form">
		<div class="modal-dialog  h-100 d-flex flex-column justify-content-center my-0 py-3">
			<div class="modal-content shadow-wrap-2">

				<!-- Modal Header -->
				<div class="modal-header p-0">
					<div class="container">
						<div class="row p-0">
							<div class="col d-flex align-items-center">
								<h4 class="modal-title py-3">Softai Inc</h4>
							</div>
							<div class="col max-w-100 d-flex align-items-center justify-content-end">
								<div class="close py-3 b-l-grey-25 cursor-pointer" data-dismiss="modal">times</div>
							</div>
						</div>
					</div>
				</div>


				<div class="responsive-scrolling-modal-content">
					<!-- Modal body -->
					<div class="modal-body p-3">
						<div class="p-3"><noscript class="ninja-forms-noscript-message">
    Notice: JavaScript is required for this content.</noscript><div id="nf-form-1-cont" class="nf-form-cont" aria-live="polite" aria-labelledby="nf-form-title-1" aria-describedby="nf-form-errors-1" role="form">

    <div class="nf-loading-spinner"></div>

</div>

        </div>					</div>


					<!-- Modal footer -->
<!--                    <div class="modal-footer">-->
<!--                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>-->
<!--                    </div>-->
				</div>

			</div>
		</div>
		</div></body>

<!-- WP FOOTER-->
<link rel="stylesheet" href="https://softai.io/wp-content/cache/minify/fe6f1.css" media="all" />
















<script src="https://softai.io/wp-content/cache/minify/4d1f2.js"></script>

<script type='text/javascript'>
window.lodash = _.noConflict();
</script>




<script src="https://softai.io/wp-content/cache/minify/3f3c4.js"></script>

<script type='text/javascript'>
/* <![CDATA[ */
var nfi18n = {"ninjaForms":"Ninja Forms","changeEmailErrorMsg":"Please enter a valid email address!","confirmFieldErrorMsg":"These fields must match!","fieldNumberNumMinError":"Number Min Error","fieldNumberNumMaxError":"Number Max Error","fieldNumberIncrementBy":"Please increment by ","fieldTextareaRTEInsertLink":"Insert Link","fieldTextareaRTEInsertMedia":"Insert Media","fieldTextareaRTESelectAFile":"Select a file","formErrorsCorrectErrors":"Please correct errors before submitting this form.","validateRequiredField":"This is a required field.","honeypotHoneypotError":"Honeypot Error","fileUploadOldCodeFileUploadInProgress":"File Upload in Progress.","fileUploadOldCodeFileUpload":"FILE UPLOAD","currencySymbol":"","fieldsMarkedRequired":"Fields marked with an <span class=\"ninja-forms-req-symbol\">*<\/span> are required","thousands_sep":",","decimal_point":".","dateFormat":"m\/d\/Y","startOfWeek":"1","of":"of","previousMonth":"Previous Month","nextMonth":"Next Month","months":["January","February","March","April","May","June","July","August","September","October","November","December"],"monthsShort":["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"],"weekdays":["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"],"weekdaysShort":["Sun","Mon","Tue","Wed","Thu","Fri","Sat"],"weekdaysMin":["Su","Mo","Tu","We","Th","Fr","Sa"]};
var nfFrontEnd = {"adminAjax":"https:\/\/softai.io\/wp-admin\/admin-ajax.php","ajaxNonce":"590cf59449","requireBaseUrl":"https:\/\/softai.io\/wp-content\/plugins\/ninja-forms\/assets\/js\/","use_merge_tags":{"user":{"address":"address","textbox":"textbox","button":"button","checkbox":"checkbox","city":"city","confirm":"confirm","date":"date","email":"email","firstname":"firstname","html":"html","hidden":"hidden","lastname":"lastname","listcheckbox":"listcheckbox","listcountry":"listcountry","listmultiselect":"listmultiselect","listradio":"listradio","listselect":"listselect","liststate":"liststate","note":"note","number":"number","password":"password","passwordconfirm":"passwordconfirm","product":"product","quantity":"quantity","recaptcha":"recaptcha","shipping":"shipping","spam":"spam","starrating":"starrating","submit":"submit","terms":"terms","textarea":"textarea","total":"total","unknown":"unknown","zip":"zip","hr":"hr"},"post":{"address":"address","textbox":"textbox","button":"button","checkbox":"checkbox","city":"city","confirm":"confirm","date":"date","email":"email","firstname":"firstname","html":"html","hidden":"hidden","lastname":"lastname","listcheckbox":"listcheckbox","listcountry":"listcountry","listmultiselect":"listmultiselect","listradio":"listradio","listselect":"listselect","liststate":"liststate","note":"note","number":"number","password":"password","passwordconfirm":"passwordconfirm","product":"product","quantity":"quantity","recaptcha":"recaptcha","shipping":"shipping","spam":"spam","starrating":"starrating","submit":"submit","terms":"terms","textarea":"textarea","total":"total","unknown":"unknown","zip":"zip","hr":"hr"},"system":{"address":"address","textbox":"textbox","button":"button","checkbox":"checkbox","city":"city","confirm":"confirm","date":"date","email":"email","firstname":"firstname","html":"html","hidden":"hidden","lastname":"lastname","listcheckbox":"listcheckbox","listcountry":"listcountry","listmultiselect":"listmultiselect","listradio":"listradio","listselect":"listselect","liststate":"liststate","note":"note","number":"number","password":"password","passwordconfirm":"passwordconfirm","product":"product","quantity":"quantity","recaptcha":"recaptcha","shipping":"shipping","spam":"spam","starrating":"starrating","submit":"submit","terms":"terms","textarea":"textarea","total":"total","unknown":"unknown","zip":"zip","hr":"hr"},"fields":{"address":"address","textbox":"textbox","button":"button","checkbox":"checkbox","city":"city","confirm":"confirm","date":"date","email":"email","firstname":"firstname","html":"html","hidden":"hidden","lastname":"lastname","listcheckbox":"listcheckbox","listcountry":"listcountry","listmultiselect":"listmultiselect","listradio":"listradio","listselect":"listselect","liststate":"liststate","note":"note","number":"number","password":"password","passwordconfirm":"passwordconfirm","product":"product","quantity":"quantity","recaptcha":"recaptcha","shipping":"shipping","spam":"spam","starrating":"starrating","submit":"submit","terms":"terms","textarea":"textarea","total":"total","unknown":"unknown","zip":"zip","hr":"hr"},"calculations":{"html":"html","hidden":"hidden","note":"note","unknown":"unknown"}},"opinionated_styles":""};
/* ]]> */
</script>


<script src="https://softai.io/wp-content/cache/minify/77254.js"></script>

<script type='text/javascript'>
var ElementorProFrontendConfig = {"ajaxurl":"https:\/\/softai.io\/wp-admin\/admin-ajax.php","nonce":"c3eced5519","shareButtonsNetworks":{"facebook":{"title":"Facebook","has_counter":true},"twitter":{"title":"Twitter"},"google":{"title":"Google+","has_counter":true},"linkedin":{"title":"LinkedIn","has_counter":true},"pinterest":{"title":"Pinterest","has_counter":true},"reddit":{"title":"Reddit","has_counter":true},"vk":{"title":"VK","has_counter":true},"odnoklassniki":{"title":"OK","has_counter":true},"tumblr":{"title":"Tumblr"},"delicious":{"title":"Delicious"},"digg":{"title":"Digg"},"skype":{"title":"Skype"},"stumbleupon":{"title":"StumbleUpon","has_counter":true},"telegram":{"title":"Telegram"},"pocket":{"title":"Pocket","has_counter":true},"xing":{"title":"XING","has_counter":true},"whatsapp":{"title":"WhatsApp"},"email":{"title":"Email"},"print":{"title":"Print"}},"facebook_sdk":{"lang":"en_US","app_id":""}};
</script>





<script src="https://softai.io/wp-content/cache/minify/f60ea.js"></script>

<script type='text/javascript'>
var elementorFrontendConfig = {"environmentMode":{"edit":false,"wpPreview":false},"i18n":{"shareOnFacebook":"Share on Facebook","shareOnTwitter":"Share on Twitter","pinIt":"Pin it","download":"Download","downloadImage":"Download image","fullscreen":"Fullscreen","zoom":"Zoom","share":"Share","playVideo":"Play Video","previous":"Previous","next":"Next","close":"Close"},"is_rtl":false,"breakpoints":{"xs":0,"sm":480,"md":768,"lg":1025,"xl":1440,"xxl":1600},"version":"3.0.16","is_static":false,"legacyMode":{"elementWrappers":true},"urls":{"assets":"https:\/\/softai.io\/wp-content\/plugins\/elementor\/assets\/"},"settings":{"page":[],"editorPreferences":[]},"kit":{"global_image_lightbox":"yes","lightbox_enable_counter":"yes","lightbox_enable_fullscreen":"yes","lightbox_enable_zoom":"yes","lightbox_enable_share":"yes","lightbox_title_src":"title","lightbox_description_src":"description"},"post":{"id":138,"title":"Softai%20Inc.%20%E2%80%93%20The%20fastest%20and%20most%20lucrative%20financial%20analytics%2FAI%20trading%20platform","excerpt":"","featuredImage":false}};
</script>
<script src="https://softai.io/wp-content/cache/minify/9f110.js"></script>

<script>
/* <![CDATA[ */
			jQuery(function($) {

				/**
				 *
				 * Checks for public Lazy_Loader instance
				 * && calls lazy-load logic
				 */
				function infinite_scroll_lazy_load(){

					if(typeof lazy_loader !== 'undefined') {
						setTimeout(function(){
							lazy_loader.reset_references();
							lazy_loader.lazy_watch();
						},100)
					}
				};

				if($('.infinite-scroll').length && $('.pagination a.next').length){

				    // @ref: https://infinite-scroll.com/options.html
					let infinite_scroll = $('.infinite-scroll').infiniteScroll({
						append: '.list-item',
						path: '.pagination a.next',
						prefill: true,
						hideNav: '.pagination',
						status: '.content-load-status',
						// debug: true,
						// history: false
					});

					infinite_scroll.on( 'load.infiniteScroll', function( event, response ) {

						infinite_scroll_lazy_load();
					});
				}


                // @ref: https://infinite-scroll.com/options.html
				if($('.load-more').length && $('.pagination a.next').length) {
					let load_more = $('.load-more').infiniteScroll({
						button: '.load-more-btn',
						loadOnScroll: false,

						append: '.list-item',
						path: '.pagination a.next',
						prefill: true,
						hideNav: '.pagination',
						status: '.content-load-status',
						// debug: true,
						// history: 'push'
					});


					load_more.on('load.infiniteScroll', function (event, response) {

						infinite_scroll_lazy_load();

						// // parse JSON
						// var data = JSON.parse( response );
						//
						// // do something with JSON...
					});
				}

		jQuery(function($) {


			var synced_sliders = []; // array to store & group synced slider references

			var slider_common_params = {
				elements: {
					// slider          : slider,
					slides_wrap: '.slides-wrap',
					slides: '.slides',
					slide: '.slide',

					slider_options: '.slider-options',
					slider_option: '.slider-option',
					slider_option_text: '.slider-option-text',
					slider_option_image:'.slider-option-img',
					slider_nav: '.slider-nav',

					slider_arrows: '.slider-arrows',
					slider_arrow: '.slider-arrow',
					slide_prev: '.slide-prev',
					slide_next: '.slide-next',

					slider_caption: '.slider-caption',
					slide_caption: '.slide-caption',

					/* .fade-wrapper opacity transitions must match the duration set in the front-end js configs */
					fade_wrapper: '.fade-wrapper',
					// scroll_content: '#content-block'
				},
				limit_slide_height: 400, // int || false
				// full_screen : {
				//     enabled : true,
				//     bottom : 70
				// },
				// transition: 'none', // [fade, slide, none(default) ]
				desktop_display_count: 1,
				// ui : {
				//     // slides_arrows : [
				//     //     'desktop,mobile'
				//     // ],
				//     // nav_arrows : [
				//     //     'desktop','mobile'
				//     // ],
				//     // slider_caption : [
				//     //     'desktop','mobile'
				//     // ]
				// },
				// dynamic_layout: dynamic_layout,
				slider_interval: 7500,
				pause_interval: 15000,
				/* .fade-wrapper opacity transitions must match the duration set in the front-end js configs */
				animation_dur: 400 // must be set for animations to work
			};
			// EVERY slider instantiated should have a corresponding unique param set (even if it's empty)
			var slider_unqiue_params = {
				"basic-slider": {
					elements: {},
					ui: {
						slides_arrows: [
							'desktop',
							// 'mobile'
						],
						nav_arrows: [
							// 'desktop',
							'mobile'
						],
						slider_caption: [
							'desktop',
							// 'mobile'
						]
					}
				},
				"gallery-slider": {
					elements: {},
					limit_slide_height: 700, // int ||
					desktop_display_count: 1,
					ui: {
						slides_arrows: [
							'desktop', 'mobile'
						]
					}
				},
				"modal-slider": {
					elements: {},
					limit_slide_height: 700, // int ||
					desktop_display_count: 1,
					ui: {
						slides_arrows: [
							'desktop', 'mobile'
						]
					}
				},

				// todo: how to do this from SG?
				"sg-slider": {
					elements: {},
					limit_slide_height: 700, // int ||
					desktop_display_count: 1,
					ui: {
						slides_arrows: [
							'desktop', 'mobile'
						]
					}
				},




				"basic-meta-group-slider": {
					elements: {},
					ui: {
						nav_arrows: [
							'desktop', 'mobile'
						],
						slider_caption: [
							'desktop', 'mobile'
						]
					}
				},
				"gallery-meta-group-slider": {
					elements: {},
					ui: {
						nav_arrows: [
							'desktop', 'mobile'
						],
						slider_caption: [
							'desktop', 'mobile'
						]
					}
				},

				"hero-slider": {
					// slider_sync : {
					//     is_enabled : true,
					//     sliders : synced_sliders
					// },
					elements: {},
					limit_slide_height: false, // int || false
					full_screen: {
						enabled: true,
						bottom: 0,//
						// mobile_bottom: -80,
						subtract: [
							'.peek-sizer',
							'#hp-hero-svcc + #sub-hero'
						]
					},
					desktop_display_count: 1,
					ui: {
						slides_arrows: [
							'desktop',
							'mobile'
						],
						slider_caption: [
							'mobile',
							'desktop'
						],
						// slider_option_text: [],
						// slider_option_image: [
						//     'mobile',
						//     'desktop'
						// ],
					}
				},
				"hero-smart-slider": {
					// slider_sync : {
					//     is_enabled : true,
					//     sliders : synced_sliders
					// },
					elements: {},
					limit_slide_height: false, // int || false
					full_screen: {
						enabled: true,
						bottom: 0,//
						// mobile_bottom: -80,
						subtract: ['.peek-sizer']
					},
					desktop_display_count: 1,
					ui: {
						slides_arrows: [
							'desktop', 'mobile'
						],
						// slider_caption: [
						//     'mobile',
						//     'desktop'
						// ],
						// slider_option_text: [],
						slider_option_image: [
							'mobile',
							'desktop'
						],
					},
					/* .fade-wrapper opacity transitions must match the duration set in the front-end js configs */
					animation_dur: 800, // must be set for animations to work
					intro_fade_in: 900
				},
				"cpt-slider": {
					// slider_sync : {
					//     is_enabled : true,
					//     sliders : synced_sliders
					// },
					elements: {},
					ui: {
						// nav_arrows : [
						//     // 'desktop',
						//     // 'mobile'
						// ],
						slides_arrows: [
							'desktop',
							'mobile'
						]
					}
				},
				"cpt-slider-testimonials": {
					// slider_sync : {
					//     is_enabled : true,
					//     sliders : synced_sliders
					// },
					elements: {},
					ui: {
						// nav_arrows : [
						//     // 'desktop',
						//     // 'mobile'
						// ],
						slides_arrows: [
							'desktop',
							'mobile'
						]
					}
				},

				//.......................
				// Supports a **SINGLE** instance of a :
				// - popup-slider | popup-meta-group-slider
				//..............................................
				"popup-slider": {
					elements: {},
					is_in_popup: true,
					ui: {
						slides_arrows: [
							'desktop', 'mobile'
						]
					}
				},
				"popup-meta-group-slider": {
					elements: {},
					is_in_popup: true,
					ui: {
						slides_arrows: [
							'desktop', 'mobile'
						],
						slider_caption: [
							'desktop', 'mobile'
						]
					}
				},

				//.......................
				// Supports a **MULTIPLE** instances & types of Popup Slider
				//..............................................
				// ... PHP: basic_meta_group_slider_popup( 'meta_groups__metal_finishes', 'Metal Finishes', true );
				"meta_groups__zc1_gallery": {
					elements: {},
					is_in_popup: true,
					ui: {
						slides_arrows: [
							'desktop', 'mobile'
						],
						slider_caption: [
							'desktop', 'mobile'
						]
					}
				},
				// ... PHP: basic_meta_group_slider_popup( 'meta_groups__glass_finishes', 'Glass Finishes', true );
				"meta_groups__zc2_gallery": {
					elements: {},
					is_in_popup: true,
					ui: {
						slides_arrows: [
							'desktop', 'mobile'
						],
						slider_caption: [
							'desktop', 'mobile'
						]
					}
				},

				// "meta_groups__metal_finishes": {
				//     elements: {},
				//     is_in_popup: true,
				//     ui: {
				//         slides_arrows: [
				//             'desktop', 'mobile'
				//         ],
				//         slider_caption: [
				//             'desktop', 'mobile'
				//         ]
				//     }
				// },
				// "meta_groups__glass_finishes": {
				//     elements: {},
				//     is_in_popup: true,
				//     ui: {
				//         slides_arrows: [
				//             'desktop', 'mobile'
				//         ],
				//         slider_caption: [
				//             'desktop', 'mobile'
				//         ]
				//     }
				// }
			};

			var slider_collection = instantiate_sliders('.slider', slider_common_params, slider_unqiue_params);


			if (slider_collection['hero-slider']) {
				slider_collection['hero-slider'].reset_ui_display(10);
			}



			var modal_slider_triggers = $('.the-gallery .image-wrapper');


			if( modal_slider_triggers.length){
				for(var x = 0; x < modal_slider_triggers.length; x++){

					var ms_trigger      = $( modal_slider_triggers[x] );

					// console.log(control_key, control_index, control_uid, target_slider);


					ms_trigger.click( function(){

						var //trigger_id      = this.attr('data-id'),
							target_modal    = $( $(this).attr('data-target') ),
							target_slider   = $( target_modal.find('.slider') ),
							// BUILD control_uid
							control_key     = target_slider.attr('data-control-key'),
							control_index   = target_slider.attr('data-control-index'),
							control_uid     = control_key + control_index, // todo "-"
							// FETCH control instance
							slider_instance = slider_collection[ control_uid ];

						// console.log($(this).attr('data-id'));

						slider_instance.toggle_slide( $(this).attr('data-id') );
						slider_instance.update_ui();
					});
				}
			}
			// console.log(slider_collection);


			// if ($('.basic-slider').length){
			//     var basic_slider = new Slider({
			//         elements : {
			//             slider          : '.basic-slider',
			//             slides_wrap     : '.slides-wrap',
			//             slides          : '.slides',
			//             slide           : '.slide',
			//
			//             slider_options  : '.slider-options',
			//             slider_option   : '.slider-option',
			//             slider_nav      : '.slider-nav',
			//
			//             slider_arrows	: '.slider-arrows',
			//             slider_arrow	: '.slider-arrow',
			//             slide_prev		: '.slide-prev',
			//             slide_next		: '.slide-next',
			//
			//             slider_caption  : '.slider-caption',
			//             slide_caption   : '.slide-caption',
			//
			//             /* .fade-wrapper opacity transitions must match the duration set in the front-end js configs */
			//             fade_wrapper: '.fade-wrapper',
			//         },
			//         limit_slide_height : 400, // int || false
			//         // full_screen : {
			//         //     enabled : true,
			//         //     bottom : 70
			//         // },
			//         transition : 'none', // [fade, slide, none(default) ]
			//         desktop_display_count : 1,
			//         ui : {
			//             // slides_arrows : [
			//             //     'desktop,mobile'
			//             // ],
			//             nav_arrows : [
			//                 'desktop','mobile'
			//             ],
			//             slider_caption : [
			//                 'desktop','mobile'
			//             ]
			//         },
			//         // dynamic_layout : dynamic_layout,
			//         slider_interval: 7500,
			//         pause_interval: 15000,
			//         /* .fade-wrapper opacity transitions must match the duration set in the front-end js configs */
			//         animation_dur: 400
			//     });
			// }
		});

				jQuery(function ($) {
					function toggle_collapse_trigger__label(trigger){


						let $trigger = $(trigger),
							$target = $($trigger.attr('data-target')),
							toggle_label = $target.hasClass('show') ? 'Show Filters' : 'Hide Filters';

						if($target.hasClass('loaded')){
							toggle_label = $target.hasClass('show') ? 'Hide Filters' : 'Show Filters';
							$target.removeClass('loaded');
						}

						$trigger.html(toggle_label);
					}
					$(document).ready(function(){
						toggle_collapse_trigger__label('.toggle-filters');
					});

					$('.toggle-filters').click(function(){
						toggle_collapse_trigger__label(this);
					});

				});


				document.addEventListener("DOMContentLoaded", function() {

				    var add_parameters_el = document.getElementById('add_parameters');

				    if(add_parameters_el == null) return;

					document.getElementById('add_parameters').addEventListener('click', parametrize);

					function parametrize() {
						let ui = document.getElementById('parameters');
						let current_url = 'https://softai.io';
						let sort_url = ui.getAttribute('data-sort-url');
						let url = sort_url != null ? sort_url : current_url;



						let params = {};
						document.querySelectorAll('#parameters input.select-selected').forEach((element) => {

							// let sort_url_mod = element.getAttribute('data-sort-url-mod');

							if (element.value.length > 0 && sort_url_mod == null)
								params[element.id] = element.value;

							if (sort_url_mod != null)
								alert(sort_url_mod);
						});
						document.querySelectorAll('#parameters input.checkbox-selected').forEach((element) => {
							params[element.id] = 0;
							if (element.checked)
								params[element.id] = 1;
						});
						document.querySelectorAll('#parameters select.select-selected').forEach((element) => {
							if (element.value.length > 0)
								params[element.id] = element.value;
						});
						let esc = encodeURIComponent;
						let query = Object.keys(params)
							.map(k => esc(k) + '=' + esc(params[k]))
							.join('&');
						url += '?' + query;


						window.location.href = String(url);
					}


				})
			/* ]]> */

			</script>
			<script id="tmpl-nf-layout" type="text/template">
	<span id="nf-form-title-{{{ data.id }}}" class="nf-form-title">
		{{{ ( 1 == data.settings.show_title ) ? '<h3>' + data.settings.title + '</h3>' : '' }}}
	</span>
	<div class="nf-form-wrap ninja-forms-form-wrap">
		<div class="nf-response-msg"></div>
		<div class="nf-debug-msg"></div>
		<div class="nf-before-form"></div>
		<div class="nf-form-layout"></div>
		<div class="nf-after-form"></div>
	</div>
</script>

<script id="tmpl-nf-empty" type="text/template">

</script>
<script id="tmpl-nf-before-form" type="text/template">
	{{{ data.beforeForm }}}
</script><script id="tmpl-nf-after-form" type="text/template">
	{{{ data.afterForm }}}
</script><script id="tmpl-nf-before-fields" type="text/template">
    <div class="nf-form-fields-required">{{{ data.renderFieldsMarkedRequired() }}}</div>
    {{{ data.beforeFields }}}
</script><script id="tmpl-nf-after-fields" type="text/template">
    {{{ data.afterFields }}}
    <div id="nf-form-errors-{{{ data.id }}}" class="nf-form-errors" role="alert"></div>
    <div class="nf-form-hp"></div>
</script>
<script id="tmpl-nf-before-field" type="text/template">
    {{{ data.beforeField }}}
</script><script id="tmpl-nf-after-field" type="text/template">
    {{{ data.afterField }}}
</script><script id="tmpl-nf-form-layout" type="text/template">
	<form>
		<div>
			<div class="nf-before-form-content"></div>
			<div class="nf-form-content {{{ data.element_class }}}"></div>
			<div class="nf-after-form-content"></div>
		</div>
	</form>
</script><script id="tmpl-nf-form-hp" type="text/template">
	<label for="nf-field-hp-{{{ data.id }}}" aria-hidden="true">
		{{{ nfi18n.formHoneypot }}}
		<input id="nf-field-hp-{{{ data.id }}}" name="nf-field-hp" class="nf-element nf-field-hp" type="text" value=""/>
	</label>
</script>
<script id="tmpl-nf-field-layout" type="text/template">
    <div id="" class="">
        <div class="nf-before-field"></div>
        <div class="nf-field"></div>
        <div class="nf-after-field"></div>
    </div>
</script>
<script id="tmpl-nf-field-before" type="text/template">
</script><script id="tmpl-nf-field-after" type="text/template">

    <div class="nf-input-limit"></div>
	
    <div id="nf-error-{{{ data.id }}}" class="nf-error-wrap nf-error" role="alert"></div>

</script>
<script id="tmpl-nf-field-wrap" type="text/template">
	<div id="nf-field-{{{ data.id }}}-wrap" class="" data-field-id="">

		<div class="nf-field-element"></div>

	</div>
</script>
<script id="tmpl-nf-field-wrap-no-label" type="text/template">
    <div id="nf-field-{{{ data.id }}}-wrap" class="" data-field-id="">
        <div class="nf-field-label"></div>
        <div class="nf-field-element"></div>
        <div class="nf-error-wrap"></div>
    </div>
</script>
<script id="tmpl-nf-field-wrap-no-container" type="text/template">

        <div class="nf-error-wrap"></div>
</script>


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
