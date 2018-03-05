package ${basePackage}.entity;

<#list imports as line >
import ${line};
</#list>
import org.dazao.easyjdbc.entity.BaseEntity;

<#if needComment && (classComment!'')?trim != ''>
/**
 * ${classComment} 
 *
 * @since ${.now} 
 */
</#if>
public class ${className}  extends BaseEntity{

<#list columns as col>
	private ${col.type} ${col.field};
</#list>
<#list columns as col>

	<#if needComment && (col.comment!'')?trim != ''>
	/**
     * ${col.comment}
     */
	</#if>
	public ${col.type} get${col.field?cap_first}() {
        return this.${col.field};
    }

    public void set${col.field?cap_first}(${col.type} ${col.field}) {
        this.${col.field} = ${col.field};
    }
</#list>
}