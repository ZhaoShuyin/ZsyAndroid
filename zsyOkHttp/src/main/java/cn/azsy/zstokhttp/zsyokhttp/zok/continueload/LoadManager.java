package cn.azsy.zstokhttp.zsyokhttp.zok.continueload;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by zsy on 2017/5/5.
 */

public class LoadManager {

    private static HashMap<String, DownLoador> tasks = new HashMap<>();

    public static DownLoador start(final String url, final String path, final DownLoador.ProgressListener progressListener) {
        DownLoador loador = null;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    DownLoador loador = tasks.get(url);

                    if (loador == null) {
                        File loadFile = new File(path, getFileName(url,path));

                        loador = new DownLoador(url, loadFile, progressListener);
                        tasks.put(url, loador);
                    }

                    final DownLoador finalLoador = loador;
                    finalLoador.startLoad();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        return loador;
    }

    private static void checkFileExcit(File loadFile) {
        if (loadFile.exists()) {

        }
    }

    private static String getFileName(String url,String path) {
        String fileName = url.substring(url.lastIndexOf("/"));
        checkFileExcit(new File(path,fileName));
        return fileName;
    }


    public static void stop(String url) {
        DownLoador loador = tasks.get(url);
        if (loador != null) {
            loador.stopLoad();
        }
    }
}
