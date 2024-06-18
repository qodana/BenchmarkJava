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

@WebServlet(value = ["/trustbound-00/BenchmarkTest00427"])
class BenchmarkTest00427 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest00427")
        if (param == null) param = ""

        // Chain a bunch of propagators in sequence
        val a70670 = param // assign
        val b70670 = StringBuilder(a70670) // stick in stringbuilder
        b70670.append(" SafeStuff") // append some safe content
        b70670.replace(
            b70670.length - "Chars".length,
            b70670.length,
            "Chars"
        ) // replace some of the end content
        val map70670 = HashMap<String, Any>()
        map70670["key70670"] = b70670.toString() // put in a collection
        val c70670 = map70670["key70670"] as String? // get it back out
        val d70670 = c70670!!.substring(0, c70670.length - 1) // extract most of it
        val e70670 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d70670.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f70670 = e70670.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val bar = thing.doSomething(f70670) // reflection

        // javax.servlet.http.HttpSession.setAttribute(java.lang.String,java.lang.Object^)
        request.session.setAttribute("userid", bar)

        response.writer
            .println(
                "Item: 'userid' with value: '"
                        + Utils.encodeForHTML(bar)
                        + "' saved in session."
            )
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
