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

public class FacadeDeleteMethodGenerator  extends AbstractJavaServiceMethodGenerator{

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
        lines.add("单条删除");
        lines.add("使用前请自行填充数据");
        lines.add("@param obj");
        lines.add("@param context");
        lines.add("@return");
        this.addJavaDoc(method, lines);
        
    }

}
