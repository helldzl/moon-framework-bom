package org.moonframework.office.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;

/**
 * Excel单元到实体的映射策略
 * 
 * 
 * @author quzile
 * 
 * @param <T>
 */
public interface ExcelEntityPolicy<T> {

	/**
	 * 
	 * excel中单元格数据下标(从0开始)到java实体的映射关系
	 * 
	 * @param row
	 * @param cell
	 * @param cellRef
	 * @param entity
	 */
	void value(Row row, Cell cell, CellReference cellRef, T entity);

}
