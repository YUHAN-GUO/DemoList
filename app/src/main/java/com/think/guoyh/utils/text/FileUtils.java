package com.think.guoyh.utils.text;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.base.gyh.baselib.utils.Utils;
import com.base.gyh.baselib.utils.mylog.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class FileUtils {
    private static Context context = Utils.getApp();
    /** 删除文件，可以是文件或文件夹
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
            Logger.d("%s++++++++++%s","guoyh", "删除文件失败:" + delFile + "不存在！");
            return false;
        } else {
            if (file.isFile()){
                return deleteSingleFile(delFile);
            }
            else{
                return false;

            }
        }
    }
    public static boolean delete( File file) {
        if (!file.exists()) {
            Logger.d("%s++++++++++%s","guoyh", "删除文件失败:" + file.getAbsolutePath() + "不存在！");
            return false;
        } else {
            if (file.isFile()){
                return deleteSingleFile(file.getAbsolutePath());
            }
            else{
                return false;

            }
        }
    }
    public static String getApkPath() {
        String directoryPath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
            directoryPath = context.getExternalFilesDir("apk").getAbsolutePath();
        } else {//没外部存储就使用内部存储
            directoryPath = context.getFilesDir() + File.separator + "apk";
        }
        File file = new File(directoryPath);
        Log.e("测试路径", directoryPath);
        if (!file.exists()) {//判断文件目录是否存在
            file.mkdirs();
        }
        return directoryPath;
    }
    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                Logger.d("%s++++++++++%s","guoyh", "删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
            Logger.d("%s++++++++++%s","guoyh", "删除单个文件失败：" + filePath$Name + "不存在！");

            return false;
        }
    }

    /** 删除目录及目录下的文件
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    private boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            Logger.d("%s++++++++++%s","guoyh", "删除目录失败：" + filePath + "不存在！");

            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            Logger.d("%s++++++++++%s","guoyh","删除目录失败");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.e("--Method--", "Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else {
            Logger.d("%s++++++++++%s","guoyh", "删除目录：" + filePath + "失败！");

            return false;
        }
    }
    //采用自己的格式去设置文件，防止文件被系统文件查询到
    public static final String SUFFIX_NB = ".nb";
    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_PDF = ".pdf";

    //获取文件夹
    public static File getFolder(String filePath){
        File file = new File(filePath);
        //如果文件夹不存在，就创建它
        if (!file.exists()){
            file.mkdirs();
        }
        return file;
    }

    //获取文件
    public static synchronized File getFile(String filePath){
        File file = new File(filePath);
        try {
            if (!file.exists()){
                //创建父类文件夹
                getFolder(file.getParent());
                //创建文件
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    //获取Cache文件夹
    public static String getCachePath(){
        if (isSdCardExist()){
            return Utils.getApp()
                    .getExternalCacheDir()
                    .getAbsolutePath();
        }
        else{
            return Utils.getApp()
                    .getCacheDir()
                    .getAbsolutePath();
        }
    }

    public static long getDirSize(File file){
        //判断文件是否存在
        if (file.exists()) {
            //如果是目录则递归计算其内容的总大小
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                long size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {
                return file.length();
            }
        } else {
            return 0;
        }
    }

    public static String getFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"b", "kb", "M", "G", "T"};
        //计算单位的，原理是利用lg,公式是 lg(1024^n) = nlg(1024)，最后 nlg(1024)/lg(1024) = n。
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        //计算原理是，size/单位值。单位值指的是:比如说b = 1024,KB = 1024^2
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    /**
     * 本来是获取File的内容的。但是为了解决文本缩进、换行的问题
     * 这个方法就是专门用来获取书籍的...
     *
     * 应该放在BookRepository中。。。
     * @param file
     * @return
     */
    public static String getFileContent(File file){
        Reader reader = null;
        String str = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            while ((str = br.readLine()) != null){
                //过滤空语句
                if (!str.equals("")){
                    //由于sb会自动过滤\n,所以需要加上去
                    sb.append("    "+str+"\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.close(reader);
        }
        return sb.toString();
    }

    //判断是否挂载了SD卡
    public static boolean isSdCardExist(){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return true;
        }
        return false;
    }

    //递归删除文件夹下的数据
    public static synchronized void deleteFile(String filePath){
        File file = new File(filePath);
        if (!file.exists()) return;

        if (file.isDirectory()){
            File[] files = file.listFiles();
            for (File subFile : files){
                String path = subFile.getPath();
                deleteFile(path);
            }
        }
        //删除文件
        file.delete();
    }

    //由于递归的耗时问题，取巧只遍历内部三层

    //获取txt文件
    public static List<File> getTxtFiles(String filePath, int layer){
        final List txtFiles = new ArrayList();
        File file = new File(filePath);

        //如果层级为 3，则直接返回
        if (layer == 3){
            return txtFiles;
        }

        //获取文件夹
        File[] dirs = file.listFiles(
                new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        if (pathname.isDirectory() && !pathname.getName().startsWith(".")) {
                            return true;
                        }
                        //获取txt文件
                        else if (pathname.getName().endsWith(".txt")) {
                            txtFiles.add(pathname);
                            return false;
                        } else {
                            return false;
                        }
                    }
                }
        );
        //遍历文件夹
        for (File dir : dirs){
            //递归遍历txt文件
            txtFiles.addAll(getTxtFiles(dir.getPath(),layer + 1));
        }
        return txtFiles;
    }

    //由于遍历比较耗时
    public static Single<List<File>> getSDTxtFile(){
        //外部存储卡路径
        final String rootPath = Environment.getExternalStorageDirectory().getPath();
        return Single.create(new SingleOnSubscribe<List<File>>() {
            @Override
            public void subscribe(SingleEmitter<List<File>> e) throws Exception {
                List<File> files = getTxtFiles(rootPath,0);
                e.onSuccess(files);
            }
        });
    }

    //获取文件的编码格式
    public static Charset getCharset(String fileName) {
        BufferedInputStream bis = null;
        Charset charset = Charset.GBK;
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            bis = new BufferedInputStream(new FileInputStream(fileName));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = Charset.UTF8;
                checked = true;
            }
            /*
             * 不支持 UTF16LE 和 UTF16BE
            else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = Charset.UTF16LE;
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = Charset.UTF16BE;
                checked = true;
            } else */

            bis.mark(0);
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = Charset.UTF8;
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(bis);
        }
        return charset;
    }

    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
