package org.mybatis.generator.config.method;

public class PropertyParam {

	private String fieldName;
	
	private String getterName;
	
	private String setterName;
	
	private String remark;
	
	private String type;
	
	private String addName;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getGetterName() {
		return getterName;
	}

	public void setGetterName(String getterName) {
		this.getterName = getterName;
	}

	public String getSetterName() {
		return setterName;
	}

	public void setSetterName(String setterName) {
		this.setterName = setterName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public String toString(){
		String str = "";
		str += "fieldname:";
		str += this.fieldName;
		str += ",type:";
		str += this.type;
		str += ", get:";
		str += this.getterName;
		str += ",set:";
		str += this.setterName;
		str += ",add";
		str += this.addName;
		
		return str;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}
	
	
}
