package org.moonframework.office;

import java.io.File;

/**
 * office组件接口
 * 
 * @author quzile
 * 
 */
public interface OfficeComponent<T> {

	/**
	 * 创建组件
	 * 
	 * @param file
	 */
	void create(File file);

	/**
	 * 读取组件
	 * 
	 * @param file
	 *            文件目录
	 * @return 结果实体对象
	 */
	ResultEntity<T> readForList(File file);

}
