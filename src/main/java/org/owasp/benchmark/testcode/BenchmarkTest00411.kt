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
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/cmdi-00/BenchmarkTest00411"])
class BenchmarkTest00411 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest00411")
        if (param == null) param = ""

        // Chain a bunch of propagators in sequence
        val a18204 = param // assign
        val b18204 = StringBuilder(a18204) // stick in stringbuilder
        b18204.append(" SafeStuff") // append some safe content
        b18204.replace(
            b18204.length - "Chars".length,
            b18204.length,
            "Chars"
        ) // replace some of the end content
        val map18204 = HashMap<String, Any>()
        map18204["key18204"] = b18204.toString() // put in a collection
        val c18204 = map18204["key18204"] as String? // get it back out
        val d18204 = c18204!!.substring(0, c18204.length - 1) // extract most of it
        val e18204 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d18204.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f18204 = e18204.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g18204 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g18204) // reflection

        val cmd =
            Utils.getInsecureOSCommandString(
                this.javaClass.classLoader
            )

        val argsEnv = arrayOf(bar)
        val r = Runtime.getRuntime()

        try {
            val p = r.exec(cmd, argsEnv)
            Utils.printOSCommandResults(p, response)
        } catch (e: IOException) {
            println("Problem executing cmdi - TestCase")
            response.writer
                .println(ESAPI.encoder().encodeForHTML(e.message))
            return
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
