package com.bufoon.commons.utils;

import com.alibaba.fastjson.JSON;
import com.bufoon.commons.base.ProjectInfo;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @Author: bufoon
 * @Email: 285395841@qq.com
 * @Datetime: Created In 2018/2/12 15:59
 * @Desc: as follows.
 */
public class GroovyUtils {

    /**
     * 执行groovy脚本，ScriptEngine方式
     * @param script 脚本
     * @param method 方法名
     * @param params 参数
     * @return
     */
    public static Object excuteScript(String script, String method, Object[] params) {
        try {
            ScriptEngineManager factory = new ScriptEngineManager();
            ScriptEngine engine = factory.getEngineByName("groovy");
            engine.eval(script);
            Invocable inv = (Invocable) engine;
            return inv.invokeFunction(method, params);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行groovy脚本，GroovyShell方式
     * @param binding 需要绑定的变量
     * @param scriptStr 脚本
     * @param method 脚本方法
     * @param args 参数，是一个数组，顺序与脚本方法参数顺序保持一致
     * @return
     */
    public static Object excuteScript(Binding binding, String scriptStr, String method, Object ...args){
        GroovyShell shell = new GroovyShell(binding);
        if (StringUtils.isEmpty(method)){
            return shell.evaluate(scriptStr);
        }
        Script script = shell.parse(scriptStr);
        return script.invokeMethod(method, args);
    }

    public static String test(){
        return "调用java方法";
    }

    public static void main(String[] args) {
        String scripts = "def hello(language) {def obj = [:];" +
                "obj['id'] = language.id;" +
                "obj['name'] = language.name;" +
                "obj['desc'] = language.desc;" +
                "return obj}";
        ProjectInfo appInfo = new ProjectInfo();
        appInfo.setId("");
        appInfo.setName("test");
        appInfo.setDesc("hahaha");
        Object[] params = new Object[]{appInfo};
        Object object = excuteScript(scripts, "hello", params);
        System.out.println(JSON.toJSONString(object));
        Binding binding = new Binding();
        binding.setVariable("foo", new Integer(2));
        binding.setVariable("test1", appInfo);
        GroovyShell shell = new GroovyShell(binding);

        String script = "import com.rttx.commons.utils.GroovyUtils;import java.text.SimpleDateFormat;" +
                "class Test{int id;String name;String desc;};"
                + "def a = 12; println 'C# md5:'; " +
                "Test test = new Test();" +
                "test.id = 2;" +
                "test.name = 'nihao';" +
                "test.desc = 'asdfasdfsdf';"
                + "return 90*24*3600*1000L;";
        System.out.println("-------------  " + excuteScript(binding, script, "", null));
        script = "import com.rttx.commons.utils.IdCardUtils;import java.text.SimpleDateFormat;" +
                "def join(String a, String b) {SimpleDateFormat format = new SimpleDateFormat(\"yyyy-MM-dd\");\n" +
                "Date entryDate = format.parse(\"2018-02-01\");\n" +
                "long oneDay = 24*3600*1000L;\n" +
                "long jobTime = (new Date().getTime()-entryDate.getTime())/oneDay;\n" +
                "int forbidTime = 90;\n" +
                "println jobTime;" +
                "return jobTime < forbidTime}";
        System.out.println(excuteScript(binding, script, "join", new String[]{"*************", "hh*************"}));
        Script script1 = shell.parse(script);
        System.out.println("****** " + script1.invokeMethod("join", new String[]{"asdf", "321321"}));
        Object value = shell.evaluate(script);
        System.out.println(value.toString());

    }
}
