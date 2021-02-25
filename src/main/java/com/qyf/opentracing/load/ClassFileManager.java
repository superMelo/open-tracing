package com.qyf.opentracing.load;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;

public class ClassFileManager extends ForwardingJavaFileManager{

    private JavaClassObject jclassObject;

    public ClassFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        System.out.println(location);
        System.out.println(kind);
        System.out.println(sibling);
        if (jclassObject == null)
            jclassObject = new JavaClassObject(className, kind);
        return jclassObject;
    }

    public JavaClassObject getJclassobject() {
        return jclassObject;
    }
}
