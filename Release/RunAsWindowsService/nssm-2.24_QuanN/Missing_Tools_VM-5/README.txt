

1. Delete service HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services

2. cd to RunAsWindowsService\nssm-2.24\win32

3. nssm install check_missing_tool ==> point to bat file

http://giordanomaestro.blogspot.com/2013/01/running-java-applications-as-windows.html


net start check_missing_tool
net stop check_missing_tool
net restart check_missing_tool


