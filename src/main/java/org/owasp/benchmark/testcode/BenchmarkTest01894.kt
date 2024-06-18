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

import org.owasp.benchmark.helpers.*
import org.owasp.esapi.ESAPI
import org.xml.sax.SAXException
import java.io.FileInputStream
import java.io.IOException
import java.net.URL
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory



@WebServlet(value = ["/xpathi-00/BenchmarkTest01894"])
class BenchmarkTest01894 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"
        val userCookie =
            Cookie("BenchmarkTest01894", "2222")
        userCookie.maxAge = 60 * 3 // Store cookie for 3 minutes
        userCookie.secure = true
        userCookie.path = request.requestURI
        userCookie.domain = URL(request.requestURL.toString()).host
        response.addCookie(userCookie)
        val rd =
            request.getRequestDispatcher("/xpathi-00/BenchmarkTest01894.html")
        rd.include(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val theCookies = request.cookies

        var param = "noCookieValueSupplied"
        if (theCookies != null) {
            for (theCookie in theCookies) {
                if (theCookie.name == "BenchmarkTest01894") {
                    param = URLDecoder.decode(theCookie.value, "UTF-8")
                    break
                }
            }
        }

        val bar = doSomething(request, param)

        try {
            val file =
                FileInputStream(
                    Utils.getFileFromClasspath(
                        "employees.xml", this.javaClass.classLoader
                    )
                )
            val builderFactory =
                DocumentBuilderFactory.newInstance()
            // Prevent XXE
            builderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
            val builder = builderFactory.newDocumentBuilder()
            val xmlDocument = builder.parse(file)
            val xpf = XPathFactory.newInstance()
            val xp = xpf.newXPath()

            val expression = "/Employees/Employee[@emplid='$bar']"
            val result = xp.evaluate(expression, xmlDocument)

            response.writer.println("Your query results are: $result<br/>")
        } catch (e: XPathExpressionException) {
            response.writer
                .println(
                    "Error parsing XPath input: '"
                            + ESAPI.encoder().encodeForHTML(bar)
                            + "'"
                )
            throw ServletException(e)
        } catch (e: ParserConfigurationException) {
            response.writer
                .println(
                    "Error parsing XPath input: '"
                            + ESAPI.encoder().encodeForHTML(bar)
                            + "'"
                )
            throw ServletException(e)
        } catch (e: SAXException) {
            response.writer
                .println(
                    "Error parsing XPath input: '"
                            + ESAPI.encoder().encodeForHTML(bar)
                            + "'"
                )
            throw ServletException(e)
        }
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String {
            val bar: String

            // Simple ? condition that assigns param to bar on false condition
            val num = 106

            bar = if ((7 * 42) - num > 200) "This should never happen" else param

            return bar
        }
    }
}
