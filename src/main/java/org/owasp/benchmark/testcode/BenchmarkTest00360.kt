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
import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/pathtraver-00/BenchmarkTest00360"])
class BenchmarkTest00360 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest00360")
        if (param == null) param = ""

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

        var fileName: String? = null
        var fis: FileInputStream? = null

        try {
            fileName = Utils.TESTFILES_DIR + bar
            fis = FileInputStream(File(fileName))
            val b = ByteArray(1000)
            val size = fis.read(b)
            response.writer
                .println(
                    "The beginning of file: '"
                            + ESAPI.encoder().encodeForHTML(fileName)
                            + "' is:\n\n"
                            + ESAPI
                        .encoder()
                        .encodeForHTML(String(b, 0, size))
                )
        } catch (e: Exception) {
            println("Couldn't open FileInputStream on file: '$fileName'")
            response.writer
                .println(
                    "Problem getting FileInputStream: "
                            + ESAPI
                        .encoder()
                        .encodeForHTML(e.message)
                )
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
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
