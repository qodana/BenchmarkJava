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
import javax.crypto.BadPaddingException
import javax.crypto.IllegalBlockSizeException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/crypto-01/BenchmarkTest00780"])
class BenchmarkTest00780 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val queryString = request.queryString
        val paramval = "BenchmarkTest00780" + "="
        var paramLoc = -1
        if (queryString != null) paramLoc = queryString.indexOf(paramval)
        if (paramLoc == -1) {
            response.writer
                .println(
                    "getQueryString() couldn't find expected parameter '"
                            + "BenchmarkTest00780"
                            + "' in query string."
                )
            return
        }

        var param =
            queryString!!.substring(
                paramLoc
                        + paramval
                    .length
            ) // 1st assume "BenchmarkTest00780" param is last
        // parameter in query string.
        // And then check to see if its in the middle of the query string and if so, trim off what
        // comes after.
        val ampersandLoc = queryString.indexOf("&", paramLoc)
        if (ampersandLoc != -1) {
            param = queryString.substring(paramLoc + paramval.length, ampersandLoc)
        }
        param = URLDecoder.decode(param, "UTF-8")

        val bar: String
        val guess = "ABC"
        val switchTarget = guess[2]

        bar = when (switchTarget) {
            'A' -> param
            'B' -> "bobs_your_uncle"
            'C', 'D' -> param
            else -> "bobs_your_uncle"
        }
        // Code based on example from:
        // http://examples.javacodegeeks.com/core-java/crypto/encrypt-decrypt-file-stream-with-des/
        try {
            val c = Utils.cipher
            // encrypt and store the results
            var input = byteArrayOf('?'.code.toByte())
            val inputParam: Any = bar
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
            val result = c!!.doFinal(input)

            val fileTarget =
                File(
                    File(Utils.TESTFILES_DIR),
                    "passwordFile.txt"
                )
            val fw =
                FileWriter(fileTarget, true) // the true will append the new data
            fw.write(
                "secret_value="
                        + ESAPI.encoder().encodeForBase64(result, true)
                        + "\n"
            )
            fw.close()
            response.writer
                .println(
                    "Sensitive value: '"
                            + ESAPI
                        .encoder()
                        .encodeForHTML(String(input))
                            + "' encrypted and stored<br/>"
                )
        } catch (e: IllegalBlockSizeException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        } catch (e: BadPaddingException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        }
        response.writer
            .println(
                "Crypto Test javax.crypto.Cipher.getInstance(java.lang.String,java.lang.String) executed"
            )
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
