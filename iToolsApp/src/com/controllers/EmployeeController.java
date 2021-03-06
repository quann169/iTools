/**
 * 
 */
package com.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.models.Assessor;
import com.models.Machine;
import com.models.Role;
import com.models.Tool;
import com.utils.MysqlConnect;

/**
 * @author svi-quannguyen
 *
 */
public class EmployeeController {

	MysqlConnect mysqlConnect = new MysqlConnect();
	final static Logger logger = Logger.getLogger(EmployeeController.class);

	/**
	 * 
	 */
	public EmployeeController() {

	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public List<Tool> getToolsOfMachine(String machineCode, String companyCode) {
		String sql = "select tools.ToolId, tools.ToolCode "
				+ " from tools inner join toolsmachineTray on tools.ToolCode = toolsmachineTray.ToolCode "
				+ " where tools.IsActive = '1' and tools.CompanyCode = '" + companyCode
				+ "' and toolsmachineTray.MachineCode = '" + machineCode + "';";
		// System.out.println(sql);
		logger.info(sql);
		List<Tool> result = new ArrayList<>();
		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				int toolId = Integer.parseInt(rs.getString(1));
				String toolName = rs.getString(2);

				Tool tool = new Tool();
				tool.setToolName(toolName);
				tool.setToolId(toolId);
				result.add(tool);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			mysqlConnect.disconnect();
		}
		logger.info(result);
		return result;
	}

	public boolean updateToolTray(String machineCode, String tool, String tray, String quantity) {

		String sql = " select toolsmachine.ToolsMachineID "
				+ "from tools inner join toolsmachine on tools.ToolCode = toolsmachine.ToolCode "
				+ "where tools.ToolCode = '" + tool + "' and toolsmachine.MachineCode = '" + machineCode + "';";
		logger.info(sql);
		String toolsMachineID = "";
		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				toolsMachineID = rs.getString(1);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		} finally {
			mysqlConnect.disconnect();
		}

		sql = " select count(*) from toolsmachinetray where toolsmachinetray.ToolCode = '" + tool
				+ "' and toolsmachinetray.TrayIndex = '" + tray + "' and toolsmachinetray.MachineCode = '" + machineCode
				+ "';";
		logger.info(sql);
		int countExisted = 0;
		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				countExisted = Integer.valueOf(rs.getString(1));
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		} finally {
			mysqlConnect.disconnect();
		}

		if (countExisted == 0) {
			sql = " insert into toolsmachinetray (MachineCode, ToolCode, TrayIndex, Quantity, IsActive) values ('"
					+ machineCode + "', '" + tool + "', '" + tray + "', '" + quantity + "', 1);";
		} else {

			if ("-1".equals(quantity)) {
				sql = " update toolsmachinetray set quantity = quantity - 1 where ToolCode = '" + tool
						+ "' and trayIndex = '" + tray + "' and machineCode = '" + machineCode + "';";
			} else {
				// sql = " update toolsmachinetray set quantity = '" + quantity
				// + "' where ToolsMachineID = '" + toolsMachineID
				// + "' and trayIndex = '" + tray + "';";
				sql = " update toolsmachinetray set quantity = '" + quantity + "' where ToolCode = '" + tool
						+ "' and trayIndex = '" + tray + "' and machineCode = '" + machineCode + "';";
			}
		}
		logger.info(sql);

		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			int countResult = statement.executeUpdate();
			if (countResult > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			return false;
		} finally {
			mysqlConnect.disconnect();
		}

		return true;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public HashMap<String, List<List<Object>>> getToolTrayQuantity(String machineCode, int minVal) {
		String sql = " select tools.ToolId, tools.ToolCode ,  toolsmachineTray.Quantity, toolsmachineTray.TrayIndex, toolsmachineTray.Machinecode "
				+ " from tools inner join toolsmachineTray on tools.ToolCode = toolsmachineTray.ToolCode "
				+ " where tools.IsActive = '1' and toolsmachineTray.MachineCode = '" + machineCode + "';";
		// System.out.println(sql);
		logger.info(sql);
		HashMap<String, List<List<Object>>> result = new HashMap<>();
		List<String> availableTools = new ArrayList<>();
		Set<String> toolTrays = new HashSet<>();
		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String toolName = rs.getString(2);
				String trayIndex = rs.getString(4);
				String toolTray = toolName + "_" + trayIndex;
				int quantity = Integer.parseInt(rs.getString(3));
				if (toolTrays.contains(toolTray)) {
					logger.warn("Machine " + machineCode + ": " + toolName + " - " + trayIndex + " existed.");
				}
				if (quantity > minVal) {
					availableTools.add(toolName);

					if (result.containsKey(toolName)) {
						List<List<Object>> existedData = result.get(toolName);
						List<Object> tmpList = new ArrayList<>();
						tmpList.add(trayIndex);
						tmpList.add(quantity);
						existedData.add(tmpList);
						result.put(toolName, existedData);
					} else {
						List<Object> tmpList = new ArrayList<>();
						tmpList.add(trayIndex);
						tmpList.add(quantity);
						List<List<Object>> existedData = new ArrayList<>();
						existedData.add(tmpList);
						result.put(toolName, existedData);
					}
				}
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			mysqlConnect.disconnect();
		}
		logger.info(result);
		return result;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public List<Tool> getAllTools(String companyCode) {
		String sql = " select tools.ToolCode from tools where tools.IsActive='1' and tools.companyCode = '"
				+ companyCode + "';";
		// System.out.println(sql);
		logger.info(sql);
		HashMap<String, List<List<Object>>> result = new HashMap<>();
		List<Tool> availableTools = new ArrayList<>();
		try {

			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String toolName = rs.getString(1);
				Tool tool = new Tool();
				tool.setToolName(toolName);
				availableTools.add(tool);
			}

		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		} finally {
			mysqlConnect.disconnect();
		}
		logger.info(result);
		return availableTools;
	}

	/**
	 * 
	 * @return
	 */
	public List<Assessor> getAllUsers() {
		String sql = "SELECT AssessorID, UserName, Password, CompanyId FROM Assessor where Assessor.IsActive=1;";
		logger.info(sql);
		List<Assessor> listAllUsers = new ArrayList<>();
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				int userId = Integer.parseInt(rs.getString(1));
				String username = rs.getString(2);
				String password = rs.getString(3);
				String companyCode = rs.getString(4);

				Assessor user = new Assessor(username, password, companyCode);
				user.setAssessorId(userId);
				listAllUsers.add(user);
			}
			return listAllUsers;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return listAllUsers;
		} finally {
			mysqlConnect.disconnect();
		}
	}

	/**
	 * 
	 */
	public List<Role> getUserRoles(String userName, String companyCode) {
		String sql = "select distinct RoleName "
				+ " from assessor inner join roleassessor  on assessor.AssessorID = roleassessor.AssessorID"
				+ " inner join roles on roles.RoleID = roleassessor.RoleID where assessor.IsIsActive = '1' and assessor.CompanyCode = '"
				+ companyCode + "' and assessor.UserName= '" + userName + "'";
		logger.info(sql);
		List<Role> listAllRoles = new ArrayList<>();
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String roleName = rs.getString(1);
				Role role = new Role(0, roleName);

				listAllRoles.add(role);
			}
			logger.info(listAllRoles);
			return listAllRoles;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return listAllRoles;
		} finally {
			mysqlConnect.disconnect();
		}
	}

	public List<Machine> findAvailableMachine(String companyCode, String selectValue) {
		String sql = "select tools.ToolID, tools.ToolCode,machine.MachineCode, toolsmachinetray.quantity "
				+ " from company " + "	inner join companymachine "
				+ "				on companymachine.CompanyCode = company.CompanyCode " + "	inner join machine  "
				+ "				on machine.MachineCode = companymachine.MachineCode " + "	inner join toolsMachine  "
				+ "				on toolsMachine.MachineCode = machine.MachineCode " + "	inner join Tools  "
				+ "				on Tools.ToolCode = toolsMachine.ToolCode " + "	left join toolsMachineTray "
				+ "				on toolsMachineTray.toolsMachineID = toolsMachine.toolsMachineID "
				+ "where Tools.Isactive = '1' and company.CompanyCode = '" + companyCode + "' and tools.ToolCode = '"
				+ selectValue + "';";
		// System.out.println(sql);
		logger.info(sql);
		List<Machine> listAllMachines = new ArrayList<>();
		try {
			PreparedStatement statement = mysqlConnect.connect().prepareStatement(sql);
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				// int toolId = Integer.valueOf(rs.getString(1));
				String machineCode = rs.getString(3);
				Machine machine = new Machine(machineCode);

				listAllMachines.add(machine);
			}
			logger.info(listAllMachines);
			return listAllMachines;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return listAllMachines;
		} finally {
			mysqlConnect.disconnect();
		}
	}
}
