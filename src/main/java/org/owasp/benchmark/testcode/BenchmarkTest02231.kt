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
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/xss-04/BenchmarkTest02231"])
class BenchmarkTest02231 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val map = request.parameterMap
        var param = ""
        if (!map.isEmpty()) {
            val values = map["BenchmarkTest02231"]
            if (values != null) param = values[0]
        }

        val bar = doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        val obj = arrayOf(bar, "b")
        response.writer.printf("Formatted like: %1\$s and %2\$s.", *obj)
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a60610 = param // assign
            val b60610 = StringBuilder(a60610) // stick in stringbuilder
            b60610.append(" SafeStuff") // append some safe content
            b60610.replace(
                b60610.length - "Chars".length,
                b60610.length,
                "Chars"
            ) // replace some of the end content
            val map60610 = HashMap<String, Any>()
            map60610["key60610"] = b60610.toString() // put in a collection
            val c60610 = map60610["key60610"] as String? // get it back out
            val d60610 = c60610!!.substring(0, c60610.length - 1) // extract most of it
            val e60610 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d60610.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f60610 =
                e60610.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g60610 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g60610) // reflection

            return bar
        }
    }
}
