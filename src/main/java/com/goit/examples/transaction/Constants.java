package com.goit.examples.transaction;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String CONNECTION_URL = "com.mysql.url";
    public static final String CONNECTION_USERNAME = "com.mysql.username";
    public static final String CONNECTION_PASSWORD = "com.mysql.password";
    public static final String CONNECTION_AUTOCOMMIT = "com.mysql.autocommit";
    public static final String TRANSACTION_ISOLATION = "com.mysql.isolation.level";
}
