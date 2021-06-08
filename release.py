# -*- coding: utf-8 -*-
# by Cjsah

import os, re, sys

args = sys.argv[1:]

files = [file for file in os.listdir(os.path.join(os.getcwd(), "build", "libs")) if re.match('[\s\S]+-[0-9]+\.[0-9]+\.[0-9]+.jar', file)]

if len(files) > 0:
    os.system('gh release create {a} --title "{a}" --notes {b}'.format(a=args[0], b=args[1]))
for file in files:
    os.system("gh release upload {} build/libs/{}".format(args[0], file))
