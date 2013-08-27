/*******************************************************************************
 * Copyright (C) 2013 Andrei Olaru.
 * 
 * This file is part of Config.
 * 
 * Config is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 * 
 * Config is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Config.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package net.xqhs.util.config;

import net.xqhs.util.config.Config.ConfigLockedException;

/**
 * The {@link Configurable} interface is meant to be used as an interface / paradigm for any class that separates
 * construction-time configuration methods from the rest of the instance's lifecycle. It is particularly appropriate for
 * classes that need a large number of parameters and consider both optional and default parameters.
 * <p>
 * While the interface itself does not offer many methods, it is expected from a class implementing {@link Configurable}
 * that it will offer a number of setters, and will respect the guidelines set in {@link Config}.
 * <p>
 * For further reference on this paradigm, see {@link Config}.
 * 
 * 
 * @author Andrei Olaru
 * 
 */
public interface Configurable
{
	/**
	 * Initializes all optional parameters to their default value.
	 * <p>
	 * From {@link Config} guidelines:
	 * <ul>
	 * <li>IMPORTANT: Remember that all initialization of the fields will be done <i>after</i> the call of
	 * makeDefaults()
	 * <li>makeDefaults() should <i>always</i> begin by calling super.makeDefaults(), and then making necessary
	 * adjustments.
	 * <li>makeDefaults() should only affect fields (from the ancestor configs) that have a default value for the
	 * configured object that is relevant to the configured object (and not to the ancestor).
	 * <li>Setting of changeable parameters (either by constructors, makeDefaults(), or other setters) will
	 * <i>always</i> be done by calling the appropriate setter function.
	 * </ul>
	 * 
	 * @return the instance itself.
	 */
	Configurable makeDefaults();
	
	/**
	 * 'Locks' the configuration, meaning that construction time is over.
	 * <p>
	 * in case any construction needs to be made, it should be done in this method, after calling
	 * <code>super.lock()</code>.
	 * 
	 * @return the instance itself.
	 */
	public Config lock();
	
	/**
	 * This method should <b>always</b> be an alias of <code>lock()</code>. The difference is only semantic:
	 * <code>build()</code> should be associated with actually building the configured instance, and <code>lock()</code>
	 * should be associated with locking the configuration / values of configured parameters.
	 * 
	 * @return the instance itself
	 */
	public Config build();
	
	/**
	 * This method should check the locking status and call <code>lock()</code> only once in the lifetime of the
	 * instance.
	 * <p>
	 * This can be called by various methods that need to ensure the locking has been performed, also resulting in a
	 * call to <code>lock()</code> the first time this method is called.
	 */
	public void ensureLocked();
	
	/**
	 * Should be called at the entry point of methods that should not be called after the instance has been 'locked'.
	 * <p>
	 * In case such a call is made, the method should throw the specified exception.
	 * 
	 * @throws ConfigLockedException
	 */
	void locked() throws ConfigLockedException;
}
