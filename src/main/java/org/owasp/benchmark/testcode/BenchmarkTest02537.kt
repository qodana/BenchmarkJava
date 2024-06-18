/**
 * OWASP Benchmark Project v1.2
 *
 *
 * This file is part of the Open Web Application Security Project (OWASP) Benchmark Project. For
 * details, please see [https://owasp.org/www-project-benchmark/](https://owasp.org/www-project-benchmark/).
 *
 *
 * The OWASP Benchmark is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, version 2.
 *
 *
 * The OWASP Benchmark is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details.
 *
 * @author Nick Sanidas
 * @created 2015
 */
package org.owasp.benchmark.testcode

import org.owasp.benchmark.helpers.DatabaseHelper
import org.owasp.esapi.ESAPI
import org.springframework.dao.DataAccessException
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/sqli-05/BenchmarkTest02537"])
class BenchmarkTest02537 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest02537")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        val bar = doSomething(request, param)

        try {
            val sql = "SELECT * from USERS where USERNAME='foo' and PASSWORD='$bar'"

            DatabaseHelper.JDBCtemplate.batchUpdate(sql)
            response.writer
                .println(
                    "No results can be displayed for query: "
                            + ESAPI.encoder().encodeForHTML(sql)
                            + "<br>"
                            + " because the Spring batchUpdate method doesn't return results."
                )
            //		System.out.println("no results for query: " + sql + " because the Spring batchUpdate
            // method doesn't return results.");
        } catch (e: DataAccessException) {
            if (DatabaseHelper.hideSQLErrors) {
                response.writer.println("Error processing request.")
            } else throw ServletException(e)
        }
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String {
            val bar: String

            // Simple if statement that assigns param to bar on true condition
            val num = 196
            bar = if ((500 / 42) + num > 200) param
            else "This should never happen"

            return bar
        }
    }
}
