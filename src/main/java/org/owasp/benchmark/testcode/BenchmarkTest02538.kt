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

import org.apache.commons.codec.binary.Base64
import org.owasp.benchmark.helpers.*
import org.owasp.benchmark.helpers.ThingFactory
import org.owasp.esapi.ESAPI
import org.springframework.dao.DataAccessException
import org.springframework.dao.EmptyResultDataAccessException
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/sqli-05/BenchmarkTest02538"])
class BenchmarkTest02538 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest02538")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        val bar = doSomething(request, param)

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

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a72634 = param // assign
            val b72634 = StringBuilder(a72634) // stick in stringbuilder
            b72634.append(" SafeStuff") // append some safe content
            b72634.replace(
                b72634.length - "Chars".length,
                b72634.length,
                "Chars"
            ) // replace some of the end content
            val map72634 = HashMap<String, Any>()
            map72634["key72634"] = b72634.toString() // put in a collection
            val c72634 = map72634["key72634"] as String? // get it back out
            val d72634 = c72634!!.substring(0, c72634.length - 1) // extract most of it
            val e72634 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d72634.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f72634 =
                e72634.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g72634 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g72634) // reflection

            return bar
        }
    }
}
