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
import java.net.URL
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/weakrand-00/BenchmarkTest00398"])
class BenchmarkTest00398 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest00398")
        if (param == null) param = ""

        var bar: String? = "safe!"
        val map43631 = HashMap<String, Any>()
        map43631["keyA-43631"] = "a_Value" // put some stuff in the collection
        map43631["keyB-43631"] = param // put it in a collection
        map43631["keyC"] = "another_Value" // put some stuff in the collection
        bar = map43631["keyB-43631"] as String? // get it back out
        bar = map43631["keyA-43631"] as String? // get safe value back out

        val value = Random().nextDouble()
        val rememberMeKey = value.toString().substring(2) // Trim off the 0. at the front.

        var user = "Donna"
        val fullClassName = this.javaClass.name
        val testCaseNumber =
            fullClassName.substring(
                fullClassName.lastIndexOf('.') + 1 + "BenchmarkTest".length
            )
        user += testCaseNumber

        val cookieName = "rememberMe$testCaseNumber"

        var foundUser = false
        val cookies = request.cookies
        if (cookies != null) {
            var i = 0
            while (!foundUser && i < cookies.size) {
                val cookie = cookies[i]
                if (cookieName == cookie.name) {
                    if (cookie.value == request.session.getAttribute(cookieName)) {
                        foundUser = true
                    }
                }
                i++
            }
        }

        if (foundUser) {
            response.writer.println("Welcome back: $user<br/>")
        } else {
            val rememberMe =
                Cookie(cookieName, rememberMeKey)
            rememberMe.secure = true
            rememberMe.isHttpOnly = true
            rememberMe.domain = URL(request.requestURL.toString()).host
            rememberMe.path = request.requestURI // i.e., set path to JUST this servlet
            // e.g., /benchmark/sql-01/BenchmarkTest01001
            request.session.setAttribute(cookieName, rememberMeKey)
            response.addCookie(rememberMe)
            response.writer
                .println(
                    user
                            + " has been remembered with cookie: "
                            + rememberMe.name
                            + " whose value is: "
                            + rememberMe.value
                            + "<br/>"
                )
        }

        response.writer.println("Weak Randomness Test java.util.Random.nextDouble() executed")
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
