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
 * @created 2017
 */
package org.owasp.benchmark.service.pojo

import javax.xml.bind.annotation.XmlAttribute
import javax.xml.bind.annotation.XmlElement
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement(name = "person")
class Person {
    @set:XmlAttribute
    var id: Long = 0

    @set:XmlElement
    var address: String? = null

    @set:XmlElement
    var firstName: String? = null

    @set:XmlElement
    var lastName: String? = null

    constructor()

    constructor(id: Long, firstName: String?, lastName: String?, address: String?) {
        this.id = id
        this.firstName = firstName
        this.address = address
    }

    override fun toString(): String {
        return ("Person [id="
                + id
                + ", address="
                + address
                + ", firstName="
                + firstName
                + ", lastName="
                + lastName
                + "]")
    }
}
