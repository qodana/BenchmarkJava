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
import org.owasp.benchmark.helpers.LDAPManager
import org.owasp.benchmark.helpers.ThingFactory
import org.owasp.esapi.ESAPI
import java.io.IOException
import javax.naming.NamingException
import javax.naming.directory.InitialDirContext
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/ldapi-00/BenchmarkTest00367"])
class BenchmarkTest00367 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        var param = request.getParameter("BenchmarkTest00367")
        if (param == null) param = ""

        // Chain a bunch of propagators in sequence
        val a12849 = param // assign
        val b12849 = StringBuilder(a12849) // stick in stringbuilder
        b12849.append(" SafeStuff") // append some safe content
        b12849.replace(
            b12849.length - "Chars".length,
            b12849.length,
            "Chars"
        ) // replace some of the end content
        val map12849 = HashMap<String, Any>()
        map12849["key12849"] = b12849.toString() // put in a collection
        val c12849 = map12849["key12849"] as String? // get it back out
        val d12849 = c12849!!.substring(0, c12849.length - 1) // extract most of it
        val e12849 = String(
            Base64.decodeBase64(
                Base64.encodeBase64(
                    d12849.toByteArray()
                )
            )
        ) // B64 encode and decode it
        val f12849 = e12849.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] // split it on a space
        val thing =
            ThingFactory.createThing()
        val g12849 = "barbarians_at_the_gate" // This is static so this whole flow is 'safe'
        val bar = thing.doSomething(g12849) // reflection

        val ads = LDAPManager()
        try {
            response.contentType = "text/html;charset=UTF-8"
            val base = "ou=users,ou=system"
            val sc = SearchControls()
            sc.searchScope = SearchControls.SUBTREE_SCOPE
            val filter = "(&(objectclass=person))(|(uid=$bar)(street={0}))"
            val filters = arrayOf("The streetz 4 Ms bar")

            val ctx = ads.dirContext
            val idc =
                ctx as InitialDirContext
            var found = false
            val results =
                idc.search(base, filter, filters, sc)
            while (results.hasMore()) {
                val sr =
                    results.next() as SearchResult
                val attrs = sr.attributes

                val attr = attrs["uid"]
                val attr2 = attrs["street"]
                if (attr != null) {
                    response.writer
                        .println(
                            "LDAP query results:<br>"
                                    + "Record found with name "
                                    + attr.get()
                                    + "<br>"
                                    + "Address: "
                                    + attr2.get()
                                    + "<br>"
                        )
                    // System.out.println("record found " + attr.get());
                    found = true
                }
            }
            if (!found) {
                response.writer
                    .println(
                        "LDAP query results: nothing found for query: "
                                + ESAPI.encoder().encodeForHTML(filter)
                    )
            }
        } catch (e: NamingException) {
            throw ServletException(e)
        } finally {
            try {
                ads.closeDirContext()
            } catch (e: Exception) {
                throw ServletException(e)
            }
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
