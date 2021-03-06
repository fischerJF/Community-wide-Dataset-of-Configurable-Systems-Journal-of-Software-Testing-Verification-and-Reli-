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
package api;

import org.apache.commons.beanutils.*;
import org.apache.commons.beanutils.converters.*;
import org.apache.tools.ant.util.SymbolicLinkUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A Java Bean that implements the component lifecycle interfaces by
 * calling the bean's setters for all configuration attributes.
 * @author lkuehne
 */
public class AutomaticBean
    implements Configurable, Contextualizable
{
    /** the configuration of this bean */
    private Configuration configuration;
    public enum OutputStreamOptions {

        /**
         * Close stream in the end.
         */
        CLOSE,

        /**
         * Do nothing in the end.
         */
        NONE,

    }


    /**
     * Creates a BeanUtilsBean that is configured to use
     * type converters that throw a ConversionException
     * instead of using the default value when something
     * goes wrong.
     *
     * @return a configured BeanUtilsBean
     */
    private static BeanUtilsBean createBeanUtilsBean()
    {
        final ConvertUtilsBean cub = new ConvertUtilsBean();
        // TODO: is there a smarter way to tell beanutils not to use defaults?

        cub.register(new BooleanConverter(), Boolean.TYPE);
        cub.register(new BooleanConverter(), Boolean.class);

        /*cub.register(new ArrayConverter(
            boolean[].class, new BooleanConverter()), boolean[].class);
        */

        cub.register(new ByteConverter(), Byte.TYPE);

        cub.register(new ByteConverter(), Byte.class);

//        cub.register(new ArrayConverter(byte[].class, new ByteConverter()),
//            byte[].class);

        cub.register(new CharacterConverter(), Character.TYPE);

        cub.register(new CharacterConverter(), Character.class);

//        cub.register(new ArrayConverter(char[].class, new CharacterConverter()),
//            char[].class);
        cub.register(new DoubleConverter(), Double.TYPE);

        cub.register(new DoubleConverter(), Double.class);

//        cub.register(new ArrayConverter(double[].class, new DoubleConverter()),
//            double[].class);

        cub.register(new FloatConverter(), Float.TYPE);

        cub.register(new FloatConverter(), Float.class);

//        cub.register(new ArrayConverter(float[].class, new FloatConverter()),
//            float[].class);

        cub.register(new IntegerConverter(), Integer.TYPE);

        cub.register(new IntegerConverter(), Integer.class);

//        cub.register(new ArrayConverter(int[].class, new IntegerConverter()),
//            int[].class);

        cub.register(new LongConverter(), Long.TYPE);

        cub.register(new LongConverter(), Long.class);

//        cub.register(new ArrayConverter(long[].class, new LongConverter()),
//            long[].class);

        cub.register(new ShortConverter(), Short.TYPE);

        cub.register(new ShortConverter(), Short.class);

//        cub.register(new ArrayConverter(short[].class, new ShortConverter()),
//            short[].class);

        cub.register(new RelaxedStringArrayConverter(), String[].class);

        // BigDecimal, BigInteger, Class, Date, String, Time, TimeStamp
        // do not use defaults in the default configuration of ConvertUtilsBean
       
        return new BeanUtilsBean(cub, new PropertyUtilsBean());
    }

    /**
     * Implements the Configurable interface using bean introspection.
     *
     * Subclasses are allowed to add behaviour. After the bean
     * based setup has completed first the method
     * {@link #finishLocalSetup finishLocalSetup}
     * is called to allow completion of the bean's local setup,
     * after that the method {@link #setupChild setupChild}
     * is called for each {@link Configuration#getChildren child Configuration}
     * of <code>configuration</code>.
     *
     * @param configuration {@inheritDoc}
     * @throws CheckstyleException {@inheritDoc}
     * @see Configurable
     */
    @Override
    public final void configure(Configuration configuration)
        throws CheckstyleException
    {
        this.configuration = configuration;

        final BeanUtilsBean beanUtils = createBeanUtilsBean();

        // TODO: debug log messages
        final String[] attributes = configuration.getAttributeNames();


        for (final String key : attributes) {
            final String value = configuration.getAttribute(key);

            try {
                // BeanUtilsBean.copyProperties silently ignores missing setters
                // for key, so we have to go through great lengths here to
                // figure out if the bean property really exists.
                final PropertyDescriptor pd =
                    PropertyUtils.getPropertyDescriptor(this, key);
                if ((pd == null) || (pd.getWriteMethod() == null)) {
                    throw new CheckstyleException(
                        "Property '" + key + "' in module "
                        + configuration.getName()
                        + " does not exist, please check the documentation");
                }

                // finally we can set the bean property
                beanUtils.copyProperty(this, key, value);
            }
            catch (final InvocationTargetException e) {
                throw new CheckstyleException(
                    "Cannot set property '" + key + "' in module "
                    + configuration.getName() + " to '" + value
                    + "': " + e.getTargetException().getMessage(), e);
            }
            catch (final IllegalAccessException e) {
                throw new CheckstyleException(
                    "cannot access " + key + " in "
                    + this.getClass().getName(), e);
            }
            catch (final NoSuchMethodException e) {
                throw new CheckstyleException(
                    "cannot access " + key + " in "
                    + this.getClass().getName(), e);
            }
            catch (final IllegalArgumentException e) {
                throw new CheckstyleException(
                    "illegal value '" + value + "' for property '" + key
                    + "' of module " + configuration.getName(), e);
            }
            catch (final ConversionException e) {
                throw new CheckstyleException(
                    "illegal value '" + value + "' for property '" + key
                    + "' of module " + configuration.getName(), e);
            }

        }

        finishLocalSetup();

        final Configuration[] childConfigs = configuration.getChildren();
        for (final Configuration childConfig : childConfigs) {
            setupChild(childConfig);
        }

    }

    /**
     * Implements the Contextualizable interface using bean introspection.
     * @param context {@inheritDoc}
     * @throws CheckstyleException {@inheritDoc}
     * @see Contextualizable
     */
    @Override
    public final void contextualize(Context context)
        throws CheckstyleException
    {

        final BeanUtilsBean beanUtils = createBeanUtilsBean();

        // TODO: debug log messages
        final LinkedList<String> attributes = context.getAttributeNames();
       
        for (final String key : attributes) {
            final Object value = context.get(key);

            try {
                beanUtils.copyProperty(this, key, value);
            }
            catch (final InvocationTargetException e) {
                // TODO: log.debug("The bean " + this.getClass()
                // + " is not interested in " + value)
                throw new CheckstyleException("cannot set property "
                    + key + " to value " + value + " in bean "
                    + this.getClass().getName(), e);
            }
            catch (final IllegalAccessException e) {
                throw new CheckstyleException(
                    "cannot access " + key + " in "
                    + this.getClass().getName(), e);
            }
            catch (final IllegalArgumentException e) {
                throw new CheckstyleException(
                    "illegal value '" + value + "' for property '" + key
                    + "' of bean " + this.getClass().getName(), e);
            }
            catch (final ConversionException e) {
                throw new CheckstyleException(
                    "illegal value '" + value + "' for property '" + key
                    + "' of bean " + this.getClass().getName(), e);
            }
        }
    }

    /**
     * Returns the configuration that was used to configure this component.
     * @return the configuration that was used to configure this component.
     */
    protected final Configuration getConfiguration()
    {
        return configuration;
    }

    /**
     * Provides a hook to finish the part of this component's setup that
     * was not handled by the bean introspection.
     * <p>
     * The default implementation does nothing.
     * </p>
     * @throws CheckstyleException if there is a configuration error.
     */
    public void finishLocalSetup() throws CheckstyleException
    {
    }

    /**
     * Called by configure() for every child of this component's Configuration.
     * <p>
     * The default implementation does nothing.
     * </p>
     * @param childConf a child of this component's Configuration
     * @throws CheckstyleException if there is a configuration error.
     * @see Configuration#getChildren
     */
    public void setupChild(Configuration childConf)
        throws CheckstyleException
    {
    }

    /**
     * A converter that does not care whether the array elements contain String
     * characters like '*' or '_'. The normal ArrayConverter class has problems
     * with this characters.
     */
    private static class RelaxedStringArrayConverter implements Converter
    {
        /** {@inheritDoc} */
        @Override
        public Object convert(@SuppressWarnings("rawtypes") Class type,
            Object value)
        {
            if (null == type) {
                throw new ConversionException("Cannot convert from null.");
            }

            // Convert to a String and trim it for the tokenizer.
            final StringTokenizer st = new StringTokenizer(
                value.toString().trim(), ",");
            final List<String> result = new ArrayList<>();

            while (st.hasMoreTokens()) {
                final String token = st.nextToken();
                result.add(token.trim());
            }

            return result.toArray(new String[result.size()]);
        }
    }
}
