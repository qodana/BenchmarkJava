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
import org.owasp.benchmark.helpers.ThingFactory
import java.io.IOException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/weakrand-05/BenchmarkTest02503"])
class BenchmarkTest02503 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest02503")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        val bar = doSomething(request, param)

        val r = Random().nextInt()
        val rememberMeKey = r.toString()

        var user = "Ingrid"
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

        response.writer.println("Weak Randomness Test java.util.Random.nextInt() executed")
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a70407 = param // assign
            val b70407 = StringBuilder(a70407) // stick in stringbuilder
            b70407.append(" SafeStuff") // append some safe content
            b70407.replace(
                b70407.length - "Chars".length,
                b70407.length,
                "Chars"
            ) // replace some of the end content
            val map70407 = HashMap<String, Any>()
            map70407["key70407"] = b70407.toString() // put in a collection
            val c70407 = map70407["key70407"] as String? // get it back out
            val d70407 = c70407!!.substring(0, c70407.length - 1) // extract most of it
            val e70407 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d70407.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f70407 =
                e70407.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g70407 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g70407) // reflection

            return bar
        }
    }
}
