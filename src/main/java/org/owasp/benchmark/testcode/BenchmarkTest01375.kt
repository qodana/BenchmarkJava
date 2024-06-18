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



@WebServlet(value = ["/trustbound-00/BenchmarkTest01375"])
class BenchmarkTest01375 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val map = request.parameterMap
        var param = ""
        if (!map.isEmpty()) {
            val values = map["BenchmarkTest01375"]
            if (values != null) param = values[0]
        }

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

            val a33070 = param // assign
            val b33070 = StringBuilder(a33070) // stick in stringbuilder
            b33070.append(" SafeStuff") // append some safe content
            b33070.replace(
                b33070.length - "Chars".length,
                b33070.length,
                "Chars"
            ) // replace some of the end content
            val map33070 = HashMap<String, Any>()
            map33070["key33070"] = b33070.toString() // put in a collection
            val c33070 = map33070["key33070"] as String? // get it back out
            val d33070 = c33070!!.substring(0, c33070.length - 1) // extract most of it
            val e33070 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d33070.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f33070 =
                e33070.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val bar = thing.doSomething(f33070) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

