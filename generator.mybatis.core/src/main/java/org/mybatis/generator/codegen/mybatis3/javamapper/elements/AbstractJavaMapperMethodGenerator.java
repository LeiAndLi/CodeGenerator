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
package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import static org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.List;
import java.util.Set;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.config.file.JavaModelGeneratorConfiguration;

/**
 * 
 * @author Jeff Butler
 */
public abstract class AbstractJavaMapperMethodGenerator extends AbstractGenerator {
	public abstract void addInterfaceElements(Interface interfaze);

	public AbstractJavaMapperMethodGenerator() {
		super();
	}

	protected String getResultAnnotation(Interface interfaze, IntrospectedColumn introspectedColumn, boolean idColumn,
			boolean constructorBased) {
		StringBuilder sb = new StringBuilder();
		if (constructorBased) {
			interfaze.addImportedType(introspectedColumn.getFullyQualifiedJavaType());
			sb.append("@Arg(column=\""); //$NON-NLS-1$
			sb.append(getRenamedColumnNameForResultMap(introspectedColumn));
			sb.append("\", javaType="); //$NON-NLS-1$
			sb.append(introspectedColumn.getFullyQualifiedJavaType().getShortName());
			sb.append(".class"); //$NON-NLS-1$
		} else {
			sb.append("@Result(column=\""); //$NON-NLS-1$
			sb.append(getRenamedColumnNameForResultMap(introspectedColumn));
			sb.append("\", property=\""); //$NON-NLS-1$
			sb.append(introspectedColumn.getJavaProperty());
			sb.append('\"');
		}

		if (stringHasValue(introspectedColumn.getTypeHandler())) {
			FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedColumn.getTypeHandler());
			interfaze.addImportedType(fqjt);
			sb.append(", typeHandler="); //$NON-NLS-1$
			sb.append(fqjt.getShortName());
			sb.append(".class"); //$NON-NLS-1$
		}

		sb.append(", jdbcType=JdbcType."); //$NON-NLS-1$
		sb.append(introspectedColumn.getJdbcTypeName());
		if (idColumn) {
			sb.append(", id=true"); //$NON-NLS-1$
		}
		sb.append(')');

		return sb.toString();
	}

	protected void addGeneratedKeyAnnotation(Interface interfaze, Method method, GeneratedKey gk) {
		StringBuilder sb = new StringBuilder();
		IntrospectedColumn introspectedColumn = introspectedTable.getColumn(gk.getColumn());
		if (introspectedColumn != null) {
			if (gk.isJdbcStandard()) {
				interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Options")); //$NON-NLS-1$
				sb.append("@Options(useGeneratedKeys=true,keyProperty=\""); //$NON-NLS-1$
				sb.append(introspectedColumn.getJavaProperty());
				sb.append("\")"); //$NON-NLS-1$
				method.addAnnotation(sb.toString());
			} else {
				interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.SelectKey")); //$NON-NLS-1$
				FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
				interfaze.addImportedType(fqjt);
				sb.append("@SelectKey(statement=\""); //$NON-NLS-1$
				sb.append(gk.getRuntimeSqlStatement());
				sb.append("\", keyProperty=\""); //$NON-NLS-1$
				sb.append(introspectedColumn.getJavaProperty());
				sb.append("\", before="); //$NON-NLS-1$
				sb.append(gk.isIdentity() ? "false" : "true"); //$NON-NLS-1$ //$NON-NLS-2$
				sb.append(", resultType="); //$NON-NLS-1$
				sb.append(fqjt.getShortName());
				sb.append(".class)"); //$NON-NLS-1$
				method.addAnnotation(sb.toString());
			}
		}
	}

	protected void addBatchMethodParameter(Method method, Set<FullyQualifiedJavaType> importedTypes) {
		String base = introspectedTable.getBaseRecordType();
		FullyQualifiedJavaType baseType = new FullyQualifiedJavaType(base);
		importedTypes.add(baseType);

		String list = "java.util.List<" + base + ">";
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(list);
		importedTypes.add(type);
		importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));

		Parameter parameter = new Parameter(type, "records");
		parameter.addAnnotation("@Param(\"records\")");
		method.addParameter(parameter);
	}

	protected String getColumnNameSql() {
		String sql = null;
		List<IntrospectedColumn> columns = this.introspectedTable.getAllColumns();
		for (IntrospectedColumn col : columns) {
			if (sql == null) {
				sql = "(";
				sql += col.getActualColumnName();
			} else {
				sql += ",";
				sql += col.getActualColumnName();
			}
		}
		sql += ")";
		return sql;
	}

	protected String getColumnTabSql() {
		String sql = null;
		List<IntrospectedColumn> columns = this.introspectedTable.getAllColumns();
		for (IntrospectedColumn col : columns) {
			if (sql == null) {
				sql = "(";
				sql += "\"\"";
			} else {
				sql += ",";
				sql += "\"\"";
			}
		}
		sql += ")";
		return sql;
	}

	protected String getColumnUpdate() {
		String sql = null;
		List<IntrospectedColumn> columns = this.introspectedTable.getAllColumns();
		for (IntrospectedColumn col : columns) {
			if (sql == null) {
				sql = col.getActualColumnName();
				sql += "=\"\"";
			} else {
				sql += ",";
				sql += col.getActualColumnName();
				sql += "=\"\"";
			}
		}
		return sql;
	}

	protected String getColumnCondtion() {
		String sql = "";
		List<IntrospectedColumn> columns = this.introspectedTable.getAllColumns();
		for (IntrospectedColumn col : columns) {
			if (context.getIgnoreColumnList().isColumnIgnored(col.getActualColumnName())) {
				continue;
			}

			sql += "and ";
			sql += col.getActualColumnName();
			sql += "=\"\"";
		}

		return sql;
	}

	protected String getColumnCondtionList() {
		String sql = "";
		List<IntrospectedColumn> columns = this.introspectedTable.getAllColumns();
		for (IntrospectedColumn col : columns) {
			if (context.getIgnoreColumnList().isColumnIgnored(col.getActualColumnName())) {
				continue;
			}

			sql += "and (";
			sql += col.getActualColumnName() + " = \"\"";
			sql += " or ";
			sql += col.getActualColumnName() + " = \"\"";
			sql += ")";
		}

		return sql;
	}

	public void addSecurityKeyMapperParam(Method method, Set<FullyQualifiedJavaType> importedTypes) {
		String stype = "java.lang.String";
		String sname = "secretKey";
		FullyQualifiedJavaType type = new FullyQualifiedJavaType(stype);
		importedTypes.add(type);
		Parameter parameter = new Parameter(type, sname);
		StringBuffer sb = new StringBuffer();
		sb.append("@Param(\""); //$NON-NLS-1$
		sb.append(sname);
		sb.append("\")"); //$NON-NLS-1$
		parameter.addAnnotation(sb.toString());
		method.addParameter(parameter);
	}

	/**
	 * 添加List<Pojo> records 参数
	 * 
	 * @param importedTypes
	 * @param method
	 */
	public void addListBasePojoParam(Set<FullyQualifiedJavaType> importedTypes, Method method) {
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		FullyQualifiedJavaType list = FullyQualifiedJavaType.getNewListInstance();
		list.addTypeArgument(parameterType);
		importedTypes.add(parameterType);
		
		Parameter p = new Parameter(list, "records");
		p.addAnnotation("@Param(\"records\")");
		method.addParameter(p); //$NON-NLS-1$
	}

	/**
	 * 添加PojoCondition condition参数
	 * 
	 * @param importedTypes
	 * @param method
	 */
	public void addPojoConditionParam(Set<FullyQualifiedJavaType> importedTypes, Method method) {
		String paramName = introspectedTable.getSelectByCondtionParamName();
		FullyQualifiedJavaType paramType = new FullyQualifiedJavaType(introspectedTable.getBaseConditionType());
		Parameter p = new Parameter(paramType, paramName);
		p.addAnnotation("@Param(\"" + paramName + "\")");
		method.addParameter(p);
		importedTypes.add(paramType);
	}

	/**
	 * 添加Pojo record 参数
	 * 
	 * @param importedTypes
	 * @param method
	 */
	public void addBasePojoParam(Set<FullyQualifiedJavaType> importedTypes, Method method) {
		FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		importedTypes.add(parameterType);
		Parameter p = new Parameter(parameterType, "item");
		p.addAnnotation("@Param(\"item\")");
		method.addParameter(p); // $NON-NLS-1$
	}
}
