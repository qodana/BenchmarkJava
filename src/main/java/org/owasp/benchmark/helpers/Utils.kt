/**
 * OWASP Benchmark Project
 *
 *
 * This file is part of the Open Web Application Security Project (OWASP) Benchmark Project For
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
package org.owasp.benchmark.helpers

import org.apache.hc.client5.http.ssl.NoopHostnameVerifier
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory
import org.apache.hc.client5.http.ssl.TrustSelfSignedStrategy
import org.apache.hc.core5.ssl.SSLContexts
import org.owasp.benchmark.service.pojo.XMLMessage
import org.owasp.esapi.ESAPI
import java.io.*
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.PosixFilePermission
import java.security.*
import java.text.MessageFormat
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

object Utils {
    // Properties used by the generated test suite
    val USERDIR: String = System.getProperty("user.dir") + File.separator

    // A 'test' directory that target test files are created in so test cases can use them
    val TESTFILES_DIR: String = USERDIR + "testfiles" + File.separator

    // This constant is used by one of the sources for Benchmark 1.2, but not in 1.3+.
    // It is used to filter out common headers. Whatever is left is considered the custom header
    // name for header names test cases
    val commonHeaders: Set<String> = HashSet(
        mutableListOf(
            "accept",
            "accept-encoding",
            "accept-language",
            "cache-control",
            "connection",
            "content-length",
            "content-type",
            "cookie",
            "host",
            "origin",
            "pragma",
            "referer",
            "sec-ch-ua",
            "sec-ch-ua-mobile",
            "sec-ch-ua-platform",
            "sec-fetch-dest",
            "sec-fetch-mode",
            "sec-fetch-site",
            "user-agent",
            "x-requested-with"
        )
    )

    private val safeDocBuilderFactory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()

    init {
        try {
            // Make DBF safe from XXE by disabling doctype declarations (per OWASP XXE cheat sheet)
            safeDocBuilderFactory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl", true
            )
        } catch (e: ParserConfigurationException) {
            println(
                "ERROR: couldn't set http://apache.org/xml/features/disallow-doctype-decl"
            )
            e.printStackTrace()
        }

        val tempDir = File(TESTFILES_DIR)
        if (!tempDir.exists()) {
            tempDir.mkdir()
            val testFile = File(TESTFILES_DIR + "FileName")
            try {
                val out = PrintWriter(testFile)
                out.write("Test is a test file.\n")
                out.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val testFile2 = File(TESTFILES_DIR + "SafeText")
            try {
                val out = PrintWriter(testFile2)
                out.write("Test is a 'safe' test file.\n")
                out.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val secreTestFile = File(TESTFILES_DIR + "SecretFile")
            try {
                val out = PrintWriter(secreTestFile)
                out.write("Test is a 'secret' file that no one should find.\n")
                out.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }

        // The target script is exploded out of the WAR file. When this occurs, the file
        // loses its execute permissions. So this hack adds the required execute permissions back.
        if (!System.getProperty("os.name").contains("Windows")) {
            val script = getFileFromClasspath("insecureCmd.sh", Utils::class.java.classLoader)
            val perms: MutableSet<PosixFilePermission> = HashSet()
            perms.add(PosixFilePermission.OWNER_READ)
            perms.add(PosixFilePermission.OWNER_WRITE)
            perms.add(PosixFilePermission.OWNER_EXECUTE)
            perms.add(PosixFilePermission.GROUP_READ)
            perms.add(PosixFilePermission.GROUP_EXECUTE)
            perms.add(PosixFilePermission.OTHERS_READ)
            perms.add(PosixFilePermission.OTHERS_EXECUTE)

            try {
                Files.setPosixFilePermissions(script!!.toPath(), perms)
            } catch (e: IOException) {
                println(
                    "Problem while changing executable permissions: " + e.message
                )
            }
        }
    }

    fun getCookie(request: HttpServletRequest, paramName: String?): String {
        val values = request.cookies
        var param = "none"
        if (paramName != null) {
            for (i in values.indices) {
                if (values[i].name == paramName) {
                    param = values[i].value
                    break // break out of for loop when param found
                }
            }
        }
        return param
    }

    fun getOSCommandString(append: String): String {
        var command: String? = null
        val osName = System.getProperty("os.name")
        command = if (osName.indexOf("Windows") != -1) {
            "cmd.exe /c $append "
        } else {
            "$append "
        }

        return command
    }

    fun getInsecureOSCommandString(classLoader: ClassLoader): String? {
        var command: String? = null
        val osName = System.getProperty("os.name")
        command = if (osName.indexOf("Windows") != -1) {
            getFileFromClasspath("insecureCmd.bat", classLoader)!!.absolutePath
        } else {
            getFileFromClasspath("insecureCmd.sh", classLoader)!!.absolutePath
        }
        return command
    }

    fun getOSCommandArray(append: String?): List<String> {
        val cmds = ArrayList<String>()

        val osName = System.getProperty("os.name")
        if (osName.indexOf("Windows") != -1) {
            cmds.add("cmd.exe")
            cmds.add("/c")
            if (append != null) {
                cmds.add(append)
            }
        } else {
            cmds.add("sh")
            cmds.add("-c")
            if (append != null) {
                cmds.add(append)
            }
        }

        return cmds
    }

    // A method used by the Benchmark JAVA test cases to format OS Command Output
    @Throws(IOException::class)
    fun printOSCommandResults(proc: Process, response: HttpServletResponse) {
        val out = response.writer
        out.write(
            """<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<p>
"""
        )

        val stdInput = BufferedReader(InputStreamReader(proc.inputStream))
        val stdError = BufferedReader(InputStreamReader(proc.errorStream))

        try {
            // read the output from the command
            // System.out.println("Here is the standard output of the
            // command:\n");
            out.write("Here is the standard output of the command:<br>")
            var s: String? = null
            while ((stdInput.readLine().also { s = it }) != null) {
                out.write(ESAPI.encoder().encodeForHTML(s))
                out.write("<br>")
            }

            // read any errors from the attempted command
            // System.out.println("Here is the standard error of the command (if
            // any):\n");
            out.write("<br>Here is the std err of the command (if any):<br>")
            while ((stdError.readLine().also { s = it }) != null) {
                out.write(ESAPI.encoder().encodeForHTML(s))
                out.write("<br>")
            }
        } catch (e: IOException) {
            println("An error occurred while reading OSCommandResults")
            e.printStackTrace()
        }
    }

    // A method used by the Benchmark JAVA test cases to format OS Command Output
    // This version is only used by the Web Services test cases.
    fun printOSCommandResults(proc: Process, resp: MutableList<XMLMessage?>) {
        val stdInput = BufferedReader(InputStreamReader(proc.inputStream))
        val stdError = BufferedReader(InputStreamReader(proc.errorStream))

        try {
            // read the output from the command
            resp.add(XMLMessage("Here is the standard output of the command:"))
            var s: String? = null
            val out = StringBuffer()
            val outError = StringBuffer()

            while ((stdInput.readLine().also { s = it }) != null) {
                out.append(s).append("\n")
            }
            resp.add(XMLMessage(out.toString()))
            // read any errors from the attempted command
            resp.add(XMLMessage("Here is the std err of the command (if any):"))
            while ((stdError.readLine().also { s = it }) != null) {
                outError.append(s).append("\n")
            }

            resp.add(XMLMessage(outError.toString()))
        } catch (e: IOException) {
            println("An error occurred while reading OSCommandResults")
            e.printStackTrace()
        }
    }

    fun getFileFromClasspath(fileName: String, classLoader: ClassLoader): File? {
        val url = classLoader.getResource(fileName)
        if (url != null) {
            try {
                return File(url.toURI().path)
            } catch (e: URISyntaxException) {
                println(
                    "The file '$fileName' cannot be loaded from the classpath."
                )
                e.printStackTrace()
            }
        } else println("The file '$fileName' cannot be found on the classpath.")
        return null
    }

    fun getLinesFromFile(file: File): List<String>? {
        if (!file.exists()) {
            try {
                println("Can't find file to get lines from: " + file.canonicalFile)
            } catch (e: IOException) {
                println("Can't find file to get lines from.")
                e.printStackTrace()
            }
            return null
        }

        val sourceLines: MutableList<String> = ArrayList()

        try {
            FileReader(file).use { fr ->
                BufferedReader(fr).use { br ->
                    var line: String
                    while ((br.readLine().also { line = it }) != null) {
                        sourceLines.add(line)
                    }
                }
            }
        } catch (e: Exception) {
            try {
                println("Problem reading contents of file: " + file.canonicalFile)
            } catch (e2: IOException) {
                println("Problem reading file to get lines from.")
                e2.printStackTrace()
            }
            e.printStackTrace()
        }

        return sourceLines
    }

    fun getLinesFromFile(filename: String?): List<String>? {
        return getLinesFromFile(File(filename))
    }

    /**
     * Encodes the supplied parameter using ESAPI's encodeForHTML(). Only supports Strings and
     * InputStreams.
     *
     * @param param - The String or InputStream to encode.
     * @return - HTML Entity encoded version of input, or "objectTypeUnknown" if not a supported
     * type.
     */
    fun encodeForHTML(param: Any?): String {
        var value: String? = "objectTypeUnknown"
        if (param is String) {
            value = param
        } else if (param is InputStream) {
            val buff = ByteArray(1000)
            var length = 0
            try {
                val stream = param
                stream.reset()
                length = stream.read(buff)
            } catch (e: IOException) {
                buff[0] = '?'.code.toByte()
                length = 1
            }
            val b = ByteArrayOutputStream()
            b.write(buff, 0, length)
            value = b.toString()
        }
        return ESAPI.encoder().encodeForHTML(value)
    }

    fun writeLineToFile(pathToFileDir: Path?, completeName: String?, line: String?): Boolean {
        var result = true
        var os: PrintStream? = null
        try {
            Files.createDirectories(pathToFileDir)
            val f = File(completeName)
            if (!f.exists()) {
                f.createNewFile()
            }
            val fos = FileOutputStream(f, true)
            os = PrintStream(fos)
            os.println(line)
        } catch (e1: IOException) {
            result = false
            e1.printStackTrace()
        } finally {
            os!!.close()
        }

        return result
    }

    /*
     * A utility method used by the generated Java Cipher test cases.
     */
    var cipher: Cipher? = null
        get() {
            if (field == null) {
                try {
                    field =
                        Cipher.getInstance(
                            "RSA/ECB/OAEPWithSHA-512AndMGF1Padding", "SunJCE"
                        )
                    // Prepare the cipher to encrypt
                    val keyGen =
                        KeyPairGenerator.getInstance("RSA")
                    keyGen.initialize(4096)
                    val publicKey = keyGen.genKeyPair().public
                    field!!.init(Cipher.ENCRYPT_MODE, publicKey)
                } catch (e: NoSuchAlgorithmException) {
                    e.printStackTrace()
                } catch (e: NoSuchProviderException) {
                    e.printStackTrace()
                } catch (e: NoSuchPaddingException) {
                    e.printStackTrace()
                } catch (e: InvalidKeyException) {
                    e.printStackTrace()
                }
            }
            return field
        }
        private set

    @get:Throws(Exception::class)
    val sSLFactory: SSLConnectionSocketFactory
        get() {
            val sslcontext =
                SSLContexts.custom().loadTrustMaterial(null, TrustSelfSignedStrategy()).build()
            // Allow TLSv1 protocol only
            val sslsf =
                SSLConnectionSocketFactory(
                    sslcontext, arrayOf("TLSv1"), null, NoopHostnameVerifier.INSTANCE
                )
            return sslsf
        }

    /**
     * This method returns information about which library the supplied class came from. This is
     * useful when determining what class a Factory instantiated, for example. Mainly used for XXE
     * verification/debugging.
     *
     * @param The name of the class being passed in.
     * @param The class to print information about.
     * @return A string containing the Component Name, the name of the class, possibly the
     * implementation vendor, spec version, implementation version, and the library it came from
     * (or Java Runtime it came from).
     */
    fun getClassImplementationInfo(componentName: String?, componentClass: Class<*>): String {
        val source = componentClass.protectionDomain.codeSource
        val p = componentClass.getPackage()
        return MessageFormat.format(
            "{0} implementation: {1} ({2}) version {3} ({4}) loaded from: {5}",
            componentName,
            componentClass.name,
            p.implementationVendor,
            p.specificationVersion,
            p.implementationVersion,
            if (source == null) "Java_Runtime" else source.location
        )
    }
}
