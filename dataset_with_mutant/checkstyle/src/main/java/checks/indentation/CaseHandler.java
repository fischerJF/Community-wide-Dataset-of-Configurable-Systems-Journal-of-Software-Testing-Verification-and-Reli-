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
package checks.indentation;

import api.DetailAST;
import api.TokenTypes;

/**
 * Handler for case statements.
 *
 * @author jrichard
 */
public class CaseHandler extends ExpressionHandler
{
    /**
     * The child elements of a case expression.
     */
    private static final int[] CASE_CHILDREN = new int[] {
        TokenTypes.LITERAL_CASE,
        TokenTypes.LITERAL_DEFAULT,
    };

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param expr          the abstract syntax tree
     * @param parent        the parent handler
     */
    public CaseHandler(IndentationCheck indentCheck,
        DetailAST expr, ExpressionHandler parent)
    {
        super(indentCheck, "case", expr, parent);
    }

    @Override
    protected IndentLevel getLevelImpl()
    {
        return new IndentLevel(getParent().getLevel(),
                               getIndentCheck().getCaseIndent());
    }

    /**
     * Check the indentation of the case statement.
     */
    private void checkCase()
    {
        checkChildren(getMainAst(), CASE_CHILDREN, getLevel(), true, false);
    }

    @Override
    public IndentLevel suggestedChildLevel(ExpressionHandler child)
    {
        return getLevel();
    }

    @Override
    public void checkIndentation()
    {
        checkCase();
    }
}
