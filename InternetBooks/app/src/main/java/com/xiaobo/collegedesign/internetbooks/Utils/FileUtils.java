package com.xiaobo.collegedesign.internetbooks.Utils;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xiaobo on 15/1/29.
 */
public class FileUtils {

    /**
     * 获取指定文件夹的大小，包含子文件夹也可以
     * @param f File实例
     * @return 文件夹大小，单位：字节
     */
    public static long getFileSize(File f) {
        long size = 0;
        if (!f.exists()) {
            return size;
        }
        File fList[] = f.listFiles();
        for (int i = 0; i < fList.length; i++) {
            if (fList[i].isDirectory()) {
                size = size + getFileSize(fList[i]);
            } else {
                size = size + fList[i].length();
            }
        }
        return size;
    }

    /**
     * 删除指定文件和文件夹，包含子文件和子文件夹
     * @param file File实例
     * @return 是否删除成功
     */
    public static boolean deleteFile(File file) {
        boolean deleted = false;
        if (!file.exists()) {
            return deleted;
        }

        if (file.isDirectory() && file.listFiles().length > 0) {
            for (File tempFile : file.listFiles()) {
                if (tempFile.isFile()) {
                    deleted = tempFile.delete();
                }else {
                    deleted = deleteFile(tempFile);
                }
            }
        }else {
            deleted = file.delete();
        }

        return deleted;
    }

    public static boolean saveFile(InputStream inputStream, String savePath) {
        File file = new File(savePath);
        if (!file.exists() || file.isDirectory()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        try {
            byte[] bytes = new byte[1024];
            int length = -1;
            FileOutputStream outputStream = new FileOutputStream(file);
            while((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }
            outputStream.flush();
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取本应用的文件夹
     * @return 本应用的文件夹路径
     */
    public static String getAppFolderPath(Context context) {
        String filePath = context.getApplicationContext().getFilesDir().getPath();
        filePath = filePath + File.separator + "Books";
        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        return filePath;
    }
}
