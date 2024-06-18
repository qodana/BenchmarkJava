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
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/cmdi-02/BenchmarkTest02148"])
class BenchmarkTest02148 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest02148")
        if (param == null) param = ""

        val bar = doSomething(request, param)

        var cmd = ""
        var a1 = ""
        var a2 = ""
        var args: Array<String>? = null
        val osName = System.getProperty("os.name")

        if (osName.indexOf("Windows") != -1) {
            a1 = "cmd.exe"
            a2 = "/c"
            cmd = "echo "
            args = arrayOf(a1, a2, cmd, bar!!)
        } else {
            a1 = "sh"
            a2 = "-c"
            cmd = Utils.getOSCommandString("ls ")
            args = arrayOf(a1, a2, cmd + bar)
        }

        val argsEnv = arrayOf("foo=bar")

        val r = Runtime.getRuntime()

        try {
            val p = r.exec(args, argsEnv)
            Utils.printOSCommandResults(p, response)
        } catch (e: IOException) {
            println("Problem executing cmdi - TestCase")
            response.writer
                .println(ESAPI.encoder().encodeForHTML(e.message))
            return
        }
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a17988 = param // assign
            val b17988 = StringBuilder(a17988) // stick in stringbuilder
            b17988.append(" SafeStuff") // append some safe content
            b17988.replace(
                b17988.length - "Chars".length,
                b17988.length,
                "Chars"
            ) // replace some of the end content
            val map17988 = HashMap<String, Any>()
            map17988["key17988"] = b17988.toString() // put in a collection
            val c17988 = map17988["key17988"] as String? // get it back out
            val d17988 = c17988!!.substring(0, c17988.length - 1) // extract most of it
            val e17988 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d17988.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f17988 =
                e17988.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g17988 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g17988) // reflection

            return bar
        }
    }
}
