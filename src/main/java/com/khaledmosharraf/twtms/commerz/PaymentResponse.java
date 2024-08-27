package com.khaledmosharraf.twtms.commerz;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentResponse {

    @JsonProperty("tran_id")
    private String tranId;

    @JsonProperty("val_id")
    private String valId;

    @JsonProperty("amount")
    private String amount;

    @JsonProperty("card_type")
    private String cardType;

    @JsonProperty("store_amount")
    private String storeAmount;

    @JsonProperty("card_no")
    private String cardNo;

    @JsonProperty("bank_tran_id")
    private String bankTranId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tran_date")
    private String tranDate;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("card_issuer")
    private String cardIssuer;

    @JsonProperty("card_brand")
    private String cardBrand;

    @JsonProperty("card_issuer_country")
    private String cardIssuerCountry;

    @JsonProperty("card_issuer_country_code")
    private String cardIssuerCountryCode;

    @JsonProperty("store_id")
    private String storeId;

    @JsonProperty("verify_sign")
    private String verifySign;

    @JsonProperty("verify_key")
    private String verifyKey;

    @JsonProperty("verify_sign_sha2")
    private String verifySignSha2;

    @JsonProperty("currency_type")
    private String currencyType;

    @JsonProperty("currency_amount")
    private String currencyAmount;

    @JsonProperty("currency_rate")
    private String currencyRate;

    @JsonProperty("base_fair")
    private String baseFair;

    @JsonProperty("value_a")
    private String valueA;

    @JsonProperty("value_b")
    private String valueB;

    @JsonProperty("value_c")
    private String valueC;

    @JsonProperty("value_d")
    private String valueD;

    @JsonProperty("risk_level")
    private String riskLevel;

    @JsonProperty("risk_title")
    private String riskTitle;

    // Getters and Setters

    public String getTranId() {
        return tranId;
    }

    public void setTranId(String tranId) {
        this.tranId = tranId;
    }

    public String getValId() {
        return valId;
    }

    public void setValId(String valId) {
        this.valId = valId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(String storeAmount) {
        this.storeAmount = storeAmount;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankTranId() {
        return bankTranId;
    }

    public void setBankTranId(String bankTranId) {
        this.bankTranId = bankTranId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardIssuer() {
        return cardIssuer;
    }

    public void setCardIssuer(String cardIssuer) {
        this.cardIssuer = cardIssuer;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getCardIssuerCountry() {
        return cardIssuerCountry;
    }

    public void setCardIssuerCountry(String cardIssuerCountry) {
        this.cardIssuerCountry = cardIssuerCountry;
    }

    public String getCardIssuerCountryCode() {
        return cardIssuerCountryCode;
    }

    public void setCardIssuerCountryCode(String cardIssuerCountryCode) {
        this.cardIssuerCountryCode = cardIssuerCountryCode;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getVerifySign() {
        return verifySign;
    }

    public void setVerifySign(String verifySign) {
        this.verifySign = verifySign;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public String getVerifySignSha2() {
        return verifySignSha2;
    }

    public void setVerifySignSha2(String verifySignSha2) {
        this.verifySignSha2 = verifySignSha2;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(String currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(String currencyRate) {
        this.currencyRate = currencyRate;
    }

    public String getBaseFair() {
        return baseFair;
    }

    public void setBaseFair(String baseFair) {
        this.baseFair = baseFair;
    }

    public String getValueA() {
        return valueA;
    }

    public void setValueA(String valueA) {
        this.valueA = valueA;
    }

    public String getValueB() {
        return valueB;
    }

    public void setValueB(String valueB) {
        this.valueB = valueB;
    }

    public String getValueC() {
        return valueC;
    }

    public void setValueC(String valueC) {
        this.valueC = valueC;
    }

    public String getValueD() {
        return valueD;
    }

    public void setValueD(String valueD) {
        this.valueD = valueD;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRiskTitle() {
        return riskTitle;
    }

    public void setRiskTitle(String riskTitle) {
        this.riskTitle = riskTitle;
    }
}
