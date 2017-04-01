
package app.lib.fingerprint;

import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.ECGenParameterSpec;

/**
 * Created by chenhao on 17/4/1.
 */

public class AsymmetricFingerprintActivity extends FragmentActivity {
    public static final String KEY_NAME = "test_key";

    private static final String DIALOG_FRAGMENT_TAG = "dialog_fragment";

    Context mContext;

    AuthenticationFragment mAuthenticationDialog;

    KeyStore mKeyStore;
    Signature mSignature;
    KeyguardManager mKeyguardManager;
    FingerprintManager mFingerprintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.activity_fingerprint);

        mKeyguardManager = (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        mFingerprintManager = (FingerprintManager) mContext
                .getSystemService(Context.FINGERPRINT_SERVICE);

        Button purchaseButton = (Button) findViewById(R.id.purchase_button);

        if (!mKeyguardManager.isKeyguardSecure()) {
            // Show a message that the user hasn't set up a fingerprint or lock screen.
            Toast.makeText(this,
                    "Secure lock screen hasn't set up.\n"
                            + "Go to 'Settings -> Security -> Fingerprint' to set up a fingerprint",
                    Toast.LENGTH_LONG).show();
            purchaseButton.setEnabled(false);
            return;
        }
        // noinspection ResourceType
        if (!mFingerprintManager.hasEnrolledFingerprints()) {
            purchaseButton.setEnabled(false);
            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                    "Go to 'Settings -> Security -> Fingerprint' and register at least one fingerprint",
                    Toast.LENGTH_LONG).show();
            return;
        }

        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");

            mKeyStore.load(null);

            if (!mKeyStore.containsAlias(KEY_NAME)) {
                createKeyPair();
            }
        } catch (Exception e) {
        }

        try {
            mSignature = Signature.getInstance("SHA256withECDSA");
        } catch (NoSuchAlgorithmException e) {
        }

        purchaseButton.setEnabled(true);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (initSignature()) {

                    // Show the fingerprint dialog. The user has the option to use the fingerprint
                    // with
                    // crypto, or you can fall back to using a server-side verified password.
                    mAuthenticationDialog
                            .setCryptoObject(new FingerprintManager.CryptoObject(mSignature));
                    mAuthenticationDialog.setFingerprintManager(mFingerprintManager);

                    mAuthenticationDialog.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
                } else {
                    // This happens if the lock screen has been disabled or or a fingerprint got
                    // enrolled. Thus show the dialog to authenticate with their password first
                    // and ask the user if they want to authenticate with fingerprints in the
                    // future

                    Toast.makeText(mContext,
                            getString(R.string.new_fingerprint_enrolled_description),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAuthenticationDialog = new AuthenticationFragment();
    }

    public void createKeyPair() {
        // The enrolling flow for fingerprint. This is where you ask the user to set up fingerprint
        // for your flow. Use of keys is necessary if you need to know if the set of
        // enrolled fingerprints has changed.
        try {
            // Set the alias of the entry in Android KeyStore where the key will appear
            // and the constrains (purposes) in the constructor of the Builder
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_SIGN)
                            .setDigests(KeyProperties.DIGEST_SHA256)
                            .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                            // Require the user to authenticate with a fingerprint to
                            // authorize
                            // every use of the private key
                            .setUserAuthenticationRequired(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setInvalidatedByBiometricEnrollment(true);
            }

            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");

            keyPairGenerator.initialize(builder.build());
            keyPairGenerator.generateKeyPair();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize the {@link Signature} instance with the created key in the
     * {@link #createKeyPair()} method.
     *
     * @return {@code true} if initialization is successful, {@code false} if the lock screen has
     *         been disabled or reset after the key was generated, or if a fingerprint got enrolled
     *         after the key was generated.
     */
    private boolean initSignature() {
        boolean result;
        try {
            mKeyStore.load(null);
            PrivateKey key = (PrivateKey) mKeyStore.getKey(KEY_NAME, null);
            mSignature.initSign(key);
            result = true;
        } catch (KeyPermanentlyInvalidatedException e) {
            result = false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }

        return result;
    }
}
