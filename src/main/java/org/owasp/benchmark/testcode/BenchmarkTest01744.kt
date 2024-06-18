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
import java.io.File
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-02/BenchmarkTest01744"])
class BenchmarkTest01744 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        val param = scr.getTheValue("BenchmarkTest01744")

        val bar = Test().doSomething(request, param)

        val fileTarget =
            File(Utils.TESTFILES_DIR, bar)
        response.writer
            .println(
                "Access to file: '"
                        + ESAPI
                    .encoder()
                    .encodeForHTML(fileTarget.toString())
                        + "' created."
            )
        if (fileTarget.exists()) {
            response.writer.println(" And file already exists.")
        } else {
            response.writer.println(" But file doesn't exist yet.")
        }
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a17402 = param // assign
            val b17402 = StringBuilder(a17402) // stick in stringbuilder
            b17402.append(" SafeStuff") // append some safe content
            b17402.replace(
                b17402.length - "Chars".length,
                b17402.length,
                "Chars"
            ) // replace some of the end content
            val map17402 = HashMap<String, Any>()
            map17402["key17402"] = b17402.toString() // put in a collection
            val c17402 = map17402["key17402"] as String? // get it back out
            val d17402 = c17402!!.substring(0, c17402.length - 1) // extract most of it
            val e17402 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d17402.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f17402 =
                e17402.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g17402 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g17402) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

