/**
 * OWASP Benchmark v1.2
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

import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/pathtraver-00/BenchmarkTest00045"])
class BenchmarkTest00045 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        // some code
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest00045")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        val fileName = Utils.TESTFILES_DIR + param

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
