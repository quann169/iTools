<style type="text/css">
.form-horizontal .control-label {
	text-align: left;
}

.payment-hide {
	display: none;
}
</style>
<div class="join-us">
	<section
		class="text-center industry-prospects-content industry-prospects">
		<div class="row">
			<div class="title">
				<h2>Thanh toán phí bảo hiểm</h2>
			</div>
		</div>
	</section>
	<section style="background-color: #f1f1f1;" class="">
		<form:form action="${pageContext.request.contextPath}/public/payment"
			modelAttribute="paymentForm" class="form-horizontal ">
			<fieldset>
				<div class="col col-sm-6">
					<div class="form-group">
						<form:label path="policyNumber"
							class="col col-sm-12 control-label text-left">Số hợp đồng/ số hồ sơ yêu cầu bảo hiểm</form:label>
						<div class="col col-sm-12">
							<form:input path="policyNumber" class="form-control" />
							<form:errors path="policyNumber" cssClass="text-danger" />
						</div>
					</div>
					<div class="form-group">
						<form:label path="idNumber" class="col col-sm-12 control-label">Số CMND của Bên mua bảo hiểm</form:label>
						<div class="col col-sm-12">
							<form:input path="idNumber" class="form-control" />
							<form:errors path="idNumber" cssClass="text-danger" />
						</div>
					</div>
					<div class="form-group">
						<form:label path="amount" class="col col-sm-12 control-label">Họ và tên Bên mua bảo hiểm</form:label>
						<div class="col col-sm-12">
							<form:input path="poName" class="form-control" />
							<form:errors path="poName" cssClass="text-danger" />
						</div>
					</div>
				</div>
				<div class="col col-sm-6">

					<div class="form-group">
						<form:label path="paymentType" class="col col-sm-12 control-label">Mục đích thanh toán</form:label>
						<div class="col col-sm-12">
							<form:errors path="paymentType" cssClass="text-danger" />
							<select class="form-control fancy-select-box"
								onchange="setPaymentType(this.options[this.selectedIndex].value)">
								<option value="0">Phí bảo hiểm ban đầu</option>
								<option value="1">Phí bảo hiểm tái tục</option>
								<option value="2">Khác</option>
							</select>
							<form:hidden path="paymentType" />
						</div>
					</div>

					<div class="form-group">
						<form:label path="amount" class="col col-sm-12 control-label">Số tiền thanh toán</form:label>
						<div class="col col-sm-12">
							<form:input path="amount" class="form-control" />
							<form:errors path="amount" cssClass="text-danger" />
						</div>
					</div>
					<div class="form-group">
						<form:label path="remark" class="col col-sm-12 control-label">Ghi chú khác</form:label>
						<div class="col col-sm-12">
							<form:input path="remark" class="form-control" />
							<form:errors path="remark" cssClass="text-danger" />
						</div>
					</div>
				</div>


				<div class="col col-sm-8 col-xs-offset-4">
					<a href="#" onclick="document.forms[0].submit()"
						class="btn btn-primary text-uppercase full-width">TIẾP TỤC</a>
				</div>
	<script type="text/javascript">
		function populateTestDate() {
			$("input[name='amount']").val("1000000");
		}
	</script>
			</fieldset>
		</form:form>
	</section>
</div>