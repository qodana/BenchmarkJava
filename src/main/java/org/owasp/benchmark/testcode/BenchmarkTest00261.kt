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
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.IOException
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/pathtraver-00/BenchmarkTest00261"])
class BenchmarkTest00261 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("BenchmarkTest00261")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        // Chain a bunch of propagators in sequence
        val a3000 = param // assign
        val b3000 = StringBuilder(a3000) // stick in stringbuilder
        b3000.append(" SafeStuff") // append some safe content
        b3000.replace(
            b3000.length - "Chars".length,
            b3000.length,
            "Chars"
        ) // replace some of the end content
        val map3000 = HashMap<String, Any>()
        map3000["key3000"] = b3000.toString() // put in a collection
        val c3000 = map3000["key3000"] as String? // get it back out
        val d3000 = c3000!!.substring(0, c3000.length - 1) // extract most of it
        val e3000 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d3000.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f3000 = e3000.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g3000 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g3000) // reflection

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
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
