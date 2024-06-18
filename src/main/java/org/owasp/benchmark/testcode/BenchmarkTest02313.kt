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



@WebServlet(value = ["/xss-04/BenchmarkTest02313"])
class BenchmarkTest02313 : HttpServlet() {
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
                    if (value == "BenchmarkTest02313") {
                        param = name
                        flag = false
                    }
                    i++
                }
            }
        }

        val bar = doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        val obj = arrayOf("a", bar)
        val out = response.writer
        out.write("<!DOCTYPE html>\n<html>\n<body>\n<p>")
        out.format(Locale.US, "Formatted like: %1\$s and %2\$s.", *obj)
        out.write("\n</p>\n</body>\n</html>")
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a31085 = param // assign
            val b31085 = StringBuilder(a31085) // stick in stringbuilder
            b31085.append(" SafeStuff") // append some safe content
            b31085.replace(
                b31085.length - "Chars".length,
                b31085.length,
                "Chars"
            ) // replace some of the end content
            val map31085 = HashMap<String, Any>()
            map31085["key31085"] = b31085.toString() // put in a collection
            val c31085 = map31085["key31085"] as String? // get it back out
            val d31085 = c31085!!.substring(0, c31085.length - 1) // extract most of it
            val e31085 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d31085.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f31085 =
                e31085.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g31085 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g31085) // reflection

            return bar
        }
    }
}
