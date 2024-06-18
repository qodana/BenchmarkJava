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
import org.owasp.benchmark.helpers.Utils
import java.io.IOException
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/trustbound-00/BenchmarkTest00189"])
class BenchmarkTest00189 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        if (request.getHeader("BenchmarkTest00189") != null) {
            param = request.getHeader("BenchmarkTest00189")
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = URLDecoder.decode(param, "UTF-8")

        // Chain a bunch of propagators in sequence
        val a14330 = param // assign
        val b14330 = StringBuilder(a14330) // stick in stringbuilder
        b14330.append(" SafeStuff") // append some safe content
        b14330.replace(
            b14330.length - "Chars".length,
            b14330.length,
            "Chars"
        ) // replace some of the end content
        val map14330 = HashMap<String, Any>()
        map14330["key14330"] = b14330.toString() // put in a collection
        val c14330 = map14330["key14330"] as String? // get it back out
        val d14330 = c14330!!.substring(0, c14330.length - 1) // extract most of it
        val e14330 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d14330.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f14330 = e14330.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g14330 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g14330) // reflection

        // javax.servlet.http.HttpSession.setAttribute(java.lang.String,java.lang.Object^)
        request.session.setAttribute("userid", bar)

        response.writer
            .println(
                "Item: 'userid' with value: '"
                        + Utils.encodeForHTML(bar)
                        + "' saved in session."
            )
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
