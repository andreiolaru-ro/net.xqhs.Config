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

/**
 * The Config is to be used as a base class / paradigm for any construction-time configuration that needs a large number
 * of parameters and considers optional and default parameters.
 * 
 * <p>
 * A mandatory configuration is a set of parameters that are absolutely necessary for the configuration. There may be
 * more such sets, and they may intersect.
 * <p>
 * In case of a config with ancestors, like RootConfig &rarr; ChildConfig &rarr; CurrentConfig, since a setter returns
 * an instance of its class (which may not be the original calling class), chained setters should be called for
 * convenience in the order:
 * <code>currentConfigInstance.setParamInCurrentConfig().setOtherParamInCurrentConfig().setParamInChildConfig().setParamInRootConfig()</code>
 * Obviously, a RootConfig instance will be returned in the end, but that can be casted back to CurrentConfig.
 * <p>
 * Rules
 * <ul>
 * <li>There are setter functions for all changeable parameters.
 * <li>All setters return <code>this</code>.
 * <li>If there are mandatory parameters, there is a constructor that takes these parameters - the primary constructor.
 * <li>If there is no mandatory parameter, there is a default constructor (i.e. Config() )
 * <li>The primary constructor takes all parameters that can be mandatory. If there is no mandatory configuration that
 * takes all potentially mandatory parameters, the primary constructor is protected. Otherwise, it is public.
 * <li>All other constructors call the primary constructor.
 * <li>Preferably constructors should be used only for mandatory parameters. Anything else should be done by setters.
 * <li>Only the primary constructor calls the super() constructor.
 * <li>IMPORTANT: Remember that all initialization of the fields will be done <i>after</i> the call of makeDefaults()
 * <li>makeDefaults() should <i>always</i> begin by calling super.makeDefaults(), and then making necessary adjustments.
 * <li>makeDefaults() should only affect fields (from the ancestor configs) that have a default value for the configured
 * object that is relevant to the configured object (and not to the ancestor).
 * <li>Setting of changeable parameters (either by constructors, makeDefaults(), or other setters) will <i>always</i> be
 * done by calling the appropriate setter function.
 * <li>If the configured object inherits from an ancestor class, the Config should inherit the Config of the ancestor
 * class (if any).
 * <li>After configuration is complete, the Config can be {@link #locked()} (by using the {@link #lock()} function). It
 * can never be unlocked. Setters may check that the configuration is locked by calling {@link #locked()}, that will
 * automatically throw an exception if necessary.
 * </ul>
 * 
 * @author Andrei Olaru
 * 
 */
public abstract class Config implements Configurable
{
	/**
	 * Exception that is thrown when a 'locked' setter is called on a locked {@link Config} instance. More precisely, it
	 * is thrown if the setter method calls {@link #locked()} and the instance was currently locked.
	 * <p>
	 * It means that the setting is not supposed to be changed after the construction / setting phase has been
	 * completed.
	 * 
	 * @author Andrei Olaru
	 */
	public class ConfigLockedException extends Exception
	{
		/**
		 * The serial UID.
		 */
		private static final long	serialVersionUID	= 8254604960026434594L;
		
		@Override
		public String toString()
		{
			return "It is illegal to modify a Config once it has been locked.";
		}
	}
	
	/**
	 * Retains the 'locked' state of the Config instance.
	 */
	private boolean	locked	= false;
	
	/**
	 * Default constructor, that calls the {@link #makeDefaults()} method.
	 */
	public Config()
	{
		makeDefaults();
	}
	
	@Override
	public Config makeDefaults()
	{
		return this;
	}
	
	@Override
	public Config lock()
	{
		locked = true;
		return this;
	}
	
	@Override
	public final Config build()
	{
		return lock();
	}
	
	@Override
	public void ensureLocked()
	{
		if(!locked)
			lock();
	}
	
	// may be overridden
	@Override
	public void locked() throws ConfigLockedException
	{
		if(locked)
			throw new ConfigLockedException();
	}
	
	// may not be overridden
	/**
	 * Alias of {@link #locked()} that cannot be overridden, meaning that a call to this method is sure to do what the
	 * implementation in {@link Config} specifies, so that extending classes cannot avoid the exception by overriding
	 * {@link #locked()}.
	 * 
	 * @throws ConfigLockedException
	 */
	protected final void lockedEx() throws ConfigLockedException
	{
		if(locked)
			throw new ConfigLockedException();
	}
	
}
