package com.eip.demo.common;

public final class EipCommon {
	
	private static Integer splitter_stand_alone_Id = 0;

	public static Integer getSplitter_stand_alone_Id() {
		return splitter_stand_alone_Id;
	}

	public static void increase_Splitter_stand_alone_Id() {
		splitter_stand_alone_Id = splitter_stand_alone_Id + 1;
	}
	
	
}
