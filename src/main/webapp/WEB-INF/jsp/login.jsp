<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta http-equiv="Cache-Control" content="no-siteapp" />
		<!--[if lt IE 9]>
		<script type="text/javascript" src="lib/html5shiv.js"></script>
		<script type="text/javascript" src="lib/respond.min.js"></script>
		<![endif]-->
		<link href="${pageContext.request.contextPath}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
		<link href="static/h-ui.admin/css/H-ui.login.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/static/js/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css" />
		<!--[if IE 6]>
		<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
		<script>DD_belatedPNG.fix('*');</script>
		<![endif]-->
		<title>后台登录</title>
	</head>
	<body>
		<input type="hidden" id="TenantId" name="TenantId" value="" />
		<!-- 
		<div class="header"></div> -->
		<div class="loginWraper">
			<div id="loginform" class="loginBox">
				<form class="form form-horizontal" action="" method="post">
					<div class="row cl">
						<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
						<div class="formControls col-xs-8">
							<input id="username" name="username" type="text" placeholder="账户"
								class="input-text size-L">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
						<div class="formControls col-xs-8">
							<input id="password" name="password" type="password" placeholder="密码"
								class="input-text size-L">
						</div>
					</div>
					<div class="row cl">
						<div class="formControls col-xs-8 col-xs-offset-3">
							<input class="input-text size-L" type="text" placeholder="验证码"
								onblur="if(this.value==''){this.value='验证码:'}"
								onclick="if(this.value=='验证码:'){this.value='';}" value="验证码:"
								style="width: 150px;"
								name="jcaptchaCode"> 
							<img class="jcaptcha-btn jcaptcha-img" src="${pageContext.request.contextPath}/jcaptcha"> 
							<a class="jcaptcha-btn" href="javascript:;">看不清，换一张</a>
						</div>
					</div>
					<div class="row cl">
						<div class="formControls col-xs-8 col-xs-offset-3">
							 <input type="checkbox" name="rememberMe" id="rememberMe" value="true">
							 <label for="rememberMe"> 使我保持登录状态</label>
						</div>
					</div>
					<div class="row cl">
						<div class="formControls col-xs-8 col-xs-offset-3">
							<input name="" type="submit" class="btn btn-success radius size-L"
								value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;"> <input
								name="" type="reset" class="btn btn-default radius size-L"
								value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
						</div>
						<div class="formControls col-xs-8 col-xs-offset-3">
							${error}
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="footer">Copyright 你的公司名称 by H-ui.admin v3.0</div>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script>
		<script type="text/javascript">
			$(function() {
		        $(".jcaptcha-btn").click(function() {
		            $(".jcaptcha-img").attr("src", '${pageContext.request.contextPath}/jcaptcha?'+new Date().getTime());
		        });
		    });
		</script>
	</body>
</html>