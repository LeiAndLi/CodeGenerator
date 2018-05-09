/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getGetterMethodName;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansField;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansGetter;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansSetter;

import java.util.List;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.JavaBeansUtil;

/**
 * 
 * @author Jeff Butler
 * 
 */
public abstract class AbstractJavaGenerator extends AbstractGenerator {
    public abstract List<CompilationUnit> getCompilationUnits();

    public static Method getGetter(Field field) {
        Method method = new Method();
        method.setName(getGetterMethodName(field.getName(), field
                .getType()));
        method.setReturnType(field.getType());
        method.setVisibility(JavaVisibility.PUBLIC);
        StringBuilder sb = new StringBuilder();
        sb.append("return "); //$NON-NLS-1$
        sb.append(field.getName());
        sb.append(';');
        method.addBodyLine(sb.toString());
        return method;
    }

    public String getRootClass() {
        String rootClass = introspectedTable
                .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_CLASS);
        if (rootClass == null) {
            Properties properties = context
                    .getJavaModelGeneratorConfiguration().getProperties();
            rootClass = properties.getProperty(PropertyRegistry.ANY_ROOT_CLASS);
        }

        return rootClass;
    }

    protected void addDefaultConstructor(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(topLevelClass.getType().getShortName());
        method.addBodyLine("super();"); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }
    
    protected Field addFieldAndMethod(IntrospectedColumn introspectedColumn, TopLevelClass topLevelClass, CommentGenerator commentGenerator){
    	 Field field = getJavaBeansField(introspectedColumn, context, introspectedTable);
         topLevelClass.addField(field);
         topLevelClass.addImportedType(field.getType());

         Method method = getJavaBeansGetter(introspectedColumn, context, introspectedTable);
         topLevelClass.addMethod(method);
         Method setmthod = getJavaBeansSetter(introspectedColumn, context, introspectedTable);
         topLevelClass.addMethod(setmthod);
         return field;
    }
    
    protected Field addListFieldAndMethod(IntrospectedColumn introspectedColumn, TopLevelClass topLevelClass, CommentGenerator commentGenerator){
    	String name = getListFieldName(introspectedColumn);
    	String type = getListFieldType(introspectedColumn);
    	Field f= JavaBeansUtil.getJavaBeanField(introspectedColumn, name, type);
    	topLevelClass.addField(f);
    	topLevelClass.addImportedType("java.util.List");
    	topLevelClass.addImportedType("java.util.ArrayList");
    	
    	Method set = JavaBeansUtil.getJavaBeansSetter(introspectedColumn, name, type);
    	topLevelClass.addMethod(set);
    	
    	Method get = JavaBeansUtil.getJavaBeansGetter(introspectedColumn, name, type);
    	topLevelClass.addMethod(get);
    	
    	Method add = JavaBeansUtil.getJavaBeansAdder(introspectedColumn, name, introspectedColumn.getJavaProperty());
    	topLevelClass.addMethod(add);
    	return f;
    }
    
    
    
    protected String getListFieldName(IntrospectedColumn introspectedColumn){
    	return  introspectedColumn.getJavaPropertySubfix("List");
    }
    
    protected String getListFieldType(IntrospectedColumn introspectedColumn){
    	FullyQualifiedJavaType dtype = introspectedColumn.getFullyQualifiedJavaType();
		String type = "List<" +dtype.getFullTypeSpecification() + ">";
		return type;
    }
}
