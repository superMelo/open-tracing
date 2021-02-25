package com.qyf.opentracing.load;

public class Test {

//    public static void main(String[] args) throws Exception {
//        String fullName = "com.qyf.opentracing.load.MyClass";
//        File file = new File("C:\\Users\\Administrator\\Desktop\\open-tracing\\src\\main\\java\\com\\qyf\\opentracing\\load\\MyClass.java");
//        if (!file.exists()){
//            PrintWriter printWriter = new PrintWriter(new FileWriter(file, true), true);
//            printWriter.print("package com.qyf.opentracing.load;\n" +
//                    "\n" +
//                    "public class MyClass {\n" +
//                    "    public String say(String str){\n" +
//                    "        return \"hello\"+str;\n" +
//                    "    }\n" +
//                    "}");
//            printWriter.close();
//        }
//        InputStream in = new FileInputStream(file);
//        byte[] bytes = IOUtils.readFully(in, -1, false);
//        String src = new String(bytes);
//        in.close();
//
////        System.out.println(src);
//        DynamicEngine de = DynamicEngine.getInstance();
//        Object instance =  de.javaCodeToObject(fullName,src.toString());
//        Class<?> clz = instance.getClass();
//        Method[] methods = clz.getDeclaredMethods();
//        for (Method method : methods) {
//            System.out.println(method.invoke(instance,  "111"));
//        }
////        System.out.println(instance);
//    }
}
