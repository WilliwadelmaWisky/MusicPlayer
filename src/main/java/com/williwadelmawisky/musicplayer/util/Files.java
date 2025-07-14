package com.williwadelmawisky.musicplayer.util;

import java.io.*;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 */
public final class Files {

    /**
     * Read all contents of the file
     * @param file A file to read
     * @return Contents of the file (Null if file doesn't exist)
     */
    public static String read(final File file) {
        if (file == null || !file.exists())
            return null;

        final StringBuilder stringBuilder = new StringBuilder();
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
    public static String read(final String path) {
        final File file = new File(path);
        return read(file);
    }


    /**
     * Write a string to a file (Overrides any previous contents)
     * @param file A file to write to
     * @param data A string to write
     * @return True or False depending on the success of the operation
     */
    public static boolean write(final File file, final String data) {
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
        final File file = new File(path);
        return write(file, data);
    }


    /**
     * @param directory
     * @param extensions
     * @param recursive
     * @param proc
     */
    public static void listFiles(final File directory, final String[] extensions, final boolean recursive, final Consumer<? super File> proc) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                if (!recursive)
                    continue;

                listFiles(file, extensions, true, proc);
                continue;
            }

            boolean includeAll = extensions == null || extensions.length == 0 || Arrays.contains(extensions, "*");
            if (includeAll || Arrays.containsFunc(extensions, extension -> file.getName().endsWith(extension)))
                proc.accept(file);
        }
    }

    /**
     * @param file
     * @return
     */
    public static String getExtension(final File file) {
        final int index = file.getName().lastIndexOf('.');
        return (index != -1) ? file.getName().substring(index + 1) : null;
    }

    /**
     * @param file
     * @return
     */
    public static String getNameWithoutExtension(final File file) {
        final int index = file.getName().lastIndexOf('.');
        return (index != -1) ? file.getName().substring(0, index) : null;
    }

    /**
     * @param file
     * @param extensions
     * @return
     */
    public static boolean doesMatchExtension(final File file, final String[] extensions) {
        final String extension = getExtension(file);
        return extension != null && Arrays.contains(extensions, extension);
    }
}
