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



@WebServlet(value = ["/xss-04/BenchmarkTest02135"])
class BenchmarkTest02135 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest02135")
        if (param == null) param = ""

        val bar = doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        response.writer.write(bar)
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a95930 = param // assign
            val b95930 = StringBuilder(a95930) // stick in stringbuilder
            b95930.append(" SafeStuff") // append some safe content
            b95930.replace(
                b95930.length - "Chars".length,
                b95930.length,
                "Chars"
            ) // replace some of the end content
            val map95930 = HashMap<String, Any>()
            map95930["key95930"] = b95930.toString() // put in a collection
            val c95930 = map95930["key95930"] as String? // get it back out
            val d95930 = c95930!!.substring(0, c95930.length - 1) // extract most of it
            val e95930 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d95930.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f95930 =
                e95930.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g95930 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g95930) // reflection

            return bar
        }
    }
}
