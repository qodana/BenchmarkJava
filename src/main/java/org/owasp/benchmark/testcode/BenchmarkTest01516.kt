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
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/cmdi-01/BenchmarkTest01516"])
class BenchmarkTest01516 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        var param = scr.getTheParameter("BenchmarkTest01516")
        if (param == null) param = ""

        val bar = Test().doSomething(request, param)

        var a1 = ""
        var a2 = ""
        val osName = System.getProperty("os.name")
        if (osName.indexOf("Windows") != -1) {
            a1 = "cmd.exe"
            a2 = "/c"
        } else {
            a1 = "sh"
            a2 = "-c"
        }
        val args = arrayOf(a1, a2, "echo $bar")

        val pb = ProcessBuilder()

        pb.command(*args)

        try {
            val p = pb.start()
            Utils.printOSCommandResults(p, response)
        } catch (e: IOException) {
            println(
                "Problem executing cmdi - java.lang.ProcessBuilder(java.util.List) Test Case"
            )
            throw ServletException(e)
        }
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a84214 = param // assign
            val b84214 = StringBuilder(a84214) // stick in stringbuilder
            b84214.append(" SafeStuff") // append some safe content
            b84214.replace(
                b84214.length - "Chars".length,
                b84214.length,
                "Chars"
            ) // replace some of the end content
            val map84214 = HashMap<String, Any>()
            map84214["key84214"] = b84214.toString() // put in a collection
            val c84214 = map84214["key84214"] as String? // get it back out
            val d84214 = c84214!!.substring(0, c84214.length - 1) // extract most of it
            val e84214 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d84214.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f84214 =
                e84214.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g84214 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g84214) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

