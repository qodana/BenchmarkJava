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
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/trustbound-00/BenchmarkTest01550"])
class BenchmarkTest01550 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        var param = scr.getTheParameter("BenchmarkTest01550")
        if (param == null) param = ""

        val bar = Test().doSomething(request, param)

        // javax.servlet.http.HttpSession.setAttribute(java.lang.String,java.lang.Object^)
        request.session.setAttribute("userid", bar)

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

            val a86153 = param // assign
            val b86153 = StringBuilder(a86153) // stick in stringbuilder
            b86153.append(" SafeStuff") // append some safe content
            b86153.replace(
                b86153.length - "Chars".length,
                b86153.length,
                "Chars"
            ) // replace some of the end content
            val map86153 = HashMap<String, Any>()
            map86153["key86153"] = b86153.toString() // put in a collection
            val c86153 = map86153["key86153"] as String? // get it back out
            val d86153 = c86153!!.substring(0, c86153.length - 1) // extract most of it
            val e86153 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d86153.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f86153 =
                e86153.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val bar = thing.doSomething(f86153) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

