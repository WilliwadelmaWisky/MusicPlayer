package com.github.williwadelmawisky.musicplayer.util;

import com.github.williwadelmawisky.musicplayer.util.event.EventHandler;

import java.io.*;
import java.util.Objects;

/**
 *
 */
public final class FileUtil {

    /**
     * Read all contents of the file
     * @param file A file to read
     * @return Contents of the file (Null if file doesn't exist)
     */
    public static String read(File file) {
        if (file == null || !file.exists())
            return null;

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return stringBuilder.toString();
    }

    /**
     * Read all contents of the file
     * @param path A path of the file to read
     * @return Contents of the file (Null if file doesn't exist)
     */
    public static String read(String path) {
        File file = new File(path);
        return read(file);
    }


    /**
     * Write a string to a file (Overrides any previous contents)
     * @param file A file to write to
     * @param data A string to write
     * @return True or False depending on the success of the operation
     */
    public static boolean write(File file, String data) {
        if (file == null || data == null)
            return false;

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(data);
        } catch(IOException ioException) {
            throw new RuntimeException(ioException);
        }

        return true;
    }

    /**
     * Write a string to a file (Overrides any previous contents)
     * @param path A path of the file to write to
     * @param data A string to write
     * @return True or False depending on the success of the operation
     */
    public static boolean write(String path, String data) {
        File file = new File(path);
        return write(file, data);
    }


    /**
     * @param directory
     * @param extensions
     * @param recursive
     * @param proc
     */
    public static void listFiles(final File directory, final String[] extensions, final boolean recursive, final EventHandler<File> proc) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory() && recursive) {
                listFiles(file, extensions, true, proc);
                continue;
            }

            if (Arrays.containsFunc(extensions, extension -> file.getName().endsWith(extension)))
                proc.invoke(file);
        }
    }
}
