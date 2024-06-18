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
 * @author Dave Wichers
 * @created 2015
 */
package org.owasp.benchmark.testcode

import org.apache.commons.codec.binary.Base64
import org.owasp.benchmark.helpers.*
import org.owasp.benchmark.helpers.ThingFactory
import org.owasp.esapi.ESAPI
import java.io.IOException
import java.security.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/weakrand-02/BenchmarkTest01136"])
class BenchmarkTest01136 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val names = request.headerNames
        while (names.hasMoreElements()) {
            val name = names.nextElement() as String

            if (Utils.commonHeaders.contains(name)) {
                continue  // If standard header, move on to next one
            }

            val values = request.getHeaders(name)
            if (values != null && values.hasMoreElements()) {
                param = name // Grabs the name of the first non-standard header as the parameter
                // value
                break
            }
        }

        // Note: We don't URL decode header names because people don't normally do that
        val bar = Test().doSomething(request, param)

        try {
            val secureRandomGenerator =
                SecureRandom.getInstance("SHA1PRNG")

            // Get 40 random bytes
            val randomBytes = ByteArray(40)
            secureRandomGenerator.nextBytes(randomBytes)

            val rememberMeKey =
                ESAPI.encoder().encodeForBase64(randomBytes, true)

            var user = "SafeByron"
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
            println("Problem executing SecureRandom.nextBytes() - TestCase")
            throw ServletException(e)
        } finally {
            response.writer
                .println(
                    "Randomness Test java.security.SecureRandom.nextBytes(byte[]) executed"
                )
        }
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a58606 = param // assign
            val b58606 = StringBuilder(a58606) // stick in stringbuilder
            b58606.append(" SafeStuff") // append some safe content
            b58606.replace(
                b58606.length - "Chars".length,
                b58606.length,
                "Chars"
            ) // replace some of the end content
            val map58606 = HashMap<String, Any>()
            map58606["key58606"] = b58606.toString() // put in a collection
            val c58606 = map58606["key58606"] as String? // get it back out
            val d58606 = c58606!!.substring(0, c58606.length - 1) // extract most of it
            val e58606 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d58606.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f58606 =
                e58606.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g58606 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g58606) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

