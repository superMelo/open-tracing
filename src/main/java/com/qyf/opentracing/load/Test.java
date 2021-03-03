package com.qyf.opentracing.load;

import sun.misc.IOUtils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Method;

public class Test {

  /*  public static void main(String[] args) throws Exception {
        //class名称
        String fullName = "com.qyf.opentracing.load.MyClass";
        //创建java文件
        File file = new File("C:\\Users\\Administrator\\Desktop\\openTracing\\open-tracing\\src\\main\\java\\com\\qyf\\opentracing\\load\\MyClass.java");
        if (!file.exists()){
            PrintWriter printWriter = new PrintWriter(new FileWriter(file, true), true);
            printWriter.print("package com.qyf.opentracing.load;\n" +
                    "\n" +
                    "public class MyClass {\n" +
                    "    public String say(String str){\n" +
                    "        return \"hello\"+str;\n" +
                    "    }\n" +
                    "}");
            printWriter.close();
        }
        InputStream in = new FileInputStream(file);
        byte[] bytes = IOUtils.readFully(in, -1, false);
        String src = new String(bytes);
        in.close();

        //动态加载
        DynamicEngine de = DynamicEngine.getInstance();
        Object instance =  de.javaCodeToObject(fullName, src.toString());
        Class<?> clz = instance.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.invoke(instance,  "111"));
        }
    }*/
      public static void main(String[] args) {
          String file = "C:\\Users\\Administrator\\Desktop\\openTracing\\open-tracing\\src\\main\\java\\com\\qyf\\opentracing\\load\\MyClass.java";
          JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
          int run = compiler.run(null, null, null, file);
          if (run == 0){
              System.out.println("success");
          }else {
              System.out.println("fail");
          }
      }
}
