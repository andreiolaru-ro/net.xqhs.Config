package net.xqhs.util.config;

import java.io.Serializable;

/**
 * The {@link Serializable} version of {@link Config}, to be used when necessary.
 * 
 * @author Andrei Olaru
 */
public abstract class SerializableConfig extends Config implements Serializable
{
	/**
	 * The serial UID
	 */
	private static final long serialVersionUID = -2532432781357095271L;
}
