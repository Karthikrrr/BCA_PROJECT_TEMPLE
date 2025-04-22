package com.business_website.service_implementation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.business_website.models.PlacementDetails;
import com.business_website.services.ExcelService;

@Service
public class ExcelServiceImplementation implements ExcelService{

    public static String[] HEADERS = {
        "id",
        "Name",
        "Email",
        "Experience",
        "Organization Worked",
        "Phone Number",
        "Qulification",
        "Resume By"
    };

    public static String SHEET_NAME = "Placements Details";


    @Override
    public ByteArrayInputStream placementDetailsToExcel(List<PlacementDetails> placementDetails) throws IOException {
         //Create Book
         Workbook workbook = new XSSFWorkbook();
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         try {
            //Create Sheets
            Sheet sheet =  workbook.createSheet(SHEET_NAME);
            //Create Row
            Row row = sheet.createRow(0);
            for(int i = 0; i < HEADERS.length; i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }
            //Row Value
            int rowIndex = 1;
            for(PlacementDetails placementDetail : placementDetails){
                Row rowdata = sheet.createRow(rowIndex);
                rowIndex++;

                rowdata.createCell(0).setCellValue(placementDetail.getId());
                rowdata.createCell(1).setCellValue(placementDetail.getName());
                rowdata.createCell(2).setCellValue(placementDetail.getEmail());
                rowdata.createCell(3).setCellValue(placementDetail.getExperience());
                rowdata.createCell(4).setCellValue(placementDetail.getOrganizationWorked());
                rowdata.createCell(5).setCellValue(placementDetail.getPhoneNo());
                rowdata.createCell(6).setCellValue(placementDetail.getQualification());
                rowdata.createCell(7).setCellValue(placementDetail.getResume());
            }
                workbook.write(out);


            return new ByteArrayInputStream(out.toByteArray());  
        } catch (IOException e) {
            return null;
        } finally {
            workbook.close();
            out.close();
        }
    }

}
