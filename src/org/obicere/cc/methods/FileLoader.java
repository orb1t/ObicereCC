package org.obicere.cc.methods;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Obicere
 */
public class FileLoader {

    private static final Logger LOGGER = Logger.getLogger(FileLoader.class.getCanonicalName());

    private final LinkedList<String> list = new LinkedList<>();

    private final String extension;
    private final String prefix;

    private FileLoader(final String extension) {
        this.extension = extension;
        this.prefix = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
    }

    private FileLoader(final String prefix, final String extension) {
        this.extension = extension;
        this.prefix = prefix;
    }

    public static List<String> searchClassPath(final String path, final String extension) {
        final FileLoader loader = new FileLoader(path, extension);
        return loader.find();
    }

    public static List<String> searchClassPath(final String extension) {
        final FileLoader loader = new FileLoader(extension);
        return loader.find();
    }

    private String normalizeSlashes(final String path) {
        return path.replace("\\", "/");
    }

    private List<String> find() {
        if (!list.isEmpty()) {
            return list;
        }

        final File dir = new File(prefix);
        if (dir.isDirectory()) {
            LOGGER.log(Level.INFO, "Analyzing directory {0}.", dir);
            lookInDirectory("", dir);
        }
        if (dir.isFile()) {
            final String name = dir.getName().toLowerCase();
            if (name.endsWith(".zip") || name.endsWith(".jar")) {
                LOGGER.log(Level.INFO, "Analyzing archive {0}.", dir);
                this.lookInArchive(dir);
            }
        }
        return this.list;
    }

    private void lookInDirectory(final String name, final File dir) {
        final File[] files = dir.listFiles();
        Objects.requireNonNull(files);
        for (final File file : files) {
            final String fileName = file.getName();
            if (file.isFile() && fileName.toLowerCase().endsWith(extension)) {
                if (extension.equalsIgnoreCase(".class")) {
                    final String className = fileName.substring(0, fileName.length() - 6);
                    list.add(name + className);
                } else {
                    list.add(name.replace('.', File.separatorChar) + fileName);
                }
            }
            if (file.isDirectory()) {
                lookInDirectory(name + fileName + ".", file);
            }
        }

    }

    private void lookInArchive(final File archive) {
        final JarFile jarFile;
        try {
            jarFile = new JarFile(archive);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        final Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            final JarEntry entry = entries.nextElement();
            final String entryName = entry.getName();
            if (entryName.toLowerCase().endsWith(extension)) {
                if (extension.equalsIgnoreCase(".class")) {
                    final String className = entryName.substring(0, entryName.length() - 6).replace('/', '.');
                    list.add(className);
                } else {
                    list.add(entryName);
                }
            }
        }
    }
}
