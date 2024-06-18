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
import java.io.IOException
import java.io.InputStream
import java.net.URLDecoder
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/pathtraver-00/BenchmarkTest00137"])
class BenchmarkTest00137 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        if (request.getHeader("BenchmarkTest00137") != null) {
            param = request.getHeader("BenchmarkTest00137")
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = URLDecoder.decode(param, "UTF-8")

        var bar: String? = "safe!"
        val map53289 = HashMap<String, Any>()
        map53289["keyA-53289"] = "a_Value" // put some stuff in the collection
        map53289["keyB-53289"] = param // put it in a collection
        map53289["keyC"] = "another_Value" // put some stuff in the collection
        bar = map53289["keyB-53289"] as String? // get it back out
        bar = map53289["keyA-53289"] as String? // get safe value back out

        val fileName = Utils.TESTFILES_DIR + bar
        var `is`: InputStream? = null

        try {
            val path = Paths.get(fileName)
            `is` = Files.newInputStream(path, StandardOpenOption.READ)
            val b = ByteArray(1000)
            val size = `is`.read(b)
            response.writer
                .println(
                    "The beginning of file: '"
                            + ESAPI.encoder().encodeForHTML(fileName)
                            + "' is:\n\n"
                )
            response.writer
                .println(ESAPI.encoder().encodeForHTML(String(b, 0, size)))
            `is`.close()
        } catch (e: Exception) {
            println("Couldn't open InputStream on file: '$fileName'")
            response.writer
                .println(
                    "Problem getting InputStream: "
                            + ESAPI
                        .encoder()
                        .encodeForHTML(e.message)
                )
        } finally {
            if (`is` != null) {
                try {
                    `is`.close()
                    `is` = null
                } catch (e: Exception) {
                    // we tried...
                }
            }
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
