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
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/xss-00/BenchmarkTest00275"])
class BenchmarkTest00275 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("Referer")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        // Chain a bunch of propagators in sequence
        val a28150 = param // assign
        val b28150 = StringBuilder(a28150) // stick in stringbuilder
        b28150.append(" SafeStuff") // append some safe content
        b28150.replace(
            b28150.length - "Chars".length,
            b28150.length,
            "Chars"
        ) // replace some of the end content
        val map28150 = HashMap<String, Any>()
        map28150["key28150"] = b28150.toString() // put in a collection
        val c28150 = map28150["key28150"] as String? // get it back out
        val d28150 = c28150!!.substring(0, c28150.length - 1) // extract most of it
        val e28150 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d28150.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f28150 = e28150.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g28150 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g28150) // reflection

        response.setHeader("X-XSS-Protection", "0")
        response.writer.print(bar!!.toCharArray())
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
