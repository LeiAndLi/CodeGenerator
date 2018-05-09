package org.mybatis.generator.codegen.mybatis3.service.element.facade;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Lists;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.mybatis3.service.element.AbstractJavaServiceMethodGenerator;

public class FacadeModifyByConditionMethodGenerator  extends AbstractJavaServiceMethodGenerator{

    @Override
    public void addMethodElements(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = this.introspectedTable.getMethodFactory().
                getMybatisMethod(this.getClass().getSimpleName(), importedTypes);
        method.addBodyLines(this.introspectedTable.getMethodFactory().getMethod(this.getClass().getSimpleName()));
        
        
        addOverrideAnnotation(method);

        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
        this.addJavaDoc(method);
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = this.introspectedTable.getMethodFactory().
                getMybatisMethod(this.getClass().getSimpleName(), importedTypes);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
        this.addJavaDoc(method);
    }

    public void addJavaDoc(Method method){
        List<String> lines = Lists.newArrayList();
        lines.add("根据条件，批量修改指定某些字段");
        lines.add("@param param");
        lines.add("@param condition");
        lines.add("@param context");
        lines.add("@return");
        this.addJavaDoc(method, lines);
        
    }
    
   
}
