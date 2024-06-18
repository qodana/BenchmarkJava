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
import org.owasp.benchmark.helpers.SeparateClassRequest
import org.owasp.benchmark.helpers.ThingFactory
import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/pathtraver-00/BenchmarkTest00621"])
class BenchmarkTest00621 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        var param = scr.getTheParameter("BenchmarkTest00621")
        if (param == null) param = ""

        // Chain a bunch of propagators in sequence
        val a28566 = param // assign
        val b28566 = StringBuilder(a28566) // stick in stringbuilder
        b28566.append(" SafeStuff") // append some safe content
        b28566.replace(
            b28566.length - "Chars".length,
            b28566.length,
            "Chars"
        ) // replace some of the end content
        val map28566 = HashMap<String, Any>()
        map28566["key28566"] = b28566.toString() // put in a collection
        val c28566 = map28566["key28566"] as String? // get it back out
        val d28566 = c28566!!.substring(0, c28566.length - 1) // extract most of it
        val e28566 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d28566.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f28566 = e28566.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g28566 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g28566) // reflection

        val fileTarget =
            File(
                File(Utils.TESTFILES_DIR), bar
            )
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
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
