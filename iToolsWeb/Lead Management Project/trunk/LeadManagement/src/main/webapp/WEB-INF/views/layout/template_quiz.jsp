<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<html class="no-js" lang="vi-vn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="description" content="FWD Việt Nam là công ty bảo hiểm cung cấp các sản phẩm và dịch vụ bảo hiểm chuyên nghiệp.">
	<meta name="keywords" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link href="../resources/vendor/font-awesome/css/font-awesome.css" rel="stylesheet"/>
	<link href="../resources/css/style.min.css"	rel="stylesheet"/>	
	<link href="../resources/css/custom.css"	rel="stylesheet"/>
	<script src="../resources/js/main.min.js"></script>
	<c:set var="titleKey">
		<tiles:insertAttribute name="title" ignore="true" />
	</c:set>	
	<tiles:insertAttribute name="header" />
</head>
<body class="vi-vn" style="margin-top: 125px;">
<header>
		<nav class="navbar navbar-fixed-top navbar-inverse" role="navigation">
			<div class="navbar-header text-center">
				<a target="_blank" href="https://www.fwd.com.vn/vi/" class="gtm-nav navbar-brand visible-xs"> <img
					src="https://www.fwd.com.vn/~/media/Images/FWDVN/mobile_logo1.png?h=16&amp;la=vi-VN&amp;w=46&amp;hash=8901C1C567D0F94B505E9F3FC6CFACC85B3F8D25"
					alt="FWD Việt Nam" width="46" height="16">
				</a>
			</div>
			<div class="mobile-menu-overlay"></div>
			<div class="navmenu collapse navbar-collapse desktop"
				aria-expanded="false">
				<div class="mobile-menu">
					<!---------- Top Menu ---------->

					<!---------- Primary Menu ---------->
					<div class="primary-nav">
						<div class="container">
							<div class="row">
								<!-- Collect the nav links, forms, and other content for toggling -->
								<a class="gtm-nav navbar-brand hidden-xs" rel="home" href="https://www.fwd.com.vn/vi/"
									title=""> <img
									src="https://www.fwd.com.vn/~/media/Images/FWDVN/fwd-logo-vn.png?h=51&amp;la=vi-VN&amp;w=163&amp;hash=2D6AFB86F5F292D94217D4669AB41620668958CB"
									alt="" width="163" height="51">
								</a>		
							</div>
						</div>
					</div>
				</div>
				<div class="navbar-toggle-wrap">
					<button type="button" class="navbar-toggle pull-left"
						data-toggle="collapse" data-target=".navmenu">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
				</div>
			</div>
		</nav>
	</header>
<div class="container home">
	<tiles:insertAttribute name="body" />
</div>
	<footer>
		
	</footer>	
</body>
</html>
