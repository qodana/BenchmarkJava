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
import java.net.URLDecoder
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/weakrand-00/BenchmarkTest00318"])
class BenchmarkTest00318 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("BenchmarkTest00318")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        // Chain a bunch of propagators in sequence
        val a30925 = param // assign
        val b30925 = StringBuilder(a30925) // stick in stringbuilder
        b30925.append(" SafeStuff") // append some safe content
        b30925.replace(
            b30925.length - "Chars".length,
            b30925.length,
            "Chars"
        ) // replace some of the end content
        val map30925 = HashMap<String, Any>()
        map30925["key30925"] = b30925.toString() // put in a collection
        val c30925 = map30925["key30925"] as String? // get it back out
        val d30925 = c30925!!.substring(0, c30925.length - 1) // extract most of it
        val e30925 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d30925.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f30925 = e30925.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g30925 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g30925) // reflection

        try {
            val rand = SecureRandom.getInstance("SHA1PRNG").nextFloat()
            val rememberMeKey =
                rand.toString().substring(2) // Trim off the 0. at the front.

            var user = "SafeFloyd"
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
                        if (cookie.value
                            == request.session.getAttribute(cookieName)
                        ) {
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
        } catch (e: NoSuchAlgorithmException) {
            println("Problem executing SecureRandom.nextFloat() - TestCase")
            throw ServletException(e)
        }
        response.writer
            .println("Weak Randomness Test java.security.SecureRandom.nextFloat() executed")
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
