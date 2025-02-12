package com.yers.pandev_tech_task.service;

import com.yers.pandev_tech_task.model.Category;
import com.yers.pandev_tech_task.repository.CategoryRepository;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Сервис для экспорта и импорта категорий в формате Excel.
 */
@Service
@RequiredArgsConstructor
public class ExcelService {
    private final CategoryRepository categoryRepository;
    private static final String DIRECTORY_PATH = "downloads/";
    private static final String FILE_PATH = DIRECTORY_PATH + "categories.xlsx";

    /**
     * Экспортирует все категории в Excel-файл.
     *
     * @return Файл с экспортированными категориями или null, если данных нет.
     */
    public File exportCategoriesToExcel() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            return null;  // Если нет данных, файл не создаётся
        }

        // 🔹 Создаём папку, если её нет
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Категории");

        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);
        headerRow.createCell(0).setCellValue("Категория");
        headerRow.createCell(1).setCellValue("Родитель");

        // Заполнение файла категориями
        for (Category category : categories) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(category.getName());
            row.createCell(1).setCellValue(category.getParent() != null ? category.getParent().getName() : "—");
        }

        File file = new File(FILE_PATH);
        try (FileOutputStream fileOut = new FileOutputStream(file)) {
            workbook.write(fileOut);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }

    /**
     * Импортирует категории из переданного Excel-файла.
     *
     * @param inputStream Входной поток файла Excel.
     * @return Сообщение о результате импорта.
     */
    public String importCategoriesFromExcel(InputStream inputStream) {
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Category> categoryMap = new HashMap<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Пропускаем заголовок

                String categoryName = row.getCell(0).getStringCellValue().trim();
                String parentName = row.getCell(1).getStringCellValue().trim();

                Category parent = categoryRepository.findByName(parentName).orElse(null);
                if (parentName.equals("—")) {
                    parent = null;
                }

                if (!categoryRepository.existsByName(categoryName)) {

                    Category category = new Category();
                    category.setName(categoryName);
                    category.setParent(parent);
                    categoryRepository.save(category);
                }
            }
            return TextsHelperUtil.fileSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            return TextsHelperUtil.fileError();
        }
    }
}