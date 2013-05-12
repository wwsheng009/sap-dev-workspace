#######################################################################
# add_numbers.py
#
# Calculates the sum for two numbers and returns it.
#######################################################################
import javax

class add_numbers(javax.servlet.http.HttpServlet):
    def doGet(self, request, response):
        self.doPost(request, response)

    def doPost(self, request, response):
        x = request.getParameter("x")
        y = request.getParameter("y")
        if not x or not y:
            sum = "<font color='red'>You must place numbers in each value box</font>"
        else:
            try:
                sum = int(x) + int(y)
            except ValueError, e:
                sum = "<font color='red'>You must place numbers only in each value box</font>"
        request.setAttribute("sum", sum)

        dispatcher = request.getRequestDispatcher("../testJSP.jsp")
        dispatcher.forward(request, response)