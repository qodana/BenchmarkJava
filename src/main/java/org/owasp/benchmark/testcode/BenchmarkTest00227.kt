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
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/hash-00/BenchmarkTest00227"])
class BenchmarkTest00227 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val names = request.headerNames
        while (names.hasMoreElements()) {
            val name = names.nextElement() as String

            if (Utils.commonHeaders.contains(name)) {
                continue  // If standard header, move on to next one
            }

            val values = request.getHeaders(name)
            if (values != null && values.hasMoreElements()) {
                param = name // Grabs the name of the first non-standard header as the parameter
                // value
                break
            }
        }

        // Note: We don't URL decode header names because people don't normally do that

        // Chain a bunch of propagators in sequence
        val a15913 = param // assign
        val b15913 = StringBuilder(a15913) // stick in stringbuilder
        b15913.append(" SafeStuff") // append some safe content
        b15913.replace(
            b15913.length - "Chars".length,
            b15913.length,
            "Chars"
        ) // replace some of the end content
        val map15913 = HashMap<String, Any>()
        map15913["key15913"] = b15913.toString() // put in a collection
        val c15913 = map15913["key15913"] as String? // get it back out
        val d15913 = c15913!!.substring(0, c15913.length - 1) // extract most of it
        val e15913 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d15913.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f15913 = e15913.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g15913 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g15913) // reflection

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
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
