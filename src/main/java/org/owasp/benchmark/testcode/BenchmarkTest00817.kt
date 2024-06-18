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
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/weakrand-01/BenchmarkTest00817"])
class BenchmarkTest00817 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val queryString = request.queryString
        val paramval = "BenchmarkTest00817" + "="
        var paramLoc = -1
        if (queryString != null) paramLoc = queryString.indexOf(paramval)
        if (paramLoc == -1) {
            response.writer
                .println(
                    "getQueryString() couldn't find expected parameter '"
                            + "BenchmarkTest00817"
                            + "' in query string."
                )
            return
        }

        var param =
            queryString!!.substring(
                paramLoc
                        + paramval
                    .length
            ) // 1st assume "BenchmarkTest00817" param is last
        // parameter in query string.
        // And then check to see if its in the middle of the query string and if so, trim off what
        // comes after.
        val ampersandLoc = queryString.indexOf("&", paramLoc)
        if (ampersandLoc != -1) {
            param = queryString.substring(paramLoc + paramval.length, ampersandLoc)
        }
        param = URLDecoder.decode(param, "UTF-8")

        // Chain a bunch of propagators in sequence
        val a9795 = param // assign
        val b9795 = StringBuilder(a9795) // stick in stringbuilder
        b9795.append(" SafeStuff") // append some safe content
        b9795.replace(
            b9795.length - "Chars".length,
            b9795.length,
            "Chars"
        ) // replace some of the end content
        val map9795 = HashMap<String, Any>()
        map9795["key9795"] = b9795.toString() // put in a collection
        val c9795 = map9795["key9795"] as String? // get it back out
        val d9795 = c9795!!.substring(0, c9795.length - 1) // extract most of it
        val e9795 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d9795.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f9795 = e9795.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val bar = thing.doSomething(f9795) // reflection

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
