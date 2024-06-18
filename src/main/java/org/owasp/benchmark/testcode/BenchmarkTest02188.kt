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



@WebServlet(value = ["/sqli-04/BenchmarkTest02188"])
class BenchmarkTest02188 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest02188")
        if (param == null) param = ""

        val bar = doSomething(request, param)

        val sql = "SELECT * from USERS where USERNAME='foo' and PASSWORD='$bar'"

        try {
            val statement =
                DatabaseHelper.sqlStatement
            val rs = statement!!.executeQuery(sql)
            DatabaseHelper.printResults(rs, sql, response)
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

            val a16667 = param // assign
            val b16667 = StringBuilder(a16667) // stick in stringbuilder
            b16667.append(" SafeStuff") // append some safe content
            b16667.replace(
                b16667.length - "Chars".length,
                b16667.length,
                "Chars"
            ) // replace some of the end content
            val map16667 = HashMap<String, Any>()
            map16667["key16667"] = b16667.toString() // put in a collection
            val c16667 = map16667["key16667"] as String? // get it back out
            val d16667 = c16667!!.substring(0, c16667.length - 1) // extract most of it
            val e16667 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d16667.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f16667 =
                e16667.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g16667 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g16667) // reflection

            return bar
        }
    }
}
