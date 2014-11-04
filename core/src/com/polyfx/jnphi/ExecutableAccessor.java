package com.polyfx.jnphi;

public class ExecutableAccessor {
	
	private byte[] block = { };
	private static int idx = -1;
	
	public boolean consumed() {
		return (block.length == 0);
	}
	
	public void clearBlock() {
		this.block = new byte[0];
	}

	public void setBlock(byte[] block) {
		++ExecutableAccessor.idx;
		this.block = block;
	}
	
	public int getIdx() {
		return ExecutableAccessor.idx;
	}
}
