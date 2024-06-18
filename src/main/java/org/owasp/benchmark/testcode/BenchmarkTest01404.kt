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
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-01/BenchmarkTest01404"])
class BenchmarkTest01404 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        var flag = true
        val names = request.parameterNames
        while (names.hasMoreElements() && flag) {
            val name = names.nextElement() as String
            val values = request.getParameterValues(name)
            if (values != null) {
                var i = 0
                while (i < values.size && flag) {
                    val value = values[i]
                    if (value == "BenchmarkTest01404") {
                        param = name
                        flag = false
                    }
                    i++
                }
            }
        }

        val bar = Test().doSomething(request, param)

        val fileTarget = File(bar, "/Test.txt")
        response.writer
            .println(
                "Access to file: '"
                        + ESAPI
                    .encoder()
                    .encodeForHTML(fileTarget.toString())
                        + "' created."
            )
        if (fileTarget.exists()) {
            response.writer.println(" And file already exists.")
        } else {
            response.writer.println(" But file doesn't exist yet.")
        }
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a75056 = param // assign
            val b75056 = StringBuilder(a75056) // stick in stringbuilder
            b75056.append(" SafeStuff") // append some safe content
            b75056.replace(
                b75056.length - "Chars".length,
                b75056.length,
                "Chars"
            ) // replace some of the end content
            val map75056 = HashMap<String, Any>()
            map75056["key75056"] = b75056.toString() // put in a collection
            val c75056 = map75056["key75056"] as String? // get it back out
            val d75056 = c75056!!.substring(0, c75056.length - 1) // extract most of it
            val e75056 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d75056.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f75056 =
                e75056.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g75056 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g75056) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

