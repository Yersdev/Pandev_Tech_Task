package com.yers.pandev_tech_task.service;

import com.yers.pandev_tech_task.model.Category;
import com.yers.pandev_tech_task.repository.CategoryRepository;
import com.yers.pandev_tech_task.util.TextsHelperUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.*;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ExcelServiceTests {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ExcelService excelService;

    private Category rootCategory;

    private Category childCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        rootCategory = new Category(1L, "Movies", null, List.of());
        childCategory = new Category(2L, "Action", rootCategory, List.of());

        when(categoryRepository.findAll()).thenReturn(List.of(rootCategory, childCategory));
        when(categoryRepository.findByName("Movies")).thenReturn(Optional.of(rootCategory));
        when(categoryRepository.findByName("Action")).thenReturn(Optional.of(childCategory));
        when(categoryRepository.existsByName("Movies")).thenReturn(true);
        when(categoryRepository.existsByName("Action")).thenReturn(true);
    }

    @Test
    @DisplayName("Экспорт категорий в Excel")
    void testExportCategoriesToExcel() throws IOException {
        File file = excelService.exportCategoriesToExcel();
        assertThat(file).isNotNull();
        assertThat(file.exists()).isTrue();
        assertThat(file.length()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Экспорт пустого списка категорий")
    void testExportEmptyCategoriesToExcel() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        File file = excelService.exportCategoriesToExcel();

        assertThat(file).isNull();
    }

    @Test
    @DisplayName("Импорт категорий из Excel")
    void testImportCategoriesFromExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet("Категории");
        var headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Категория");
        headerRow.createCell(1).setCellValue("Родитель");

        var row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("Comedy");
        row1.createCell(1).setCellValue("Movies");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        when(categoryRepository.existsByName("Comedy")).thenReturn(false);
        when(categoryRepository.findByName("Movies")).thenReturn(Optional.of(rootCategory));

        String result = excelService.importCategoriesFromExcel(inputStream);

        assertThat(result).isEqualTo(TextsHelperUtil.fileSuccess());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("Импорт категории, которая уже существует")
    void testImportExistingCategory() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet("Категории");
        var row = sheet.createRow(1);
        row.createCell(0).setCellValue("Movies");
        row.createCell(1).setCellValue("—");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        when(categoryRepository.existsByName("Movies")).thenReturn(true);

        String result = excelService.importCategoriesFromExcel(inputStream);

        assertThat(result).isEqualTo(TextsHelperUtil.fileSuccess());
        verify(categoryRepository, never()).save(any(Category.class));
    }
}
