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

@WebServlet(value = ["/xss-00/BenchmarkTest00385"])
class BenchmarkTest00385 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest00385")
        if (param == null) param = ""

        // Chain a bunch of propagators in sequence
        val a21475 = param // assign
        val b21475 = StringBuilder(a21475) // stick in stringbuilder
        b21475.append(" SafeStuff") // append some safe content
        b21475.replace(
            b21475.length - "Chars".length,
            b21475.length,
            "Chars"
        ) // replace some of the end content
        val map21475 = HashMap<String, Any>()
        map21475["key21475"] = b21475.toString() // put in a collection
        val c21475 = map21475["key21475"] as String? // get it back out
        val d21475 = c21475!!.substring(0, c21475.length - 1) // extract most of it
        val e21475 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d21475.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f21475 = e21475.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val bar = thing.doSomething(f21475) // reflection

        response.setHeader("X-XSS-Protection", "0")
        val obj = arrayOf<Any?>(bar, "b")
        response.writer.printf("Formatted like: %1\$s and %2\$s.", *obj)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
