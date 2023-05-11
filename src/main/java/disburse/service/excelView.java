package disburse.service;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class excelView  extends AbstractXlsView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Sheet incidents = workbook.createSheet("incidents");

        Map<String, Object> map = (Map<String, Object>) model.get("map");
        response.setContentType("application/ms-excel");
        response.setHeader("Content-disposition", "attachments;filename=incidents.xlsx");
    }
}
