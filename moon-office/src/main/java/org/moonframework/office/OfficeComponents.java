package org.moonframework.office;

import org.moonframework.office.excel.ExcelComponent;
import org.moonframework.office.excel.ExcelResultEntity;

import java.io.File;


/**
 * static factory
 * <p>
 * <p>
 * 静态工厂
 * </p>
 *
 * @author quzile
 */
public class OfficeComponents {

    public static <T> ExcelResultEntity<T> excelReadForList(File file,
                                                            ExcelComponent<T> excel) {
        return excel.readForList(file);
    }

    public static <T> ExcelResultEntity<T> excelReadForList(String filename,
                                                            ExcelComponent<T> excel) {
        return excelReadForList(new File(filename), excel);
    }

}
