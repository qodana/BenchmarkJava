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
import org.owasp.esapi.ESAPI
import java.io.IOException
import java.io.InputStream
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/securecookie-00/BenchmarkTest00170"])
class BenchmarkTest00170 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        if (request.getHeader("BenchmarkTest00170") != null) {
            param = request.getHeader("BenchmarkTest00170")
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = URLDecoder.decode(param, "UTF-8")

        // Chain a bunch of propagators in sequence
        val a9823 = param // assign
        val b9823 = StringBuilder(a9823) // stick in stringbuilder
        b9823.append(" SafeStuff") // append some safe content
        b9823.replace(
            b9823.length - "Chars".length,
            b9823.length,
            "Chars"
        ) // replace some of the end content
        val map9823 = HashMap<String, Any>()
        map9823["key9823"] = b9823.toString() // put in a collection
        val c9823 = map9823["key9823"] as String? // get it back out
        val d9823 = c9823!!.substring(0, c9823.length - 1) // extract most of it
        val e9823 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d9823.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f9823 = e9823.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g9823 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g9823) // reflection

        val input = ByteArray(1000)
        var str = "?"
        val inputParam: Any = param
        if (inputParam is String) str = inputParam
        if (inputParam is InputStream) {
            val i = inputParam.read(input)
            if (i == -1) {
                response.writer
                    .println(
                        "This input source requires a POST, not a GET. Incompatible UI for the InputStream source."
                    )
                return
            }
            str = String(input, 0, i)
        }
        if ("" == str) str = "No cookie value supplied"
        val cookie = Cookie("SomeCookie", str)

        cookie.secure = false
        cookie.isHttpOnly = true
        cookie.path = request.requestURI // i.e., set path to JUST this servlet
        // e.g., /benchmark/sql-01/BenchmarkTest01001
        response.addCookie(cookie)

        response.writer
            .println(
                "Created cookie: 'SomeCookie': with value: '"
                        + ESAPI.encoder().encodeForHTML(str)
                        + "' and secure flag set to: false"
            )
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
