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



@WebServlet(value = ["/xss-03/BenchmarkTest01598"])
class BenchmarkTest01598 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest01598")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        val bar = Test().doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        response.writer.write(bar)
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a75770 = param // assign
            val b75770 = StringBuilder(a75770) // stick in stringbuilder
            b75770.append(" SafeStuff") // append some safe content
            b75770.replace(
                b75770.length - "Chars".length,
                b75770.length,
                "Chars"
            ) // replace some of the end content
            val map75770 = HashMap<String, Any>()
            map75770["key75770"] = b75770.toString() // put in a collection
            val c75770 = map75770["key75770"] as String? // get it back out
            val d75770 = c75770!!.substring(0, c75770.length - 1) // extract most of it
            val e75770 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d75770.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f75770 =
                e75770.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val bar = thing.doSomething(f75770) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

