
<div class="title margin-top-l">
	<h2>Đăng nhập</h2>
</div>

<form name='f'
	action='${pageContext.request.contextPath}/j_spring_security_check'
	method='POST'>
	<div class="col-sm-6 margin-bottom-l">
		<div class="row">
			Tên đăng nhập
		</div>		
		<br/>
		<div class="row">			
			<input class='selectize-input' type='text' name='j_username' value='demo'>			
		</div>
		<br/>
		<div class="row">
			Mật khẩu
		</div>
		<br/>
		<div class="row">
			<input class='selectize-input' type='password' name='j_password' value="demo" />
		</div>
		<div class="row">			
			<input class="btn btn-primary text-uppercase full-width" name="submit" type="submit" value="ĐĂNG NHẬP" />			
		</div>
	</div>
</form>
