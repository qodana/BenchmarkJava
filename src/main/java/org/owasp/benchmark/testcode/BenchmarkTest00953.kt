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

import org.owasp.benchmark.helpers.*
import org.owasp.esapi.ESAPI
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-01/BenchmarkTest00953"])
class BenchmarkTest00953 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        val userCookie =
            Cookie("BenchmarkTest00953", "FileName")
        userCookie.maxAge = 60 * 3 // Store cookie for 3 minutes
        userCookie.secure = true
        userCookie.path = request.requestURI
        userCookie.domain = URL(request.requestURL.toString()).host
        response.addCookie(userCookie)
        val rd =
            request.getRequestDispatcher("/pathtraver-01/BenchmarkTest00953.html")
        rd.include(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val theCookies = request.cookies

        var param = "noCookieValueSupplied"
        if (theCookies != null) {
            for (theCookie in theCookies) {
                if (theCookie.name == "BenchmarkTest00953") {
                    param = URLDecoder.decode(theCookie.value, "UTF-8")
                    break
                }
            }
        }

        val bar = Test().doSomething(request, param)

        val fileName = Utils.TESTFILES_DIR + bar

        try {
            FileOutputStream(FileInputStream(fileName).fd).use { fos ->
                response.writer
                    .println(
                        "Now ready to write to file: "
                                + ESAPI.encoder().encodeForHTML(fileName)
                    )
            }
        } catch (e: Exception) {
            println("Couldn't open FileOutputStream on file: '$fileName'")
        }
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String?): String {
            var bar = ""
            if (param != null) {
                val valuesList: MutableList<String> = ArrayList()
                valuesList.add("safe")
                valuesList.add(param)
                valuesList.add("moresafe")

                valuesList.removeAt(0) // remove the 1st safe value

                bar = valuesList[0] // get the param value
            }

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

