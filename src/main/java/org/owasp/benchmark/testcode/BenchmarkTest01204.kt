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
import org.owasp.benchmark.helpers.*
import org.owasp.benchmark.helpers.ThingFactory
import java.io.IOException
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/trustbound-00/BenchmarkTest01204"])
class BenchmarkTest01204 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("BenchmarkTest01204")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        val bar = Test().doSomething(request, param)

        // javax.servlet.http.HttpSession.putValue(java.lang.String,java.lang.Object^)
        request.session.putValue("userid", bar)

        response.writer
            .println(
                "Item: 'userid' with value: '"
                        + Utils.encodeForHTML(bar)
                        + "' saved in session."
            )
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a40584 = param // assign
            val b40584 = StringBuilder(a40584) // stick in stringbuilder
            b40584.append(" SafeStuff") // append some safe content
            b40584.replace(
                b40584.length - "Chars".length,
                b40584.length,
                "Chars"
            ) // replace some of the end content
            val map40584 = HashMap<String, Any>()
            map40584["key40584"] = b40584.toString() // put in a collection
            val c40584 = map40584["key40584"] as String? // get it back out
            val d40584 = c40584!!.substring(0, c40584.length - 1) // extract most of it
            val e40584 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d40584.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f40584 =
                e40584.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val bar = thing.doSomething(f40584) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

