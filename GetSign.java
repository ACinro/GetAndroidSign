

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.FileUtils;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GetSign {
    /**
     * 根据包名签名
     *
     * @param context 在MainActivity传入(this)
     * @param
     * @return
     */
    public void getSigh(Context context) {
        try {
            int i = 0;
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                i++;
                //SHA
                MessageDigest messageDigestSha = MessageDigest.getInstance("SHA");
                //SHA1
                MessageDigest messageDigestSha1 = MessageDigest.getInstance("SHA1");
                //SHA256
                MessageDigest messageDigestSha256 = MessageDigest.getInstance("SHA256");
                //MD5
                MessageDigest messageDigestMd5 = MessageDigest.getInstance("MD5");

                messageDigestSha.update(signature.toByteArray());
                messageDigestSha1.update(signature.toByteArray());
                messageDigestMd5.update(signature.toByteArray());
                messageDigestSha256.update(signature.toByteArray());

                String md5 = toHextring(messageDigestMd5.digest());
                String sha1 = toHextring(messageDigestSha1.digest());
                String sha256 = toHextring(messageDigestSha256.digest());

                String hash = Base64.encodeToString(messageDigestSha.digest(), Base64.DEFAULT);

                Log.e("获取应用KeyHash",  hash);
                Log.e("获取应用MD5", md5);
                Log.e("获取应用sha1",  sha1);
                Log.e("获取应用sha256", sha256);

            }
        } catch (Exception e) {
            Log.e("获取应用失败", e.getMessage());
        }
    }

    private String toHextring(byte[] block) {
        StringBuffer buf = new StringBuffer();
        for (byte aBlock : block) {
            byte2Hex(aBlock, buf);
        }
        return buf.toString();
    }

    private void byte2Hex(byte b, StringBuffer buf) {
        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);

        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }
}
