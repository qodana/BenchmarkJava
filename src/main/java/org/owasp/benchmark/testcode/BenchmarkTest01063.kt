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
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/xss-02/BenchmarkTest01063"])
class BenchmarkTest01063 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        if (request.getHeader("Referer") != null) {
            param = request.getHeader("Referer")
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = URLDecoder.decode(param, "UTF-8")

        val bar = Test().doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        response.writer.write("Parameter value: $bar")
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a92400 = param // assign
            val b92400 = StringBuilder(a92400) // stick in stringbuilder
            b92400.append(" SafeStuff") // append some safe content
            b92400.replace(
                b92400.length - "Chars".length,
                b92400.length,
                "Chars"
            ) // replace some of the end content
            val map92400 = HashMap<String, Any>()
            map92400["key92400"] = b92400.toString() // put in a collection
            val c92400 = map92400["key92400"] as String? // get it back out
            val d92400 = c92400!!.substring(0, c92400.length - 1) // extract most of it
            val e92400 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d92400.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f92400 =
                e92400.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val bar = thing.doSomething(f92400) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

