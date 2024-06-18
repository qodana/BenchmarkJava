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

@WebServlet(value = ["/xss-00/BenchmarkTest00277"])
class BenchmarkTest00277 : HttpServlet() {
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
        val a54571 = param // assign
        val b54571 = StringBuilder(a54571) // stick in stringbuilder
        b54571.append(" SafeStuff") // append some safe content
        b54571.replace(
            b54571.length - "Chars".length,
            b54571.length,
            "Chars"
        ) // replace some of the end content
        val map54571 = HashMap<String, Any>()
        map54571["key54571"] = b54571.toString() // put in a collection
        val c54571 = map54571["key54571"] as String? // get it back out
        val d54571 = c54571!!.substring(0, c54571.length - 1) // extract most of it
        val e54571 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d54571.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f54571 = e54571.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g54571 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g54571) // reflection

        response.setHeader("X-XSS-Protection", "0")
        response.writer.print(bar)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
