package org.mybatis.generator.codegen.mybatis3.service;

import java.util.List;

import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaRPCMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaServiceMethodGenerator;
import org.mybatis.generator.config.AnnotationConfig;
import org.mybatis.generator.config.ImportConfig;
import org.mybatis.generator.config.MethodParamConfig;
import org.mybatis.generator.config.file.javaVoGeneratorConfig;

public abstract class AbstractJavaServiceGenerator extends AbstractJavaGenerator {

	protected void initializeAndExecuteGenerator(AbstractJavaMapperMethodGenerator methodGenerator,
			Interface interfaze) {
		methodGenerator.setContext(context);
		methodGenerator.setIntrospectedTable(introspectedTable);
		methodGenerator.setProgressCallback(progressCallback);
		methodGenerator.setWarnings(warnings);
		methodGenerator.addInterfaceElements(interfaze);
	}

	protected void initializeAndExecuteGenerator(AbstractJavaServiceMethodGenerator methodGenerator,
			TopLevelClass topLevelClass) {
		methodGenerator.setContext(context);
		methodGenerator.setIntrospectedTable(introspectedTable);
		methodGenerator.setProgressCallback(progressCallback);
		methodGenerator.setWarnings(warnings);
		methodGenerator.addMethodElements(topLevelClass);
	}
	
	protected void initializeAndExecuteRPCGenerator(AbstractJavaRPCMethodGenerator methodGenerator,
			TopLevelClass topLevelClass) {
		methodGenerator.setContext(context);
		methodGenerator.setIntrospectedTable(introspectedTable);
		methodGenerator.setProgressCallback(progressCallback);
		methodGenerator.setWarnings(warnings);
		methodGenerator.addRPCMethod(topLevelClass);
	}
	
	protected void initializeAndExecuteRPCTestGenerator(AbstractJavaRPCMethodGenerator methodGenerator,
			TopLevelClass topLevelClass) {
		methodGenerator.setContext(context);
		methodGenerator.setIntrospectedTable(introspectedTable);
		methodGenerator.setProgressCallback(progressCallback);
		methodGenerator.setWarnings(warnings);
		methodGenerator.addRPCTestMethod(topLevelClass);
	}
	
	protected void addResourceField(TopLevelClass topLevelClass, String fieldName, String fieldType){
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(fieldType);
		
		
		Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(type);
        field.setName(fieldName);
        field.addAnnotation("@Resource");
        
        topLevelClass.addImportedType(type);
        topLevelClass.addImportedType(new FullyQualifiedJavaType("javax.annotation.Resource"));
        
        topLevelClass.addField(field);
	}
	
	protected void addStaticField(TopLevelClass topLevelClass, String fieldName, String fieldType, String fieldValue, String remark){
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(fieldType);
		
		
		Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(type);
        field.setName(fieldName);
        field.setStatic(true);
        field.setInitializationString(fieldValue);
        field.addJavaDocLine("/*");
        field.addJavaDocLine("*"+ remark);
        field.addJavaDocLine("*/");
        
        topLevelClass.addImportedType(type);
        topLevelClass.addField(field);
	}
	
	protected void addComponent(TopLevelClass topLevelClass, String componentName){
		topLevelClass.addAnnotation("@Component(\""+componentName+"\")");
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component;"));
	}
	
	protected void addRPCComponent(TopLevelClass topLevelClass, String urlName){
		topLevelClass.addAnnotation("@Component");
		topLevelClass.addAnnotation("@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)");
		topLevelClass.addAnnotation("@WebResource(\"/"+urlName+"\")");
		
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.context.annotation.Scope"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.context.annotation.ScopedProxyMode"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.alibaba.citrus.extension.rpc.annotation.WebResource"));
		
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.alibaba.citrus.extension.rpc.annotation.ResourceMapping"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.alibaba.citrus.extension.rpc.databind.JsonParam"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("com.alibaba.nonda.databind.annotation.RequestParam"));
		
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.ArrayList"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("java.lang.String"));
		topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.util.Assert"));
		
		javaVoGeneratorConfig voconfig = context.getVoGeneratorConfig();
		String voType = voconfig.getFullClassType(introspectedTable);
		topLevelClass.addImportedType(new FullyQualifiedJavaType(voType));
		
		String doType = this.introspectedTable.getBaseRecordType();
		topLevelClass.addImportedType(new FullyQualifiedJavaType(doType));
		
	}
	
	protected void addImportList(List<ImportConfig> cfgs, TopLevelClass topLevelClass){
		if(cfgs != null){
			for(ImportConfig cfg : cfgs){
				String type = cfg.getType();
				topLevelClass.addImportedType(type);
			}
		}
	}
	
	protected void addImportList(List<ImportConfig> cfgs, Interface interfaze ){
		if(cfgs != null){
			for(ImportConfig cfg : cfgs){
				String type = cfg.getType();
				interfaze.addImportedType(new FullyQualifiedJavaType(type));
			}
		}
	}
	
	protected void addMethodAnnotation( List<AnnotationConfig> cfgs, Method method){
		if(cfgs != null && method != null){
			for(AnnotationConfig cfg : cfgs){
				String type = cfg.getType();
				String value = cfg.getValue();
				if(type.equals("method")){
					method.addAnnotation(value);
				}
			}
		}
	}
	
	protected void addMethodParam(List<MethodParamConfig> cfgs, Method method){
		if(cfgs != null && method != null){
			for(MethodParamConfig cfg : cfgs){
				String name = cfg.getName();
				if(method.hasParameters(name)){
					Parameter param = method.getParameters(name);
					param.addAnnotation(cfg.getAnnotation());
				}else{
					FullyQualifiedJavaType type = new FullyQualifiedJavaType(cfg.getType());
					Parameter param = new Parameter(type, name);
					method.addParameter(param);
				}	
			}
		}
	}
	
	
}
