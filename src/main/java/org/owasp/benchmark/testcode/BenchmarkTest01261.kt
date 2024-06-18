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
 * @author Dave Wichers
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



@WebServlet(value = ["/xss-02/BenchmarkTest01261"])
class BenchmarkTest01261 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest01261")
        if (param == null) param = ""

        val bar = Test().doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        response.writer.println(bar!!.toCharArray())
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a34194 = param // assign
            val b34194 = StringBuilder(a34194) // stick in stringbuilder
            b34194.append(" SafeStuff") // append some safe content
            b34194.replace(
                b34194.length - "Chars".length,
                b34194.length,
                "Chars"
            ) // replace some of the end content
            val map34194 = HashMap<String, Any>()
            map34194["key34194"] = b34194.toString() // put in a collection
            val c34194 = map34194["key34194"] as String? // get it back out
            val d34194 = c34194!!.substring(0, c34194.length - 1) // extract most of it
            val e34194 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d34194.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f34194 =
                e34194.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val bar = thing.doSomething(f34194) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

