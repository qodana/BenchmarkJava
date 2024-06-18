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
package org.owasp.benchmark.helpers.entities

class Employee {
    var id: Int = 0
    var firstName: String? = null
    var lastName: String? = null
    var salary: Int = 0
    var certificates: Set<Certificate>? = null

    constructor()

    constructor(fname: String?, lname: String?, salary: Int) {
        this.firstName = fname
        this.lastName = lname
        this.salary = salary
    }
}
