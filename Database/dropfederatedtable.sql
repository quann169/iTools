DROP TABLE IF EXISTS federated_Company;
DROP TABLE IF EXISTS federated_Assessor;
DROP TABLE IF EXISTS federated_Roles;
DROP TABLE IF EXISTS federated_RoleAssessor;
DROP TABLE IF EXISTS federated_Machine;
DROP TABLE IF EXISTS federated_CompanyMachine;
DROP TABLE IF EXISTS federated_Tools;
DROP TABLE IF EXISTS federated_ToolsMachine;
DROP TABLE IF EXISTS federated_ToolsMachineTray;
DROP TABLE IF EXISTS federated_WorkingTransaction;
DROP TABLE IF EXISTS federated_MasterLog;


call updateFromHost();

call updateFromLocal();