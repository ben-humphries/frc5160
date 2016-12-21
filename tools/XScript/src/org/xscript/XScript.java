package org.xscript;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

public class XScript {
	private File file; 
	private HashMap<String,Object> values; 
	private HashMap<String,Object> functions; 
	public XScript(File file){
		this.file = file;
		values = new HashMap<String,Object>();
		functions = new HashMap<String,Object>();
	}
	public void RegisterFunctions(Method... methods){
		for(Method m : methods){
			functions.put(m.getName().toLowerCase(),m);
		}
	}
	public void parse(){
		int lineNumber = 1;
		String line = null;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    while ((line = br.readLine()) != null) {
		    	line = line.toLowerCase().split("#")[0];
		    	if(line.contains("=")){
		    		parseEquals(line);
		    	}
		    	else{
		    		parseLine(line);
		    	}
		    	
		    	lineNumber++;
		    	
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e){
			System.err.println("Error parsing line "+lineNumber);
			System.err.println(line);
		}
	}
	private Object findValue(String arg) throws ParseException{
		if (Character.isDigit(arg.charAt(0))){
			if(arg.contains(".")){
				return Float.parseFloat(arg);
			}
			else {
				return Integer.parseInt(arg);
			}
		}
		
		else if (arg.indexOf('"')>=0){
			return arg.substring(arg.indexOf('"')+1, arg.lastIndexOf('"'));
		}
		else if (arg.equals("true")){
			return true;
		}
		else if (arg.equals("false")){
			return false; 
		}
		else if(values.containsKey(arg)){
			return values.get(arg);
		}
		else if (functions.containsKey(arg)){
			return callFunction(arg);
		}
		else{
			throw new ParseException();
		}
	}
	private void parseLine(String line) throws ParseException{
		callFunction(line);
		
	}
	private Object callFunction(String line) throws ParseException{
		String[] split = line.replace(" ","").replace(")", "").split("[(]",2);
		String fName = split[0];
		String[] arguments = split[1].split(",");
		Method method = (Method) functions.get(fName);
		if(arguments.length!=method.getParameterCount()&&!(arguments.length==1 && method.getParameterCount()==0)){
			throw new ParseException();
		}
		try {
			
			if(arguments.length==1 && arguments[0].equals("")){
				return method.invoke(null, null);
			}
			return method.invoke(null, parseArgs(arguments));
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new ParseException();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new ParseException();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new ParseException();
		}
	}
	private Object[] parseArgs(String[] args) throws ParseException{
		Object[] ret = new Object[args.length];
		for(int i = 0; i < args.length; i++){
			ret[i] = findValue(args[i]);
		}
		return ret;
	}
	private void parseEquals(String line) throws ParseException{
		String[] split = line.replace(" ","").split("=",2);
		String variable = split[0];
		String set = split[1];
		if(variable.length() < 1 || set.length() < 1){
			throw new ParseException();
		}
		values.put(variable, findValue(set));
	}

}
