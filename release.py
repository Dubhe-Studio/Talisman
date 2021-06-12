# -*- coding: utf-8 -*-
# by Cjsah

import os, re, requests
from lxml import etree


def rename(name, num):
    file = os.path.join(os.getcwd(), 'build', 'libs')
    result = '{}+build.{}.jar'.format(name[:-4], num)
    os.rename(os.path.join(file, name), os.path.join(file, result))
    return result


if __name__ == '__main__':
    files = [file for file in os.listdir(os.path.join(os.getcwd(), 'build', 'libs')) if re.match('[\s\S]+-[0-9]+\.[0-9]+\.[0-9]+.jar', file)]

    if len(files) > 0:
        xml = requests.get('http://ab.elyimg.pro/guestAuth/app/rest/builds').content
        number = etree.XML(xml).xpath('//builds/build/@number')[0]
        file = rename(files[-1], number)
        tag = re.split('[-+]', file)[-2]
        os.system('"C:\Program Files (x86)\GitHub CLI\gh" auth login --with-token < githubtoken.txt')
        os.system('""C:\Program Files (x86)\GitHub CLI\gh" release create {a} --title "{a}" --notes ""'.format(a=tag))
        os.system('"C:\Program Files (x86)\GitHub CLI\gh" release upload {} {}/build/libs/{}'.format(tag, os.getcwd().replace("\\", "/"), file))
    else print('没有可发布的release')