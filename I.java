import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by lukasz on 17.05.2017.
 */

public class OnceMore extends ClassLoader {

    public class classLoaderoverride extends ClassLoader {

        Map<String, byte[]> list = new HashMap<>();
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            if (name.startsWith("java.") || name.startsWith("sun.")){
                return super.loadClass(name);
            }
            InputStream stream = getClass().getClassLoader().getResourceAsStream(name.replace('.','/')+".class");
            byte[] tab = new byte[0];
            int size = 0;
            try {
                size = stream.available();
                tab = new byte[size];
                stream.read(tab);
            } catch (IOException e) {
            }
            list.put(name,tab);
            return defineClass(name,tab,0,size);
        }
    }

    public static Runnable produceWithClassLoader(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        OnceMore b = new OnceMore();
        classLoaderoverride A = b.new classLoaderoverride();
        Class a = A.loadClass(className);

        return (Runnable) a.newInstance();
    }

    private static byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    public static void saveToFile(String fileName, Runnable object) throws IOException
    {
        FileOutputStream dest = new FileOutputStream(fileName);
        ZipOutputStream zip = new ZipOutputStream(dest);

        classLoaderoverride loader = (classLoaderoverride) object.getClass().getClassLoader();
        Iterator it = loader.list.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
//            System.out.println(pair.getKey() + " = " + pair.getValue());

            ZipEntry e  = new ZipEntry(pair.getKey()+".class");
            zip.putNextEntry(e);
            zip.write((byte[]) pair.getValue(),0,((byte[]) pair.getValue()).length);
            zip.closeEntry();
        }
        ZipEntry e  = new ZipEntry(object+".dat");
        zip.putNextEntry(e);

//        System.out.println(convertToBytes(object));
        zip.write(convertToBytes(object),0,convertToBytes(object).length);
        zip.closeEntry();
        zip.close();
    }

    public static class classLoaderoverrideload extends ObjectInputStream  {

        Map<String,Class> mapclass = new HashMap<>();

        public classLoaderoverrideload(InputStream in) throws IOException {
            super(in);
        }


        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {

            if( desc.getName().endsWith(".class") ||  desc.getName().endsWith(".sun") ) {
                return super.resolveClass(desc);
            }
            else {
                return mapclass.get(desc.getName());
            }
        }
    }

    public class Loader extends ClassLoader{
        public Class define(String name, InputStream stream) {
            byte[] tab = new byte[0];
            int size = 0;
            try {
                size = stream.available();
                tab = new byte[size];
                stream.read(tab);
            } catch (IOException e) {
            }
            return super.defineClass(name,tab,0,tab.length);
        }
    }


    public static Runnable loadFromFile(String fileName) throws IOException
    {
        Map<String,Class> map = new HashMap<>();
        OnceMore oncemore = new OnceMore();
        Loader loader = oncemore.new Loader();

        ZipFile zipFile = new ZipFile(fileName);
        try
        {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while ( entries.hasMoreElements() )
            {
                ZipEntry entry = entries.nextElement();
                if( entry.getName().endsWith(".class") ||  entry.getName().endsWith(".sun") ){

                    String name = null;
                    if(entry.getName().endsWith(".class")) name = entry.getName().substring(0,entry.getName().length()-6);
                    else name = entry.getName().substring(0,entry.getName().length()-4);

                    Class a = loader.define(name,zipFile.getInputStream(entry));

                    map.put(name, a );
                }
                else {

//                    System.out.println(entry.getName());
                    classLoaderoverrideload objectInputStream = new classLoaderoverrideload(zipFile.getInputStream(entry));
                    objectInputStream.mapclass = map;
                    try {
                        return (Runnable) objectInputStream.readObject();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally
        {
            zipFile.close();
        }
        return null;
    }
}