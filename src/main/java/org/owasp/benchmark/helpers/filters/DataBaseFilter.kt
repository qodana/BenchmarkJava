package org.owasp.benchmark.helpers.filters

import org.owasp.benchmark.helpers.*
import java.io.IOException
import java.sql.SQLException
import javax.servlet.*

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
 * @author Juan GaMa
 * @created 2015
 */
class DataBaseFilter : Filter {
    protected var config: FilterConfig? = null

    @Throws(ServletException::class)
    override fun init(config: FilterConfig) {
        this.config = config
    }

    /** Filter to roll back after every test.  */
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain) {
        filterChain.doFilter(request, response)

        try {
            DatabaseHelper.sqlConnection!!.rollback()
        } catch (e: SQLException) {
            if (DatabaseHelper.hideSQLErrors) {
                println("Problem while rolling back the database")
                return
            } else throw ServletException(e)
        }
    }

    override fun destroy() {}
}
