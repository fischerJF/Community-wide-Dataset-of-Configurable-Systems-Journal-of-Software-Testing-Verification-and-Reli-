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
import specifications.Configuration;

import api.DetailAST;
import api.TokenTypes;
import checks.AbstractFormatCheck;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * <p>
 * Checks for illegal token text.
 * </p>
 * <p> An example of how to configure the check to forbid String literals
 * containing <code>"a href"</code> is:
 * </p>
 * <pre>
 * &lt;module name="IllegalTokenText"&gt;
 *     &lt;property name="tokens" value="STRING_LITERAL"/&gt;
 *     &lt;property name="format" value="a href"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p> An example of how to configure the check to forbid leading zeros in an
 * integer literal, other than zero and a hex literal is:
 * </p>
 * <pre>
 * &lt;module name="IllegalTokenText"&gt;
 *     &lt;property name="tokens" value="NUM_INT,NUM_LONG"/&gt;
 *     &lt;property name="format" value="^0[^lx]"/&gt;
 *     &lt;property name="ignoreCase" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 */
public class IllegalTokenTextCheck
    extends AbstractFormatCheck
{
//	@Conditional
//	private static boolean IllegalTokenTextCheck = true;
	
	@Override
	public boolean isEnabled() {
		return Configuration.IllegalTokenTextCheck;
	}
    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "illegal.token.text";

    /**
     * Custom message for report if illegal regexp found
     * ignored if empty.
     */
    private String message = "";

    /**
     * Instantiates a new instance.
     */
    public IllegalTokenTextCheck()
    {
        super("$^"); // the empty language
    }

    @Override
    public void beginTree(DetailAST rootAST)
    {
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    @Override
    public int[] getAcceptableTokens()
    {
        // Any tokens set by property 'tokens' are acceptable
        final Set<String> tokenNames = getTokenNames();
        final int[] result = new int[tokenNames.size()];
        int i = 0;
        for (final String name : tokenNames) {
            result[i] = TokenTypes.getTokenId(name);
            i++;
        }
        return result;
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        final String text = ast.getText();
        if (getRegexp().matcher(text).find()) {
            String message = getMessage();
            if ("".equals(message)) {
                message = MSG_KEY;
            }
            log(
                ast.getLineNo(),
                ast.getColumnNo(),
                message,
                getFormat());
        }
    }

    /**
     * Setter for message property.
     * @param message custom message which should be used
     *                 to report about violations.
     */
    public void setMessage(String message)
    {
        this.message = (null == message) ? "" : message;
    }

    /**
     * Getter for message property.
     * @return custom message which should be used
     * to report about violations.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Set whether or not the match is case sensitive.
     * @param caseInsensitive true if the match is case insensitive.
     */
    public void setIgnoreCase(boolean caseInsensitive)
    {
        if (caseInsensitive) {
            setCompileFlags(Pattern.CASE_INSENSITIVE);
        }
    }
}
