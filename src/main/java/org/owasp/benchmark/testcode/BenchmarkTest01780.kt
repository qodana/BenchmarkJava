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

import org.owasp.benchmark.helpers.*
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/cmdi-02/BenchmarkTest01780"])
class BenchmarkTest01780 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        val param = scr.getTheValue("BenchmarkTest01780")

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

        val pb = ProcessBuilder(*args)

        try {
            val p = pb.start()
            Utils.printOSCommandResults(p, response)
        } catch (e: IOException) {
            println(
                "Problem executing cmdi - java.lang.ProcessBuilder(java.lang.String[]) Test Case"
            )
            throw ServletException(e)
        }
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            var bar: String? = "safe!"
            val map90655 = HashMap<String, Any>()
            map90655["keyA-90655"] = "a_Value" // put some stuff in the collection
            map90655["keyB-90655"] = param // put it in a collection
            map90655["keyC"] = "another_Value" // put some stuff in the collection
            bar = map90655["keyB-90655"] as String? // get it back out
            bar = map90655["keyA-90655"] as String? // get safe value back out

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

