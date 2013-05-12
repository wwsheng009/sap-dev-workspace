# -*- coding: utf-8 -*- 
#作者：王卫生
#时间：2013-3-30

import sys
import clr
#import System


#添加VB DLL的目录
from System.IO import Directory,Path
directory = Directory.GetCurrentDirectory()
binpath = Path.Combine(directory,'bin\\')
binpath = 'D:\\wangws\\sap_interface_project\\SAPAutomation\\SAPGuiInspector\\bin\\release\\'
#sys.path.append(r'D:\wangws\sap_interface_project\SAPAutomation\SAPGuiInspector\bin\Debug')
#sys.path.append(System.AppDomain.CurrentDomain.BaseDirectory)
sys.path.append(binpath)

filename = 'SapGuiApplicationVB.dll'

clr.AddReferenceToFile(filename)

from SapGuiApplicationVB import *
sapgui =  SapGuiConnector()

Session = sapgui.getSessionByTcode("MB03")

import System
from System.Collections import *
h = Hashtable()
h["a"] = "IronPython"
