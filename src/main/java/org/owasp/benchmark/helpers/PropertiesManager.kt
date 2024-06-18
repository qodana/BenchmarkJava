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

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class PropertiesManager {
    private var file: File? = null

    // This loads the default benchmark.properties file
    constructor() {
        file = Utils.getFileFromClasspath("benchmark.properties", this.javaClass.classLoader)
    }

    // This can be used to load an alternate properties file specified by the fileName
    constructor(fileName: String) {
        file = Utils.getFileFromClasspath(fileName, this.javaClass.classLoader)
    }

    // This can be used to load an alternate properties file specified by the path and fileName
    constructor(path: String, fileName: String) {
        file = File(path + File.separator + fileName)
        if (!file!!.exists()) {
            try {
                file!!.createNewFile()
            } catch (e: IOException) {
                println(
                    "Problem creating new empty properties file: " + file!!.absolutePath
                )
            }
        }
    }

    fun displayProperties() {
        val props = loadProperties()

        println(props.keys)
        println(props.values)
    }

    fun getProperty(key: String?, defaultValue: String?): String {
        val props = loadProperties()
        return props.getProperty(key, defaultValue)
    }

    fun getProperty(key: String?, defaultValue: Int): Int {
        val props = loadProperties()
        return props.getProperty(key, defaultValue.toString()).toInt()
    }

    fun saveProperty(key: String?, value: String?) {
        val props = loadProperties()
        try {
            FileOutputStream(file).use { out ->
                props.setProperty(key, value)
                props.store(out, null)
            }
        } catch (e: IOException) {
            println("There was a problem saving a property in the properties file")
            e.printStackTrace()
        }
    }

    fun removeProperty(key: String?) {
        val props = loadProperties()
        try {
            FileOutputStream(file).use { out ->
                props.remove(key)
                props.store(out, null)
            }
        } catch (e: IOException) {
            println("There was a problem removing a property from the properties file")
            e.printStackTrace()
        }
    }

    private fun loadProperties(): Properties {
        val props = Properties()
        try {
            FileInputStream(file).use { `is` ->
                props.load(`is`)
            }
        } catch (e: IOException) {
            println("Error loading properties file")
            e.printStackTrace()
        }
        return props
    }
}
