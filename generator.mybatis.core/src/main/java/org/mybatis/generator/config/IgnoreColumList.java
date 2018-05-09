package org.mybatis.generator.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.dom.xml.XmlElement;

public class IgnoreColumList  extends PropertyHolder {

	 /** The ignored columns. */
    private Map<IgnoredColumn, Boolean> ignoredColumns;
    
    public IgnoreColumList(){
    	super();
    	ignoredColumns = new HashMap<IgnoredColumn, Boolean>();
    }
    
    public XmlElement toXmlElement() {
        XmlElement answer = new XmlElement("ignoreColumns"); //$NON-NLS-1$
        
        if (ignoredColumns.size() > 0) {
            for (IgnoredColumn ignoredColumn : ignoredColumns.keySet()) {
            	answer.addElement(ignoredColumn.toXmlElement());
            }
        }
        
        addPropertyXmlElements(answer);

        return answer;
    }
    
    public void validate(List<String> errors, String contextId) {
    	
    }
    
    /**
     * Checks if is column ignored.
     *
     * @param columnName
     *            the column name
     * @return true, if is column ignored
     */
    public boolean isColumnIgnored(String columnName) {
        for (Map.Entry<IgnoredColumn, Boolean> entry : ignoredColumns
                .entrySet()) {
            if (entry.getKey().matches(columnName)) {
                entry.setValue(Boolean.TRUE);
                return true;
            }
        }
        /*
        for (IgnoredColumnPattern ignoredColumnPattern : ignoredColumnPatterns) {
            if (ignoredColumnPattern.matches(columnName)) {
                return true;
            }
        }
        */

        return false;
    }
    
    /**
     * Adds the ignored column.
     *
     * @param ignoredColumn
     *            the ignored column
     */
    public void addIgnoredColumn(IgnoredColumn ignoredColumn) {
        ignoredColumns.put(ignoredColumn, Boolean.FALSE);
    }
}
