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
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/xss-01/BenchmarkTest00883"])
class BenchmarkTest00883 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        val param = scr.getTheValue("BenchmarkTest00883")

        // Chain a bunch of propagators in sequence
        val a69063 = param // assign
        val b69063 = StringBuilder(a69063) // stick in stringbuilder
        b69063.append(" SafeStuff") // append some safe content
        b69063.replace(
            b69063.length - "Chars".length,
            b69063.length,
            "Chars"
        ) // replace some of the end content
        val map69063 = HashMap<String, Any>()
        map69063["key69063"] = b69063.toString() // put in a collection
        val c69063 = map69063["key69063"] as String? // get it back out
        val d69063 = c69063!!.substring(0, c69063.length - 1) // extract most of it
        val e69063 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d69063.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f69063 = e69063.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val bar = thing.doSomething(f69063) // reflection

        response.setHeader("X-XSS-Protection", "0")
        val obj = arrayOf<Any?>("a", bar)
        response.writer.printf(Locale.US, "Formatted like: %1\$s and %2\$s.", *obj)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
