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



@WebServlet(value = ["/xss-04/BenchmarkTest02408"])
class BenchmarkTest02408 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        var param = scr.getTheParameter("BenchmarkTest02408")
        if (param == null) param = ""

        val bar = doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        response.writer.write(bar!!.toCharArray())
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a67594 = param // assign
            val b67594 = StringBuilder(a67594) // stick in stringbuilder
            b67594.append(" SafeStuff") // append some safe content
            b67594.replace(
                b67594.length - "Chars".length,
                b67594.length,
                "Chars"
            ) // replace some of the end content
            val map67594 = HashMap<String, Any>()
            map67594["key67594"] = b67594.toString() // put in a collection
            val c67594 = map67594["key67594"] as String? // get it back out
            val d67594 = c67594!!.substring(0, c67594.length - 1) // extract most of it
            val e67594 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d67594.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f67594 =
                e67594.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g67594 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g67594) // reflection

            return bar
        }
    }
}
