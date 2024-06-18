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



@WebServlet(value = ["/sqli-03/BenchmarkTest01469"])
class BenchmarkTest01469 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        var flag = true
        val names = request.parameterNames
        while (names.hasMoreElements() && flag) {
            val name = names.nextElement() as String
            val values = request.getParameterValues(name)
            if (values != null) {
                var i = 0
                while (i < values.size && flag) {
                    val value = values[i]
                    if (value == "BenchmarkTest01469") {
                        param = name
                        flag = false
                    }
                    i++
                }
            }
        }

        val bar = Test().doSomething(request, param)

        val sql = "SELECT * from USERS where USERNAME='foo' and PASSWORD='$bar'"
        try {
            val list =
                DatabaseHelper.JDBCtemplate.queryForList(sql)
            response.writer.println("Your results are: <br>")

            //		System.out.println("Your results are");
            for (o in list) {
                response.writer
                    .println(
                        ESAPI.encoder().encodeForHTML(o.toString())
                                + "<br>"
                    )
                //			System.out.println(o.toString());
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
                return
            } else throw ServletException(e)
        }
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a83916 = param // assign
            val b83916 = StringBuilder(a83916) // stick in stringbuilder
            b83916.append(" SafeStuff") // append some safe content
            b83916.replace(
                b83916.length - "Chars".length,
                b83916.length,
                "Chars"
            ) // replace some of the end content
            val map83916 = HashMap<String, Any>()
            map83916["key83916"] = b83916.toString() // put in a collection
            val c83916 = map83916["key83916"] as String? // get it back out
            val d83916 = c83916!!.substring(0, c83916.length - 1) // extract most of it
            val e83916 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d83916.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f83916 =
                e83916.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g83916 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g83916) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

