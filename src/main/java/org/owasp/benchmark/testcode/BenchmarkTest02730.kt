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



@WebServlet(value = ["/sqli-06/BenchmarkTest02730"])
class BenchmarkTest02730 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        val param = scr.getTheValue("BenchmarkTest02730")

        val bar = doSomething(request, param)

        val sql = "SELECT * from USERS where USERNAME=? and PASSWORD='$bar'"

        try {
            val connection =
                DatabaseHelper.sqlConnection
            val statement =
                connection!!.prepareStatement(sql, arrayOf("Column1", "Column2"))
            statement.setString(1, "foo")
            statement.execute()
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

            val a81593 = param // assign
            val b81593 = StringBuilder(a81593) // stick in stringbuilder
            b81593.append(" SafeStuff") // append some safe content
            b81593.replace(
                b81593.length - "Chars".length,
                b81593.length,
                "Chars"
            ) // replace some of the end content
            val map81593 = HashMap<String, Any>()
            map81593["key81593"] = b81593.toString() // put in a collection
            val c81593 = map81593["key81593"] as String? // get it back out
            val d81593 = c81593!!.substring(0, c81593.length - 1) // extract most of it
            val e81593 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d81593.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f81593 =
                e81593.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g81593 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g81593) // reflection

            return bar
        }
    }
}
