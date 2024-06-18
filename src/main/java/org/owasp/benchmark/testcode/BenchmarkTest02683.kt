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
import org.owasp.benchmark.helpers.SeparateClassRequest
import org.owasp.benchmark.helpers.ThingFactory
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/xss-05/BenchmarkTest02683"])
class BenchmarkTest02683 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        val param = scr.getTheValue("BenchmarkTest02683")

        val bar = doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        response.writer.print(bar)
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a47309 = param // assign
            val b47309 = StringBuilder(a47309) // stick in stringbuilder
            b47309.append(" SafeStuff") // append some safe content
            b47309.replace(
                b47309.length - "Chars".length,
                b47309.length,
                "Chars"
            ) // replace some of the end content
            val map47309 = HashMap<String, Any>()
            map47309["key47309"] = b47309.toString() // put in a collection
            val c47309 = map47309["key47309"] as String? // get it back out
            val d47309 = c47309!!.substring(0, c47309.length - 1) // extract most of it
            val e47309 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d47309.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f47309 =
                e47309.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g47309 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g47309) // reflection

            return bar
        }
    }
}
