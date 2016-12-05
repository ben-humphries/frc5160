package org.xscript;

import java.io.File;
import java.lang.reflect.Method;

public class Xtest {
	public static void main(String[] args){
		XScript test = new XScript(new File("test.xs"));
		test.RegisterFunctions(XTestMethods.class.getMethods());
		test.parse();
	}
	
}
