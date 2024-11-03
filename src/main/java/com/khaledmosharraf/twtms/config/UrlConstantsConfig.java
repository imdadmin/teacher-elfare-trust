package com.khaledmosharraf.twtms.config;

import com.khaledmosharraf.twtms.utils.UrlConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlConstantsConfig {
    @Bean(name = "urlConstants")
    public UrlConstantsWrapper urlConstants() {
        return new UrlConstantsWrapper();
    }

    public static class UrlConstantsWrapper {


        public final UrlConstants.Common common= new UrlConstants.Common();

        public final UrlConstants.User user = new UrlConstants.User();
        public final UrlConstants.SubscriptionPayment subscriptionPayment = new UrlConstants.SubscriptionPayment();
        public final UrlConstants.Grant grant = new UrlConstants.Grant();

        public final UrlConstants.Deposit deposit = new UrlConstants.Deposit();
        public final UrlConstants.Expense expense = new UrlConstants.Expense();

        public final UrlConstants.Bank bank = new UrlConstants.Bank();
        public final UrlConstants.Yearlyfee yearlyfee = new UrlConstants.Yearlyfee();

        public final UrlConstants.District district = new UrlConstants.District();
        public final UrlConstants.Upazila upazila = new UrlConstants.Upazila();
        public final UrlConstants.Report report = new UrlConstants.Report();
    }
}
