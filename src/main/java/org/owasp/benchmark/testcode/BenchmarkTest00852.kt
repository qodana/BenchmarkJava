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

import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import org.xml.sax.SAXException
import java.io.FileInputStream
import java.io.IOException
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.xpath.XPathExpressionException
import javax.xml.xpath.XPathFactory



@WebServlet(value = ["/xpathi-00/BenchmarkTest00852"])
class BenchmarkTest00852 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val queryString = request.queryString
        val paramval = "BenchmarkTest00852" + "="
        var paramLoc = -1
        if (queryString != null) paramLoc = queryString.indexOf(paramval)
        if (paramLoc == -1) {
            response.writer
                .println(
                    "getQueryString() couldn't find expected parameter '"
                            + "BenchmarkTest00852"
                            + "' in query string."
                )
            return
        }

        var param =
            queryString!!.substring(
                paramLoc
                        + paramval
                    .length
            ) // 1st assume "BenchmarkTest00852" param is last
        // parameter in query string.
        // And then check to see if its in the middle of the query string and if so, trim off what
        // comes after.
        val ampersandLoc = queryString.indexOf("&", paramLoc)
        if (ampersandLoc != -1) {
            param = queryString.substring(paramLoc + paramval.length, ampersandLoc)
        }
        param = URLDecoder.decode(param, "UTF-8")

        var bar: String? = "safe!"
        val map47458 = HashMap<String, Any>()
        map47458["keyA-47458"] = "a_Value" // put some stuff in the collection
        map47458["keyB-47458"] = param // put it in a collection
        map47458["keyC"] = "another_Value" // put some stuff in the collection
        bar = map47458["keyB-47458"] as String? // get it back out
        bar = map47458["keyA-47458"] as String? // get safe value back out

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
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
