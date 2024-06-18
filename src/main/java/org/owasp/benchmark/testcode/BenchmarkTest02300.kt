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
import org.owasp.benchmark.helpers.*
import org.owasp.benchmark.helpers.ThingFactory
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-02/BenchmarkTest02300"])
class BenchmarkTest02300 : HttpServlet() {
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
                    if (value == "BenchmarkTest02300") {
                        param = name
                        flag = false
                    }
                    i++
                }
            }
        }

        val bar = doSomething(request, param)

        val fileTarget =
            File(
                File(Utils.TESTFILES_DIR), bar
            )
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

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a20919 = param // assign
            val b20919 = StringBuilder(a20919) // stick in stringbuilder
            b20919.append(" SafeStuff") // append some safe content
            b20919.replace(
                b20919.length - "Chars".length,
                b20919.length,
                "Chars"
            ) // replace some of the end content
            val map20919 = HashMap<String, Any>()
            map20919["key20919"] = b20919.toString() // put in a collection
            val c20919 = map20919["key20919"] as String? // get it back out
            val d20919 = c20919!!.substring(0, c20919.length - 1) // extract most of it
            val e20919 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d20919.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f20919 =
                e20919.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g20919 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g20919) // reflection

            return bar
        }
    }
}
