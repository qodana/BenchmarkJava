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



@WebServlet(value = ["/sqli-05/BenchmarkTest02539"])
class BenchmarkTest02539 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest02539")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        val bar = doSomething(request, param)

        val sql = "SELECT  * from USERS where USERNAME='foo' and PASSWORD='$bar'"
        try {
            val results =
                DatabaseHelper.JDBCtemplate.queryForRowSet(sql)
            response.writer.println("Your results are: ")

            //		System.out.println("Your results are");
            while (results.next()) {
                response.writer
                    .println(
                        ESAPI
                            .encoder()
                            .encodeForHTML(results.getString("USERNAME"))
                                + " "
                    )
                //			System.out.println(results.getString("USERNAME"));
            }
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

            val a9290 = param // assign
            val b9290 = StringBuilder(a9290) // stick in stringbuilder
            b9290.append(" SafeStuff") // append some safe content
            b9290.replace(
                b9290.length - "Chars".length,
                b9290.length,
                "Chars"
            ) // replace some of the end content
            val map9290 = HashMap<String, Any>()
            map9290["key9290"] = b9290.toString() // put in a collection
            val c9290 = map9290["key9290"] as String? // get it back out
            val d9290 = c9290!!.substring(0, c9290.length - 1) // extract most of it
            val e9290 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d9290.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f9290 =
                e9290.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g9290 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g9290) // reflection

            return bar
        }
    }
}
