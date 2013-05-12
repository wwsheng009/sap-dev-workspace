# -*- coding: utf-8 -*-
'''
Created on 2013-5-3

@author: wangweisheng
'''

#new_list = list.decode('utf-8')
s="[u'T501', u'MANDT', u'1', u'0001', u'000000', u'MANDT', u'MANDT', u'T000', u'000003', u'000006', u'000003', u'000000', u'CLNT', u'C', u'', u'', u'T501', u'', u'', u'', u'', u'0000', u'', u'03', u'10', u'15', u'20', u'\u5ba2\u6237\u7aef', u'\u7c7b', u'\u96c6\u56e2', u'\u5ba2\u6237\u7aef', u'\u5ba2\u6237\u7aef', u'X', u'', u'', u'', u'', u'', u'', u'', u'X', u'X', u'E', u'MANDT', u'', u''][u'T501', u'PERSG', u'1', u'0002', u'000006', u'PERSG', u'PERSG', u'', u'000001', u'000002', u'000001', u'000000', u'CHAR', u'C', u'', u'', u'T501', u'', u'PRG', u'X', u'', u'0000', u'', u'05', u'10', u'12', u'20', u'\u5458\u5de5\u7ec4', u'\u5de5\u7ec4', u'\u5458\u5de5\u7ec4', u'\u5458\u5de5\u7ec4', u'\u5458\u5de5\u7ec4', u'X', u'', u'', u'', u'', u'', u'', u'', u'X', u'', u'E', u'PERSG', u'', u'']"
print s.decode('unicode-escape')