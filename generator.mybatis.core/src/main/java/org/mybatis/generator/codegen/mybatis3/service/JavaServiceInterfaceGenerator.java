package org.mybatis.generator.codegen.mybatis3.service;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.BatchUpdateByConditionSrvMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.CountByConditionSrvMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.DeleteAllMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.DeleteByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByConditionAllMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryByIdMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryCodeListByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryKVMapByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryMapByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryShortByConditionAllMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.QueryShortByConditionMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.UpdateByConditionSrvMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchDeleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchInsertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.batchModifyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.deleteMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.insertMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.modifyMethodGenerator;
import org.mybatis.generator.codegen.mybatis3.service.element.service.selectAllMethodGenerator;
import org.mybatis.generator.config.file.javaServiceGeneratorConfig;

public class JavaServiceInterfaceGenerator extends AbstractJavaServiceGenerator{

	@Override
	public List<CompilationUnit> getCompilationUnits() {
		List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
		CommentGenerator commentGenerator = context.getCommentGenerator();
		
		javaServiceGeneratorConfig config = context.getServiceGeneratorConfig();
		String objectName = config.getFullClassType(introspectedTable);

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(objectName);
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        commentGenerator.addInterfaceComment(interfaze);
        
        addImportList(config.getImportConfigs(), interfaze);
        
        //insert
        addInsertMethod(interfaze);
        if(introspectedTable.getRules().generateBatchInsert()){
            this.addBatchInsertMethod(interfaze, config.getRootClass());
        }
        
        //modify
        addModifyMethod(interfaze);
        if(introspectedTable.getRules().generateBatchUpdate()){
            this.addBatchModifyMethod(interfaze, config.getRootClass());
        }
        if(introspectedTable.getRules().generateCondition()){
            this.addUpdateByConditionMethod(interfaze);
            this.addBatchUpdateByConditionMethod(interfaze);
        }
        
        //delete
        addDeleteMethod(interfaze);
        if(introspectedTable.getRules().generateCondition()){
            this.addDeleteAllMethod(interfaze);
            this.addDeleteByConditionMethod(interfaze);
        }
        if(introspectedTable.getRules().generateBatchDelete()){
            this.addBatchDeleteMethod(interfaze, config.getRootClass());
        }    
        
        //count
        if(introspectedTable.getRules().generateCondition()){
            this.addCountByConditionMethod(interfaze);
        }
       
        
        //query
        addSelectAllMethod(interfaze);
        addQueryByIdMethod(interfaze);
       
        if(introspectedTable.getRules().generateCondition()){
        	this.addQueryByConditionMethod(interfaze);
        	this.addQueryByConditionAllMethod(interfaze);
        	
        	this.addQueryShortByConditionMethod(interfaze);
        	this.addQueryShortByConditionAllMethod(interfaze);
        	
        	this.addQueryMapByConditionMethod(interfaze);
        	this.addQueryKVMapByConditionMethod(interfaze);
        	this.addQueryCodeListByConditionMethod(interfaze);
        	
        	
        }
        
        List<Method> methods = interfaze.getMethods();
		if(methods != null){
			for(Method method : methods){
				addMethodAnnotation(config.getAnnotations(), method);
				addMethodParam(config.getMethodParamConfigs(), method);
			}
		}
        
        
        answer.add(interfaze);
		return answer;
	}
	
	protected void addQueryByIdMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method = new QueryByIdMethodGenerator();
		
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	
	protected void addInsertMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method = new insertMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addModifyMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method = new modifyMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addDeleteMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method = new deleteMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addBatchInsertMethod(Interface interfaze, String rootClass){
		AbstractJavaMapperMethodGenerator method = new batchInsertMethodGenerator(rootClass);
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addBatchModifyMethod(Interface interfaze, String rootClass){
		AbstractJavaMapperMethodGenerator method = new batchModifyMethodGenerator(rootClass);
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addBatchDeleteMethod(Interface interfaze, String rootClass){
		AbstractJavaMapperMethodGenerator method = new batchDeleteMethodGenerator(rootClass);
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addQueryByConditionMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method  = new QueryByConditionMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addSelectAllMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method  = new selectAllMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	
	protected void addQueryByConditionAllMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method  =  new QueryByConditionAllMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addDeleteByConditionMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method  =  new DeleteByConditionMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addQueryShortByConditionMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method  =  new QueryShortByConditionMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addQueryShortByConditionAllMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method  =  new QueryShortByConditionAllMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addCountByConditionMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method  =  new CountByConditionSrvMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addUpdateByConditionMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method  =  new UpdateByConditionSrvMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addBatchUpdateByConditionMethod(Interface interfaze){
		AbstractJavaMapperMethodGenerator method  =  new BatchUpdateByConditionSrvMethodGenerator();
		initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void addDeleteAllMethod(Interface interfaze){
	    AbstractJavaMapperMethodGenerator method = new DeleteAllMethodGenerator();
	    initializeAndExecuteGenerator(method, interfaze);
	    
	}
	
	
	protected void  addQueryMapByConditionMethod(Interface interfaze){
	    AbstractJavaMapperMethodGenerator method = new QueryMapByConditionMethodGenerator();
        initializeAndExecuteGenerator(method, interfaze);
	}
	
	protected void  addQueryKVMapByConditionMethod(Interface interfaze){
        AbstractJavaMapperMethodGenerator method = new QueryKVMapByConditionMethodGenerator();
        initializeAndExecuteGenerator(method, interfaze);
    }
    
	protected void  addQueryCodeListByConditionMethod(Interface interfaze){
        AbstractJavaMapperMethodGenerator method = new QueryCodeListByConditionMethodGenerator();
        initializeAndExecuteGenerator(method, interfaze);
    }
    
}
