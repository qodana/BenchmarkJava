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
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/cmdi-00/BenchmarkTest00559"])
class BenchmarkTest00559 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        var flag = true
        val names = request.parameterNames
        while (names.hasMoreElements() && flag) {
            val name = names.nextElement() as String
            val values = request.getParameterValues(name)
            if (values != null) {
                var i = 0
                while (i < values.size && flag) {
                    val value = values[i]
                    if (value == "BenchmarkTest00559") {
                        param = name
                        flag = false
                    }
                    i++
                }
            }
        }

        // Chain a bunch of propagators in sequence
        val a39502 = param // assign
        val b39502 = StringBuilder(a39502) // stick in stringbuilder
        b39502.append(" SafeStuff") // append some safe content
        b39502.replace(
            b39502.length - "Chars".length,
            b39502.length,
            "Chars"
        ) // replace some of the end content
        val map39502 = HashMap<String, Any>()
        map39502["key39502"] = b39502.toString() // put in a collection
        val c39502 = map39502["key39502"] as String? // get it back out
        val d39502 = c39502!!.substring(0, c39502.length - 1) // extract most of it
        val e39502 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d39502.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f39502 = e39502.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g39502 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g39502) // reflection

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

        val pb = ProcessBuilder(argList)

        try {
            val p = pb.start()
            Utils.printOSCommandResults(p, response)
        } catch (e: IOException) {
            println(
                "Problem executing cmdi - java.lang.ProcessBuilder(java.util.List) Test Case"
            )
            throw ServletException(e)
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
