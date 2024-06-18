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
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/crypto-00/BenchmarkTest00524"])
class BenchmarkTest00524 : HttpServlet() {
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
                    if (value == "BenchmarkTest00524") {
                        param = name
                        flag = false
                    }
                    i++
                }
            }
        }

        // Chain a bunch of propagators in sequence
        val a18964 = param // assign
        val b18964 = StringBuilder(a18964) // stick in stringbuilder
        b18964.append(" SafeStuff") // append some safe content
        b18964.replace(
            b18964.length - "Chars".length,
            b18964.length,
            "Chars"
        ) // replace some of the end content
        val map18964 = HashMap<String, Any>()
        map18964["key18964"] = b18964.toString() // put in a collection
        val c18964 = map18964["key18964"] as String? // get it back out
        val d18964 = c18964!!.substring(0, c18964.length - 1) // extract most of it
        val e18964 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d18964.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f18964 = e18964.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val bar = thing.doSomething(f18964) // reflection

        // Code based on example from:
        // http://examples.javacodegeeks.com/core-java/crypto/encrypt-decrypt-file-stream-with-des/
        // 8-byte initialization vector
        //		byte[] iv = {
        //			(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
        //			(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3033
        //		};
        //		java.security.SecureRandom random = new java.security.SecureRandom();
        //		byte[] iv = random.generateSeed(16);
        try {
            val benchmarkprops = Properties()
            benchmarkprops.load(
                this.javaClass.classLoader.getResourceAsStream("benchmark.properties")
            )
            val algorithm = benchmarkprops.getProperty("cryptoAlg2", "AES/ECB/PKCS5Padding")
            val c = Cipher.getInstance(algorithm)

            // Prepare the cipher to encrypt
            val key = KeyGenerator.getInstance("AES").generateKey()
            c.init(Cipher.ENCRYPT_MODE, key)

            // encrypt and store the results
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
            val result = c.doFinal(input)

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
        } catch (e: NoSuchAlgorithmException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        } catch (e: NoSuchPaddingException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        } catch (e: IllegalBlockSizeException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        } catch (e: BadPaddingException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        } catch (e: InvalidKeyException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
