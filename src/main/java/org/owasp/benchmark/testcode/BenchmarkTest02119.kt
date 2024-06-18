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
import java.io.*
import java.security.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/hash-02/BenchmarkTest02119"])
class BenchmarkTest02119 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest02119")
        if (param == null) param = ""

        val bar = doSomething(request, param)

        try {
            val md =
                MessageDigest.getInstance("SHA-512", "SUN")
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
                "Problem executing hash - TestCase java.security.MessageDigest.getInstance(java.lang.String,java.lang.String)"
            )
            throw ServletException(e)
        } catch (e: NoSuchProviderException) {
            println(
                "Problem executing hash - TestCase java.security.MessageDigest.getInstance(java.lang.String,java.lang.String)"
            )
            throw ServletException(e)
        }

        response.writer
            .println(
                "Hash Test java.security.MessageDigest.getInstance(java.lang.String,java.lang.String) executed"
            )
    } // end doPost

    companion object {
        private const val serialVersionUID = 1L

        @Throws(ServletException::class, IOException::class)
        private fun doSomething(request: HttpServletRequest, param: String): String? {
            var bar: String? = "safe!"
            val map70096 = HashMap<String, Any>()
            map70096["keyA-70096"] = "a-Value" // put some stuff in the collection
            map70096["keyB-70096"] = param // put it in a collection
            map70096["keyC"] = "another-Value" // put some stuff in the collection
            bar = map70096["keyB-70096"] as String? // get it back out

            return bar
        }
    }
}
