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
import java.io.FileWriter
import java.io.IOException
import java.io.InputStream
import java.net.URLDecoder
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/hash-00/BenchmarkTest00796"])
class BenchmarkTest00796 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val queryString = request.queryString
        val paramval = "BenchmarkTest00796" + "="
        var paramLoc = -1
        if (queryString != null) paramLoc = queryString.indexOf(paramval)
        if (paramLoc == -1) {
            response.writer
                .println(
                    "getQueryString() couldn't find expected parameter '"
                            + "BenchmarkTest00796"
                            + "' in query string."
                )
            return
        }

        var param =
            queryString!!.substring(
                paramLoc
                        + paramval
                    .length
            ) // 1st assume "BenchmarkTest00796" param is last
        // parameter in query string.
        // And then check to see if its in the middle of the query string and if so, trim off what
        // comes after.
        val ampersandLoc = queryString.indexOf("&", paramLoc)
        if (ampersandLoc != -1) {
            param = queryString.substring(paramLoc + paramval.length, ampersandLoc)
        }
        param = URLDecoder.decode(param, "UTF-8")

        var bar: String? = "safe!"
        val map72213 = HashMap<String, Any>()
        map72213["keyA-72213"] = "a-Value" // put some stuff in the collection
        map72213["keyB-72213"] = param // put it in a collection
        map72213["keyC"] = "another-Value" // put some stuff in the collection
        bar = map72213["keyB-72213"] as String? // get it back out

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
