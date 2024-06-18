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
import org.owasp.benchmark.helpers.ThingFactory
import java.io.IOException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/xss-00/BenchmarkTest00383"])
class BenchmarkTest00383 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest00383")
        if (param == null) param = ""

        // Chain a bunch of propagators in sequence
        val a2196 = param // assign
        val b2196 = StringBuilder(a2196) // stick in stringbuilder
        b2196.append(" SafeStuff") // append some safe content
        b2196.replace(
            b2196.length - "Chars".length,
            b2196.length,
            "Chars"
        ) // replace some of the end content
        val map2196 = HashMap<String, Any>()
        map2196["key2196"] = b2196.toString() // put in a collection
        val c2196 = map2196["key2196"] as String? // get it back out
        val d2196 = c2196!!.substring(0, c2196.length - 1) // extract most of it
        val e2196 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d2196.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f2196 = e2196.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val bar = thing.doSomething(f2196) // reflection

        response.setHeader("X-XSS-Protection", "0")
        val obj = arrayOf("a", "b")
        response.writer.printf(Locale.US, bar, *obj)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
