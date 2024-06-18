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
import java.net.URL
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/weakrand-00/BenchmarkTest00067"])
class BenchmarkTest00067 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        val userCookie =
            Cookie("BenchmarkTest00067", "anything")
        userCookie.maxAge = 60 * 3 // Store cookie for 3 minutes
        userCookie.secure = true
        userCookie.path = request.requestURI
        userCookie.domain = URL(request.requestURL.toString()).host
        response.addCookie(userCookie)
        val rd =
            request.getRequestDispatcher("/weakrand-00/BenchmarkTest00067.html")
        rd.include(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val theCookies = request.cookies

        var param = "noCookieValueSupplied"
        if (theCookies != null) {
            for (theCookie in theCookies) {
                if (theCookie.name == "BenchmarkTest00067") {
                    param = URLDecoder.decode(theCookie.value, "UTF-8")
                    break
                }
            }
        }

        // Chain a bunch of propagators in sequence
        val a71153 = param // assign
        val b71153 = StringBuilder(a71153) // stick in stringbuilder
        b71153.append(" SafeStuff") // append some safe content
        b71153.replace(
            b71153.length - "Chars".length,
            b71153.length,
            "Chars"
        ) // replace some of the end content
        val map71153 = HashMap<String, Any>()
        map71153["key71153"] = b71153.toString() // put in a collection
        val c71153 = map71153["key71153"] as String? // get it back out
        val d71153 = c71153!!.substring(0, c71153.length - 1) // extract most of it
        val e71153 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d71153.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f71153 = e71153.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g71153 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g71153) // reflection

        val value = Math.random()
        val rememberMeKey = value.toString().substring(2) // Trim off the 0. at the front.

        var user = "Doug"
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
        response.writer.println("Weak Randomness Test java.lang.Math.random() executed")
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
