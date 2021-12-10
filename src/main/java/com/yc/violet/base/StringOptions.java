package com.yc.violet.base;

/**
 * @author 姚琛
 * @description 针对各种String操作的记录
 * 参考：https://blog.csdn.net/qq_35692642/article/details/115409764
 *      https://blog.csdn.net/weixin_40208575/article/details/104528388
 *      https://www.cnblogs.com/mjyung/p/12688295.html
 *      https://blog.csdn.net/weixin_34413802/article/details/88009934
 * @remarks JDK版本1.8, 注释中的常量池，均指 堆中的字符串常量池
 * @date 2021/12/10
 */
public class StringOptions {
    public static void main(String[] args) {
        section7();
    }

    public static void section1() {
        /**
         *   1.直接定义的String变量，会存在字符串常量池中
         *   2.在编译后final关键字会被优化掉，详见javap反编字节码文件
         *   3.使用String构造方法创建出的字符串对象，对象会在堆中创建；同时将该字符串放置在常量池中
         */
        String str = "abc";
        final String str1 = "ab";
        String str2 = new String("abc");
        System.out.println(str == str2);
    }

    public static void section2() {
        /**
         * 使用javap反编后可看到，字符串对象使用“+”符号做添加操作时，会使用StringBuilder.append()方法进行连接，
         * 且最终调用toString()方法获取结果。此方法内部实现为new String()，会在堆中创建String对象,此操作并不会将字符串放入常量池
         * str1指向字符串常量池中的“abcd”
         * str3指向堆中的String对象
         */
        String str1 = "abcd";
        String str2 = "abc";
        String str3 = str2 + "d";
        System.out.println(str3 == str1);
    }

    public static void section3() {
        /**
         * Q:此行代码在运行过程中，共创建了几个对象？
         * A:首先是两个堆中创建的String对象,以及放置在常量池中的字符串("ab","c")本身,
         * 做连接操作的StringBuilder,以及StringBuilder拼接后得到的String对象.
         * 共6个
         */
        String str1 = new String("ab") + new String("c");
    }

    public static void section4() {
        /**
         * 1.堆中创建String对象，同时在字符串常量池放置字符串“ab”
         * 2.intern()会在字符串常量池查找与str1内容相同的字符串或引用,此时常量池中已存在字符串ab
         * str1指向堆中的String对象，str2执行字符串常量池中的"ab",str3同str2(指向同一对象)
         */
        String str1 = new String("ab");
        String str2 = str1.intern();
        String str3 = "ab";
        System.out.println(str1 == str2);//false
        System.out.println(str1 == str3);//false
        System.out.println(str2 == str3);//true
    }

    public static void section5() {
        /**
         * 1.常量池中放置字符串“ab”
         * 2.堆中创建String对象
         * 3.在常量池中查找与str2等值的字符串或引用
         * str1与str3指向常量池中同一字符串“ab”，str2指向堆中的String对象
         */
        String str1 = "ab";
        String str2 = new String("ab");
        String str3 = str2.intern();
        System.out.println(str1 == str2);//false
        System.out.println(str1 == str3);//true
        System.out.println(str2 == str3);//false
    }

    public static void section6() {
        /**
         * 1.堆中创建a,b,ab以及StringBuilder对象，字符串常量池中放置"a","b"字符串
         * 2.到字符串常量池中查找与str1("ab")等值的字符串或引用，结果未找到，因此将str1引用放置字符串常量池中，并返回
         * 3.str3在字符串常量池中查找，最终指向堆中的String对象(“ab”)
         * 最终三个对象均指向str1在堆中创建的“ab”对象
         */
        String str1 = new String("a") + new String("b");
        String str2 = str1.intern();
        String str3 = "ab";
        System.out.println(str1 == str2);//true
        System.out.println(str1 == str3);//true
        System.out.println(str2 == str3);//true
    }
    public static void section7() {
        /**
         * 1.在常量池中放置“ab”对象
         * 2.在堆中创建String对象
         * 3.在常量池中查找与str2等值的字符串或引用
         * str1和str3指向字符串常量池中的“ab”,str2指向堆中的String对象
         */
        String str1 = "ab";
        String str2 = new String("a") + new String("b");
        String str3 = str2.intern();
        System.out.println(str1 == str2);//false
        System.out.println(str1 == str3);//true
        System.out.println(str2 == str3);//false
    }
}
