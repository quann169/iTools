<style type="text/css">
.form-horizontal .control-label {
	text-align: left;
}

.payment-hide {
	display: none;
}

.form-group {
	min-height: 110px
}

label.checkbox input[type="checkbox"] {
	border: 0 none;
	clip: rect(0px, 0px, 0px, 0px);
	height: 1px;
	margin: -1px;
	overflow: hidden;
	padding: 0;
	position: absolute;
	width: 1px;
}

label.checkbox  input[type="checkbox"]+span>span.tick, 
label.checkbox  input[type="checkbox"]:checked+span>span.tick
	{
	transition: opacity 0.125s linear 0s;
}

label.checkbox input[type="checkbox"] + span > span.tick {
    background-color: transparent;
    background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAAPCAMAAAGpX1+2AAAAqFBMVEXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUXTEUV+TxMFAAAAN3RSTlMABggKDg8REhMVFhgaHCAkJyorQEVJTlJTV19ja3F4t7q8v8HHy8/R09XX293h4+fq9fb3+fz+naLXIwAAAJBJREFUGNNtjldywgAUAxdsegkdQg1gSkJNYtj734wP8Bhm0JekJ2keAEUBsApgDqIGAPSBgdgWuIcAhn5BBbpaAuZq/XFq8owgIRezCVkBY4jdAhsnv+4AAvVwz4Sekl6eNxhdF8GL8XlRozA1erG6LgCUvp1C50/9KQOQmanXf3VfSz9dqh4/XnZzq3MrVTeq7xAMMFWDSQAAAABJRU5ErkJggg==");
    background-position: 3px 1px;
    background-repeat: no-repeat;
    background-size: 18px auto;
    display: block;
    height: 20px;
    left: -3px;
    opacity: 0;
    position: absolute;
    top: -3px;
    width: 20px;
    z-index: 100;
}

label.checkbox  input[type="checkbox"]:checked+span>span.tick {
	opacity: 1;
}

label.checkbox input[type="checkbox"]+span {
	background-color: #fff;
	border: 3px solid #dbdad4;
	cursor: pointer;
	display: inline-block;
	height: 20px;
	margin: -2px 10px 0 0;
	position: relative;
	vertical-align: middle;
	width: 20px;
}
</style>

<div class="title margin-top-l">
	<h2>
		<spring:message code="register.titleSuccess" />
	</h2>
</div>
<form:form action="${pageContext.request.contextPath}/public/register"
			modelAttribute="registerForm" class="form-horizontal ">
	<fieldset>			
		<div class="margin-top-l" style="background-color: #f1f1f1;">
			<section style="background-color: #f1f1f1;">
				Chúc mừng bạn, điều bạn chọn sẽ trở thành hiện thực. <br> 
				Hãy đi đến điểm <b>${f:h(registerForm.selection)}</b> để  khám phá điều đó
			</section>
			<img src="../resources/img/FWD_map.jpg" alt="" width="" >
			<div class="margin-top-l"></div>
		</div>
	</fieldset>
</form:form>
		
		