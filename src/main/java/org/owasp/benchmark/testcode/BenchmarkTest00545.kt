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

@WebServlet(value = ["/xss-01/BenchmarkTest00545"])
class BenchmarkTest00545 : HttpServlet() {
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
                    if (value == "BenchmarkTest00545") {
                        param = name
                        flag = false
                    }
                    i++
                }
            }
        }

        // Chain a bunch of propagators in sequence
        val a80566 = param // assign
        val b80566 = StringBuilder(a80566) // stick in stringbuilder
        b80566.append(" SafeStuff") // append some safe content
        b80566.replace(
            b80566.length - "Chars".length,
            b80566.length,
            "Chars"
        ) // replace some of the end content
        val map80566 = HashMap<String, Any>()
        map80566["key80566"] = b80566.toString() // put in a collection
        val c80566 = map80566["key80566"] as String? // get it back out
        val d80566 = c80566!!.substring(0, c80566.length - 1) // extract most of it
        val e80566 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d80566.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f80566 = e80566.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g80566 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g80566) // reflection

        response.setHeader("X-XSS-Protection", "0")
        response.writer.print(bar!!.toCharArray())
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
