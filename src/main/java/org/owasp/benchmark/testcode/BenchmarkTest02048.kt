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
import java.net.URLDecoder
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/xss-03/BenchmarkTest02048"])
class BenchmarkTest02048 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("Referer")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        val bar = doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        val obj = arrayOf("a", bar)
        response.writer.printf(Locale.US, "Formatted like: %1\$s and %2\$s.", *obj)
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a96053 = param // assign
            val b96053 = StringBuilder(a96053) // stick in stringbuilder
            b96053.append(" SafeStuff") // append some safe content
            b96053.replace(
                b96053.length - "Chars".length,
                b96053.length,
                "Chars"
            ) // replace some of the end content
            val map96053 = HashMap<String, Any>()
            map96053["key96053"] = b96053.toString() // put in a collection
            val c96053 = map96053["key96053"] as String? // get it back out
            val d96053 = c96053!!.substring(0, c96053.length - 1) // extract most of it
            val e96053 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d96053.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f96053 =
                e96053.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g96053 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g96053) // reflection

            return bar
        }
    }
}
