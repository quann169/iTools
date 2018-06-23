package com.message;

public enum Enum {
	
	NULL(""),
	// Role
	ADMIN("Admin"), SUBADMIN("SubAdmin"), ACCT("Accounting"), EMP("Emp"), OTHER("Other"), PUTIN("PutIns"), TKOVER(
			"TakeOver"), EDTTRANS("EditTransaction"), GETTOOL("GetTools"),

	// Table name

	MASTERLOG("MasterLog"), WORKINGTRANSACTION("WorkingTransaction"), TOOLSMACHINETRAY(
			"ToolsMachineTray"), TOOLSMACHINE("ToolsMachine"), TOOLS("Tools"), COMPANYMACHINE(
					"CompanyMachine"), MACHINE("Machine"), ROLEASSESSOR(
							"RoleAssessor"), ROLES("Roles"), ASSESSOR("Assessor"), COMPANY("Company"),
	
	// Action
	LOGIN_PAGE("LoginPage"),
	DASHBOARD_PAGE("DashboardPage"),
	LOCK_UNLOCK_PAGE("LockUnlockAccountPage"),
	EMP_PAGE("EmployeePage"),
	RESET_PASS_PAGE("ResetPasswordPage"),
	PUTIN_TAKEOVER_PAGE("PutInTakeOverPage"),
	UPDATE_PASS_FIRST_TIME_LOGIN("FirstTimeChangePass"),
	
	REQUEST_TOOL("SendRequest"),
	TIME_OUT("TimeOut"),
	
	SHOW_DASHBOARD("ShowDashboard"),
	LOGIN("Login"),
	LOGIN_FAIL("LoginFail"),
	FORGOT_PASS("ForgotPass"),
	CREATE("Create"),
	UPDATE("Update"),
	CREATE_FAIL("CreateFail"),
	UPDATE_FAIL("UpdateFail"),
	DELETE("Delete"),
	CHANGE_PASS("ChangePass"),
	RESET_PASS("ResetPass"),
	CHANGE_PASS_FAIL("ChangePassFail"),
	RESET_PASS_FAIL("ResetPassFail"),
	LOGOUT("Logout"),
	UNLOCK_MACHINE("UnlockMachine"),
	LOCK_MACHINE("LockMachine"),
	UNLOCK_MACHINE_FAIL("UnlockMachineFail"),
	LOCK_MACHINE_FAIL("LockMachineFail"),
	UNLOCK_USER("UnlockUser"),
	LOCK_USER("LockUser"),
	UNLOCK_USER_FAIL("UnlockUserFail"),
	LOCK_USER_FAIL("LockUserFail"),
	SYNC_MANUALLY("SyncManually"),
	SYNC_MANUALLY_FAIL("SyncManuallyFail"),
	;

	private String text;

	Enum(String text) {
		this.text = text;
	}

	public String text() {
		return text;
	}
}
