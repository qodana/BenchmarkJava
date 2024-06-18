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
import org.owasp.benchmark.helpers.*
import org.owasp.benchmark.helpers.ThingFactory
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-02/BenchmarkTest02380"])
class BenchmarkTest02380 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        var param = scr.getTheParameter("BenchmarkTest02380")
        if (param == null) param = ""

        val bar = doSomething(request, param)

        var fileName: String? = null
        var fos: FileOutputStream? = null

        try {
            fileName = Utils.TESTFILES_DIR + bar

            fos = FileOutputStream(File(fileName))
            response.writer
                .println(
                    "Now ready to write to file: "
                            + ESAPI.encoder().encodeForHTML(fileName)
                )
        } catch (e: Exception) {
            println("Couldn't open FileOutputStream on file: '$fileName'")
            //			System.out.println("File exception caught and swallowed: " + e.getMessage());
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                    fos = null
                } catch (e: Exception) {
                    // we tried...
                }
            }
        }
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a76789 = param // assign
            val b76789 = StringBuilder(a76789) // stick in stringbuilder
            b76789.append(" SafeStuff") // append some safe content
            b76789.replace(
                b76789.length - "Chars".length,
                b76789.length,
                "Chars"
            ) // replace some of the end content
            val map76789 = HashMap<String, Any>()
            map76789["key76789"] = b76789.toString() // put in a collection
            val c76789 = map76789["key76789"] as String? // get it back out
            val d76789 = c76789!!.substring(0, c76789.length - 1) // extract most of it
            val e76789 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d76789.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f76789 =
                e76789.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g76789 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g76789) // reflection

            return bar
        }
    }
}
