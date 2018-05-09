package org.mybatis.generator.codegen.mybatis3.service.element;



import java.util.List;

import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;

public abstract class AbstractJavaServiceMethodGenerator extends 
AbstractJavaMapperMethodGenerator{

	public abstract void addMethodElements(TopLevelClass topLevelClass);
	
	
	
	protected void addOverrideAnnotation(Method method){
		StringBuilder sb = new StringBuilder();
		sb.append("@Override"); //$NON-NLS-1$
		method.addAnnotation(sb.toString());
	}
	
	
	protected void addJavaDoc(Method method, List<String> docLines){
	    if(docLines != null){
	        method.addJavaDocLine("/**");
	        for(String doc : docLines){
	            method.addJavaDocLine(" * "+doc);
	        }
	        method.addAnnotation(" */");
	    }
	}
	
}
