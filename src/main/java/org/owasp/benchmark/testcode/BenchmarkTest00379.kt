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

@WebServlet(value = ["/xss-00/BenchmarkTest00379"])
class BenchmarkTest00379 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest00379")
        if (param == null) param = ""

        // Chain a bunch of propagators in sequence
        val a75704 = param // assign
        val b75704 = StringBuilder(a75704) // stick in stringbuilder
        b75704.append(" SafeStuff") // append some safe content
        b75704.replace(
            b75704.length - "Chars".length,
            b75704.length,
            "Chars"
        ) // replace some of the end content
        val map75704 = HashMap<String, Any>()
        map75704["key75704"] = b75704.toString() // put in a collection
        val c75704 = map75704["key75704"] as String? // get it back out
        val d75704 = c75704!!.substring(0, c75704.length - 1) // extract most of it
        val e75704 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d75704.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f75704 = e75704.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g75704 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g75704) // reflection

        response.setHeader("X-XSS-Protection", "0")
        response.writer.print(bar!!.toCharArray())
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
