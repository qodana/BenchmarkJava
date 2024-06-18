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
import org.owasp.benchmark.helpers.DatabaseHelper
import org.owasp.benchmark.helpers.ThingFactory
import org.owasp.esapi.ESAPI
import org.springframework.dao.DataAccessException
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/sqli-01/BenchmarkTest00599"])
class BenchmarkTest00599 : HttpServlet() {
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
                    if (value == "BenchmarkTest00599") {
                        param = name
                        flag = false
                    }
                    i++
                }
            }
        }

        // Chain a bunch of propagators in sequence
        val a99467 = param // assign
        val b99467 = StringBuilder(a99467) // stick in stringbuilder
        b99467.append(" SafeStuff") // append some safe content
        b99467.replace(
            b99467.length - "Chars".length,
            b99467.length,
            "Chars"
        ) // replace some of the end content
        val map99467 = HashMap<String, Any>()
        map99467["key99467"] = b99467.toString() // put in a collection
        val c99467 = map99467["key99467"] as String? // get it back out
        val d99467 = c99467!!.substring(0, c99467.length - 1) // extract most of it
        val e99467 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d99467.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f99467 = e99467.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g99467 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g99467) // reflection

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
