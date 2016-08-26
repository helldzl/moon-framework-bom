package org.moonframework.office.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.moonframework.office.AbstractOfficeComponent;
import org.moonframework.office.PersistenceComponent;

import java.io.*;
import java.util.*;


/**
 * excel产品的核心逻辑处理组件
 *
 * @author quzile
 */
public abstract class ExcelComponent<T> extends AbstractOfficeComponent<T>
        implements PersistenceComponent<T> {

    public static Byte byteValue(Cell cell) {
        Byte value = null;

        try {
            value = doubleValue(cell).byteValue();
        } catch (Exception e) {
        }

        return value;
    }

    public static Short shortValue(Cell cell) {
        Short value = null;

        try {
            value = doubleValue(cell).shortValue();
        } catch (Exception e) {
        }

        return value;
    }

    public static Integer intValue(Cell cell) {
        Integer value = null;

        try {
            value = doubleValue(cell).intValue();
        } catch (Exception e) {
        }

        return value;
    }

    public static Long longValue(Cell cell) {
        Long value = null;

        try {
            value = doubleValue(cell).longValue();
        } catch (Exception e) {
        }

        return value;
    }

    public static Float floatValue(Cell cell) {
        Float value = null;

        try {
            value = doubleValue(cell).floatValue();
        } catch (Exception e) {
        }

        return value;
    }

    public static Double doubleValue(Cell cell) {
        Double value = null;

        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = Double.valueOf(cell.getRichStringCellValue()
                            .getString());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (!DateUtil.isCellDateFormatted(cell))
                        value = cell.getNumericCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue() ? 1D : 0D;
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    break;
                default:
            }
        } catch (Exception e) {
        }

        return value;
    }

    public static Date dateValue(Cell cell) {
        Date value = null;

        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = DATE.parse(cell.getRichStringCellValue().getString());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell))
                        value = cell.getDateCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    break;
                default:
            }
        } catch (Exception e) {
        }

        return value;
    }

    public static String stringValue(Cell cell) {
        String value = null;

        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell))
                        value = DATE.format(cell.getDateCellValue());
                    else
                        value = String.valueOf(cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = cell.getCellFormula();
                    break;
                default:
            }
        } catch (Exception e) {
        }

        return value;
    }

    public static Boolean booleanValue(Cell cell) {
        Boolean value = null;

        try {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = Boolean.valueOf(cell.getRichStringCellValue()
                            .getString());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (!DateUtil.isCellDateFormatted(cell))
                        value = cell.getNumericCellValue() == 0D ? false : true;
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    break;
                default:
            }
        } catch (Exception e) {
        }

        return value;
    }

    public ExcelComponent() {
        this(false);
    }

    public ExcelComponent(boolean rewrite) {
        this(rewrite, DataGroup.ROW);
    }

    public ExcelComponent(boolean rewrite, DataGroup dataGroup) {
        this(rewrite, dataGroup, 1, 0);
    }

    /**
     * @param rewrite          是否重写
     * @param dataGroup        分组策略
     * @param startRowIndex    起始行
     * @param startColumnIndex 起始列
     */
    public ExcelComponent(boolean rewrite, DataGroup dataGroup,
                          int startRowIndex, int startColumnIndex) {
        super(rewrite);
        this.dataGroup = dataGroup == null ? DataGroup.ROW : dataGroup;
        this.startRowIndex = startRowIndex;
        this.startColumnIndex = startColumnIndex;
        this.dataMap = dataMap();
    }

    @Override
    public ExcelResultEntity<T> readForList(File file) {
        try {
            List<T> list = new ArrayList<T>();
            Map<T, EntityInfo> entities = new HashMap<T, EntityInfo>();

            InputStream is = new FileInputStream(file);
            Workbook wb = WorkbookFactory.create(is);
            doWorkbook(wb, list, entities);

            // get default style and set failed message
            CellStyle style = defaultStyle(wb);
            int correctNum = 0;
            for (T entity : list) {

                try {
                    persist(entity);
                    correctNum += 1;
                } catch (Exception e) {
                    Cell cell = entities.get(entity).msgCell();
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellStyle(style);
                        cell.setCellValue(e.getMessage());
                    }
                }
            }

            // rewrite
            if (isRewrite())
                write(wb, file);
            ExcelResultEntity<T> resultEntity = new ExcelResultEntity<T>(list,
                    correctNum);
            return resultEntity;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not exists error! file:"
                    + file.getAbsolutePath());
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 默认消息样式
     *
     * @param wb
     * @return
     */
    protected CellStyle defaultStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setFillForegroundColor(HSSFColor.YELLOW.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        Font font = wb.createFont();
        font.setColor(HSSFColor.RED.index);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        return style;
    }

    /**
     * @param wb
     * @param filename
     */
    protected void write(Workbook wb, String filename) {
        write(wb, new File(filename));
    }

    /**
     * @param wb
     * @param file
     */
    protected void write(Workbook wb, File file) {
        FileOutputStream fos = null;
        try {
            try {
                fos = new FileOutputStream(file);
                wb.write(fos);
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }
        } catch (Exception e) {
        }
    }

    /**
     * 循环工作区
     *
     * @param wb
     * @param list
     * @param entities
     */
    protected void doWorkbook(Workbook wb, List<T> list,
                              Map<T, EntityInfo> entities) {
        for (int i = 0, numberOfSheets = wb.getNumberOfSheets(); i < numberOfSheets; i++)
            doSheet(wb.getSheetAt(i), list, entities);
    }

    /**
     * 单工作区的读取方式处理
     *
     * @param sheet
     * @param list
     * @param entities
     */
    protected void doSheet(Sheet sheet, List<T> list,
                           Map<T, EntityInfo> entities) {
        // 每个工作区都有一个独立的实体映射集合
        Map<Integer, T> map = new HashMap<Integer, T>();
        for (Row row : sheet) {
            if (row.getRowNum() < startRowIndex)
                continue;
            for (Cell cell : row) {
                if (cell.getColumnIndex() < startColumnIndex)
                    continue;

                int rowIndex = cell.getRowIndex();
                int columnIndex = cell.getColumnIndex();

                // 按行分组或按列分组处理数据
                boolean isRow = DataGroup.ROW == dataGroup;
                int key = isRow ? rowIndex : columnIndex;
                int dataIndex = isRow ? columnIndex : rowIndex;

                if (!map.containsKey(key)) {
                    T entity = newEntity();
                    map.put(key, entity);
                    list.add(map.get(key));
                    entities.put(entity, new EntityInfo(entity, sheet, key));
                }

                CellReference cellRef = new CellReference(row.getRowNum(),
                        cell.getColumnIndex());
                doCell(row, cell, cellRef, map.get(key), dataIndex);
            }
        }
    }

    /**
     * Excel最小单元处理
     *
     * @param row
     * @param cell
     * @param cellRef
     * @param t         实体
     * @param dataIndex 实体数据在excel文件中的索引值, 如果是DataGroup.ROW分组, 该值是rowIndex,
     *                  如果是DataGroup.COLUMN分组, 该值是columnIndex
     */
    protected void doCell(Row row, Cell cell, CellReference cellRef, T t,
                          int dataIndex) {

        String format = "row is [%s] and column is [%s], value is [%s]";
        if (cell != null) {
            String info = String.format(format, cell.getRowIndex(),
                    cell.getColumnIndex(), cell);
            logger.debug(info);
        }

        try {
            readCell(row, cell, cellRef, t, dataIndex);
            // writeCell(row, cell, cellRef, t, dataIndex);
        } catch (Exception e) {
            if (cell != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug(String.format(
                            "Row [%s] and column [%s] is ignored!",
                            cell.getRowIndex(), cell.getColumnIndex()));
                }
            }

        }

    }

    ;

    /**
     * 读取单元数据, 处理Excel中元素与实体的映射关系
     *
     * @param row
     * @param cell
     * @param cellRef
     * @param t
     * @param dataIndex
     * @return
     */
    protected void readCell(Row row, Cell cell, CellReference cellRef, T t,
                            int dataIndex) {
        if (dataMap != null && dataMap.containsKey(dataIndex))
            dataMap.get(dataIndex).value(row, cell, cellRef, t);
    }

    /**
     * 如果readCell返回true, 则给用户一个机会来更新单元元素, 校验cell
     * <p>
     * 保留方法, 日后扩展用
     *
     * @param row
     * @param cell
     * @param cellRef
     * @param t
     * @param dataIndex
     */
    @Deprecated
    protected void writeCell(Row row, Cell cell, CellReference cellRef, T t,
                             int dataIndex) {
    }

    abstract protected Map<Integer, ExcelEntityPolicy<T>> dataMap();

    /**
     * 数据处理方式默认按行分组
     */
    private final DataGroup dataGroup;

    /**
     * 从第几行开始区数据
     */
    private final int startRowIndex;

    /**
     * 从第几列开始取数据
     */
    private final int startColumnIndex;

    /**
     * 数据映射
     */
    private Map<Integer, ExcelEntityPolicy<T>> dataMap;

    /**
     * @author quzile
     */
    protected class EntityInfo {

        /**
         *
         */
        private T entity;

        /**
         * 实体所在工作区
         */
        private Sheet sheet;

        /**
         *
         */
        private int lastRowNum;

        /**
         *
         */
        private int key;

        /**
         *
         */
        private Cell cell = null;

        protected EntityInfo(T entity, Sheet sheet, int key) {
            super();
            this.entity = entity;
            this.sheet = sheet;
            this.key = key;
            this.lastRowNum = sheet.getLastRowNum();
        }

        public Cell msgCell() {
            if (cell == null) {
                if (DataGroup.ROW == dataGroup) {
                    Row row = sheet.getRow(key);
                    cell = row.createCell(row.getLastCellNum());
                } else {
                    Row row = null;
                    row = sheet.getRow(lastRowNum + 1);
                    if (row == null) {
                        row = sheet.createRow(lastRowNum + 1);
                    }
                    cell = row.createCell(key);
                }
            }
            return cell;
        }

        public T getEntity() {
            return entity;
        }

        public void setEntity(T entity) {
            this.entity = entity;
        }

        public Sheet getSheet() {
            return sheet;
        }

        public void setSheet(Sheet sheet) {
            this.sheet = sheet;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

    }

}
