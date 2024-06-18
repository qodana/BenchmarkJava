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

import org.owasp.benchmark.helpers.*
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.IOException
import java.net.URL
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-02/BenchmarkTest01834"])
class BenchmarkTest01834 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        val userCookie =
            Cookie("BenchmarkTest01834", "FileName")
        userCookie.maxAge = 60 * 3 // Store cookie for 3 minutes
        userCookie.secure = true
        userCookie.path = request.requestURI
        userCookie.domain = URL(request.requestURL.toString()).host
        response.addCookie(userCookie)
        val rd =
            request.getRequestDispatcher("/pathtraver-02/BenchmarkTest01834.html")
        rd.include(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val theCookies = request.cookies

        var param = "noCookieValueSupplied"
        if (theCookies != null) {
            for (theCookie in theCookies) {
                if (theCookie.name == "BenchmarkTest01834") {
                    param = URLDecoder.decode(theCookie.value, "UTF-8")
                    break
                }
            }
        }

        val bar = doSomething(request, param)

        val fileTarget =
            File(Utils.TESTFILES_DIR, bar)
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
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String {
            val bar: String
            val guess = "ABC"
            val switchTarget = guess[1] // condition 'B', which is safe

            bar = when (switchTarget) {
                'A' -> param
                'B' -> "bob"
                'C', 'D' -> param
                else -> "bob's your uncle"
            }
            return bar
        }
    }
}
