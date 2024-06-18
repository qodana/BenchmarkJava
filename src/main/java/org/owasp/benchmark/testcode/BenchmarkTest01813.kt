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
 * @author Dave Wichers
 * @created 2015
 */
package org.owasp.benchmark.testcode

import org.owasp.benchmark.helpers.*
import org.owasp.esapi.ESAPI
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/sqli-03/BenchmarkTest01813"])
class BenchmarkTest01813 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        val param = scr.getTheValue("BenchmarkTest01813")

        val bar = Test().doSomething(request, param)

        val sql =
            "SELECT TOP 1 USERNAME from USERS where USERNAME='foo' and PASSWORD='$bar'"
        try {
            val results: Any =
                DatabaseHelper.JDBCtemplate.queryForObject(
                    sql, arrayOf(), String::class.java
                )
            response.writer.println("Your results are: ")

            //		System.out.println("Your results are");
            response.writer
                .println(ESAPI.encoder().encodeForHTML(results.toString()))
            //		System.out.println(results.toString());
        } catch (e: EmptyResultDataAccessException) {
            response.writer
                .println(
                    "No results returned for query: "
                            + ESAPI.encoder().encodeForHTML(sql)
                )
        } catch (e: DataAccessException) {
            if (DatabaseHelper.hideSQLErrors) {
                response.writer.println("Error processing request.")
            } else throw ServletException(e)
        }
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String {
            val bar: String

            // Simple if statement that assigns param to bar on true condition
            val num = 196
            bar = if ((500 / 42) + num > 200) param
            else "This should never happen"

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

