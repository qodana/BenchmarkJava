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
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/cmdi-03/BenchmarkTest02697"])
class BenchmarkTest02697 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        val param = scr.getTheValue("BenchmarkTest02697")

        val bar = doSomething(request, param)

        val argList: MutableList<String> = ArrayList()

        val osName = System.getProperty("os.name")
        if (osName.indexOf("Windows") != -1) {
            argList.add("cmd.exe")
            argList.add("/c")
        } else {
            argList.add("sh")
            argList.add("-c")
        }
        argList.add("echo $bar")

        val pb = ProcessBuilder()

        pb.command(argList)

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

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            var bar: String? = "safe!"
            val map29916 = HashMap<String, Any>()
            map29916["keyA-29916"] = "a-Value" // put some stuff in the collection
            map29916["keyB-29916"] = param // put it in a collection
            map29916["keyC"] = "another-Value" // put some stuff in the collection
            bar = map29916["keyB-29916"] as String? // get it back out

            return bar
        }
    }
}
