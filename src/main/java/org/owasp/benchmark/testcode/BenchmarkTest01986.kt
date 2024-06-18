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
import java.net.URI
import java.net.URISyntaxException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-02/BenchmarkTest01986"])
class BenchmarkTest01986 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val names = request.headerNames
        while (names.hasMoreElements()) {
            val name = names.nextElement() as String

            if (Utils.commonHeaders.contains(name)) {
                continue  // If standard header, move on to next one
            }

            val values = request.getHeaders(name)
            if (values != null && values.hasMoreElements()) {
                param = name // Grabs the name of the first non-standard header as the parameter
                // value
                break
            }
        }

        // Note: We don't URL decode header names because people don't normally do that
        val bar = doSomething(request, param)

        // FILE URIs are tricky because they are different between Mac and Windows because of lack
        // of standardization.
        // Mac requires an extra slash for some reason.
        var startURIslashes = ""
        if (System.getProperty("os.name").indexOf("Windows") != -1) startURIslashes =
            if (System.getProperty("os.name").indexOf("Windows") != -1) "/"
            else "//"

        try {
            val fileURI =
                URI(
                    "file",
                    null,
                    startURIslashes
                            + Utils.TESTFILES_DIR
                        .replace('\\', File.separatorChar)
                        .replace(' ', '_')
                            + bar,
                    null,
                    null
                )
            val fileTarget = File(fileURI)
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
        } catch (e: URISyntaxException) {
            throw ServletException(e)
        }
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            var bar: String? = "safe!"
            val map83331 = HashMap<String, Any>()
            map83331["keyA-83331"] = "a_Value" // put some stuff in the collection
            map83331["keyB-83331"] = param // put it in a collection
            map83331["keyC"] = "another_Value" // put some stuff in the collection
            bar = map83331["keyB-83331"] as String? // get it back out
            bar = map83331["keyA-83331"] as String? // get safe value back out

            return bar
        }
    }
}
