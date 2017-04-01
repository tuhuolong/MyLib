
package app.lib.fingerprint;

import android.app.DialogFragment;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by chenhao on 17/4/1.
 */

public class AuthenticationFragment extends DialogFragment implements FingerprintUiHelper.Callback {
    public FingerprintManager.CryptoObject mCryptoObject;
    FingerprintManager mFingerprintManager;
    private Button mCancelButton;
    private FingerprintUiHelper mFingerprintUiHelper;
    private FingerprintUiHelper.FingerprintUiHelperBuilder mFingerprintUiHelperBuilder;

    @Override
    public void onAuthenticated() {

    }

    @Override
    public void onError() {

    }

    public void setCryptoObject(FingerprintManager.CryptoObject cryptoObject) {
        mCryptoObject = cryptoObject;
    }

    public void setFingerprintManager(FingerprintManager fingerprintManager) {
        mFingerprintManager = fingerprintManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Do not create a new Fragment when the Activity is re-created such as orientation changes.
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);

        // We register a new user account here. Real apps should do this with proper UIs.
        enroll();
    }

    /**
     * Enrolls a user to the fake backend.
     */
    private void enroll() {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            PublicKey publicKey = keyStore.getCertificate(AsymmetricFingerprintActivity.KEY_NAME)
                    .getPublicKey();
            // Provide the public key to the backend. In most cases, the key needs to be transmitted
            // to the backend over the network, for which Key.getEncoded provides a suitable wire
            // format (X.509 DER-encoded). The backend can then create a PublicKey instance from the
            // X.509 encoded form using KeyFactory.generatePublic. This conversion is also currently
            // needed on API Level 23 (Android M) due to a platform bug which prevents the use of
            // Android Keystore public keys when their private keys require user authentication.
            // This conversion creates a new public key which is not backed by Android Keystore and
            // thus is not affected by the bug.
            KeyFactory factory = KeyFactory.getInstance(publicKey.getAlgorithm());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey.getEncoded());
            PublicKey verificationKey = factory.generatePublic(spec);
            // mStoreBackend.enroll("user", "password", verificationKey);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException
                | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.sign_in));
        View rootView = inflater.inflate(R.layout.fingerprint_dialog_container, container, false);
        mCancelButton = (Button) rootView.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mCancelButton.setText(R.string.cancel);

        mFingerprintUiHelperBuilder = new FingerprintUiHelper.FingerprintUiHelperBuilder(
                mFingerprintManager);

        mFingerprintUiHelper = mFingerprintUiHelperBuilder.build(
                (ImageView) rootView.findViewById(R.id.fingerprint_icon),
                (TextView) rootView.findViewById(R.id.fingerprint_status), this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mFingerprintUiHelper.startListening(mCryptoObject);
    }

    @Override
    public void onPause() {
        super.onPause();

        mFingerprintUiHelper.stopListening();
    }
}
