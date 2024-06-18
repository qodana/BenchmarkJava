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

import org.owasp.benchmark.helpers.LDAPManager
import org.owasp.benchmark.helpers.SeparateClassRequest
import org.owasp.esapi.ESAPI
import java.io.IOException
import javax.naming.NamingException
import javax.naming.directory.SearchControls
import javax.naming.directory.SearchResult
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



@WebServlet(value = ["/ldapi-00/BenchmarkTest01492"])
class BenchmarkTest01492 : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    public override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        doPost(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    public override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html;charset=UTF-8"

        val scr =
            SeparateClassRequest(request)
        var param = scr.getTheParameter("BenchmarkTest01492")
        if (param == null) param = ""

        val bar = Test().doSomething(request, param)

        val ads = LDAPManager()
        try {
            response.contentType = "text/html;charset=UTF-8"
            val ctx = ads.dirContext
            val base = "ou=users,ou=system"
            val sc = SearchControls()
            sc.searchScope = SearchControls.SUBTREE_SCOPE
            val filter = "(&(objectclass=person)(uid=$bar))"
            // System.out.println("Filter " + filter);
            var found = false
            val results =
                ctx!!.search(base, filter, sc)
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
    } // end doPost

    private inner class Test {
        @Throws(ServletException::class, IOException::class)
        fun doSomething(request: HttpServletRequest?, param: String?): String {
            val bar: String

            // Simple ? condition that assigns constant to bar on true condition
            val num = 106

            bar = if ((7 * 18) + num > 200) "This_should_always_happen" else param!!

            return bar
        }
    } // end innerclass Test

    companion object {
        private const val serialVersionUID = 1L
    }
} // end DataflowThruInnerClass

