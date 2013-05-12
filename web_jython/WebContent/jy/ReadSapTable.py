# -*- coding: utf-8 -*-
import sys
sys.setdefaultencoding( "utf-8" )
from javax.servlet.http import HttpServlet
import rfcFunction1
import json
class ReadSapTable (HttpServlet):
    def doGet(self,request,response):
        self.doPost (request,response)

    def doPost(self,request,response):


        tableName = request.getParameter("tableName")
        if not tableName:
            result = "<font color='red'>Please input Table Name</font>"
        else:
            tableContent = rfcFunction1.getTableFieldInfo(tableName)
            #print tableContent
            
      #  a = ''.join(mara)
            #result = ''.join(map(str, tableContent))
            #result = result.decode('unicode-escape')
            #print result
            #s = result.decode('unicode-escape')
            #result = json.dumps([dict(mpn=pn) for pn in tableContent])
            #result = json.dumps([pn for pn in tableContent])
            result = json.dumps(tableContent)
            #result = str(result)
            #result = json.dumps(tableContent)
            #x = result.decode('unicode_escape')
            #result = json.load(result)
            #result = json.JSONEncoder(tableContent)
            #result = result.decode('utf-8') 
            #print tableContent.decode('unicode-escape')
            result = result.decode('unicode-escape')
            #print s
            #print s
        request.setAttribute("result",result)
        #request.setCharacterEncoding("utf-8")
        dispatcher = request.getRequestDispatcher("../testReadSapTable.jsp")
        #response.setCharacterEncoding("utf-8")
        dispatcher.forward(request,response)
        
    def getServletInfo(self):
        return "Short Description"
    
    