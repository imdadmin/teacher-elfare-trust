package com.khaledmosharraf.twtms.service.impl;

import com.khaledmosharraf.twtms.service.PdfService;

import com.lowagie.text.pdf.BaseFont;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public ByteArrayInputStream generatePdf(String htmlContent) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();

        // Load and register the Bangla font
        String pathString = new ClassPathResource("static/fonts/kalpurush.ttf").getURL().toString();
        renderer.getFontResolver().addFont(pathString, BaseFont.IDENTITY_V, BaseFont.NOT_EMBEDDED);

      //  renderer.getFontResolver().addFont(pathString, true);
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(baos);
        baos.close();

        return new ByteArrayInputStream(baos.toByteArray());
    }
}

