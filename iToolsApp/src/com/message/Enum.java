package com.message;

import java.util.ArrayList;
import java.util.List;

public enum Enum {

	NULL(""),
	// Role
	ADMIN("Admin"), SUBADMIN("SubAdmin"), ACCT("Accounting"), EMP("Emp"), OTHER("Other"), PUTIN("PutIns"), TKOVER(
			"TakeOver"), EDTTRANS("EditTransaction"), GETTOOL("GetTools"),

	// Table name
	EMAIL("Email"),
	MASTERLOG("MasterLog"), WORKINGTRANSACTION("WorkingTransaction"), TOOLSMACHINETRAY(
			"ToolsMachineTray"), TOOLSMACHINE("ToolsMachine"), TOOLS("Tools"), COMPANYMACHINE(
					"CompanyMachine"), MACHINE("Machine"), ROLEASSESSOR(
							"RoleAssessor"), ROLES("Roles"), ASSESSOR("Assessor"), COMPANY("Company"),

	// Action
	LOGIN_PAGE("LoginPage"), DASHBOARD_PAGE("DashboardPage"), LOCK_UNLOCK_PAGE("LockUnlockAccountPage"), EMP_PAGE(
			"EmployeePage"), RESET_PASS_PAGE("ResetPasswordPage"), FORGOT_PASS_PAGE("ForgotPasswordPage"), PUTIN_TAKEOVER_PAGE(
					"PutInTakeOverPage"), UPDATE_PASS_FIRST_TIME_LOGIN("FirstTimeChangePass"),

	REQUEST_TOOL("SendRequest"), TIME_OUT("TimeOut"),
	INVALID_SIGNAL_RECEIVE("InvalidSignalReceived"),
	SEND_SIGNAL_TO_BOARD("SendRequestToBoard"),
	MOTOR_START("MotorStart"),
	MOTOR_STOP("MotorStop"),
	PRODUCT_OK("ProductOK"),
	PRODUCT_FAIL("ProductFail"),
	
	
	SEND_SIGNAL_TO_BOARD_FAIL("FailSendRequestToBoard"),
	RECEIVE_SIGNAL_FROM_BOARD_1_FAIL("FailSendRequestToBoard1"),
	RECEIVE_SIGNAL_FROM_BOARD_2_FAIL("FailSendRequestToBoard2"),
	RECEIVE_SIGNAL_FROM_BOARD_3_FAIL("FailSendRequestToBoard3"),
	RECEIVE_SIGNAL_FROM_BOARD_4_FAIL("FailSendRequestToBoard4"),
	
	FORGOT_PASS_USERNAME_EMAIL_NOT_MATCH("ForgotPassUsernameNotMatchEmail"),
	FORGOT_PASS_RESET_OK("ForgotPassResetOk"),

	SHOW_DASHBOARD("ShowDashboard"), LOGIN("Login"), LOCKED_ACC("LockedAcc"), LOGIN_FAIL("LoginFail"), FORGOT_PASS("ForgotPass"), CREATE(
			"Create"), UPDATE("Update"), CREATE_FAIL("CreateFail"), UPDATE_FAIL("UpdateFail"), DELETE(
					"Delete"), CHANGE_PASS("ChangePass"), RESET_PASS("ResetPass"), CHANGE_PASS_FAIL(
							"ChangePassFail"), RESET_PASS_FAIL("ResetPassFail"), LOGOUT("Logout"), UNLOCK_MACHINE(
									"UnlockMachine"), LOCK_MACHINE("LockMachine"), UNLOCK_MACHINE_FAIL(
											"UnlockMachineFail"), LOCK_MACHINE_FAIL("LockMachineFail"), UNLOCK_USER(
													"UnlockUser"), LOCK_USER("LockUser"), UNLOCK_USER_FAIL(
															"UnlockUserFail"), LOCK_USER_FAIL(
																	"LockUserFail"), SYNC_MANUALLY(
																			"SyncManually"), SYNC_MANUALLY_FAIL(
																					"SyncManuallyFail"),

	TRAY_1("TRAY_01"), TRAY_2("TRAY_02"), TRAY_3("TRAY_03"), TRAY_4("TRAY_04"), TRAY_5("TRAY_05"), TRAY_6("TRAY_06"), TRAY_7(
			"TRAY_07"), TRAY_8("TRAY_08"), TRAY_9("TRAY_09"), TRAY_10("TRAY_10"), TRAY_11("TRAY_11"), TRAY_12(
					"TRAY_12"), TRAY_13("TRAY_13"), TRAY_14("TRAY_14"), TRAY_15("TRAY_15"), TRAY_16("TRAY_16"), TRAY_17(
							"TRAY_17"), TRAY_18("TRAY_18"), TRAY_19("TRAY_19"), TRAY_20("TRAY_20"), TRAY_21(
									"TRAY_21"), TRAY_22("TRAY_22"), TRAY_23("TRAY_23"), TRAY_24("TRAY_24"), TRAY_25(
											"TRAY_25"), TRAY_26("TRAY_26"), TRAY_27("TRAY_27"), TRAY_28(
													"TRAY_28"), TRAY_29("TRAY_29"), TRAY_30("TRAY_30"), TRAY_31(
															"TRAY_31"), TRAY_32("TRAY_32"), TRAY_33("TRAY_33"), TRAY_34(
																	"TRAY_34"), TRAY_35("TRAY_35"), TRAY_36(
																			"TRAY_36"), TRAY_37("TRAY_37"), TRAY_38(
																					"TRAY_38"), TRAY_39(
																							"TRAY_39"), TRAY_40(
																									"TRAY_40"), TRAY_41(
																											"TRAY_41"), TRAY_42(
																													"TRAY_42"), TRAY_43(
																															"TRAY_43"), TRAY_44(
																																	"TRAY_44"), TRAY_45(
																																			"TRAY_45"), TRAY_46(
																																					"TRAY_46"), TRAY_47(
																																							"TRAY_47"), TRAY_48(
																																									"TRAY_48"), TRAY_49(
																																											"TRAY_49"), TRAY_50(
																																													"TRAY_50"), TRAY_51(
																																															"TRAY_51"), TRAY_52(
																																																	"TRAY_52"), TRAY_53(
																																																			"TRAY_53"), TRAY_54(
																																																					"TRAY_54"), TRAY_55(
																																																							"TRAY_55"), TRAY_56(
																																																									"TRAY_56"), TRAY_57(
																																																											"TRAY_57"), TRAY_58(
																																																													"TRAY_58"), TRAY_59(
																																																															"TRAY_59"), TRAY_60(
																																																																	"TRAY_60"),

	;

	private String text;

	Enum(String text) {
		this.text = text;
	}

	public String text() {
		return text;
	}

	public static List<String> getTrays() {
		List<String> result = new ArrayList<>();
		result.add(Enum.TRAY_1.text());
		result.add(Enum.TRAY_2.text());
		result.add(Enum.TRAY_3.text());
		result.add(Enum.TRAY_4.text());
		result.add(Enum.TRAY_5.text());
		result.add(Enum.TRAY_6.text());
		result.add(Enum.TRAY_7.text());
		result.add(Enum.TRAY_8.text());
		result.add(Enum.TRAY_9.text());
		result.add(Enum.TRAY_10.text());
		result.add(Enum.TRAY_11.text());
		result.add(Enum.TRAY_12.text());
		result.add(Enum.TRAY_13.text());
		result.add(Enum.TRAY_14.text());
		result.add(Enum.TRAY_15.text());
		result.add(Enum.TRAY_16.text());
		result.add(Enum.TRAY_17.text());
		result.add(Enum.TRAY_18.text());
		result.add(Enum.TRAY_19.text());
		result.add(Enum.TRAY_20.text());
		result.add(Enum.TRAY_21.text());
		result.add(Enum.TRAY_22.text());
		result.add(Enum.TRAY_23.text());
		result.add(Enum.TRAY_24.text());
		result.add(Enum.TRAY_25.text());
		result.add(Enum.TRAY_26.text());
		result.add(Enum.TRAY_27.text());
		result.add(Enum.TRAY_28.text());
		result.add(Enum.TRAY_29.text());
		result.add(Enum.TRAY_30.text());
		result.add(Enum.TRAY_31.text());
		result.add(Enum.TRAY_32.text());
		result.add(Enum.TRAY_33.text());
		result.add(Enum.TRAY_34.text());
		result.add(Enum.TRAY_35.text());
		result.add(Enum.TRAY_36.text());
		result.add(Enum.TRAY_37.text());
		result.add(Enum.TRAY_38.text());
		result.add(Enum.TRAY_39.text());
		result.add(Enum.TRAY_40.text());
		result.add(Enum.TRAY_41.text());
		result.add(Enum.TRAY_42.text());
		result.add(Enum.TRAY_43.text());
		result.add(Enum.TRAY_44.text());
		result.add(Enum.TRAY_45.text());
		result.add(Enum.TRAY_46.text());
		result.add(Enum.TRAY_47.text());
		result.add(Enum.TRAY_48.text());
		result.add(Enum.TRAY_49.text());
		result.add(Enum.TRAY_50.text());
		result.add(Enum.TRAY_51.text());
		result.add(Enum.TRAY_52.text());
		result.add(Enum.TRAY_53.text());
		result.add(Enum.TRAY_54.text());
		result.add(Enum.TRAY_55.text());
		result.add(Enum.TRAY_56.text());
		result.add(Enum.TRAY_57.text());
		result.add(Enum.TRAY_58.text());
		result.add(Enum.TRAY_59.text());
		result.add(Enum.TRAY_60.text());

		return result;
	}
}
