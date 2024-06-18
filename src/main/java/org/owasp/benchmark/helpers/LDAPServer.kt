/*-
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.  The
 * ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.owasp.benchmark.helpers

import org.apache.commons.io.FileUtils
import org.apache.directory.api.ldap.model.exception.LdapException
import org.apache.directory.api.ldap.model.schema.registries.DefaultSchema
import org.apache.directory.api.ldap.schema.loader.JarLdifSchemaLoader
import org.apache.directory.api.ldap.schema.loader.LdifSchemaLoader
import org.apache.directory.server.core.api.DirectoryService
import org.apache.directory.server.core.factory.DefaultDirectoryServiceFactory
import org.apache.directory.server.core.factory.JdbmPartitionFactory
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmIndex
import org.apache.directory.server.core.partition.impl.btree.jdbm.JdbmPartition
import org.apache.directory.server.ldap.LdapServer
import org.apache.directory.server.protocol.shared.transport.TcpTransport
import org.apache.directory.server.xdbm.IndexNotFoundException
import java.io.File
import java.io.IOException

/** Call init() to start the server and destroy() to shut it down.  */
class LDAPServer {
    var directoryService: DirectoryService? = null
    var ldapServer: LdapServer? = null
    var basePartition: JdbmPartition? = null
    var deleteInstanceDirectoryOnStartup: Boolean = true
    var deleteInstanceDirectoryOnShutdown: Boolean = true

    @Throws(LdapException::class, IOException::class)
    protected fun addSchemaExtensions() {
        // override to add custom attributes to the schema
    }

    init {
        // BEGIN HACK
        try {
            val dir =
                Utils.getFileFromClasspath(
                    "benchmark.properties", LDAPServer::class.java.classLoader
                )!!
                    .parent
            val workDir = File("$dir/../ldap")
            workDir.mkdirs()
            System.setProperty("workingDiretory", workDir.path)

            init()
        } catch (e: Exception) {
            println("Error initializing LDAP Server: " + e.message)
            e.printStackTrace()
        }

        val emd = LDAPManager()
        var ldapP = LDAPPerson()
        ldapP.name = "foo"
        ldapP.password = "MrFooPa$\$word"
        ldapP.address = "AddressForFoo #345"

        emd.insert(ldapP)

        ldapP = LDAPPerson()
        ldapP.name = "Ms Bar"
        ldapP.password = "barM\$B4dPass"
        ldapP.address = "The streetz 4 Ms bar"

        emd.insert(ldapP)

        ldapP = LDAPPerson()
        ldapP.name = "Mr Unknown"
        ldapP.password = "YouwontGue$$"
        ldapP.address = "Whe home is #678"

        emd.insert(ldapP)
        // END HACK
    }

    @Throws(Exception::class)
    fun init() {
        if (directoryService == null) {
            if (deleteInstanceDirectoryOnStartup) {
                deleteDirectory(guessedInstanceDirectory)
            }

            val serviceFactory = DefaultDirectoryServiceFactory()
            serviceFactory.init(directoryServiceName)
            directoryService = serviceFactory.directoryService

            directoryService!!.getChangeLog().isEnabled = false
            directoryService!!.setDenormalizeOpAttrsEnabled(true)

            createBasePartition()

            directoryService!!.startup()

            createRootEntry()
        }

        if (ldapServer == null) {
            ldapServer = LdapServer()
            ldapServer!!.directoryService = directoryService
            ldapServer!!.setTransports(TcpTransport(ldapServerPort))
            ldapServer!!.start()
        }
    }

    @Throws(Exception::class)
    fun destroy() {
        val instanceDirectory = directoryService!!.instanceLayout.instanceDirectory
        ldapServer!!.stop()
        directoryService!!.shutdown()
        ldapServer = null
        directoryService = null
        if (deleteInstanceDirectoryOnShutdown) {
            deleteDirectory(instanceDirectory)
        }
    }

    val directoryServiceName: String
        get() = basePartitionName + "DirectoryService"

    @Throws(Exception::class)
    protected fun createBasePartition() {
        val jdbmPartitionFactory = JdbmPartitionFactory()
        basePartition = jdbmPartitionFactory.createPartition(
            directoryService!!.schemaManager,
            directoryService!!.dnFactory,
            basePartitionName,
            baseStructure,
            baseCacheSize,
            basePartitionPath
        )
        addSchemaExtensions()
        createBaseIndices()
        directoryService!!.addPartition(basePartition)
    }

    @Throws(Exception::class)
    protected fun createBaseIndices() {
        //
        // Default indices, that can be seen with getSystemIndexMap() and
        // getUserIndexMap(), are minimal.  There are no user indices by
        // default and the default system indices are:
        //
        // apacheOneAlias, entryCSN, apacheSubAlias, apacheAlias,
        // objectClass, apachePresence, apacheRdn, administrativeRole
        //
        for (attrName in attrNamesToIndex) {
            basePartition!!.addIndex(createIndexObjectForAttr(attrName))
        }
    }

    @Throws(LdapException::class)
    protected fun createIndexObjectForAttr(attrName: String, withReverse: Boolean = false): JdbmIndex<*> {
        val oid = getOidByAttributeName(attrName)
            ?: throw RuntimeException("OID could not be found for attr $attrName")
        return JdbmIndex<Any?>(oid, withReverse)
    }

    @Throws(LdapException::class)
    protected fun createRootEntry() {
        val entry =
            directoryService!!
                .newEntry(directoryService!!.dnFactory.create(baseStructure))
        entry.add("objectClass", "top", "domain", "extensibleObject")
        entry.add("dc", basePartitionName)
        val session = directoryService!!.adminSession
        try {
            session.add(entry)
        } finally {
            session.unbind()
        }
    }

    @get:Throws(IndexNotFoundException::class)
    val systemIndexMap: Map<String, String>
        /** @return A map where the key is the attribute name the value is the oid.
         */
        get() {
            val result: MutableMap<String, String> = LinkedHashMap()
            val it = basePartition!!.systemIndices
            while (it.hasNext()) {
                val oid = it.next()
                val index =
                    basePartition!!
                        .getSystemIndex(
                            directoryService!!.schemaManager.getAttributeType(oid)
                        )
                result[index.attribute.name] = index.attributeId
            }
            return result
        }

    @get:Throws(IndexNotFoundException::class)
    val userIndexMap: Map<String, String>
        /** @return A map where the key is the attribute name the value is the oid.
         */
        get() {
            val result: MutableMap<String, String> = LinkedHashMap()
            val it = basePartition!!.userIndices
            while (it.hasNext()) {
                val oid = it.next()
                val index =
                    basePartition!!
                        .getUserIndex(
                            directoryService!!.schemaManager.getAttributeType(oid)
                        )
                result[index.attribute.name] = index.attributeId
            }
            return result
        }

    val partitionsDirectory: File
        get() = directoryService!!.instanceLayout.partitionsDirectory

    val basePartitionPath: File
        get() = File(partitionsDirectory, basePartitionName)

    val guessedInstanceDirectory: File
        /** Used at init time to clear out the likely instance directory before anything is created.  */
        get() {
            // See source code for DefaultDirectoryServiceFactory
            // buildInstanceDirectory.  ApacheDS looks at the workingDirectory
            // system property first and then defers to the java.io.tmpdir
            // system property.
            val property = System.getProperty("workingDirectory")
            return File(
                property
                    ?: (System.getProperty("java.io.tmpdir")
                            + File.separator
                            + "server-work-"
                            + directoryServiceName)
            )
        }

    @Throws(LdapException::class)
    fun getOidByAttributeName(attrName: String?): String {
        return directoryService!!
            .getSchemaManager()
            .attributeTypeRegistry
            .getOidByName(attrName)
    }

    /**
     * Add additional schemas to the directory server. This takes a path to the schema directory and
     * uses the LdifSchemaLoader.
     *
     * @param schemaLocation The path to the directory containing the "ou=schema" directory for an
     * additional schema
     * @param schemaName The name of the schema
     * @return true if the schemas have been loaded and the registries is consistent
     */
    @Throws(LdapException::class, IOException::class)
    fun addSchemaFromPath(schemaLocation: File?, schemaName: String?): Boolean {
        val schemaLoader = LdifSchemaLoader(schemaLocation)
        val schema = DefaultSchema(schemaLoader, schemaName)
        return directoryService!!.schemaManager.load(schema)
    }

    /**
     * Add additional schemas to the directory server. This uses JarLdifSchemaLoader, which will
     * search for the "ou=schema" directory within "/schema" on the classpath. If packaging the
     * schema as part of a jar using Gradle or Maven, you'd probably want to put your "ou=schema"
     * directory in src/main/resources/schema.
     *
     *
     * It's also required that a META-INF/apacheds-schema.index be present in your classpath that
     * lists each LDIF file in your schema directory.
     *
     * @param schemaName The name of the schema
     * @return true if the schemas have been loaded and the registries is consistent
     */
    @Throws(LdapException::class, IOException::class)
    fun addSchemaFromClasspath(schemaName: String?): Boolean {
        // To debug if your apacheds-schema.index isn't found:
        // Enumeration<URL> indexes =
        // getClass().getClassLoader().getResources("META-INF/apacheds-schema.index");
        val schemaLoader = JarLdifSchemaLoader()
        val schema = schemaLoader.getSchema(schemaName)
        return schema != null && directoryService!!.schemaManager.load(schema)
    }

    companion object {
        // API References:
        // https://nightlies.apache.org/directory/apacheds/2.0.0.AM27/apidocs/
        // https://nightlies.apache.org/directory/api/2.2.3/
        val basePartitionName: String = "mydomain"
        private const val BASE_DOMAIN = "org"
        val baseStructure: String = "dc=" + basePartitionName + ",dc=" + BASE_DOMAIN

        val ldapServerPort: Int = 10389
        val baseCacheSize: Int = 1000
        val attrNamesToIndex: List<String> = mutableListOf("uid")

        @Throws(IOException::class)
        private fun deleteDirectory(path: File) {
            FileUtils.deleteDirectory(path)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            LDAPServer()
        }
    }
}
