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

import org.owasp.benchmark.helpers.*
import org.owasp.esapi.ESAPI
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.SAXException
import java.io.FileInputStream
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory



@WebServlet(value = ["/xpathi-00/BenchmarkTest01632"])
class BenchmarkTest01632 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest01632")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        val bar = Test().doSomething(request, param)

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
            val nodeList =
                xp.compile(expression)
                    .evaluate(xmlDocument, XPathConstants.NODESET) as NodeList

            response.writer.println("Your query results are: <br/>")

            for (i in 0 until nodeList.length) {
                val value = nodeList.item(i) as Element
                response.writer.println(value.textContent + "<br/>")
            }
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

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String {
            val bar: String
            val guess = "ABC"
            val switchTarget = guess[1] // condition 'B', which is safe

            bar = when (switchTarget) {
                'A' -> param
                'B' -> "bob"
                'C', 'D' -> param
                else -> "bob's your uncle"
            }
            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

