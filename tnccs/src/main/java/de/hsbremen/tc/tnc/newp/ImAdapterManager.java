package de.hsbremen.tc.tnc.newp;

import java.util.Map;

import de.hsbremen.tc.tnc.adapter.im.ImAdapter;

public interface ImAdapterManager<T extends ImAdapter<?>> {
	public abstract Map<Long,T> getAdapter();
	public abstract ImMessageRouter getRouter();
	public abstract void removeAdapter(long id);
}