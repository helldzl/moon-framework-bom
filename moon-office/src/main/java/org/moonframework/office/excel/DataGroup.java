package org.moonframework.office.excel;

/**
 * <p>
 * Excel文件处理的方式, ROW的话按行分组, COLUMN的话是按列分组处理数据。分组表示一行或一列应该是一组数据记录, 会被映射到实体类当中
 * </p>
 * 
 * @author quzile
 *
 */
public enum DataGroup {

	/**
	 * 数据按行分组
	 */
	ROW,

	/**
	 * 数据按列分组
	 */
	COLUMN;

	private DataGroup() {
	}

}
