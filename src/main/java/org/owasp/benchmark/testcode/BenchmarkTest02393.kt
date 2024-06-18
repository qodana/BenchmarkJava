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
import java.io.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/hash-02/BenchmarkTest02393"])
class BenchmarkTest02393 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        var param = scr.getTheParameter("BenchmarkTest02393")
        if (param == null) param = ""

        val bar = doSomething(request, param)

        try {
            val benchmarkprops = Properties()
            benchmarkprops.load(
                this.javaClass.classLoader.getResourceAsStream("benchmark.properties")
            )
            val algorithm = benchmarkprops.getProperty("hashAlg1", "SHA512")
            val md = MessageDigest.getInstance(algorithm)
            var input = byteArrayOf('?'.code.toByte())
            val inputParam: Any? = bar
            if (inputParam is String) input = inputParam.toByteArray()
            if (inputParam is InputStream) {
                val strInput = ByteArray(1000)
                val i = inputParam.read(strInput)
                if (i == -1) {
                    response.writer
                        .println(
                            "This input source requires a POST, not a GET. Incompatible UI for the InputStream source."
                        )
                    return
                }
                input = strInput.copyOf(i)
            }
            md.update(input)

            val result = md.digest()
            val fileTarget =
                File(
                    File(Utils.TESTFILES_DIR),
                    "passwordFile.txt"
                )
            val fw =
                FileWriter(fileTarget, true) // the true will append the new data
            fw.write(
                "hash_value="
                        + ESAPI.encoder().encodeForBase64(result, true)
                        + "\n"
            )
            fw.close()
            response.writer
                .println(
                    "Sensitive value '"
                            + ESAPI
                        .encoder()
                        .encodeForHTML(String(input))
                            + "' hashed and stored<br/>"
                )
        } catch (e: NoSuchAlgorithmException) {
            println("Problem executing hash - TestCase")
            throw ServletException(e)
        }

        response.writer
            .println(
                "Hash Test java.security.MessageDigest.getInstance(java.lang.String) executed"
            )
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a24492 = param // assign
            val b24492 = StringBuilder(a24492) // stick in stringbuilder
            b24492.append(" SafeStuff") // append some safe content
            b24492.replace(
                b24492.length - "Chars".length,
                b24492.length,
                "Chars"
            ) // replace some of the end content
            val map24492 = HashMap<String, Any>()
            map24492["key24492"] = b24492.toString() // put in a collection
            val c24492 = map24492["key24492"] as String? // get it back out
            val d24492 = c24492!!.substring(0, c24492.length - 1) // extract most of it
            val e24492 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d24492.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f24492 =
                e24492.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g24492 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g24492) // reflection

            return bar
        }
    }
}
