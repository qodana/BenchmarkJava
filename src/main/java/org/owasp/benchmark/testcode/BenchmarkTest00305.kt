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
import java.io.File
import java.io.IOException
import java.net.URLDecoder
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/cmdi-00/BenchmarkTest00305"])
class BenchmarkTest00305 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("BenchmarkTest00305")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        // Chain a bunch of propagators in sequence
        val a99928 = param // assign
        val b99928 = StringBuilder(a99928) // stick in stringbuilder
        b99928.append(" SafeStuff") // append some safe content
        b99928.replace(
            b99928.length - "Chars".length,
            b99928.length,
            "Chars"
        ) // replace some of the end content
        val map99928 = HashMap<String, Any>()
        map99928["key99928"] = b99928.toString() // put in a collection
        val c99928 = map99928["key99928"] as String? // get it back out
        val d99928 = c99928!!.substring(0, c99928.length - 1) // extract most of it
        val e99928 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d99928.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f99928 = e99928.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g99928 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g99928) // reflection

        var cmd: String? = ""
        var a1 = ""
        var a2 = ""
        var args: Array<String?>? = null
        val osName = System.getProperty("os.name")

        if (osName.indexOf("Windows") != -1) {
            a1 = "cmd.exe"
            a2 = "/c"
            cmd = "echo "
            args = arrayOf(a1, a2, cmd, bar)
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
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
