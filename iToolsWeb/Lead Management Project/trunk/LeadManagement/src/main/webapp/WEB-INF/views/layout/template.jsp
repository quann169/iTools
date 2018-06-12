<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<html class="no-js" lang="vi-vn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="description" content="FWD Việt Nam là công ty bảo hiểm cung cấp các sản phẩm và dịch vụ bảo hiểm chuyên nghiệp.">
	<meta name="keywords" content="">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link href="${pageContext.request.contextPath}/resources/vendor/font-awesome/css/font-awesome.css" rel="stylesheet"/>
	<link href="${pageContext.request.contextPath}/resources/css/style.min.css"	rel="stylesheet"/>	
	<link href="${pageContext.request.contextPath}/resources/css/custom.css"	rel="stylesheet"/>
	<script src="${pageContext.request.contextPath}/resources/js/main.min.js"></script>
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
				<img src="${pageContext.request.contextPath}/resources/img/abbank.png" alt="" width="46" >
			</div>
			<div class="mobile-menu-overlay"></div>
			<div class="navmenu collapse navbar-collapse desktop"
				aria-expanded="false">
				<div class="mobile-menu">
					<!---------- Top Menu ---------->
					<div class="top-nav">
						<div class="container">
							<div class="row">
								<ul class="nav navbar-nav left">
								</ul>
								<ul class="nav navbar-nav navbar-right">
									<li class="text-center"><a>AB000001-Nguyễn Văn A</a></li>								
									<li class="text-center"><a class="gtm-nav" href="#" title="VI" lang="vi">Log out</a></li>										
								</ul>
							</div>
						</div>
					</div>
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
								<a class="gtm-nav hidden-xs" rel="home" href="https://www.fwd.com.vn/vi/"
									title=""> <img src="${pageContext.request.contextPath}/resources/img/abbank.png" alt="" width="173" style="margin-top: 35px">
								</a>
								<ul class="nav navbar-nav navbar-right">
									<li><a href="${pageContext.request.contextPath}/bankstaff/register" class="gtm-nav">GIỚI THIỆU<br/>KHÁCH HÀNG</a></li>
									<li><a href="${pageContext.request.contextPath}/bankstaff/report">BÁO CÁO</a></li>
									<li><a href="${pageContext.request.contextPath}/fsc/leadSearch">DANH SÁCH<BR/>KH TIỀM NĂNG</a></li>
									<li><a href="${pageContext.request.contextPath}/bankstaff/user-guide class="gtm-nav">HƯỚNG DẪN<br/>SỬ DỤNG</a></li>									
								</ul>
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
		<span id="top-link-block" style="right: 281.5px;" class="affix">
			<a href="#top" class="text-center btn btn-default" onclick="$('html,body').animate({scrollTop:0},'slow');return false;">
				<i class="fa fa-caret-up"></i>Top
		</a>
		</span>
		<!-- /top-link-block -->
		<div class="sitemap">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-3 col-md-3 hotline">
						<h3>
							<a class="gtm-footer" href="https://www.fwd.com.vn/vi/lien-he">Liên hệ <i
								class="fa fa-caret-right"></i></a>
						</h3>
						<small>FWD Chăm sóc khách hàng</small> 
						<big
							class="gtm-footer primary-color tel"><a
							href="tel:0439386767">04 3938 6767 (Hà Nội)</a><br> <a
							href="tel:0862563677">08 6256 3677 (TP.HCM)</a>
						</big>
						<div class="operation-hour small"></div>
					</div>
					<div class="col-xs-12 col-sm-8 col-md-7 page-nav">
						<div class="row">
							<div class="col-md-4 col-sm-4 col-xs-6">
								<div>
									<a target="_blank" href="https://www.fwd.com.vn/vi/bao-ve" class="gtm-footer">Bảo vệ</a>
								</div>
								<div>
									<a target="_blank" href="https://www.fwd.com.vn/vi/dau-tu" class="gtm-footer">Đầu tư </a>
								</div>
								<div>
									<a target="_blank" href="https://www.fwd.com.vn/vi/tiet-kiem" class="gtm-footer">Tiết kiệm </a>
								</div>
							</div>
							<div class="col-md-4 col-sm-4 col-xs-6">
								<a target="_blank" href="https://www.fwd.com.vn/vi/ho-tro-khach-hang" class="gtm-footer">Hỗ trợ
									khách hàng</a>
							</div>
							<div class="col-md-3 col-sm-3 col-xs-6">
								<a target="_blank" href="https://www.fwd.com.vn/vi/thong-cao-bao-chi" class="gtm-footer">Thông cáo
									báo chí </a>
							</div>
							<div class="col-md-4 col-sm-4 col-xs-6">
								<a target="_blank" href="https://www.fwd.com.vn/vi/ve-tap-doan-fwd" class="gtm-footer">Về chúng
									tôi </a>
							</div>
							<div class="col-md-3 col-sm-3 col-xs-6">
								<a target="_blank" href="https://www.fwd.com.vn/vi/tuyen-dung" class="gtm-footer">Tuyển dụng </a>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="copyright">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-md-5">
						<ul class="nav">
							<li><a target="_blank" href="https://www.fwd.com.vn/vi/so-do-website" class="gtm-footer">Sơ đồ
									website&nbsp;&nbsp;|&nbsp;&nbsp;</a></li>
							<li><a target="_blank" href="https://www.fwd.com.vn/vi/mien-tru-trach-nhiem" class="gtm-footer">Miễn
									trừ trách nhiệm&nbsp;&nbsp;|&nbsp;&nbsp;</a></li>
							<li><a target="_blank" href="https://www.fwd.com.vn/vi/chinh-sach-bao-mat-truc-tuyen"
								class="gtm-footer">Chính sách bảo mật trực
									tuyến&nbsp;&nbsp;|&nbsp;&nbsp;</a></li>
							<li><a
								href="https://www.fwd.com.vn/vi/chinh-sach-va-thong-le-bao-ve-du-lieu-thong-tin-ca-nhan"
								class="gtm-footer">Chính sách và Thông lệ Bảo vệ Dữ liệu
									Thông tin Cá nhân</a></li>

						</ul>
					</div>
					<!-- div class="col-xs-12 col-md-7">
						<div>
							<label class="control-label">Website FWD tại nơi của bạn</label>
							<select class="form-control fancy-select-box" onchange="window.open(this.options[this.selectedIndex].value, '_blank')">
                            <option value="http://www.fwd.co.vn">FWD Việt Nam</option>
                            <option value="http://www.fwd.com.hk">FWD Hồng Kông</option>
                            <option value="http://www.fwd.com.mo">FWD Ma Cao</option>
                            <option value="http://www.fwd.cn">FWD Trung Quốc</option>
                            <option value="http://www.fwd.co.th">FWD Thái Lan</option>
                            <option value="http://www.fwd.com.ph">FWD Philippines</option>
                            <option value="http://www.fwd.co.id">FWD Indonesia</option>
                            <option value="http://www.fwd.com.sg">FWD Singapore</option>
                        	</select>
						</div>
					</div -->
					<div class="col-xs-12 col-md-5">&copy; 2016 Bản quyền thuộc
						về Công ty TNHH Bảo hiểm Nhân thọ FWD Việt Nam.</div>
				</div>
			</div>
		</div>
	</footer>
	
	
<%-- 	<script src="${pageContext.request.contextPath}/resources/vendor/jquery/jquery.min.js"></script> --%>
<%-- 	<script src="${pageContext.request.contextPath}/resources/vendor/bootstrap/dist/js/bootstrap.min.js"></script>  --%>
<%-- 	<script src="${pageContext.request.contextPath}/resources/vendor/fancyselect/jquery.fancy.select.min.js"></script>  --%>
<%--  	<script src="${pageContext.request.contextPath}/resources/js/custom.js"></script> --%>
	
</body>
</html>
