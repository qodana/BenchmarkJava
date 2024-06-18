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
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/xss-00/BenchmarkTest00493"])
class BenchmarkTest00493 : HttpServlet() {
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
            val values = map["BenchmarkTest00493"]
            if (values != null) param = values[0]
        }

        var bar: String? = "safe!"
        val map8943 = HashMap<String, Any>()
        map8943["keyA-8943"] = "a_Value" // put some stuff in the collection
        map8943["keyB-8943"] = param // put it in a collection
        map8943["keyC"] = "another_Value" // put some stuff in the collection
        bar = map8943["keyB-8943"] as String? // get it back out
        bar = map8943["keyA-8943"] as String? // get safe value back out

        response.setHeader("X-XSS-Protection", "0")
        response.writer.write("Parameter value: $bar")
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
