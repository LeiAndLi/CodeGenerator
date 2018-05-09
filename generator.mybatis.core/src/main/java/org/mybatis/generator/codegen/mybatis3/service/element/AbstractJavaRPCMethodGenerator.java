package org.mybatis.generator.codegen.mybatis3.service.element;

import org.mybatis.generator.api.dom.java.TopLevelClass;

public abstract class AbstractJavaRPCMethodGenerator extends AbstractJavaServiceMethodGenerator {

	public abstract void addRPCMethod(TopLevelClass topLevelClass);
	
	public abstract void addRPCTestMethod(TopLevelClass topLevelClass);
}
