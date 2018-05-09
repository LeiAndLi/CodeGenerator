package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class BatchDeleteMethodGenerator  extends
AbstractJavaMapperMethodGenerator  {
	
	public BatchDeleteMethodGenerator() {
		super();
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();

        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setName(introspectedTable.getBatchDeleteStatementId());
        
    	importedTypes.add(new FullyQualifiedJavaType("java.util.List"));
    	importedTypes.add(new FullyQualifiedJavaType("java.lang.Long"));
    	
    	String list = "List<Long>";
    	FullyQualifiedJavaType type = new FullyQualifiedJavaType(list);
        importedTypes.add(type);
        importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
        
        Parameter parameter = new Parameter(type, "records");
        parameter.addAnnotation("@Param(\"records\")");
        method.addParameter(parameter);
        

        addMapperAnnotations(interfaze, method);
        
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
    	String tableName = this.introspectedTable.getTableConfiguration().getTableName();
    	String sql = "delete from " + tableName + "where id in()";
    	method.addJavaDocLine("/*");
    	method.addJavaDocLine("*" + sql);
    	method.addJavaDocLine("*/");
    }
    
    
    
   

}
