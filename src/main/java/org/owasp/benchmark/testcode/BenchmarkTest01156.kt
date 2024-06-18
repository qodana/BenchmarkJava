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
import java.io.File
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-01/BenchmarkTest01156"])
class BenchmarkTest01156 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("BenchmarkTest01156")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        val bar = Test().doSomething(request, param)

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
                    "file:"
                            + startURIslashes
                            + Utils.TESTFILES_DIR
                        .replace('\\', '/')
                        .replace(' ', '_')
                            + bar
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

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            var bar: String? = "safe!"
            val map55476 = HashMap<String, Any>()
            map55476["keyA-55476"] = "a-Value" // put some stuff in the collection
            map55476["keyB-55476"] = param // put it in a collection
            map55476["keyC"] = "another-Value" // put some stuff in the collection
            bar = map55476["keyB-55476"] as String? // get it back out

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

