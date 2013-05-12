# -*- coding: utf-8 -*-
import sapint
destName = 'LH205'
destination = sapint.getDestination(destName)

destination.ping()
print("Distination " + destName + " works")

#把rfctable转换成jython数组,根据RFC表的列数，动态生成一个数组
def rfcTableToList(fieldtab):
    rows = []
    #print("Table Rows Count: " + fieldtab.getNumRows())
    for r in range(fieldtab.getNumRows()):
        fieldtab.setRow(r)
        row = []
        for c in range(fieldtab.getFieldCount()):
            row.append(fieldtab.getValue(c))
        rows.append(row)
    return rows

#封装SAP系统的标准函数DDIF_FIELDINFO_GET
def getTableFieldInfo(_tableName):
    rfcFunctionSearch = destination.getRepository().getFunction("DDIF_FIELDINFO_GET")
    rfcFunctionSearch.getImportParameterList().setValue("TABNAME", _tableName)
    rfcFunctionSearch.execute(destination)
    
    fieldtab = rfcFunctionSearch.getTableParameterList().getTable("DFIES_TAB")
    
    #fieldtab.getFieldCount()
    #fieldtab.getNumRows()
    
    #range(fieldtab.getFieldCount())
    # rows = []
    # for r in range(fieldtab.getNumRows()):
    #     fieldtab.setRow(r)
    #     row = []
    #     for c in range(fieldtab.getFieldCount()):
    #         row.append(fieldtab.getValue(c))
    #     rows.append(row)
    # return Rows
    return rfcTableToList(fieldtab)

#封装一个SAP标准RFC函数RFC_FUNCTION_SEARCH
def searchRfcFunction(_funame,functionGroup):
    print("Start Searching..........\n\r")
    RFC_FUNCTION_SEARCH = destination.getRepository().getFunction("RFC_FUNCTION_SEARCH");
    RFC_FUNCTION_SEARCH.getImportParameterList().setValue("FUNCNAME", _funame)
    RFC_FUNCTION_SEARCH.getImportParameterList().setValue("GROUPNAME", functionGroup)
    RFC_FUNCTION_SEARCH.execute(destination);
    FUNCTIONS = RFC_FUNCTION_SEARCH.getTableParameterList().getTable("FUNCTIONS")
    return rfcTableToList(FUNCTIONS)


# result = searchRfcFunction('z*',"")
# print(result)
table = getTableFieldInfo("T100")

print(table)

