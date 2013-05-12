# -*- coding: utf-8 -*-
import sys;
sys.path.append("E:\\jython\\Lib\\sapjco3.jar");


import java.util.HashMap;
import java.util.Properties;
from com.sap.conn.jco import JCoDestination;
from com.sap.conn.jco import JCoDestinationManager;
from com.sap.conn.jco import JCoException;
from com.sap.conn.jco.ext import DataProviderException;
from com.sap.conn.jco.ext import DestinationDataEventListener;
from com.sap.conn.jco.ext import DestinationDataProvider;

#from com.sap.conn.jco import *

class BackupDestinationConfiguration (DestinationDataProvider):
    def getDestinationProperties(self,name):
        if name == "LH205":
            connectProperties = java.util.Properties();
            connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,"192.168.0.205");
            connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,"00");
            connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,"500");
            connectProperties.setProperty(DestinationDataProvider.JCO_USER,"wwsheng");
            connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,"wwsheng");
            connectProperties.setProperty(DestinationDataProvider.JCO_LANG,"zh");
            connectProperties.setProperty(DestinationDataProvider.JCO_SAPROUTER,"/H/183.62.136.248/H/" );
            return connectProperties
    def ChangeEventsSupported(self):
        return 0