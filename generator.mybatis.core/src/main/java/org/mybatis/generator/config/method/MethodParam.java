package org.mybatis.generator.config.method;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansField;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansGetter;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansSetter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.velocity.VelocityContext;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.file.JavaDTOParamGeneratorConfig;
import org.mybatis.generator.config.file.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.file.JavaVOAdapterGeneratorConfig;
import org.mybatis.generator.config.file.javaFacadeGeneratorConfig;
import org.mybatis.generator.config.file.javaServiceGeneratorConfig;
import org.mybatis.generator.config.file.javaVoGeneratorConfig;
import org.mybatis.generator.internal.util.JavaBeansUtil;

public class MethodParam {
	
	private String javaMapperName;
	
	private String javaExtMapperName;
	

	private String domainObjectName;
	
	private String baseRecordType;
	
	private String domainObjectShortType;
	
	
	private String voObjectName;
	
	private String voObjectType;
	
	private String voObjectShortType;
	
	
	private String planObjectName;
	
	private String planObjectType;
	
	private String planObjectShortType;
	
	
	private String serviceInterfaceName;
	
	private String serviceInterfaceType;
	
	private String voAdapterInterfaceName;
	
	private String voAdapterInterfaceType;
	
	private String facadeInterfaceName;
	
	private String facadeInterfaceType;
	
	private String componentUrl;
	
	private String conditionObjectName;
	
	private String conditionObjectType;
	
	private String conditionObjectShortType;
	
	private String paramObjectName;
	
	private String paramObjectType;
	
	
	private List<PropertyParam> modelProList;
	
	private List<PropertyParam> condProList;
	
	private boolean isSecurity;
	
	
	public MethodParam(){
		this.domainObjectName = "";
		this.javaMapperName = "";
		this.javaExtMapperName = "";
		this.baseRecordType = "";
	}
	
	public MethodParam(IntrospectedTable introspectedTable){
		Context context = introspectedTable.getContext();
		
		
		this.isSecurity = introspectedTable.getTableConfiguration().hasSecurityColumn();
		
		
		
		this.domainObjectName = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
		this.javaMapperName = introspectedTable.getMyBatis3MapperName();
		this.javaExtMapperName = introspectedTable.getMyBatis3ExtMapperName();
		this.baseRecordType = introspectedTable.getBaseRecordType();
		int idx = this.baseRecordType.lastIndexOf(".");
		this.domainObjectShortType = this.baseRecordType.substring(idx+1);
		
		
		//有历史表
		if(introspectedTable.getTableConfiguration().getPtc() != null){
		    TableConfiguration ptc = introspectedTable.getTableConfiguration().getPtc();
            this.planObjectName = ptc.getDomainObjectName();
            this.planObjectType = introspectedTable.getPackageStr() + "." + this.planObjectName;
            this.planObjectShortType = this.planObjectName;
		}
		
		
		
		this.conditionObjectType = introspectedTable.getBaseConditionType();
		this.conditionObjectShortType = introspectedTable.getConditioObjectName();
		this.conditionObjectName = conditionObjectShortType.substring(0,1).toLowerCase() + conditionObjectShortType.substring(1);
		
		javaVoGeneratorConfig voconfig = context.getVoGeneratorConfig();
		if(voconfig != null){
			this.setVoObjectName(voconfig.getLowShortClassName(introspectedTable));
			this.setVoObjectType(voconfig.getFullClassType(introspectedTable));
			idx = this.voObjectType.lastIndexOf(".");
			this.voObjectShortType = this.voObjectType.substring(idx+1);
		}
		
		javaServiceGeneratorConfig servconfig = context.getServiceGeneratorConfig();
		if(servconfig != null){
			this.setServiceInterfaceName(servconfig.getLowShortClassName(introspectedTable));
			this.setServiceInterfaceType(servconfig.getFullClassType(introspectedTable));
		}
		
		JavaVOAdapterGeneratorConfig voAdpConfig = context.getVoAdapterGeneratorConfig();
		if(voAdpConfig != null){
		    this.voAdapterInterfaceName = voAdpConfig.getLowShortClassName(introspectedTable);
		    this.voAdapterInterfaceType = voAdpConfig.getFullClassType(introspectedTable);
		}
		
		javaFacadeGeneratorConfig faconfig = context.getFacadeGeneratorConfig();
		if(faconfig != null){
			this.facadeInterfaceName = faconfig.getLowShortClassName(introspectedTable);
			this.facadeInterfaceType = faconfig.getFullClassType(introspectedTable);
		}
		
		JavaDTOParamGeneratorConfig paramCfg = context.getDtoParamGeneratorConfig();
        if(paramCfg != null){
            this.paramObjectName = paramCfg.getLowShortClassName(introspectedTable);
            this.paramObjectType = paramCfg.getFullClassType(introspectedTable);
        }
		
		
		String domain = introspectedTable.getFullyQualifiedTable().getDomainObjectName();
		String prefix = domain.substring(0,1);
		String subfix = domain.substring(1);
		String url = prefix.toLowerCase() + subfix;
		this.componentUrl =  "/" + url + "/";
		
		this.modelProList = new ArrayList<PropertyParam>();
		
		List<IntrospectedColumn> allCols = introspectedTable.getAllColumns();
		for(IntrospectedColumn col : allCols){
			if(context.getIgnoreColumnList().isColumnIgnored(col.getActualColumnName())){
				continue;
			}
			 
			 org.mybatis.generator.api.dom.java.Field fd = getJavaBeansField(col, context, introspectedTable);
			 Method getMethod = getJavaBeansGetter(col, context, introspectedTable);
			 Method setMethod = getJavaBeansSetter(col, context, introspectedTable);
			 
			 PropertyParam pp = new PropertyParam();
			 pp.setFieldName(col.getJavaProperty());
			 pp.setRemark(col.getRemarks());
			 pp.setGetterName(getMethod.getName());
			 pp.setSetterName(setMethod.getName());
			 pp.setType(fd.getType().toString());
			 
			 this.modelProList.add(pp);
		}
		
		this.condProList = new ArrayList<PropertyParam>();
		
		for(IntrospectedColumn col : allCols){
			if(context.getIgnoreColumnList().isColumnIgnored(col.getActualColumnName())){
				continue;
			}
			 
			 String listName = col.getJavaPropertySubfix("List");
			 FullyQualifiedJavaType dtype = col.getFullyQualifiedJavaType();
			 String listType = "List&lt;" +dtype.getFullTypeSpecification() + "&gt;";
			 org.mybatis.generator.api.dom.java.Field listfd = JavaBeansUtil.getJavaBeanField(col, listName, listType);
			 
			 Method set = JavaBeansUtil.getJavaBeansSetter(col, listName, listType);
			 Method get = JavaBeansUtil.getJavaBeansGetter(col, listName, listType);
		     Method add = JavaBeansUtil.getJavaBeansAdder(col, listName, col.getJavaProperty());
		    	
			 PropertyParam lp = new PropertyParam();
			 lp.setFieldName(listName);
			 lp.setType(listfd.getType().toString());
			 lp.setRemark(col.getRemarks() + "List");
			 lp.setGetterName(get.getName());
			 lp.setSetterName(set.getName());
			 lp.setAddName(add.getName());
			 
			 this.condProList.add(lp);
			 
		}
		
	}
	
	public VelocityContext toVelocityContext(){
		VelocityContext context = new VelocityContext();
		Field[] farr = MethodParam.class.getDeclaredFields();
		for(Field f : farr){
			f.setAccessible(true);
			String name = f.getName();
			

			try{
				Object value = f.get(this);
				context.put(name, value);
				//TODO debug
				//System.out.println(name + " : " + value.toString());
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
		}
		
		return context;
	}

	public String getDomainObjectName() {
		return domainObjectName;
	}

	public void setDomainObjectName(String domainObjectName) {
		this.domainObjectName = domainObjectName;
	}

	public String getJavaMapperName() {
		return javaMapperName;
	}

	public void setJavaMapperName(String javaMapperName) {
		this.javaMapperName = javaMapperName;
	}

	public String getBaseRecordType() {
		return baseRecordType;
	}

	public void setBaseRecordType(String baseRecordType) {
		this.baseRecordType = baseRecordType;
	}

	public String getVoObjectName() {
		return voObjectName;
	}

	public void setVoObjectName(String voObjectName) {
		this.voObjectName = voObjectName;
	}

	public String getServiceInterfaceName() {
		return serviceInterfaceName;
	}

	public void setServiceInterfaceName(String serviceInterfaceName) {
		this.serviceInterfaceName = serviceInterfaceName;
	}

	public String getVoObjectType() {
		return voObjectType;
	}

	public void setVoObjectType(String voObjectType) {
		this.voObjectType = voObjectType;
	}

	public String getServiceInterfaceType() {
		return serviceInterfaceType;
	}

	public void setServiceInterfaceType(String serviceInterfaceType) {
		this.serviceInterfaceType = serviceInterfaceType;
	}

	public String getVoObjectShortType() {
		return voObjectShortType;
	}

	public void setVoObjectShortType(String voObjectShortType) {
		this.voObjectShortType = voObjectShortType;
	}

	public String getDomainObjectShortType() {
		return domainObjectShortType;
	}

	public void setDomainObjectShortType(String domainObjectShortType) {
		this.domainObjectShortType = domainObjectShortType;
	}

	public String getFacadeInterfaceName() {
		return facadeInterfaceName;
	}

	public void setFacadeInterfaceName(String facadeInterfaceName) {
		this.facadeInterfaceName = facadeInterfaceName;
	}

	public String getFacadeInterfaceType() {
		return facadeInterfaceType;
	}

	public void setFacadeInterfaceType(String facadeInterfaceType) {
		this.facadeInterfaceType = facadeInterfaceType;
	}

	public String getComponentUrl() {
		return componentUrl;
	}

	public void setComponentUrl(String componentUrl) {
		this.componentUrl = componentUrl;
	}

	

	public List<PropertyParam> getModelProList() {
		return modelProList;
	}

	public void setModelProList(List<PropertyParam> modelProList) {
		this.modelProList = modelProList;
	}

	public String getConditionObjectName() {
		return conditionObjectName;
	}

	public void setConditionObjectName(String conditionObjectName) {
		this.conditionObjectName = conditionObjectName;
	}

	public String getConditionObjectType() {
		return conditionObjectType;
	}

	public void setConditionObjectType(String conditionObjectType) {
		this.conditionObjectType = conditionObjectType;
	}

	public String getConditionObjectShortType() {
		return conditionObjectShortType;
	}

	public void setConditionObjectShortType(String conditionObjectShortType) {
		this.conditionObjectShortType = conditionObjectShortType;
	}

	public List<PropertyParam> getCondProList() {
		return condProList;
	}

	public void setCondProList(List<PropertyParam> condProList) {
		this.condProList = condProList;
	}

	public boolean isSecurity() {
		return isSecurity;
	}

	public void setSecurity(boolean isSecurity) {
		this.isSecurity = isSecurity;
	}

    public String getVoAdapterInterfaceName() {
        return voAdapterInterfaceName;
    }

    public void setVoAdapterInterfaceName(String voAdapterInterfaceName) {
        this.voAdapterInterfaceName = voAdapterInterfaceName;
    }

    public String getVoAdapterInterfaceType() {
        return voAdapterInterfaceType;
    }

    public void setVoAdapterInterfaceType(String voAdapterInterfaceType) {
        this.voAdapterInterfaceType = voAdapterInterfaceType;
    }

    public String getPlanObjectName() {
        return planObjectName;
    }

    public void setPlanObjectName(String planObjectName) {
        this.planObjectName = planObjectName;
    }

    public String getPlanObjectType() {
        return planObjectType;
    }

    public void setPlanObjectType(String planObjectType) {
        this.planObjectType = planObjectType;
    }

    public String getPlanObjectShortType() {
        return planObjectShortType;
    }

    public void setPlanObjectShortType(String planObjectShortType) {
        this.planObjectShortType = planObjectShortType;
    }

    public String getParamObjectName() {
        return paramObjectName;
    }

    public void setParamObjectName(String paramObjectName) {
        this.paramObjectName = paramObjectName;
    }

    public String getParamObjectType() {
        return paramObjectType;
    }

    public void setParamObjectType(String paramObjectType) {
        this.paramObjectType = paramObjectType;
    }

    public String getJavaExtMapperName() {
        return javaExtMapperName;
    }

    public void setJavaExtMapperName(String javaExtMapperName) {
        this.javaExtMapperName = javaExtMapperName;
    }

  
	
	
	
}
