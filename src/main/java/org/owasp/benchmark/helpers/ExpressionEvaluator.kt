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

import javax.servlet.jsp.el.ExpressionEvaluator
import javax.servlet.jsp.el.ELException
import javax.servlet.jsp.el.VariableResolver
import javax.servlet.jsp.el.FunctionMapper
import javax.servlet.jsp.el.Expression

@Suppress("deprecation")
class ExpressionEvaluator : ExpressionEvaluator() {
    @Throws(ELException::class)
    override fun evaluate(
        arg0: String,
        arg1: Class<*>?,
        arg2: VariableResolver,
        arg3: FunctionMapper
    ): Any? {
        return null
    }

    @Throws(ELException::class)
    override fun parseExpression(
        arg0: String, arg1: Class<*>?, arg2: FunctionMapper
    ): Expression? {
        return null
    }

    companion object {
        fun evaluateEL(expression: String?, properties: Map<String?, Any?>?): String? {
            return null
        }
    }
}
