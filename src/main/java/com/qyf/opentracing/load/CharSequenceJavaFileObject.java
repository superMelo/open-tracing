package com.qyf.opentracing.load;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

    private String content;


    public CharSequenceJavaFileObject(String className, String content) {
        super(URI.create("string:///" + className.replace('.', '/')
                + Kind.SOURCE.extension), Kind.SOURCE);
        this.content = content;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return content;
    }
}
