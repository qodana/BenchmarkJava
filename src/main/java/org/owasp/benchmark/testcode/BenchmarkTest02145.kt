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



@WebServlet(value = ["/xss-04/BenchmarkTest02145"])
class BenchmarkTest02145 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest02145")
        if (param == null) param = ""

        val bar = doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        response.writer.write("Parameter value: $bar")
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a32743 = param // assign
            val b32743 = StringBuilder(a32743) // stick in stringbuilder
            b32743.append(" SafeStuff") // append some safe content
            b32743.replace(
                b32743.length - "Chars".length,
                b32743.length,
                "Chars"
            ) // replace some of the end content
            val map32743 = HashMap<String, Any>()
            map32743["key32743"] = b32743.toString() // put in a collection
            val c32743 = map32743["key32743"] as String? // get it back out
            val d32743 = c32743!!.substring(0, c32743.length - 1) // extract most of it
            val e32743 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d32743.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f32743 =
                e32743.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val bar = thing.doSomething(f32743) // reflection

            return bar
        }
    }
}
