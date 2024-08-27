package com.khaledmosharraf.twtms.commerz;

import java.time.Instant;

import java.util.UUID;

public class TransactionIdGenerator {
    public String generateTransactionId() {
        return UUID.randomUUID().toString();
    }
}
