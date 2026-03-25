package io.mtapi.mt4.network.messages.req;

import io.mtapi.mt4.network.MetaTraderMessage;
import io.mtapi.mt4.network.MetaTraderRequest;
import io.mtapi.mt4.network.OutputFactory;
import io.mtapi.mt4.utils.MT4Constants;

import java.nio.charset.StandardCharsets;

/**
 * Changes the account password on the MT4 server.
 */
public class ChangePasswordReq implements MetaTraderRequest {
    /**
     * The new password (max 16 characters).
     */
    public String newPassword;

    /**
     * {@code true} to change the investor (read-only) password,
     * {@code false} to change the main trading password.
     */
    public boolean investor;

    @Override
    public void build(OutputFactory outputFactory) {
        if (newPassword == null)
            throw new IllegalArgumentException("ChangePasswordReq: newPassword required");

        outputFactory.single((byte) (investor ? 1 : 0));
        outputFactory.pad(3); // reserved

        byte[] passBytes = newPassword.getBytes(StandardCharsets.US_ASCII);
        int len = Math.min(passBytes.length, 16);
        outputFactory.array(passBytes, len);
        outputFactory.pad(16 - len);
    }

    @Override
    public byte getCommand() {
        return MT4Constants.SEND_CHANGE_PASSWORD;
    }

    @Override
    public Class<? extends MetaTraderMessage> getExpectedResponseClass() {
        return null;
    }

    @Override
    public void reset() {
        newPassword = null;
        investor = false;
    }
}
