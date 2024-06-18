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

import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/cmdi-00/BenchmarkTest00742"])
class BenchmarkTest00742 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val values = request.getParameterValues("BenchmarkTest00742")
        val param = if (values != null && values.size > 0) values[0]
        else ""

        var bar: String? = "safe!"
        val map62435 = HashMap<String, Any>()
        map62435["keyA-62435"] = "a_Value" // put some stuff in the collection
        map62435["keyB-62435"] = param // put it in a collection
        map62435["keyC"] = "another_Value" // put some stuff in the collection
        bar = map62435["keyB-62435"] as String? // get it back out
        bar = map62435["keyA-62435"] as String? // get safe value back out

        var cmd: String? = ""
        val osName = System.getProperty("os.name")
        if (osName.indexOf("Windows") != -1) {
            cmd = Utils.getOSCommandString("echo")
        }

        val argsEnv = arrayOf("Foo=bar")
        val r = Runtime.getRuntime()

        try {
            val p =
                r.exec(cmd + bar, argsEnv, File(System.getProperty("user.dir")))
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
