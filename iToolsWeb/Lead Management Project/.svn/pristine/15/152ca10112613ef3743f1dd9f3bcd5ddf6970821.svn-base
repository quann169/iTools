<!-- 
<div class="title margin-top-l">
	<h2>Các kênh đóng phí bảo hiểm</h2>
</div> -->
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
		<form:form action="https://testsecureacceptance.cybersource.com/pay"		
			modelAttribute="paymentForm" class="form-horizontal ">
			<!-- access_key,profile_id,transaction_uuid,signed_field_names,unsigned_field_names,signed_date_time,locale,transaction_type,reference_number,amount,currency -->
			<form:hidden path="access_key"/>
			<form:hidden path="profile_id"/>
			<form:hidden path="transaction_uuid"/>
			<form:hidden path="signed_field_names"/>
			<form:hidden path="unsigned_field_names"/>
			<form:hidden path="signed_date_time"/>			
			<form:hidden path="locale"/>			
			<form:hidden path="currency"/>
			<form:hidden path="transaction_type"/>
			<form:hidden path="reference_number"/>						
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
						<form:label path="amount" class="col col-sm-12 control-label">Họ và tên Bên mua bảo hiểm</form:label>
						<div class="col col-sm-12">
							<form:input path="poName" class="form-control" />
							<form:errors path="poName" cssClass="text-danger" />
						</div>
					</div>
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
							<form:hidden path="paymentType"/>
						</div>
					</div>
					<div class="form-group">
						<form:label path="remark" class="col col-sm-12 control-label">Địa chỉ email của chủ thẻ</form:label>
						<div class="col col-sm-12">
							<form:input path="remark" class="form-control" />
							<form:errors path="remark" cssClass="text-danger" />
						</div>
					</div>
					<div class="form-group">
						<form:label path="idNumber" class="col col-sm-12 control-label">Số CMND của Bên mua bảo hiểm</form:label>
						<div class="col col-sm-12">
							<form:input path="idNumber" class="form-control" />
							<form:errors path="idNumber" cssClass="text-danger" />
						</div>
					</div>
				</div>
				<div class="col col-sm-6">


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

					<div class="form-group">
						<form:label path="remark" class="col col-sm-12 control-label">Loại thẻ thanh toán</form:label>
						<div class="col col-sm-12">
							<select class="form-control fancy-select-box"
								onchange="triggerBank(this.options[this.selectedIndex].value)">
								<option value="0">Chọ loại thẻ thanh toán</option>
								<option value="1">ATM</option>
								<option value="2">Visa/Master/JCB</option>
							</select>
						</div>
					</div>
					<div class="form-group bankonly payment-hide">
						<form:label path="remark" class="col col-sm-12 control-label">Ngân hàng phát hành</form:label>
						<div class="col col-sm-12">
							<form:input path="remark" class="form-control" />
							<form:errors path="remark" cssClass="text-danger" />
						</div>
					</div>
					<div class="form-group bankonly payment-hide">
						<form:label path="remark" class="col col-sm-12 control-label">Họ và tên chủ thẻ</form:label>
						<div class="col col-sm-12">
							<form:input path="remark" class="form-control" />
							<form:errors path="remark" cssClass="text-danger" />
						</div>
					</div>
					<div class="form-group bankonly payment-hide">
						<form:label path="remark" class="col col-sm-12 control-label">Số điện thoại di động của chủ thẻ</form:label>
						<div class="col col-sm-12">
							<form:input path="remark" class="form-control" />
							<form:errors path="remark" cssClass="text-danger" />
						</div>
					</div>
				</div>

				<script type="text/javascript">
					function triggerBank(value) {
						if (value === "1") {//ATM
							$(".bankonly").removeClass("payment-hide");
						} else {
							$(".bankonly").addClass("payment-hide");
						}
					}
					function setPaymentType(value) {						
						$("input[name=paymentType]").val(value);
					}
					
				</script>


				<div class="col col-sm-8 col-xs-offset-4">
					<a href="#" onclick="document.forms[0].submit()" class="btn btn-primary text-uppercase full-width">TIẾP
						TỤC</a>
				</div>
				
			</fieldset>
		</form:form>
	</section>
</div>