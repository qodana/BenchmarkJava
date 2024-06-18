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
 * @author Nick Sanidas
 * @created 2015
 */
package org.owasp.benchmark.helpers

import java.util.*

object ThingFactory {
    @JvmStatic
    fun createThing(): ThingInterface {
        val props = Properties()

        // create a thing using reflection
        try {
            ThingFactory::class.java.classLoader.getResourceAsStream("thing.properties").use { thingproperties ->
                if (thingproperties == null) {
                    println("Can't find thing.properties")
                    return Thing2()
                }
                props.load(thingproperties)
                val which = "org.owasp.benchmark.helpers." + props.getProperty("thing")

                val thing = Class.forName(which)
                val thingConstructor = thing.getConstructor()
                val thingInstance = thingConstructor.newInstance()
                return thingInstance as ThingInterface
            }
        } catch (e: Exception) {
            println("Error constructing Thing.")
            e.printStackTrace()
            return Thing1()
        }
    }
}
