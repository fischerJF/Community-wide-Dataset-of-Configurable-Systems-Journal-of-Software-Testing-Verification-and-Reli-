////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package testset.whitespace;

//import static com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck.MSG_LINE_NEW;
//import static com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck.MSG_LINE_PREVIOUS;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

//import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import checkstyle.DefaultConfiguration;
import testset.checkstyle.AbstractModuleTestSupport;
import api.CheckstyleException;
import api.TokenTypes;
import checks.whitespace.SeparatorWrapCheck;
//import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SeparatorWrapCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/separatorwrap";
    }

//    @Test
//    public void testDot()
//            throws Exception {
//        final DefaultConfiguration checkConfig = createModuleConfig(SeparatorWrapCheck.class);
//        checkConfig.addAttribute("option", "NL");
//        checkConfig.addAttribute("tokens", "DOT");
//        final String[] expected = {
//            "31:10: " + getCheckMessage(MSG_LINE_NEW, "."),
//        };
//        verify(checkConfig, getPath("InputSeparatorWrapForTestDot.java"), expected);
//    }

//    @Test
//    public void testComma() throws Exception {
//        final DefaultConfiguration checkConfig = createModuleConfig(SeparatorWrapCheck.class);
//        checkConfig.addAttribute("option", "EOL");
//        checkConfig.addAttribute("tokens", "COMMA");
//        final String[] expected = {
//            "39:17: " + getCheckMessage(MSG_LINE_PREVIOUS, ","),
//        };
//        verify(checkConfig, getPath("InputSeparatorWrapForTestComma.java"), expected);
//    }

//    @Test
//    public void testMethodRef() throws Exception {
//        final DefaultConfiguration checkConfig = createModuleConfig(SeparatorWrapCheck.class);
//        checkConfig.addAttribute("option", "NL");
//        checkConfig.addAttribute("tokens", "METHOD_REF");
//        final String[] expected = {
//            "17:56: " + getCheckMessage(MSG_LINE_NEW, "::"),
//        };
//        verify(checkConfig, getPath("InputSeparatorWrapForTestMethodRef.java"), expected);
//    }

    @Test
    public void testGetDefaultTokens() {
        final SeparatorWrapCheck separatorWrapCheckObj = new SeparatorWrapCheck();
        final int[] actual = separatorWrapCheckObj.getDefaultTokens();
        final int[] expected = {
            TokenTypes.DOT,
            TokenTypes.COMMA,
        };
        Assert.assertArrayEquals("Invalid default tokens", expected, actual);
    }

//    @Test
//    public void testInvalidOption() throws Exception {
//        final DefaultConfiguration checkConfig = createModuleConfig(SeparatorWrapCheck.class);
//        checkConfig.addAttribute("option", "invalid_option");
//
//        try {
//            final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
//
//            verify(checkConfig, getPath("InputSeparatorWrapForInvalidOption.java"), expected);
//            fail("exception expected");
//        }
//        catch (CheckstyleException ex) {
//            final String messageStart = "cannot initialize module "
//                + "com.puppycrawl.tools.checkstyle.TreeWalker - Cannot set property 'option' to "
//                + "'invalid_option' in module";
//            assertTrue("Invalid exception message, should start with: " + messageStart,
//                ex.getMessage().startsWith(messageStart));
//        }
//    }

//    @Test
//    public void testEllipsis() throws Exception {
//        final DefaultConfiguration checkConfig = createModuleConfig(SeparatorWrapCheck.class);
//        checkConfig.addAttribute("option", "EOL");
//        checkConfig.addAttribute("tokens", "ELLIPSIS");
//        final String[] expected = {
//            "11:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "..."),
//        };
//        verify(checkConfig, getPath("InputSeparatorWrapForEllipsis.java"), expected);
//    }

//    @Test
//    public void testArrayDeclarator() throws Exception {
//        final DefaultConfiguration checkConfig = createModuleConfig(SeparatorWrapCheck.class);
//        checkConfig.addAttribute("option", "EOL");
//        checkConfig.addAttribute("tokens", "ARRAY_DECLARATOR");
//        final String[] expected = {
//            "9:13: " + getCheckMessage(MSG_LINE_PREVIOUS, "["),
//        };
//        verify(checkConfig, getPath("InputSeparatorWrapForArrayDeclarator.java"), expected);
//    }

}
