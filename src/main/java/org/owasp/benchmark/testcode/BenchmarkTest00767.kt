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
import org.springframework.dao.EmptyResultDataAccessException
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/sqli-01/BenchmarkTest00767"])
class BenchmarkTest00767 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest00767")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        val bar: String
        val guess = "ABC"
        val switchTarget = guess[2]

        bar = when (switchTarget) {
            'A' -> param
            'B' -> "bobs_your_uncle"
            'C', 'D' -> param
            else -> "bobs_your_uncle"
        }
        val sql = "SELECT userid from USERS where USERNAME='foo' and PASSWORD='$bar'"
        try {
            // Long results =
            // org.owasp.benchmark.helpers.DatabaseHelper.JDBCtemplate.queryForLong(sql);
            val results =
                DatabaseHelper.JDBCtemplate.queryForObject(
                    sql, Long::class.java
                )
            response.writer.println("Your results are: $results")
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
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
