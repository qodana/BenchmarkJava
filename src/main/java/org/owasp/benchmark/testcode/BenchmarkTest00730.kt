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



@WebServlet(value = ["/xss-01/BenchmarkTest00730"])
class BenchmarkTest00730 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest00730")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        var bar: String? = "safe!"
        val map29173 = HashMap<String, Any>()
        map29173["keyA-29173"] = "a_Value" // put some stuff in the collection
        map29173["keyB-29173"] = param // put it in a collection
        map29173["keyC"] = "another_Value" // put some stuff in the collection
        bar = map29173["keyB-29173"] as String? // get it back out
        bar = map29173["keyA-29173"] as String? // get safe value back out

        response.setHeader("X-XSS-Protection", "0")
        response.writer.write(bar!!.toCharArray())
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
