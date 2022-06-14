package ru.otus.testprocessor;

import ru.otus.testprocessor.annotations.After;
import ru.otus.testprocessor.annotations.Befor;
import ru.otus.testprocessor.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestProcessor {

    public static void process(String className) throws ClassNotFoundException {

        Class<?> processedClass = Class.forName(className);

        // Отбираем методы помеченные аннотациями
        Map<Class<?>, List<Method>> methodsMap = pickAnnotatedMethods(processedClass);

        // Запуск методов
        TestStatistic testStatistic = runTests(processedClass, methodsMap);

        // Выводим статистику
        printStatistics(testStatistic);
    }

    private static HashMap<Class<?>, List<Method>> pickAnnotatedMethods(Class<?> processedClass) {
        var methodsMap = new HashMap<Class<?>, List<Method>>();

        List<Method> beforeMethods = new ArrayList<>();
        List<Method> afterMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();

        Method[] allMethods = processedClass.getDeclaredMethods();

        for (var method : allMethods) {
            if (method.isAnnotationPresent(Befor.class)) {
                beforeMethods.add(method);
            }

            if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }

            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }
        }

        methodsMap.put(Befor.class, beforeMethods);
        methodsMap.put(After.class, afterMethods);
        methodsMap.put(Test.class, testMethods);

        return methodsMap;
    }

    private static TestStatistic runTests(Class<?> processedClass, Map<Class<?>, List<Method>> mapMethods) {
        int successfulCount = 0;
        int errorCount = 0;
        int allCount = 0;

        List<Method> beforeMethods = mapMethods.get(Befor.class);
        List<Method> afterMethods = mapMethods.get(After.class);
        List<Method> testMethods = mapMethods.get(Test.class);

        for (var testMethod : testMethods) {
            allCount++;

            var obj = ReflectionHelper.instantiate(processedClass);

            //Вызываем методы Before
            for (var beforeMethod : beforeMethods) {
                beforeMethod.setAccessible(true);
                try {
                    beforeMethod.invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            //Вызываем методы Test
            testMethod.setAccessible(true);
            try {
                testMethod.invoke(obj);
                successfulCount++;
            } catch (Exception e) {
                System.out.println("Test failed");
                errorCount++;
            }

            //Вызываем методы After
            for (var afterMethod : afterMethods) {
                afterMethod.setAccessible(true);
                try {
                    afterMethod.invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            System.out.println();
        }

        return new TestStatistic(successfulCount, errorCount, allCount);
    }

    private static void printStatistics(TestStatistic testStatistic) {
        System.out.println("Результаты тестирования:");
        System.out.println("Прошло успешно " + testStatistic.getSuccessfulCount());
        System.out.println("Не прошло " + testStatistic.getErrorCount());
        System.out.println("Всего тестов " + testStatistic.getAllCount());
    }
}
