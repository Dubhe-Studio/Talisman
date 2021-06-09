# -*- coding: utf-8 -*-
# by Cjsah

from __future__ import print_function
import ctypes, sys, os, re

def is_admin():
    try:
        return ctypes.windll.shell32.IsUserAnAdmin()
    except:
        return False
if is_admin():
    # 将要运行的代码加到这里
    files = [file for file in os.listdir(os.path.join(os.getcwd(), "build", "libs")) if re.match('[\s\S]+-[0-9]+\.[0-9]+\.[0-9]+.jar', file)]

    if len(files) > 0:
        file = files[-1]
        tag = file.split("-")[1][:-4]
        os.popen('gh release create {a} --title "{a}" --notes "" -R Dubhe-Studio/Talisman'.format(a=tag))
        os.popen("gh release upload {} build/libs/{} -R Dubhe-Studio/Talisman".format(tag, file))
else:
    if sys.version_info[0] == 3:
        ctypes.windll.shell32.ShellExecuteW(None, "runas", sys.executable, __file__, None, 1)
    else:#in python2.x
        ctypes.windll.shell32.ShellExecuteW(None, u"runas", unicode(sys.executable), unicode(__file__), None, 1)
