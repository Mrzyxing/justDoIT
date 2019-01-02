package x.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.Eval;
import groovy.util.GroovyScriptEngine;
import java.io.IOException;
import java.util.Date;

/**
 * Created by zyxing on 2018/11/8.
 */
public class Main {

    static String rootPath = Main.class.getResource("/").getPath();
    static GroovyScriptEngine groovyScriptEngine;

    static {
        try {
            groovyScriptEngine = new GroovyScriptEngine(rootPath + "../../ext");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        runEval();
        runSingleScript();
        runClassLoader();
        runScriptEngine();
    }

    /**
     * 最简单的方式 表达式简单可以这么用
     * 不会缓存 每次需要编译
     */
    public static void runEval() {
        System.out.println(Eval.x(4, "2*x"));
        System.out.println(Eval.me("k", 4, "2*k"));
    }

    /**
     * 可以加载类
     */
    public static void runClassLoader() {
        GroovyClassLoader gcl = new GroovyClassLoader();
        Class clazz1 = gcl.parseClass("3*5");
    }

    /**
     * 单个脚本 非线程安全
     * 会缓存编译好的class 但是需要注意如果脚本是text会导致每次创建的class不可回收进而导致perm gen OOM
     * text不可回收的原因是每次的classname是随机生成的 导致缓存的class无法回收
     * 避免的办法是用file或者每次clean cache:shell.getClassLoader().clearCache()
     */
    public static void runSingleScript() {
        GroovyShell shell = new GroovyShell();
        System.out.println(shell.evaluate("3*5"));

        Script script = shell.parse("3*5");
        System.out.println(script.run());

        shell.setProperty("text", "I am shared data!");
        shell.setProperty("date", new Date());
        System.out.println(shell.evaluate("\"At $date,$text\""));
    }

    public static void runScriptEngine() throws Exception {
        Binding binds = new Binding();
        while (true) {
            Object ret = groovyScriptEngine.run("sayHello.groovy", binds);
            Object exeMethodRet = ret.getClass().getMethod("sayHello").invoke(ret);
            Thread.sleep(1000L);
        }
    }

}
