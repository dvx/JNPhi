package com.polyfx.jnphi;

public class ExecutableAccessor {
	
	private Class<?> clazz = null;
	private static int idx = -1;
	
	public boolean consumed() {
		return (this.clazz == null);
	}
	
	public void clearBlock() {
		this.clazz = null;
	}

	public void setRType(Class<?> clazz) {
		++ExecutableAccessor.idx;
		this.clazz = clazz;
	}
	
	public int getIdx() {
		return ExecutableAccessor.idx;
	}
}
