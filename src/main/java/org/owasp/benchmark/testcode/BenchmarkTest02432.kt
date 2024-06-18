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

import org.owasp.benchmark.helpers.*
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/cmdi-02/BenchmarkTest02432"])
class BenchmarkTest02432 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        var param = scr.getTheParameter("BenchmarkTest02432")
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
            val p = r.exec(args, argsEnv, File(System.getProperty("user.dir")))
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
            var bar: String? = "safe!"
            val map15850 = HashMap<String, Any>()
            map15850["keyA-15850"] = "a-Value" // put some stuff in the collection
            map15850["keyB-15850"] = param // put it in a collection
            map15850["keyC"] = "another-Value" // put some stuff in the collection
            bar = map15850["keyB-15850"] as String? // get it back out

            return bar
        }
    }
}
