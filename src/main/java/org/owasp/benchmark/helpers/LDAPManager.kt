/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.owasp.benchmark.helpers

import org.owasp.esapi.Encoder
import org.owasp.esapi.reference.DefaultEncoder
import java.util.*
import javax.naming.Context
import javax.naming.NamingException
import javax.naming.directory.*

/**
 * A simple example exposing how to embed Apache Directory Server into an application.
 *
 * @author [Apache Directory Project](mailto:dev@directory.apache.org)
 * @version $Rev$, $Date$
 */
class LDAPManager {
    private var ctx: DirContext? = null

    init {
        try {
            ctx = dirContext
        } catch (e: NamingException) {
            // FIXME: Don't eat exceptions!
            println("Failed to get Directory Context: " + e.message)
            e.printStackTrace()
        }
    }

    protected fun createEnv(): Hashtable<Any, Any> {
        val env = Hashtable<Any, Any>()
        env[Context.PROVIDER_URL] = "ldap://localhost:10389"
        env[Context.SECURITY_AUTHENTICATION] = "simple"
        env[Context.SECURITY_PRINCIPAL] = "uid=admin,ou=system"
        env[Context.SECURITY_CREDENTIALS] = "secret"
        env[Context.INITIAL_CONTEXT_FACTORY] = "com.sun.jndi.ldap.LdapCtxFactory"
        return env
    }

    fun insert(person: LDAPPerson): Boolean {
        val matchAttrs: Attributes = BasicAttributes(true)
        matchAttrs.put(BasicAttribute("uid", person.name))
        matchAttrs.put(BasicAttribute("cn", person.name))
        matchAttrs.put(BasicAttribute("street", person.address))
        matchAttrs.put(BasicAttribute("sn", person.name))
        matchAttrs.put(BasicAttribute("userpassword", person.password))
        matchAttrs.put(BasicAttribute("objectclass", "top"))
        matchAttrs.put(BasicAttribute("objectclass", "person"))
        matchAttrs.put(BasicAttribute("objectclass", "organizationalPerson"))
        matchAttrs.put(BasicAttribute("objectclass", "inetorgperson"))
        val name = "uid=" + person.name + ",ou=users,ou=system"
        val iniDirContext = ctx as InitialDirContext?

        try {
            iniDirContext!!.bind(name, ctx, matchAttrs)
        } catch (e: NamingException) {
            if (!e.message!!.contains("ENTRY_ALREADY_EXISTS")) {
                println("Record already exist or an error occurred: " + e.message)
            }
        }

        return true
    }

    /**
     * Search LDAPPerson by name
     *
     * @param person to search
     * @return true if record found
     */
    @Suppress("unused")
    private fun search(person: LDAPPerson): Boolean {
        try {
            val ctx = dirContext
            val base = "ou=users,ou=system"

            val sc = SearchControls()
            sc.searchScope = SearchControls.SUBTREE_SCOPE

            val filter =
                ("(&(objectclass=person)(uid="
                        + ESAPI_Encoder.encodeForLDAP(person.name)
                        + "))")

            val results = ctx.search(base, filter, sc)

            while (results.hasMore()) {
                val sr = results.next() as SearchResult
                val attrs = sr.attributes

                val attr = attrs["uid"]
                if (attr != null) {
                    // logger.debug("record found " + attr.get());
                    // System.out.println("record found " + attr.get());
                }
            }
            ctx.close()

            return true
        } catch (e: Exception) {
            println("LDAP error search: ")
            e.printStackTrace()
            return false
        }
    }

    @get:Throws(NamingException::class)
    val dirContext: DirContext
        get() {
            return ctx ?: InitialDirContext(createEnv())
        }

    @Throws(NamingException::class)
    fun closeDirContext() {
        ctx?.close()
    }

    companion object {
        private val ESAPI_Encoder: Encoder = DefaultEncoder.getInstance()
    }
}
