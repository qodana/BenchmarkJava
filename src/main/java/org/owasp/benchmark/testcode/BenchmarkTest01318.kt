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
import java.security.*
import java.security.spec.AlgorithmParameterSpec
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/crypto-01/BenchmarkTest01318"])
class BenchmarkTest01318 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val map = request.parameterMap
        var param = ""
        if (!map.isEmpty()) {
            val values = map["BenchmarkTest01318"]
            if (values != null) param = values[0]
        }

        val bar = Test().doSomething(request, param)

        // Code based on example from:
        // http://examples.javacodegeeks.com/core-java/crypto/encrypt-decrypt-file-stream-with-des/
        // 8-byte initialization vector
        //	    byte[] iv = {
        //	    	(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
        //	    	(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3033
        //	    };
        val random = SecureRandom()
        val iv = random.generateSeed(8) // DES requires 8 byte keys

        try {
            val c =
                Cipher.getInstance(
                    "DES/CBC/PKCS5PADDING", Security.getProvider("SunJCE")
                )

            // Prepare the cipher to encrypt
            val key = KeyGenerator.getInstance("DES").generateKey()
            val paramSpec: AlgorithmParameterSpec =
                IvParameterSpec(iv)
            c.init(Cipher.ENCRYPT_MODE, key, paramSpec)

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
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        } catch (e: NoSuchPaddingException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
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
        } catch (e: InvalidKeyException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            response.writer
                .println(
                    "Problem executing crypto - javax.crypto.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case"
                )
            e.printStackTrace(response.writer)
            throw ServletException(e)
        }
        response.writer
            .println(
                "Crypto Test javax.crypto.Cipher.getInstance(java.lang.String,java.security.Provider) executed"
            )
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String): String? {
            // Chain a bunch of propagators in sequence

            val a66881 = param // assign
            val b66881 = StringBuilder(a66881) // stick in stringbuilder
            b66881.append(" SafeStuff") // append some safe content
            b66881.replace(
                b66881.length - "Chars".length,
                b66881.length,
                "Chars"
            ) // replace some of the end content
            val map66881 = HashMap<String, Any>()
            map66881["key66881"] = b66881.toString() // put in a collection
            val c66881 = map66881["key66881"] as String? // get it back out
            val d66881 = c66881!!.substring(0, c66881.length - 1) // extract most of it
            val e66881 = String(
                Base64.decodeBase64(
                    Base64.encodeBase64(
                        d66881.toByteArray()
                    )
                )
            ) // B64 encode and decode it
            val f66881 =
                e66881.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
            val thing =
                ThingFactory.createThing()
            val g66881 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
            val bar = thing.doSomething(g66881) // reflection

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

