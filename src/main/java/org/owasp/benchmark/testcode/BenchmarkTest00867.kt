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

import org.owasp.benchmark.helpers.SeparateClassRequest
import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import java.io.IOException
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-01/BenchmarkTest00867"])
class BenchmarkTest00867 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        val param = scr.getTheValue("BenchmarkTest00867")

        var bar = ""
        if (param != null) {
            val valuesList: MutableList<String> = ArrayList()
            valuesList.add("safe")
            valuesList.add(param)
            valuesList.add("moresafe")

            valuesList.removeAt(0) // remove the 1st safe value

            bar = valuesList[0] // get the param value
        }

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
