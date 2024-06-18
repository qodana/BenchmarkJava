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

class Certificate {
    var id: Int = 0
    var name: String? = null

    constructor()

    constructor(name: String?) {
        this.name = name
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) return false
        if (this.javaClass != obj.javaClass) return false

        val obj2 = obj as Certificate
        if ((this.id == obj2.id) && (this.name == obj2.name)) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        var tmp = 0
        tmp = (id.toString() + name).hashCode()
        return tmp
    }
}
