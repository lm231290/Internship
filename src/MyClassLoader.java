import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class MyClassLoader extends ClassLoader {
    public MyClassLoader(String fileName, String packageName) {
        this.fileName = fileName;
        this.packageName = packageName;

        cacheClasses();
    }


    private HashMap<String, Class<?>> cache = new HashMap<>();
    private String fileName;
    private String packageName;

    private void cacheClasses() {
        try {
            JarFile jarFile = new JarFile(fileName);

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {

                JarEntry jarEntry = entries.nextElement();

                if (match(urlToPackageName(jarEntry.getName()), packageName)) {
                    byte[] classData = loadClassData(jarFile, jarEntry);

                    if (classData != null) {
                        Class<?> clazz = defineClass(stripClassName(urlToPackageName(jarEntry.getName())), classData, 0, classData.length);
                        cache.put(clazz.getName(), clazz);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Class<?> loadClass(String name) throws ClassNotFoundException {

        Class<?> result = cache.get(name);

        if (result == null)
            result = cache.get(packageName + "." + name);

        if (result == null)
            result = super.findSystemClass(name);

        return result;
    }

    private byte[] loadClassData(JarFile jarFile, JarEntry jarEntry) throws IOException {
        long size = jarEntry.getSize();

        if (size < 1)
            return null;
        byte[] data = new byte[(int)size];

        InputStream in = jarFile.getInputStream(jarEntry);
        in.read(data);
        return data;
    }

    private String stripClassName(String str) {
        return str.substring(0, str.length() - 6);
    }

    private boolean match(String className, String packageName) {
        return className.startsWith(packageName) && className.endsWith(".class");
    }

    private String urlToPackageName(String str) {
        return str.replaceAll("/", ".");
    }

}
