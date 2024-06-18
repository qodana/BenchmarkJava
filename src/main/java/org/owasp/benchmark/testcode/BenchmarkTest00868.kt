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
import org.owasp.benchmark.helpers.SeparateClassRequest
import org.owasp.benchmark.helpers.ThingFactory
import org.owasp.benchmark.helpers.Utils
import org.owasp.esapi.ESAPI
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.Security
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/hash-01/BenchmarkTest00868"])
class BenchmarkTest00868 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        val param = scr.getTheValue("BenchmarkTest00868")

        // Chain a bunch of propagators in sequence
        val a51118 = param // assign
        val b51118 = StringBuilder(a51118) // stick in stringbuilder
        b51118.append(" SafeStuff") // append some safe content
        b51118.replace(
            b51118.length - "Chars".length,
            b51118.length,
            "Chars"
        ) // replace some of the end content
        val map51118 = HashMap<String, Any>()
        map51118["key51118"] = b51118.toString() // put in a collection
        val c51118 = map51118["key51118"] as String? // get it back out
        val d51118 = c51118!!.substring(0, c51118.length - 1) // extract most of it
        val e51118 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d51118.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f51118 = e51118.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val bar = thing.doSomething(f51118) // reflection

        val provider = Security.getProviders()
        val md: MessageDigest

        try {
            md = if (provider.size > 1) {
                MessageDigest.getInstance("SHA1", provider[0])
            } else {
                MessageDigest.getInstance("SHA1", "SUN")
            }
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
            println(
                "Problem executing hash - TestCase java.security.MessageDigest.getInstance(java.lang.String,java.security.Provider)"
            )
            throw ServletException(e)
        } catch (e: NoSuchProviderException) {
            println(
                "Problem executing hash - TestCase java.security.MessageDigest.getInstance(java.lang.String,java.security.Provider)"
            )
            throw ServletException(e)
        }

        response.writer
            .println(
                "Hash Test java.security.MessageDigest.getInstance(java.lang.String,java.security.Provider) executed"
            )
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
