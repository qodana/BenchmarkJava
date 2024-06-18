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
import java.io.IOException
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/sqli-05/BenchmarkTest02367"])
class BenchmarkTest02367 : HttpServlet() {
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
                    if (value == "BenchmarkTest02367") {
                        param = name
                        flag = false
                    }
                    i++
                }
            }
        }

        val bar = doSomething(request, param)

        val sql = "SELECT * from USERS where USERNAME='foo' and PASSWORD='$bar'"

        try {
            val statement =
                DatabaseHelper.sqlStatement
            statement!!.execute(sql, intArrayOf(1, 2))
            DatabaseHelper.printResults(statement, sql, response)
        } catch (e: SQLException) {
            if (DatabaseHelper.hideSQLErrors) {
                response.writer.println("Error processing request.")
                return
            } else throw ServletException(e)
        }
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a41891 = param // assign
            val b41891 = StringBuilder(a41891) // stick in stringbuilder
            b41891.append(" SafeStuff") // append some safe content
            b41891.replace(
                b41891.length - "Chars".length,
                b41891.length,
                "Chars"
            ) // replace some of the end content
            val map41891 = HashMap<String, Any>()
            map41891["key41891"] = b41891.toString() // put in a collection
            val c41891 = map41891["key41891"] as String? // get it back out
            val d41891 = c41891!!.substring(0, c41891.length - 1) // extract most of it
            val e41891 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d41891.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f41891 =
                e41891.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g41891 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g41891) // reflection

            return bar
        }
    }
}
