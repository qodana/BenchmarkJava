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
 * @author Juan Gama
 * @created 2015
 */
package org.owasp.benchmark.helpers

import org.owasp.benchmark.service.pojo.Person
import org.owasp.benchmark.service.pojo.XMLMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class DataBaseServer {
    @GetMapping(value = ["/resetdb"])
    @Throws(ServletException::class, IOException::class)
    fun getOtherOrder(
        @RequestBody model: Person?, request: HttpServletRequest?, response: HttpServletResponse?
    ): ResponseEntity<List<XMLMessage>> {
        val resp = ArrayList<XMLMessage>()
        resp.add(XMLMessage("Not Implemented."))
        return ResponseEntity(resp, HttpStatus.OK)
    }

    @PostMapping(value = ["/testdb"])
    @Throws(ServletException::class, IOException::class)
    fun createOrder2(
        @RequestBody model: Person?, request: HttpServletRequest?, response: HttpServletResponse?
    ): ResponseEntity<List<XMLMessage>> {
        val resp: MutableList<XMLMessage> = ArrayList()
        resp.add(XMLMessage("Not Implemented."))
        return ResponseEntity(resp, HttpStatus.OK)
    }

    @GetMapping(value = ["/getall"])
    @Throws(ServletException::class, IOException::class)
    fun getAll(
        request: HttpServletRequest?, response: HttpServletResponse?
    ): ResponseEntity<List<XMLMessage?>> {
        val resp: MutableList<XMLMessage?> = ArrayList()
        val sql = "SELECT * from USERS"
        try {
            val connection = DatabaseHelper.sqlConnection
            val statement = connection!!.prepareStatement(sql)
            statement.execute()
            DatabaseHelper.printResults(statement, sql, resp)
        } catch (e: SQLException) {
            if (DatabaseHelper.hideSQLErrors) {
                e.printStackTrace()
                resp.add(XMLMessage("Error processing request: " + e.message))
                return ResponseEntity(resp, HttpStatus.OK)
            } else throw ServletException(e)
        }
        return ResponseEntity(resp, HttpStatus.OK)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // This empty main() method is required to be able to start the Database. Otherwise you get
            // the error:

            /*
        [java] Error: Main method not found in class org.owasp.benchmark.helpers.DataBaseServer, please define the main method as:
        [java]    public static void main(String[] args)
        [java] or a JavaFX application class must extend javafx.application.Application
        */
        }
    }
}
