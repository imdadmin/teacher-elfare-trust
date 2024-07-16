package com.khaledmosharraf.twtms.controller;
import com.lowagie.text.pdf.BaseFont;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.OutputStream;

@Controller
public class PdfTwoController {

    private final TemplateEngine templateEngine;

    public PdfTwoController(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @GetMapping("/generatetwo")
    private void printBanglaPdf(HttpServletResponse response) throws Throwable {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=bangla_pdf_report.pdf";
        response.setHeader(headerKey, headerValue);
        Context context = new Context();
        context.setVariable("banglaWord", "মোট উপার্জন");
        String processHTML = templateEngine.process("adminPanel/report/templatetwo", context);
        ServletOutputStream outputStream = response.getOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver resolver = renderer.getFontResolver();
      //  resolver.addFont("C:\\kalpurush.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        resolver.addFont(new ClassPathResource("static/fonts/Nikosh.ttf").getURL().toString(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        renderer.setDocumentFromString(processHTML);
        renderer.layout();
        renderer.createPDF(outputStream, false);
        renderer.finishPDF();
        outputStream.close();
    }
}
