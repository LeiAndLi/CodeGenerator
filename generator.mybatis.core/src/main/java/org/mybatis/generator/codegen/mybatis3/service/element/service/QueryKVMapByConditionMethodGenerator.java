package org.mybatis.generator.codegen.mybatis3.service.element.service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaRPCMethodGenerator;

public class QueryKVMapByConditionMethodGenerator  extends AbstractJavaRPCMethodGenerator{

    @Override
    public void addRPCMethod(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        
        String tag = this.getClass().getSimpleName() + "RPC";
        Method method = this.introspectedTable.getMethodFactory().getMybatisMethod(tag, importedTypes);
        List<String> lines = this.introspectedTable.getMethodFactory().getMethod(tag);
        method.addBodyLines(lines);
        method.addAnnotation("@ResourceMapping");
        
        topLevelClass.addMethod(method);
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.LinkedHashMap"));
        topLevelClass.addImportedTypes(importedTypes);
    }

    @Override
    public void addRPCTestMethod(TopLevelClass topLevelClass) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addMethodElements(TopLevelClass topLevelClass) {
Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        
        Method method = this.introspectedTable.getMethodFactory().
                getMybatisMethod(this.getClass().getSimpleName(), importedTypes);
        List<String> bodyLines = this.introspectedTable.getMethodFactory().getMethod(
                this.getClass().getSimpleName());
        method.addBodyLines(bodyLines);
        
        addOverrideAnnotation(method);

        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.LinkedHashMap"));
        topLevelClass.addMethod(method);
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        
        Method method = this.introspectedTable.getMethodFactory().
                getMybatisMethod(this.getClass().getSimpleName(), importedTypes);
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.Map"));
        interfaze.addMethod(method);
    }

}
