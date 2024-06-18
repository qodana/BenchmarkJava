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
 * PURPOSE. See the GNU General Public License for more details
 *
 * @author Dave Wichers
 * @created 2015
 */
package org.owasp.benchmark.helpers

import javax.servlet.http.HttpServletRequest

class SeparateClassRequest(private val request: HttpServletRequest) {
    fun getTheParameter(p: String?): String {
        return request.getParameter(p)
    }

    fun getTheCookie(c: String): String {
        val cookies = request.cookies

        var value = ""

        if (cookies != null) {
            for (cookie in cookies) {
                if (cookie.name == c) {
                    value = cookie.value
                    break
                }
            }
        }

        return value
    }

    // This method is a 'safe' source.
    fun getTheValue(p: String?): String {
        return "bar"
    }
}
