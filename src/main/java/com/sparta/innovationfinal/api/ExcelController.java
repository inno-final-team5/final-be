package com.sparta.innovationfinal.api;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class ExcelController {
    private final BoxOfficeRepository boxOfficeRepository;

    @GetMapping("/excel")
    public String main() { // 1
        return "excel";
    }

    // 엑셀불러오기
    @PostMapping("/excel/read")
    public String readExcel(@RequestParam("file") MultipartFile file)
            throws IOException { // 2


        String extension = FilenameUtils.getExtension(file.getOriginalFilename()); // 3

        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        Workbook workbook = null;

        if (extension.equals("xlsx")) {
            workbook = new XSSFWorkbook(file.getInputStream());
        } else if (extension.equals("xls")) {
            workbook = new HSSFWorkbook(file.getInputStream());
        }

        Sheet worksheet = workbook.getSheetAt(0);

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) { // 4

            Row row = worksheet.getRow(i);

            ExcelData data = new ExcelData();

            data.setRanking((int)row.getCell(0).getNumericCellValue());
            data.setMovieId((int)row.getCell(1).getNumericCellValue());
            data.setTitle(row.getCell(2).getStringCellValue());
            data.setTag(row.getCell(3).getStringCellValue());
            data.setPoster_path(row.getCell(4).getStringCellValue());

            boxOfficeRepository.save(data);
        }

//        model.addAttribute("datas", dataList); // 5

        return "excelList";

    }
}
