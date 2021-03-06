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

package testset.sizes;

import static checks.sizes.MethodLengthCheck.MSG_KEY;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

//import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import checkstyle.DefaultConfiguration;
import testset.checkstyle.AbstractModuleTestSupport;
import api.TokenTypes;
//import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import checks.sizes.MethodLengthCheck;

public class MethodLengthCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/methodlength";
    }

//    @Test
//    public void testGetRequiredTokens() {
//        final MethodLengthCheck checkObj = new MethodLengthCheck();
//        assertArrayEquals(
//            "MethodLengthCheck#getRequiredTokens should return empty array by default",
//            CommonUtil.EMPTY_INT_ARRAY, checkObj.getRequiredTokens());
//    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodLengthCheck methodLengthCheckObj =
            new MethodLengthCheck();
        final int[] actual = methodLengthCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };

        assertArrayEquals("Default acceptable tokens are invalid", expected, actual);
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodLengthCheck.class);
        checkConfig.addAttribute("max", "19");
        final String[] expected = {
            "79:5: " + getCheckMessage(MSG_KEY, 20, 19),
        };
        verify(checkConfig, getPath("InputMethodLengthSimple.java"), expected);
    }

//    @Test
//    public void testCountEmpty() throws Exception {
//        final DefaultConfiguration checkConfig =
//            createModuleConfig(MethodLengthCheck.class);
//        checkConfig.addAttribute("max", "19");
//        checkConfig.addAttribute("countEmpty", "false");
//        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
//        verify(checkConfig, getPath("InputMethodLengthSimple.java"), expected);
//    }

    @Test
    public void testWithComments() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MethodLengthCheck.class);
        checkConfig.addAttribute("max", "7");
        checkConfig.addAttribute("countEmpty", "false");
        final String[] expected = {
            "18:5: " + getCheckMessage(MSG_KEY, 8, 7),
        };
        verify(checkConfig, getPath("InputMethodLengthComments.java"), expected);
    }

//    @Test
//    public void testAbstract() throws Exception {
//        final DefaultConfiguration checkConfig =
//            createModuleConfig(MethodLengthCheck.class);
//        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
//        verify(checkConfig, getPath("InputMethodLengthModifier.java"), expected);
//    }

}
