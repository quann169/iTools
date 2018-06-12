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
		<form:form action="https://testsecureacceptance.cybersource.com/pay" modelAttribute="paymentForm" class="form-horizontal ">		
			<form:hidden path="access_key" />
			<form:hidden path="profile_id" />
			<form:hidden path="transaction_uuid" />
			<form:hidden path="signed_field_names" />
			<form:hidden path="unsigned_field_names" />
			<form:hidden path="signed_date_time" />
			<form:hidden path="locale" />
			<form:hidden path="currency" />
			<form:hidden path="transaction_type" />
			<form:hidden path="reference_number" />
			<form:hidden path="signature" />
			<form:hidden path="amount" />

			<fieldset>
				<div class="col col-sm-6">
					<div class="form-group">
						<form:label path="policyNumber"
							class="col col-sm-12 control-label text-left">Số hợp đồng/ số hồ sơ yêu cầu bảo hiểm</form:label>
						<div class="col col-sm-12">
							${paymentForm.policyNumber}
						</div>
					</div>
					<div class="form-group">
						<form:label path="amount" class="col col-sm-12 control-label">Số tiê</form:label>
						<div class="col col-sm-12">
							${paymentForm.amount}
						</div>
					</div>
					<div class="form-group">
						<form:label path="poName" class="col col-sm-12 control-label">Họ và tên Bên mua bảo hiểm</form:label>
						<div class="col col-sm-12">
							${paymentForm.poName}
						</div>
					</div>
				</div>
			</fieldset>
			<div class="col col-sm-8 col-xs-offset-4">
				<a href="#" onclick="document.forms[0].submit()"
					class="btn btn-primary text-uppercase full-width">TIẾP TỤC</a>
			</div>
		</form:form>		
	</section>
</div>