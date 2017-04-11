
package app.lib.common.http.async;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import app.lib.common.http.Error;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by chenhao on 17/4/11.
 */

public abstract class FileAsyncHandler extends AsyncHandler<File, Error> {

    protected static final int BUFFER_SIZE = 4096;

    protected final File mFile;
    protected final boolean append;

    public FileAsyncHandler(File file) {
        this(file, false);
    }

    public FileAsyncHandler(File file, boolean append) {
        // CommonUtil.asserts(file != null,
        // "File passed into FileAsyncHttpResponseHandler constructor must not be null");
        // CommonUtil.asserts(!file.isDirectory(),
        // "File passed into FileAsyncHttpResponseHandler constructor must not point to directory");
        // if (!file.getParentFile().isDirectory()) {
        // CommonUtil.asserts(file.getParentFile().mkdirs(),
        // "Cannot create parent directories for requested File location");
        // }
        checkFileAsyncHandlerValidDirectory(file, false);
        this.mFile = file;
        this.append = append;
    }

    public boolean checkFileAsyncHandlerValidDirectory(File file, boolean throwException) {
        if (file == null) {
            if (throwException) {
                asserts(false,
                        "File passed into FileAsyncHttpResponseHandler constructor must not be null");
            }
            return false;
        }
        if (file.isDirectory()) {
            if (throwException) {
                asserts(false,
                        "File passed into FileAsyncHttpResponseHandler constructor must not point to directory");
            }
            return false;
        }
        if (!file.getParentFile().isDirectory()) {
            if (!file.getParentFile().mkdirs()) {
                if (throwException) {
                    asserts(false, "Cannot create parent directories for requested File location");
                }
                return false;
            }
        }

        return true;

    }

    protected File getTargetFile() {
        assert (mFile != null);
        return mFile;
    }

    @Override
    public void processResponse(Response response) {
        if (response.isSuccessful()) {
            try {
                InputStream inputStream = response.body().byteStream();
                long contentLength = response.body().contentLength();
                FileOutputStream buffer = new FileOutputStream(getTargetFile(), this.append);
                if (inputStream != null) {
                    try {
                        byte[] tmp = new byte[BUFFER_SIZE];
                        int l, count = 0;

                        while ((l = inputStream.read(tmp)) != -1) {
                            count += l;
                            buffer.write(tmp, 0, l);
                            sendProgressMessage(count, contentLength);
                        }
                    } finally {
                        silentCloseInputStream(inputStream);
                        buffer.flush();
                        silentCloseOutputStream(buffer);
                    }
                }
                sendSuccessMessage(getTargetFile(), response);
            } catch (Exception e) {
                sendFailureMessage(new Error(-1, ""), e, response);
            }
        } else {
            sendFailureMessage(new Error(response.code(), ""), null, response);
        }
    }

    @Override
    public void processFailure(Call call, IOException e) {
        sendFailureMessage(new Error(-1, ""), e, null);
    }

    @Override
    public abstract void onSuccess(File result, Response response);

    @Override
    public abstract void onFailure(Error error, Exception e, Response response);

    public void asserts(final boolean expression, final String failedMessage) {
        if (!expression) {
            throw new AssertionError(failedMessage);
        }
    }

    public void silentCloseInputStream(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
        }
    }

    public void silentCloseOutputStream(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
        }
    }
}
