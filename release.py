# -*- coding: utf-8 -*-
# by Cjsah

import os, re

files = [file for file in os.listdir(os.path.join(os.getcwd(), "build", "libs")) if re.match('[\s\S]+-[0-9]+\.[0-9]+\.[0-9]+.jar', file)]

tag = files[0].split("-")[1][:-4]

if len(files) > 0:
    os.system('gh release create {a} --title "{a}" --notes ""'.format(a=tag))
for file in files:
    os.system("gh release upload {} build/libs/{}".format(tag, file))
