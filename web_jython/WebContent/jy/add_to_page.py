#######################################################################
# add_to_page.py
#
# Simple servlet that takes some text from a web page and redisplays
# it.
#######################################################################
import java, javax, sys

class add_to_page(javax.servlet.http.HttpServlet):
    def doGet(self, request, response):
        self.doPost(request, response)

    def doPost(self, request, response):
        addtext = request.getParameter("p")
        if not addtext:
            addtext = ""
        request.setAttribute("page_text", addtext)
        dispatcher = request.getRequestDispatcher("../testJSP.jsp")
        dispatcher.forward(request, response)