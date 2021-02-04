package com.raise.weapon_base.secret;

import androidx.annotation.NonNull;

/**
 * Created by raise.yang on 19/07/17.
 */
public interface ICipher {
    @NonNull
    byte[] encrypt(@NonNull byte[] plainData);

    @NonNull
    byte[] decrypt(@NonNull byte[] cipherData);

    @NonNull
    String encrypt(@NonNull String plainData);

    @NonNull
    String decrypt(@NonNull String cipherData);

    @NonNull
    byte[] getKey();

}
