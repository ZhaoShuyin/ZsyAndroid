package cn.azsy.zstokhttp.utils.utilclass;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileUtils {


    /**
     * remove the specified dir recursively
     * 递归删除给定目录下的所有文件
     *
     * @param dir
     * @throws Exception
     */
    public static void clearDir(String dir) throws Exception {
        /*if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
			return;
		}*/
        File file = new File(dir);
        if (!file.exists()) {
            return;
        }
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                removeDir(f.getAbsolutePath());
            } else if (f.isFile()) {
                f.delete();
            }
        }
    }

    //递归删除给定目录下的所有文件
    public static void clearDir(String dir, long timeout) throws Exception {
        File file = new File(dir);
        if (!file.exists()) {
            return;
        }
        for (File f : file.listFiles()) {
            if (f == null) continue;
            removeDir(f.getAbsolutePath(), timeout);
        }
    }

    /**
     * remove the specified dir recursively
     *
     * @param dir
     * @throws Exception
     */
    public static void removeDir(String dir) throws Exception {
        // 定义文件路径
        File file = new File(dir);
        // 判断是文件还是目录
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] subs = file.listFiles();
                int length = subs.length;
                for (int i = 0; i < length; i++) {
                    if (subs[i].isDirectory()) {
                        removeDir(subs[i].getAbsolutePath());
                    } else {
                        subs[i].delete();
                    }
                }
            } else {
                file.delete();
            }
        }
    }

    /**
     * remove the specified dir recursively
     *
     * @param dir
     * @throws Exception
     */

    public static void removeDir(String dir, long timeout) throws Exception {
        // 定义文件路径
        File file = new File(dir);
        // 判断是文件还是目录
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    if (f == null) continue;
                    if (f.isDirectory()) {
                        removeDir(f.getAbsolutePath(), timeout);
                    } else {
                        try {
                            long time = System.currentTimeMillis() - f.lastModified();
                            if (time > timeout) f.delete();
                            Thread.sleep(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                try {
                    long time = System.currentTimeMillis() - file.lastModified();
                    if (time > timeout) file.delete();
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static final int BUFF_SIZE = 1024 * 1024; // 1M Byte

    /**
     * 解压缩一个文件
     *
     * @param zipFile    压缩文件
     * @param folderPath 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    public static void unZipFile(File zipFile, String folderPath)
            throws ZipException, IOException {
        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        ZipFile zf = new ZipFile(zipFile);
        for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            InputStream in = zf.getInputStream(entry);
            String str = folderPath + File.separator + entry.getName();
            str = new String(str.getBytes("8859_1"), "GB2312");
            File desFile = new File(str);
            if (!desFile.exists()) {
                File fileParentDir = desFile.getParentFile();
                if (!fileParentDir.exists()) {
                    fileParentDir.mkdirs();
                }
                desFile.createNewFile();
            }
            OutputStream out = new FileOutputStream(desFile);
            byte buffer[] = new byte[BUFF_SIZE];
            int realLength;
            while ((realLength = in.read(buffer)) > 0) {
                out.write(buffer, 0, realLength);
            }
            in.close();
            out.close();
        }
    }

    /**
     * 压缩文件
     *
     * @param resFile  需要压缩的文件（夹）
     * @param rootpath 压缩的文件路径
     * @throws FileNotFoundException 找不到文件时抛出
     * @throws IOException           当压缩过程出错时抛出
     */
    public static void zipFile(File resFile,
                               File zipFile,
                               String rootpath) throws FileNotFoundException, IOException {

        rootpath = rootpath
                + (rootpath.trim().length() == 0 ? "" : File.separator)
                + resFile.getName();
        //rootpath = new String(rootpath.getBytes("8859_1"), "utf-8");
        if (resFile.isDirectory()) {
            File[] fileList = resFile.listFiles();
            for (File file : fileList) {
                zipFile(file, zipFile, rootpath);
            }
        } else {
            ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(zipFile));
            byte buffer[] = new byte[BUFF_SIZE];
            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream(resFile), BUFF_SIZE);
            zipout.putNextEntry(new ZipEntry(rootpath));
            int realLength;
            while ((realLength = in.read(buffer)) != -1) {
                zipout.write(buffer, 0, realLength);
            }
            in.close();
            zipout.flush();
            zipout.closeEntry();
            zipout.close();
        }
    }

    /**
     * detect sd card is available
     *
     * @return
     */
    public static boolean isSdAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);

    }

    /**
     * 通过递归调用删除一个文件夹及下面的所有文件
     */
    public static void deleteFile(File file) {
        if (file.isFile()) {    //表示该文件不是文件夹
            file.delete();
        } else {
            //首先得到当前的路径
            String[] childFilePaths = file.list();
            if (childFilePaths != null && childFilePaths.length > 0) {
                for (String childFilePath : childFilePaths) {
                    File childFile = new File(file.getAbsolutePath() + "/" + childFilePath);
                    deleteFile(childFile);
                }
                file.delete();
            }
        }
    }

    public static void deleteFileOne(String fileName) {
        File file = new File(fileName);
        if (file == null || !file.exists())
            return;
        file.delete();
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteOneFile(String sPath) {
        Boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 判断是否存在一个文件夹，创建
     *
     * @param strFolder
     * @return
     */
    public static boolean isFolderExists(String strFolder) {
        File file = new File(strFolder);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否存在一个文件
     *
     * @param path 路径
     * @return
     */
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //return size/1048576;
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 保存文件到本地
     * //目录：data/data/当前应用程序的包名/files/
     *
     * @param fileName
     * @param fileContent
     * @param context
     */
    public static void saveFileToLocal(Context context, String fileName, String fileContent) {
        FileOutputStream outStream = null;
        if (!fileName.equals("") || !fileContent.equals("")) {
            try {
                outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                outStream.write(fileContent.getBytes());
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String FormetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static void saveToSDCard(String name, String content) {
        FileOutputStream fos = null;
        try {
            //Environment.getExternalStorageDirectory()。获取sd卡的路径
            File file = new File(Environment.getExternalStorageDirectory(), name);
            fos = new FileOutputStream(file);
            fos.write(content.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getFromAssets(Context context,String fileName) {
        String result;
        StringBuilder sb = new StringBuilder();
        InputStream is;
        try {
            is = context.getResources().getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        result = sb.toString();
        return result;
    }

    public static long calculateSize(File dir) {
        if (dir == null) return 0;
        if (!dir.isDirectory()) return dir.length();
        long result = 0;
        File[] children = dir.listFiles();
        if (children != null)
            for (File child : children)
                result += calculateSize(child);
        return result;
    }


}
