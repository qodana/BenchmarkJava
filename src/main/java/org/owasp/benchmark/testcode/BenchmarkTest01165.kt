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
 * @author Dave Wichers
 * @created 2015
 */
package org.owasp.benchmark.testcode

import org.apache.commons.codec.binary.Base64
import org.owasp.benchmark.helpers.*
import org.owasp.benchmark.helpers.ThingFactory
import org.owasp.esapi.ESAPI
import java.io.*
import java.net.URLDecoder
import java.security.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/hash-01/BenchmarkTest01165"])
class BenchmarkTest01165 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = ""
        val headers = request.getHeaders("BenchmarkTest01165")

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement() // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = URLDecoder.decode(param, "UTF-8")

        val bar = Test().doSomething(request, param)

        try {
            val md = MessageDigest.getInstance("SHA1", "SUN")
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

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a91034 = param // assign
            val b91034 = StringBuilder(a91034) // stick in stringbuilder
            b91034.append(" SafeStuff") // append some safe content
            b91034.replace(
                b91034.length - "Chars".length,
                b91034.length,
                "Chars"
            ) // replace some of the end content
            val map91034 = HashMap<String, Any>()
            map91034["key91034"] = b91034.toString() // put in a collection
            val c91034 = map91034["key91034"] as String? // get it back out
            val d91034 = c91034!!.substring(0, c91034.length - 1) // extract most of it
            val e91034 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d91034.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f91034 =
                e91034.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g91034 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g91034) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

