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
import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import java.io.FileOutputStream
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/pathtraver-00/BenchmarkTest00220"])
class BenchmarkTest00220 : HttpServlet() {
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

        // Chain a bunch of propagators in sequence
        val a25969 = param // assign
        val b25969 = StringBuilder(a25969) // stick in stringbuilder
        b25969.append(" SafeStuff") // append some safe content
        b25969.replace(
            b25969.length - "Chars".length,
            b25969.length,
            "Chars"
        ) // replace some of the end content
        val map25969 = HashMap<String, Any>()
        map25969["key25969"] = b25969.toString() // put in a collection
        val c25969 = map25969["key25969"] as String? // get it back out
        val d25969 = c25969!!.substring(0, c25969.length - 1) // extract most of it
        val e25969 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d25969.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f25969 = e25969.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g25969 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g25969) // reflection

        var fileName: String? = null
        var fos: FileOutputStream? = null

        try {
            fileName = Utils.TESTFILES_DIR + bar

            fos = FileOutputStream(fileName, false)
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
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
