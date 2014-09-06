package nxt.http;

import nxt.Account;
import nxt.Alias;
import nxt.Asset;
import nxt.Constants;
import nxt.Currency;
import nxt.DigitalGoodsStore;
import nxt.Nxt;
import nxt.crypto.Crypto;
import nxt.crypto.EncryptedData;
import nxt.util.Convert;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static nxt.http.JSONResponses.*;

final class ParameterParser {

    static Alias getAlias(HttpServletRequest req) throws ParameterException {
        Long aliasId;
        try {
            aliasId = Convert.parseUnsignedLong(Convert.emptyToNull(req.getParameter("alias")));
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_ALIAS);
        }
        String aliasName = Convert.emptyToNull(req.getParameter("aliasName"));
        Alias alias;
        if (aliasId != null) {
            alias = Alias.getAlias(aliasId);
        } else if (aliasName != null) {
            alias = Alias.getAlias(aliasName);
        } else {
            throw new ParameterException(MISSING_ALIAS_OR_ALIAS_NAME);
        }
        if (alias == null) {
            throw new ParameterException(UNKNOWN_ALIAS);
        }
        return alias;
    }

    static byte getByte(HttpServletRequest req, String name, byte min, byte max) throws ParameterException {
        String paramValue = Convert.emptyToNull(req.getParameter(name));
        if (paramValue == null) {
            throw new ParameterException(missing(name));
        }
        byte value;
        try {
            value = Byte.parseByte(paramValue);
        } catch (RuntimeException e) {
            throw new ParameterException(incorrect(name));
        }
        if (value < min || value > max) {
            throw new ParameterException(incorrect(name));
        }
        return value;
    }

    static int getInt(HttpServletRequest req, String name, int min, int max) throws ParameterException {
        String paramValue = Convert.emptyToNull(req.getParameter(name));
        if (paramValue == null) {
            throw new ParameterException(missing(name));
        }
        int value;
        try {
            value = Integer.parseInt(paramValue);
        } catch (RuntimeException e) {
            throw new ParameterException(incorrect(name));
        }
        if (value < min || value > max) {
            throw new ParameterException(incorrect(name));
        }
        return value;
    }

    static long getLong(HttpServletRequest req, String name, long min, long max) throws ParameterException {
        String paramValue = Convert.emptyToNull(req.getParameter(name));
        if (paramValue == null) {
            throw new ParameterException(missing(name));
        }
        long value;
        try {
            value = Long.parseLong(paramValue);
        } catch (RuntimeException e) {
            throw new ParameterException(incorrect(name));
        }
        if (value < min || value > max) {
            throw new ParameterException(incorrect(name));
        }
        return value;
    }

    static long getAmountNQT(HttpServletRequest req) throws ParameterException {
        String amountValueNQT = Convert.emptyToNull(req.getParameter("amountNQT"));
        if (amountValueNQT == null) {
            throw new ParameterException(MISSING_AMOUNT);
        }
        long amountNQT;
        try {
            amountNQT = Long.parseLong(amountValueNQT);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_AMOUNT);
        }
        if (amountNQT <= 0 || amountNQT >= Constants.MAX_BALANCE_NQT) {
            throw new ParameterException(INCORRECT_AMOUNT);
        }
        return amountNQT;
    }

    static long getFeeNQT(HttpServletRequest req) throws ParameterException {
        String feeValueNQT = Convert.emptyToNull(req.getParameter("feeNQT"));
        if (feeValueNQT == null) {
            throw new ParameterException(MISSING_FEE);
        }
        long feeNQT;
        try {
            feeNQT = Long.parseLong(feeValueNQT);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_FEE);
        }
        if (feeNQT < 0 || feeNQT >= Constants.MAX_BALANCE_NQT) {
            throw new ParameterException(INCORRECT_FEE);
        }
        return feeNQT;
    }

    static long getPriceNQT(HttpServletRequest req) throws ParameterException {
        String priceValueNQT = Convert.emptyToNull(req.getParameter("priceNQT"));
        if (priceValueNQT == null) {
            throw new ParameterException(MISSING_PRICE);
        }
        long priceNQT;
        try {
            priceNQT = Long.parseLong(priceValueNQT);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_PRICE);
        }
        if (priceNQT <= 0 || priceNQT > Constants.MAX_BALANCE_NQT) {
            throw new ParameterException(INCORRECT_PRICE);
        }
        return priceNQT;
    }

    static Asset getAsset(HttpServletRequest req) throws ParameterException {
        String assetValue = Convert.emptyToNull(req.getParameter("asset"));
        if (assetValue == null) {
            throw new ParameterException(MISSING_ASSET);
        }
        Asset asset;
        try {
            Long assetId = Convert.parseUnsignedLong(assetValue);
            asset = Asset.getAsset(assetId);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_ASSET);
        }
        if (asset == null) {
            throw new ParameterException(UNKNOWN_ASSET);
        }
        return asset;
    }

    static Currency getCurrency(HttpServletRequest req) throws ParameterException {
        String currencyValue = Convert.emptyToNull(req.getParameter("currency"));
        if (currencyValue == null) {
            throw new ParameterException(MISSING_CURRENCY);
        }
        Currency currency;
        try {
            Long currencyId = Convert.parseUnsignedLong(currencyValue);
            currency = Currency.getCurrency(currencyId);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_CURRENCY);
        }
        if (currency == null) {
            throw new ParameterException(UNKNOWN_CURRENCY);
        }
        return currency;
    }

    static long getQuantityQNT(HttpServletRequest req) throws ParameterException {
        String quantityValueQNT = Convert.emptyToNull(req.getParameter("quantityQNT"));
        if (quantityValueQNT == null) {
            throw new ParameterException(MISSING_QUANTITY);
        }
        long quantityQNT;
        try {
            quantityQNT = Long.parseLong(quantityValueQNT);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_QUANTITY);
        }
        if (quantityQNT <= 0 || quantityQNT > Constants.MAX_ASSET_QUANTITY_QNT) {
            throw new ParameterException(INCORRECT_ASSET_QUANTITY);
        }
        return quantityQNT;
    }

    static Long getOrderId(HttpServletRequest req) throws ParameterException {
        String orderValue = Convert.emptyToNull(req.getParameter("order"));
        if (orderValue == null) {
            throw new ParameterException(MISSING_ORDER);
        }
        try {
            return Convert.parseUnsignedLong(orderValue);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_ORDER);
        }
    }

    static DigitalGoodsStore.Goods getGoods(HttpServletRequest req) throws ParameterException {
        String goodsValue = Convert.emptyToNull(req.getParameter("goods"));
        if (goodsValue == null) {
            throw new ParameterException(MISSING_GOODS);
        }
        DigitalGoodsStore.Goods goods;
        try {
            Long goodsId = Convert.parseUnsignedLong(goodsValue);
            goods = DigitalGoodsStore.getGoods(goodsId);
            if (goods == null) {
                throw new ParameterException(UNKNOWN_GOODS);
            }
            return goods;
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_GOODS);
        }
    }

    static int getGoodsQuantity(HttpServletRequest req) throws ParameterException {
        String quantityString = Convert.emptyToNull(req.getParameter("quantity"));
        try {
            int quantity = Integer.parseInt(quantityString);
            if (quantity < 0 || quantity > Constants.MAX_DGS_LISTING_QUANTITY) {
                throw new ParameterException(INCORRECT_QUANTITY);
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new ParameterException(INCORRECT_QUANTITY);
        }
    }

    static EncryptedData getEncryptedMessage(HttpServletRequest req, Account recipientAccount) throws ParameterException {
        String data = Convert.emptyToNull(req.getParameter("encryptedMessageData"));
        String nonce = Convert.emptyToNull(req.getParameter("encryptedMessageNonce"));
        if (data != null && nonce != null) {
            try {
                return new EncryptedData(Convert.parseHexString(data), Convert.parseHexString(nonce));
            } catch (RuntimeException e) {
                throw new ParameterException(INCORRECT_ENCRYPTED_MESSAGE);
            }
        }
        String plainMessage = Convert.emptyToNull(req.getParameter("messageToEncrypt"));
        if (plainMessage == null) {
            return null;
        }
        if (recipientAccount == null) {
            throw new ParameterException(INCORRECT_RECIPIENT);
        }
        String secretPhrase = getSecretPhrase(req);
        boolean isText = !"false".equalsIgnoreCase(req.getParameter("messageToEncryptIsText"));
        try {
            byte[] plainMessageBytes = isText ? Convert.toBytes(plainMessage) : Convert.parseHexString(plainMessage);
            return recipientAccount.encryptTo(plainMessageBytes, secretPhrase);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_PLAIN_MESSAGE);
        }
    }

    static EncryptedData getEncryptToSelfMessage(HttpServletRequest req) throws ParameterException {
        String data = Convert.emptyToNull(req.getParameter("encryptToSelfMessageData"));
        String nonce = Convert.emptyToNull(req.getParameter("encryptToSelfMessageNonce"));
        if (data != null && nonce != null) {
            try {
                return new EncryptedData(Convert.parseHexString(data), Convert.parseHexString(nonce));
            } catch (RuntimeException e) {
                throw new ParameterException(INCORRECT_ENCRYPTED_MESSAGE);
            }
        }
        String plainMessage = Convert.emptyToNull(req.getParameter("messageToEncryptToSelf"));
        if (plainMessage == null) {
            return null;
        }
        String secretPhrase = getSecretPhrase(req);
        Account senderAccount = Account.getAccount(Crypto.getPublicKey(secretPhrase));
        boolean isText = !"false".equalsIgnoreCase(req.getParameter("messageToEncryptToSelfIsText"));
        try {
            byte[] plainMessageBytes = isText ? Convert.toBytes(plainMessage) : Convert.parseHexString(plainMessage);
            return senderAccount.encryptTo(plainMessageBytes, secretPhrase);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_PLAIN_MESSAGE);
        }
    }

    static EncryptedData getEncryptedGoods(HttpServletRequest req) throws ParameterException {
        String data = Convert.emptyToNull(req.getParameter("goodsData"));
        String nonce = Convert.emptyToNull(req.getParameter("goodsNonce"));
        if (data != null && nonce != null) {
            try {
                return new EncryptedData(Convert.parseHexString(data), Convert.parseHexString(nonce));
            } catch (RuntimeException e) {
                throw new ParameterException(INCORRECT_DGS_ENCRYPTED_GOODS);
            }
        }
        return null;
    }

    static DigitalGoodsStore.Purchase getPurchase(HttpServletRequest req) throws ParameterException {
        String purchaseIdString = Convert.emptyToNull(req.getParameter("purchase"));
        if (purchaseIdString == null) {
            throw new ParameterException(MISSING_PURCHASE);
        }
        try {
            DigitalGoodsStore.Purchase purchase = DigitalGoodsStore.getPurchase(Convert.parseUnsignedLong(purchaseIdString));
            if (purchase == null) {
                throw new ParameterException(INCORRECT_PURCHASE);
            }
            return purchase;
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_PURCHASE);
        }
    }

    static String getSecretPhrase(HttpServletRequest req) throws ParameterException {
        String secretPhrase = Convert.emptyToNull(req.getParameter("secretPhrase"));
        if (secretPhrase == null) {
            throw new ParameterException(MISSING_SECRET_PHRASE);
        }
        return secretPhrase;
    }

    static Account getSenderAccount(HttpServletRequest req) throws ParameterException {
        Account account;
        String secretPhrase = Convert.emptyToNull(req.getParameter("secretPhrase"));
        String publicKeyString = Convert.emptyToNull(req.getParameter("publicKey"));
        if (secretPhrase != null) {
            account = Account.getAccount(Crypto.getPublicKey(secretPhrase));
        } else if (publicKeyString != null) {
            try {
                account = Account.getAccount(Convert.parseHexString(publicKeyString));
            } catch (RuntimeException e) {
                throw new ParameterException(INCORRECT_PUBLIC_KEY);
            }
        } else {
            throw new ParameterException(MISSING_SECRET_PHRASE_OR_PUBLIC_KEY);
        }
        if (account == null) {
            throw new ParameterException(UNKNOWN_ACCOUNT);
        }
        return account;
    }

    static Account getAccount(HttpServletRequest req) throws ParameterException {
        String accountValue = Convert.emptyToNull(req.getParameter("account"));
        if (accountValue == null) {
            throw new ParameterException(MISSING_ACCOUNT);
        }
        try {
            Account account = Account.getAccount(Convert.parseAccountId(accountValue));
            if (account == null) {
                throw new ParameterException(UNKNOWN_ACCOUNT);
            }
            return account;
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_ACCOUNT);
        }
    }

    static List<Account> getAccounts(HttpServletRequest req) throws ParameterException {
        String[] accountValues = req.getParameterValues("account");
        if (accountValues == null || accountValues.length == 0) {
            throw new ParameterException(MISSING_ACCOUNT);
        }
        List<Account> result = new ArrayList<>();
        for (String accountValue : accountValues) {
            if (accountValue == null || accountValue.equals("")) {
                continue;
            }
            try {
                Account account = Account.getAccount(Convert.parseAccountId(accountValue));
                if (account == null) {
                    throw new ParameterException(UNKNOWN_ACCOUNT);
                }
                result.add(account);
            } catch (RuntimeException e) {
                throw new ParameterException(INCORRECT_ACCOUNT);
            }
        }
        return result;
    }

    static int getTimestamp(HttpServletRequest req) throws ParameterException {
        String timestampValue = Convert.emptyToNull(req.getParameter("timestamp"));
        if (timestampValue == null) {
            return 0;
        }
        int timestamp;
        try {
            timestamp = Integer.parseInt(timestampValue);
        } catch (NumberFormatException e) {
            throw new ParameterException(INCORRECT_TIMESTAMP);
        }
        if (timestamp < 0) {
            throw new ParameterException(INCORRECT_TIMESTAMP);
        }
        return timestamp;
    }

    static Long getRecipientId(HttpServletRequest req) throws ParameterException {
        String recipientValue = Convert.emptyToNull(req.getParameter("recipient"));
        if (recipientValue == null || "0".equals(recipientValue)) {
            throw new ParameterException(MISSING_RECIPIENT);
        }
        Long recipientId;
        try {
            recipientId = Convert.parseAccountId(recipientValue);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_RECIPIENT);
        }
        if (recipientId == null) {
            throw new ParameterException(INCORRECT_RECIPIENT);
        }
        return recipientId;
    }

    static Long getSellerId(HttpServletRequest req) throws ParameterException {
        String sellerIdValue = Convert.emptyToNull(req.getParameter("seller"));
        try {
            return Convert.parseAccountId(sellerIdValue);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_RECIPIENT);
        }
    }

    static Long getBuyerId(HttpServletRequest req) throws ParameterException {
        String buyerIdValue = Convert.emptyToNull(req.getParameter("buyer"));
        try {
            return Convert.parseAccountId(buyerIdValue);
        } catch (RuntimeException e) {
            throw new ParameterException(INCORRECT_RECIPIENT);
        }
    }

    static int getFirstIndex(HttpServletRequest req) {
        int firstIndex;
        try {
            firstIndex = Integer.parseInt(req.getParameter("firstIndex"));
            if (firstIndex < 0) {
                return 0;
            }
        } catch (NumberFormatException e) {
            return 0;
        }
        return firstIndex;
    }

    static int getLastIndex(HttpServletRequest req) {
        int lastIndex;
        try {
            lastIndex = Integer.parseInt(req.getParameter("lastIndex"));
            if (lastIndex < 0) {
                return Integer.MAX_VALUE;
            }
        } catch (NumberFormatException e) {
            return Integer.MAX_VALUE;
        }
        return lastIndex;
    }

    static int getNumberOfConfirmations(HttpServletRequest req) throws ParameterException {
        String numberOfConfirmationsValue = Convert.emptyToNull(req.getParameter("numberOfConfirmations"));
        if (numberOfConfirmationsValue != null) {
            try {
                int numberOfConfirmations = Integer.parseInt(numberOfConfirmationsValue);
                if (numberOfConfirmations <= Nxt.getBlockchain().getHeight()) {
                    return numberOfConfirmations;
                }
                throw new ParameterException(INCORRECT_NUMBER_OF_CONFIRMATIONS);
            } catch (NumberFormatException e) {
                throw new ParameterException(INCORRECT_NUMBER_OF_CONFIRMATIONS);
            }
        }
        return 0;
    }

    private ParameterParser() {} // never

}
