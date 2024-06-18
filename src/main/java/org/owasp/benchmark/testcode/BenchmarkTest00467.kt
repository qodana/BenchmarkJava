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

import java.io.IOException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/xss-00/BenchmarkTest00467"])
class BenchmarkTest00467 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val map = request.parameterMap
        var param = ""
        if (!map.isEmpty()) {
            val values = map["BenchmarkTest00467"]
            if (values != null) param = values[0]
        }

        var bar: String? = "safe!"
        val map88136 = HashMap<String, Any>()
        map88136["keyA-88136"] = "a-Value" // put some stuff in the collection
        map88136["keyB-88136"] = param // put it in a collection
        map88136["keyC"] = "another-Value" // put some stuff in the collection
        bar = map88136["keyB-88136"] as String? // get it back out

        response.setHeader("X-XSS-Protection", "0")
        val obj = arrayOf("a", "b")
        response.writer.format(Locale.US, bar, *obj)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
