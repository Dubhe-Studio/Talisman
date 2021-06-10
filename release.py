# -*- coding: utf-8 -*-
# by Cjsah

import os, re

files = [file for file in os.listdir(os.path.join(os.getcwd(), "build", "libs")) if re.match('[\s\S]+-[0-9]+\.[0-9]+\.[0-9]+.jar', file)]

if len(files) > 0:
    file = files[-1]
    tag = file.split("-")[1][:-4]
    os.system('""C:\Program Files (x86)\GitHub CLI\gh" release create {a} --title "{a}" --notes ""'.format(a=tag))
    os.system('"C:\Program Files (x86)\GitHub CLI\gh" release upload {} {}/build/libs/{}'.format(tag, os.getcwd().replace("\\", "/"), file))
