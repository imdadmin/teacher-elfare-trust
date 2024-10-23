package com.khaledmosharraf.twtms.sbl.Controller;

import java.util.UUID;

public class InvoiceNoGenerator {
    public String generateInvoiceNo() {
        return UUID.randomUUID().toString();
    }
}
