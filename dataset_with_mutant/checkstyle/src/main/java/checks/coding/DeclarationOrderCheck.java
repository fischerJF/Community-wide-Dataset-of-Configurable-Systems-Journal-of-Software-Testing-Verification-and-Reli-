////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package checks.coding;

//import gov.nasa.jpf.annotation.Conditional;
import api.*;
import specifications.Configuration;
/**
 * <p>
 * Checks that the parts of a class or interface declaration
 * appear in the order suggested by the
 * <a
 * href="http://www.oracle.com/technetwork/java/javase/documentation/codeconventions-141855.html#1852"
 * >Code Conventions for the Java Programming Language</a>.
 *
 *
 * <ol>
 * <li> Class (static) variables. First the public class variables, then
 *      the protected, then package level (no access modifier), and then
 *      the private. </li>
 * <li> Instance variables. First the public class variables, then
 *      the protected, then package level (no access modifier), and then
 *      the private. </li>
 * <li> Constructors </li>
 * <li> Methods </li>
 * </ol>
 *
 * <p>
 * An example of how to configure the check is:
 *
 * <pre>
 * &lt;module name="DeclarationOrder"/&gt;
 * </pre>
 *
 * @author r_auckenthaler
 */
public class DeclarationOrderCheck extends Check
{
//	@Conditional
//	private static boolean DeclarationOrder = true;
	public boolean isEnabled() {
		return Configuration.DeclarationOrder;
	}
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_CONSTRUCTOR = "declaration.order.constructor";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_METHOD = "declaration.order.method";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_STATIC = "declaration.order.static";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_INSTANCE = "declaration.order.instance";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_ACCESS = "declaration.order.access";

    /** State for the VARIABLE_DEF */
    private static final int STATE_STATIC_VARIABLE_DEF = 1;

    /** State for the VARIABLE_DEF */
    private static final int STATE_INSTANCE_VARIABLE_DEF = 2;

    /** State for the CTOR_DEF */
    private static final int STATE_CTOR_DEF = 3;

    /** State for the METHOD_DEF */
    private static final int STATE_METHOD_DEF = 4;

    /**
     * List of Declaration States. This is necessary due to
     * inner classes that have their own state
     */
    private final FastStack<ScopeState> scopeStates = FastStack.newInstance();

    /**
     * private class to encapsulate the state
     */
    private static class ScopeState
    {
        /** The state the check is in */
        private int scopeState = STATE_STATIC_VARIABLE_DEF;

        /** The sub-state the check is in */
        private Scope declarationAccess = Scope.PUBLIC;
    }

    /** If true, ignores the check to constructors. */
    private boolean ignoreConstructors;
    /** If true, ignore the check to methods. */
    private boolean ignoreMethods;
    /** If true, ignore the check to modifiers (fields, ...). */
    private boolean ignoreModifiers;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.MODIFIERS,
            TokenTypes.OBJBLOCK,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.MODIFIERS,
            TokenTypes.OBJBLOCK,
        };
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        final int parentType = ast.getParent().getType();
        ScopeState state;

        if (ast.getType() == TokenTypes.OBJBLOCK) {
            scopeStates.push(new ScopeState());
        } else if (ast.getType() == TokenTypes.CTOR_DEF) {

            if (parentType != TokenTypes.OBJBLOCK) {
                return;
            }

            state = scopeStates.peek();
            if (state.scopeState > STATE_CTOR_DEF) {
                if (!ignoreConstructors) {
                    log(ast, MSG_CONSTRUCTOR);
                }
            } else {
                state.scopeState = STATE_CTOR_DEF;
            }
        } else if (ast.getType() == TokenTypes.METHOD_DEF) {

            state = scopeStates.peek();
            if (parentType != TokenTypes.OBJBLOCK) {
                return;
            }

            if (state.scopeState > STATE_METHOD_DEF) {
                if (!ignoreMethods) {
                    log(ast, MSG_METHOD);
                }
            } else {
                state.scopeState = STATE_METHOD_DEF;
            }
        } else if (ast.getType() == TokenTypes.MODIFIERS) {

            if ((parentType != TokenTypes.VARIABLE_DEF)
                    || (ast.getParent().getParent().getType()
                    != TokenTypes.OBJBLOCK)) {
                return;
            }

            state = scopeStates.peek();
            if (ast.findFirstToken(TokenTypes.LITERAL_STATIC) != null) {
                if (state.scopeState > STATE_STATIC_VARIABLE_DEF) {
                    if (!ignoreModifiers
                            || state.scopeState > STATE_INSTANCE_VARIABLE_DEF) {
                        log(ast, MSG_STATIC);
                    }
                } else {
                    state.scopeState = STATE_STATIC_VARIABLE_DEF;
                }
            } else {
                if (state.scopeState > STATE_INSTANCE_VARIABLE_DEF) {
                    log(ast, MSG_INSTANCE);
                } else if (state.scopeState == STATE_STATIC_VARIABLE_DEF) {
                    state.declarationAccess = Scope.PUBLIC;
                    state.scopeState = STATE_INSTANCE_VARIABLE_DEF;
                }
            }

            final Scope access = ScopeUtils.getScopeFromMods(ast);
            if (state.declarationAccess.compareTo(access) > 0) {
                if (!ignoreModifiers) {
                    log(ast, MSG_ACCESS);
                }
            } else {
                state.declarationAccess = access;
            }
        } else {
        }
    }

    @Override
    public void leaveToken(DetailAST ast)
    {
        if (ast.getType() == TokenTypes.OBJBLOCK) {
            scopeStates.pop();
        } else {
        }
    }

    /**
     * Sets whether to ignore constructors.
     * @param ignoreConstructors whether to ignore constructors.
     */
    public void setIgnoreConstructors(boolean ignoreConstructors)
    {
        this.ignoreConstructors = ignoreConstructors;
    }

    /**
     * Sets whether to ignore methods.
     * @param ignoreMethods whether to ignore methods.
     */
    public void setIgnoreMethods(boolean ignoreMethods)
    {
        this.ignoreMethods = ignoreMethods;
    }

    /**
     * Sets whether to ignore modifiers.
     * @param ignoreModifiers whether to ignore modifiers.
     */
    public void setIgnoreModifiers(boolean ignoreModifiers)
    {
        this.ignoreModifiers = ignoreModifiers;
    }
}
