package com.qyf.opentracing.agent;



public class AgentClassLoader extends ClassLoader{

    private static AgentClassLoader DEFAULT_LOADER;


    public AgentClassLoader(ClassLoader parent) throws Exception {
        super(parent);

    }

    public static void initDefaultLoader() throws Exception {
        if (DEFAULT_LOADER == null) {
            synchronized (AgentClassLoader.class) {
                if (DEFAULT_LOADER == null) {
                    DEFAULT_LOADER = new AgentClassLoader(AgentClassLoader.class.getClassLoader());
                }
            }
        }
    }

    public static AgentClassLoader getDefault() {
        return DEFAULT_LOADER;
    }

}
