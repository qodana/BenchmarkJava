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



@WebServlet(value = ["/xss-05/BenchmarkTest02491"])
class BenchmarkTest02491 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest02491")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        val bar = doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        response.writer.println(bar!!.toCharArray())
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a17852 = param // assign
            val b17852 = StringBuilder(a17852) // stick in stringbuilder
            b17852.append(" SafeStuff") // append some safe content
            b17852.replace(
                b17852.length - "Chars".length,
                b17852.length,
                "Chars"
            ) // replace some of the end content
            val map17852 = HashMap<String, Any>()
            map17852["key17852"] = b17852.toString() // put in a collection
            val c17852 = map17852["key17852"] as String? // get it back out
            val d17852 = c17852!!.substring(0, c17852.length - 1) // extract most of it
            val e17852 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d17852.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f17852 =
                e17852.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g17852 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g17852) // reflection

            return bar
        }
    }
}
