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

import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/pathtraver-00/BenchmarkTest00135"])
class BenchmarkTest00135 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        if (request.getHeader("BenchmarkTest00135") != null) {
            param = request.getHeader("BenchmarkTest00135")
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = URLDecoder.decode(param, "UTF-8")

        val bar: String
        val guess = "ABC"
        val switchTarget = guess[1] // condition 'B', which is safe

        bar = when (switchTarget) {
            'A' -> param
            'B' -> "bob"
            'C', 'D' -> param
            else -> "bob's your uncle"
        }
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
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
