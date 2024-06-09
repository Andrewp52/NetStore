package org.example.netstore.common.passencoders;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.LongPasswordStrategies;

public class BcryptPasswordEncoder implements PasswordEncoder {
    private static final BCrypt.Hasher hasher = BCrypt.with(BCrypt.Version.VERSION_2B, LongPasswordStrategies.none());
    private static final BCrypt.Verifyer verifier = BCrypt.verifyer(BCrypt.Version.VERSION_2B, LongPasswordStrategies.none());
    private static final int COST = 10;
    @Override
    public String encode(String password) {
        return hasher.hashToString(COST, password.toCharArray());
    }

    @Override
    public boolean match(String hash, String password) {
        BCrypt.Result result = verifier.verify(password.toCharArray(), hash.toCharArray());
        return result.validFormat && result.verified;
    }

}
