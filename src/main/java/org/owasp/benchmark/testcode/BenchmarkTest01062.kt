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
 * @author Dave Wichers
 * @created 2015
 */
package org.owasp.benchmark.testcode

import org.apache.commons.codec.binary.Base64
import org.owasp.esapi.ESAPI
import java.io.*
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/securecookie-00/BenchmarkTest01062"])
class BenchmarkTest01062 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        if (request.getHeader("BenchmarkTest01062") != null) {
            param = request.getHeader("BenchmarkTest01062")
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = URLDecoder.decode(param, "UTF-8")

        val bar = Test().doSomething(request, param)

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

        cookie.secure = true
        cookie.isHttpOnly = true
        cookie.path = request.requestURI // i.e., set path to JUST this servlet
        // e.g., /benchmark/sql-01/BenchmarkTest01001
        response.addCookie(cookie)

        response.writer
            .println(
                "Created cookie: 'SomeCookie': with value: '"
                        + ESAPI.encoder().encodeForHTML(str)
                        + "' and secure flag set to: true"
            )
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String?): String {
            var bar = ""
            if (param != null) {
                bar = String(
                    Base64.decodeBase64(
                        Base64.encodeBase64(
                            param.toByteArray()
                        )
                    )
                )
            }

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

