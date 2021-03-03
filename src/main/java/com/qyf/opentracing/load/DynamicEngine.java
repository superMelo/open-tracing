package com.qyf.opentracing.load;

import javax.tools.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class DynamicEngine {
    private static DynamicEngine dynamicEngine = new DynamicEngine();

    private String classpath;

    private URLClassLoader parentClassLoader;
    private DynamicEngine() {
        this.parentClassLoader = (URLClassLoader)this.getClass().getClassLoader();
        this.buildClassPath();
    }

    public static DynamicEngine getInstance(){
        return dynamicEngine;
    }

    private void buildClassPath(){
        this.classpath = null;
        StringBuilder sb = new StringBuilder();
        for (URL url : this.parentClassLoader.getURLs()){
            String file = url.getFile();
            sb.append(file).append(File.pathSeparator);
        }
        this.classpath = sb.toString();
    }

    public Object javaCodeToObject(String fullClassName, String javaCode) throws Exception{
        long start = System.currentTimeMillis();
        Object instance = null;

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics  = new DiagnosticCollector<>();

        ClassFileManager manager = new ClassFileManager(compiler.getStandardFileManager(diagnostics, null, null));

//        DynamicJavaFileManager manager = new DynamicJavaFileManager(compiler.getStandardFileManager(diagnostics, null, null));
        ArrayList<JavaFileObject> javaFileObjects = new ArrayList<>();

        javaFileObjects.add(new CharSequenceJavaFileObject(fullClassName, javaCode));

        ArrayList<String> options = new ArrayList<>();
        options.add("-encoding");
        options.add("UTF-8");
        options.add("-classpath");
        options.add(this.classpath);

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, options, null, javaFileObjects);

        boolean success = task.call();

        if (success){
            //获取类对象
            JavaClassObject jco = manager.getJclassobject();
            //获取动态加载器
            DynamicClassLoader dynamicClassLoader = new DynamicClassLoader(this.parentClassLoader);
            //获取类
            Class clz = dynamicClassLoader.loadClass(fullClassName, jco);
            //获取对象
            instance = clz.newInstance();
        }else {
            String error = "";
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()){
                error += compilePrint(diagnostic);
            }
            System.out.println(error);
        }
        long end = System.currentTimeMillis();
        System.out.println("javaCodeToObject use:"+(end-start)+"ms");
        return instance;
    }

    private String compilePrint(Diagnostic diagnostic) {
        System.out.println("Code:" + diagnostic.getCode());
        System.out.println("Kind:" + diagnostic.getKind());
        System.out.println("Position:" + diagnostic.getPosition());
        System.out.println("Start Position:" + diagnostic.getStartPosition());
        System.out.println("End Position:" + diagnostic.getEndPosition());
        System.out.println("Source:" + diagnostic.getSource());
        System.out.println("Message:" + diagnostic.getMessage(null));
        System.out.println("LineNumber:" + diagnostic.getLineNumber());
        System.out.println("ColumnNumber:" + diagnostic.getColumnNumber());
        StringBuffer res = new StringBuffer();
        res.append("Code:[" + diagnostic.getCode() + "]\n");
        res.append("Kind:[" + diagnostic.getKind() + "]\n");
        res.append("Position:[" + diagnostic.getPosition() + "]\n");
        res.append("Start Position:[" + diagnostic.getStartPosition() + "]\n");
        res.append("End Position:[" + diagnostic.getEndPosition() + "]\n");
        res.append("Source:[" + diagnostic.getSource() + "]\n");
        res.append("Message:[" + diagnostic.getMessage(null) + "]\n");
        res.append("LineNumber:[" + diagnostic.getLineNumber() + "]\n");
        res.append("ColumnNumber:[" + diagnostic.getColumnNumber() + "]\n");
        return res.toString();
    }
}
