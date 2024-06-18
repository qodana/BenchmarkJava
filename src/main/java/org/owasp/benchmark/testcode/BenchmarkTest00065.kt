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
import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.net.URLDecoder
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/pathtraver-00/BenchmarkTest00065"])
class BenchmarkTest00065 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        val userCookie =
            Cookie("BenchmarkTest00065", "FileName")
        userCookie.maxAge = 60 * 3 // Store cookie for 3 minutes
        userCookie.secure = true
        userCookie.path = request.requestURI
        userCookie.domain = URL(request.requestURL.toString()).host
        response.addCookie(userCookie)
        val rd =
            request.getRequestDispatcher("/pathtraver-00/BenchmarkTest00065.html")
        rd.include(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val theCookies = request.cookies

        var param = "noCookieValueSupplied"
        if (theCookies != null) {
            for (theCookie in theCookies) {
                if (theCookie.name == "BenchmarkTest00065") {
                    param = URLDecoder.decode(theCookie.value, "UTF-8")
                    break
                }
            }
        }

        var bar = ""
        if (param != null) {
            bar = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        param.toByteArray()
                    )
                )
            )
        }

        val fileName = Utils.TESTFILES_DIR + bar
        var `is`: InputStream? = null

        try {
            val path = Paths.get(fileName)
            `is` = Files.newInputStream(path, StandardOpenOption.READ)
            val b = ByteArray(1000)
            val size = `is`.read(b)
            response.writer
                .println(
                    "The beginning of file: '"
                            + ESAPI.encoder().encodeForHTML(fileName)
                            + "' is:\n\n"
                )
            response.writer
                .println(ESAPI.encoder().encodeForHTML(String(b, 0, size)))
            `is`.close()
        } catch (e: Exception) {
            println("Couldn't open InputStream on file: '$fileName'")
            response.writer
                .println(
                    "Problem getting InputStream: "
                            + ESAPI
                        .encoder()
                        .encodeForHTML(e.message)
                )
        } finally {
            if (`is` != null) {
                try {
                    `is`.close()
                    `is` = null
                } catch (e: Exception) {
                    // we tried...
                }
            }
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
