package com.khaledmosharraf.twtms.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface PdfService {
    ByteArrayInputStream generatePdf(String htmlContent) throws IOException;
}
