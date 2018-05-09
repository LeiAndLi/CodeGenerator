package org.mybatis.generator.codegen.mybatis3.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaRPCMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByIdMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchDeleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchInsertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchModifyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.deleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.insertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.modifyMethodGenerator;
import org.mybatis.generator.config.file.JavaRpcTestGeneratorConfig;
import org.mybatis.generator.config.file.javaVoGeneratorConfig;

public class javaRpcTestGenerator extends AbstractJavaServiceGenerator {

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();

		CommentGenerator commentGenerator = context.getCommentGenerator();
		JavaRpcTestGeneratorConfig config = context.getRpcTestGeneratorConfig();
		String objectName = config.getFullClassType(introspectedTable);
		String superClass = config.getRootClass();

		FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
		TopLevelClass topLevelClass = new TopLevelClass(type);
		topLevelClass.setVisibility(JavaVisibility.PUBLIC);
		commentGenerator.addJavaFileComment(topLevelClass);
		commentGenerator.addModelClassComment(topLevelClass, introspectedTable);
		
		addImport(topLevelClass);
		addImportList(config.getImportConfigs(), topLevelClass);
		addSuperClasss(topLevelClass, superClass);
		addStaticField(topLevelClass, "host", "String", "\"http://\"", "请求地址");
		addStaticField(topLevelClass, "account", "String", "\"\"", "请求账号");
		
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
	
	
	protected void addImport(TopLevelClass topClass){
		
		javaVoGeneratorConfig voConfig = context.getVoGeneratorConfig();
		
		String[] arr = {
				"com.meterware.httpunit.GetMethodWebRequest",
				"com.meterware.httpunit.HttpUnitOptions",
				"com.meterware.httpunit.WebRequest",
				"com.taobao.itest.annotation.DataProvider",
				"org.junit.Test",
				"java.net.MalformedURLException",
				"org.junit.Assert",
				"org.junit.Ignore",
				"net.sf.json.JSONObject",
				"java.util.ArrayList",
				"java.util.List",
				"net.sf.json.JSONArray",
		};
		
		for(String cls : arr){
			topClass.addImportedType(new FullyQualifiedJavaType(cls));
		}
		
		topClass.addImportedType(voConfig.getFullClassType(introspectedTable));
		
	}
	
	protected void addSuperClasss(TopLevelClass topClass, String superClass){
		topClass.setSuperClass(new FullyQualifiedJavaType(superClass));
	}
	
	
	protected void addQueryByIdMethod(TopLevelClass topLevelClass) {
		AbstractJavaRPCMethodGenerator method = new QueryByIdMethodGenerator();

		initializeAndExecuteRPCTestGenerator(method, topLevelClass);
	}

	protected void addInsertMethod(TopLevelClass topLevelClass) {
		AbstractJavaRPCMethodGenerator method = new insertMethodGenerator();

		initializeAndExecuteRPCTestGenerator(method, topLevelClass);
	}

	protected void addModifyMethod(TopLevelClass topLevelClass) {
		AbstractJavaRPCMethodGenerator method = new modifyMethodGenerator();

		initializeAndExecuteRPCTestGenerator(method, topLevelClass);
	}

	protected void addDeleteMethod(TopLevelClass topLevelClass) {
		AbstractJavaRPCMethodGenerator method = new deleteMethodGenerator();

		initializeAndExecuteRPCTestGenerator(method, topLevelClass);
	}
	
	protected void addBatchInsertMethod(TopLevelClass topLevelClass, String rootClass){
		AbstractJavaRPCMethodGenerator method = new batchInsertMethodGenerator(rootClass);

		initializeAndExecuteRPCTestGenerator(method, topLevelClass);
	}
	
	protected void addBatchModifyMethod(TopLevelClass topLevelClass, String rootClass){
		AbstractJavaRPCMethodGenerator method = new batchModifyMethodGenerator(rootClass);

		initializeAndExecuteRPCTestGenerator(method, topLevelClass);
	}
	
	protected void addBatchDeleteMethod(TopLevelClass topLevelClass, String rootClass){
		AbstractJavaRPCMethodGenerator method = new batchDeleteMethodGenerator(rootClass);

		initializeAndExecuteRPCTestGenerator(method, topLevelClass);
	}
	
	protected void addQueryByCondition(TopLevelClass topLevelClass){
		AbstractJavaRPCMethodGenerator method = new  QueryByConditionMethodGenerator();
		
		initializeAndExecuteRPCTestGenerator(method, topLevelClass);
	}

	

}
