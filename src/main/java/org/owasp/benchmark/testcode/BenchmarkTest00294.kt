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
import java.io.IOException
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/cmdi-00/BenchmarkTest00294"])
class BenchmarkTest00294 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("BenchmarkTest00294")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        val bar: String

        // Simple ? condition that assigns param to bar on false condition
        val num = 106

        bar = if ((7 * 42) - num > 200) "This should never happen" else param

        val argList: MutableList<String> = ArrayList()

        val osName = System.getProperty("os.name")
        if (osName.indexOf("Windows") != -1) {
            argList.add("cmd.exe")
            argList.add("/c")
        } else {
            argList.add("sh")
            argList.add("-c")
        }
        argList.add("echo $bar")

        val pb = ProcessBuilder(argList)

        try {
            val p = pb.start()
            Utils.printOSCommandResults(p, response)
        } catch (e: IOException) {
            println(
                "Problem executing cmdi - java.lang.ProcessBuilder(java.util.List) Test Case"
            )
            throw ServletException(e)
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
