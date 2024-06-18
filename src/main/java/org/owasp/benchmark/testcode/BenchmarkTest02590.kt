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
import java.net.URLDecoder
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/xss-05/BenchmarkTest02590"])
class BenchmarkTest02590 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val queryString = request.queryString
        val paramval = "BenchmarkTest02590" + "="
        var paramLoc = -1
        if (queryString != null) paramLoc = queryString.indexOf(paramval)
        if (paramLoc == -1) {
            response.writer
                .println(
                    "getQueryString() couldn't find expected parameter '"
                            + "BenchmarkTest02590"
                            + "' in query string."
                )
            return
        }

        var param: String? =
            queryString!!.substring(
                paramLoc
                        + paramval
                    .length
            ) // 1st assume "BenchmarkTest02590" param is last
        // parameter in query string.
        // And then check to see if its in the middle of the query string and if so, trim off what
        // comes after.
        val ampersandLoc = queryString.indexOf("&", paramLoc)
        if (ampersandLoc != -1) {
            param = queryString.substring(paramLoc + paramval.length, ampersandLoc)
        }
        param = URLDecoder.decode(param, "UTF-8")

        val bar = doSomething(request, param)

        response.setHeader("X-XSS-Protection", "0")
        val obj = arrayOf("a", "b")
        response.writer.printf(Locale.US, bar, *obj)
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            var bar: String? = "safe!"
            val map35520 = HashMap<String, Any>()
            map35520["keyA-35520"] = "a_Value" // put some stuff in the collection
            map35520["keyB-35520"] = param // put it in a collection
            map35520["keyC"] = "another_Value" // put some stuff in the collection
            bar = map35520["keyB-35520"] as String? // get it back out
            bar = map35520["keyA-35520"] as String? // get safe value back out

            return bar
        }
    }
}
