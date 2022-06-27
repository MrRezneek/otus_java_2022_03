package ru.otus.autologging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Ioc {

    private Ioc() {
    }

    static TestLogging createMyClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLogging myClass;
        private final List<Method> annotatedMethods;

        DemoInvocationHandler(TestLogging myClass) {
            this.myClass = myClass;
            annotatedMethods = new ArrayList<>();
            var methods = myClass.getClass().getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Log.class)) {
                    annotatedMethods.add(method);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            var classMethod = myClass.getClass().getMethod(method.getName(), method.getParameterTypes());

            if (annotatedMethods.contains(classMethod)) {
                StringBuilder parameters = new StringBuilder();
                Parameter[] methodParameters = method.getParameters();
                for (int i = 0; i < args.length; i++) {
                    parameters.append(methodParameters[i].getName()).append(": ").append(args[i]).append(", ");
                }
                parameters.delete(parameters.length() - 2, parameters.length());
                System.out.println("executed method: " + method.getName() + ", " + parameters);
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
