package org.mybatis.generator.codegen.mybatis3.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaRPCMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaServiceMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByIdMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryKVMapByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchDeleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchInsertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchModifyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.deleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.insertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.modifyMethodGenerator;
import org.mybatis.generator.config.file.JavaVOAdapterGeneratorConfig;
import org.mybatis.generator.config.file.javaRpcGeneratorConfig;
import org.mybatis.generator.config.file.javaServiceGeneratorConfig;

public class javaRpcGenerator extends AbstractJavaServiceGenerator {

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

		CommentGenerator commentGenerator = context.getCommentGenerator();
		javaRpcGeneratorConfig config = context.getRpcGenratorConfig();
		String objectName = config.getFullClassType(introspectedTable);

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);
		commentGenerator.addModelClassComment(topLevelClass, introspectedTable);
		
		addResourceField(topLevelClass);
		addComponent(topLevelClass);
		addImportList(config.getImportConfigs(), topLevelClass);
		
		this.addQueryByIdMethod(topLevelClass);
		this.addInsertMethod(topLevelClass);
		this.addModifyMethod(topLevelClass);
		this.addDeleteMethod(topLevelClass);
		
		if(introspectedTable.getRules().generateBatchInsert()){
			this.addBatchInsertMethod(topLevelClass, config.getRootClass());
        }
        
        if(introspectedTable.getRules().generateBatchUpdate()){
        	this.addBatchModifyMethod(topLevelClass, config.getRootClass());
        }
        
        if(introspectedTable.getRules().generateBatchDelete()){
        	this.addBatchDeleteMethod(topLevelClass, config.getRootClass());
        }
		
		if(introspectedTable.getRules().generateCondition()){
			this.addQueryByCondition(topLevelClass);
			this.addQueryKVMapByCondition(topLevelClass);
		}
		
		
		List<Method> methods = topLevelClass.getMethods();
		if(methods != null){
			for(Method method : methods){
				addMethodAnnotation(config.getAnnotations(), method);
				addMethodParam(config.getMethodParamConfigs(), method);
			}
		}
		
		answer.add(topLevelClass);

		return answer;
	}
	
	
	protected void addResourceField(TopLevelClass topLevelClass){
		javaServiceGeneratorConfig interConfig = context.getServiceGeneratorConfig();
		
		String type = interConfig.getFullClassType(introspectedTable);
		String name = interConfig.getLowShortClassName(introspectedTable);
		
		addResourceField(topLevelClass, name,type);
		
		JavaVOAdapterGeneratorConfig voAdapter = context.getVoAdapterGeneratorConfig();
		String facadeType = voAdapter.getFullClassType(introspectedTable);
		String facadeName = voAdapter.getLowShortClassName(introspectedTable);
		addResourceField(topLevelClass, facadeName, facadeType);
	}
	
	protected void addComponent(TopLevelClass topLevelClass){
		String domain = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
		String prefix = domain.substring(0,1);
		String subfix = domain.substring(1);
		String url = prefix.toLowerCase() + subfix;
		
		addRPCComponent(topLevelClass, url);
	}
	
	
	protected void addQueryByIdMethod(TopLevelClass topLevelClass) {
		AbstractJavaRPCMethodGenerator method = new QueryByIdMethodGenerator();

		initializeAndExecuteRPCGenerator(method, topLevelClass);
	}

	protected void addInsertMethod(TopLevelClass topLevelClass) {
		AbstractJavaRPCMethodGenerator method = new insertMethodGenerator();

		initializeAndExecuteRPCGenerator(method, topLevelClass);
	}

	protected void addModifyMethod(TopLevelClass topLevelClass) {
		AbstractJavaRPCMethodGenerator method = new modifyMethodGenerator();

		initializeAndExecuteRPCGenerator(method, topLevelClass);
	}

	protected void addDeleteMethod(TopLevelClass topLevelClass) {
		AbstractJavaRPCMethodGenerator method = new deleteMethodGenerator();

		initializeAndExecuteRPCGenerator(method, topLevelClass);
	}
	
	protected void addBatchInsertMethod(TopLevelClass topLevelClass, String rootClass){
		AbstractJavaRPCMethodGenerator method = new batchInsertMethodGenerator(rootClass);

		initializeAndExecuteRPCGenerator(method, topLevelClass);
	}
	
	protected void addBatchModifyMethod(TopLevelClass topLevelClass, String rootClass){
		AbstractJavaRPCMethodGenerator method = new batchModifyMethodGenerator(rootClass);

		initializeAndExecuteRPCGenerator(method, topLevelClass);
	}
	
	protected void addBatchDeleteMethod(TopLevelClass topLevelClass, String rootClass){
		AbstractJavaRPCMethodGenerator method = new batchDeleteMethodGenerator(rootClass);

		initializeAndExecuteRPCGenerator(method, topLevelClass);
	}
	
	protected void addQueryByCondition(TopLevelClass topLevelClass){
		AbstractJavaRPCMethodGenerator method = new QueryByConditionMethodGenerator();
		
		initializeAndExecuteRPCGenerator(method, topLevelClass);
	}
	
	protected void addQueryKVMapByCondition(TopLevelClass topLevelClass){
	    AbstractJavaRPCMethodGenerator method = new QueryKVMapByConditionMethodGenerator();
	    
	    initializeAndExecuteRPCGenerator(method, topLevelClass);
	}
	
	
	

}
