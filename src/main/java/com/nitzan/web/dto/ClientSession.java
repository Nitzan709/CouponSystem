package com.nitzan.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientSession {
    public static final int CUSTOMER_TYPE = 0;
    public static final int COMPANY_TYPE = 1;
    private final UUID uuid;
    private long lastAccessedMillis;
    private int clientType;

    public static ClientSession of(UUID uuid, int clientType) {
        return new ClientSession(uuid, System.currentTimeMillis(), clientType);
    }

    public void updateLastAccessedMillis() {
        lastAccessedMillis = System.currentTimeMillis();
    }
}
