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
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory



@WebServlet(value = ["/xpathi-00/BenchmarkTest01633"])
class BenchmarkTest01633 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest01633")
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

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a31144 = param // assign
            val b31144 = StringBuilder(a31144) // stick in stringbuilder
            b31144.append(" SafeStuff") // append some safe content
            b31144.replace(
                b31144.length - "Chars".length,
                b31144.length,
                "Chars"
            ) // replace some of the end content
            val map31144 = HashMap<String, Any>()
            map31144["key31144"] = b31144.toString() // put in a collection
            val c31144 = map31144["key31144"] as String? // get it back out
            val d31144 = c31144!!.substring(0, c31144.length - 1) // extract most of it
            val e31144 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d31144.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f31144 =
                e31144.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g31144 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g31144) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

