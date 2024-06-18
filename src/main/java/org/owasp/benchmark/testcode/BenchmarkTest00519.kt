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

import org.owasp.benchmark.helpers.DatabaseHelper
import java.io.IOException
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(value = ["/sqli-01/BenchmarkTest00519"])
class BenchmarkTest00519 : HttpServlet() {
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
            val values = map["BenchmarkTest00519"]
            if (values != null) param = values[0]
        }

        var bar: String? = "safe!"
        val map30190 = HashMap<String, Any>()
        map30190["keyA-30190"] = "a_Value" // put some stuff in the collection
        map30190["keyB-30190"] = param // put it in a collection
        map30190["keyC"] = "another_Value" // put some stuff in the collection
        bar = map30190["keyB-30190"] as String? // get it back out
        bar = map30190["keyA-30190"] as String? // get safe value back out

        val sql = "INSERT INTO users (username, password) VALUES ('foo','$bar')"

        try {
            val statement =
                DatabaseHelper.sqlStatement
            val count = statement!!.executeUpdate(sql, arrayOf("USERNAME", "PASSWORD"))
            DatabaseHelper.outputUpdateComplete(sql, response)
        } catch (e: SQLException) {
            if (DatabaseHelper.hideSQLErrors) {
                response.writer.println("Error processing request.")
                return
            } else throw ServletException(e)
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
