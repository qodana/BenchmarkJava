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
import java.io.IOException
import java.net.URLDecoder
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/sqli-00/BenchmarkTest00206"])
class BenchmarkTest00206 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        if (request.getHeader("BenchmarkTest00206") != null) {
            param = request.getHeader("BenchmarkTest00206")
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = URLDecoder.decode(param, "UTF-8")

        // Chain a bunch of propagators in sequence
        val a13396 = param // assign
        val b13396 = StringBuilder(a13396) // stick in stringbuilder
        b13396.append(" SafeStuff") // append some safe content
        b13396.replace(
            b13396.length - "Chars".length,
            b13396.length,
            "Chars"
        ) // replace some of the end content
        val map13396 = HashMap<String, Any>()
        map13396["key13396"] = b13396.toString() // put in a collection
        val c13396 = map13396["key13396"] as String? // get it back out
        val d13396 = c13396!!.substring(0, c13396.length - 1) // extract most of it
        val e13396 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d13396.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f13396 = e13396.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g13396 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g13396) // reflection

        val sql = "INSERT INTO users (username, password) VALUES ('foo','$bar')"

        try {
            val statement =
                DatabaseHelper.sqlStatement
            val count = statement!!.executeUpdate(sql, arrayOf("USERNAME", "PASSWORD"))
            DatabaseHelper.outputUpdateComplete(sql, response)
        } catch (e: SQLException) {
            if (DatabaseHelper.hideSQLErrors) {
                response.writer.println("Error processing request.")
                return
            } else throw ServletException(e)
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
