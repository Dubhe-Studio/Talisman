# -*- coding: UTF-8 -*-
import os
import re
files = [file for file in os.listdir(os.path.join(os.getcwd(), "build", "libs")) if re.match('[\s\S]+-[0-9]+\.[0-9]+\.[0-9]+.jar', file)]

if len(files) > 0:
    file = files[-1]
    tag = file.split("-")[1][:-4]
    os.popen(''C:\Program Files (x86)\GitHub CLI\gh.exe' release create {a} --title "{a}" --notes "" -R Dubhe-Studio/Talisman'.format(a=tag))
    os.popen("'C:\Program Files (x86)\GitHub CLI\gh.exe' release upload {} build/libs/{} -R Dubhe-Studio/Talisman".format(tag, file))
