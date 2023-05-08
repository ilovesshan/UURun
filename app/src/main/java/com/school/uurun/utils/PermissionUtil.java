package com.school.uurun.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    public static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CALL_PHONE,
    };

    public static boolean checkAndRequestPermission(Activity activity, String[] permissions, int checkCode) {
        int checkResult = PackageManager.PERMISSION_GRANTED;
        // Android 6 之后
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                checkResult = ContextCompat.checkSelfPermission(activity, permission);
                if (checkResult != PackageManager.PERMISSION_GRANTED) {
                    break;
                }
            }
            if (checkResult == PackageManager.PERMISSION_DENIED) {
                // 申请权限弹窗
                activity.requestPermissions(permissions, checkCode);
                return false;
            }
        }
        return true;
    }

    public static void checkGranted(AppCompatActivity activity, int requestCode, String[] permissions, int[] grantResults) {
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                switch (permissions[i]) {
                    // 拨号权限未授权
                    case Manifest.permission.CALL_PHONE:
                        Toast.makeText(activity, "需要打开拨号权限才能使用该功能，您也可以前往设置->应用。。。开启权限", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }
        }
    }
}
