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
import java.io.FileInputStream
import java.io.IOException
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-02/BenchmarkTest02032"])
class BenchmarkTest02032 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("BenchmarkTest02032")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        val bar = doSomething(request, param)

        var fileName: String? = null
        var fis: FileInputStream? = null

        try {
            fileName = Utils.TESTFILES_DIR + bar
            fis = FileInputStream(fileName)
            val b = ByteArray(1000)
            val size = fis.read(b)
            response.writer
                .println(
                    "The beginning of file: '"
                            + ESAPI.encoder().encodeForHTML(fileName)
                            + "' is:\n\n"
                )
            response.writer
                .println(ESAPI.encoder().encodeForHTML(String(b, 0, size)))
        } catch (e: Exception) {
            println("Couldn't open FileInputStream on file: '$fileName'")
            //			System.out.println("File exception caught and swallowed: " + e.getMessage());
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                    fis = null
                } catch (e: Exception) {
                    // we tried...
                }
            }
        }
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String {
            val bar: String

            // Simple if statement that assigns param to bar on true condition
            val num = 196
            bar = if ((500 / 42) + num > 200) param
            else "This should never happen"

            return bar
        }
    }
}
