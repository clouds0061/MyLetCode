package com.learn

/**
 * 学习第三天
 */
class Kt3 constructor() {

    //Kotlin 条件控制
    //IF表达式
    //和java差不太多
    fun ifTest(b: Int) {
        var max = 1
        if (max < b) max = b
        else max = 1

        println(max)
        //也可以作为表达式 可以赋值给变量 所以不用像java一样 写三元操作符
        var a = if (b < 3) 3 else b
        println(a)
    }


    //使用区间
    fun sectionTest(a: Int) {
        if (a in 1..5) println("$a is in [1,5]")
    }

    //When表达式
    //when 将它的参数和所有的分支条件顺序比较，直到某个分支满足条件。
    //when 既可以被当做表达式使用也可以被当做语句使用。如果它被当做表达式，符合条件的分支的值就是整个表达式的值，如果当做语句使用， 则忽略个别分支的值。
    //when 类似其他语言的 switch 操作符。
    fun testWhen(a: Int) {
        //最简单的形式
        when (a) {
            1 -> println("a is 1")
            2 -> println("a is 2")
            else -> println("a is not 1 or 2")//else 相当于java switch语句中的default分支
        }

        //如果分支处理方式相同，分支可以合并，用都好分隔
        when (a) {
            1, 2 -> println("a is $a")
            else -> println("a is not 1 or 2")
        }

        //我们也可以检测一个值在（in）或者不在（!in）一个区间或者集合中
        var aList = Array(6) { i -> i }
        for (i in aList) println(i)
        when (a) {
            in 1..5 -> println("a is in [1,5]")
            !in 1..5 -> println("a is not in [1,5]")
            in aList -> println("a is in aList")
            else -> println("a is $a")
        }
        // 第一个分支范围是[1,5],第三个分支范围是[0,5] 但是只走了第一个分支，所以这个是互斥的。

        //另一种可能性是检测一个值是（is）或者不是（!is）一个特定类型的值。注意： 由于智能转换，你可以访问该类型的方法和属性而无需 任何额外的检测。
        var x: Any = "1234"
        var booleanX: Boolean = when (x) {
            is String -> x.endsWith("4")
            else -> false
        }

        //when 也可以用来取代 if-else if链。 如果不提供参数，所有的分支条件都是简单的布尔表达式，而当一个分支的条件为真时则执行该分支
        when {
            booleanX -> println("x is endWith 4")
            else -> println("x is not endWith 4")
        }

        when {
            a in aList -> println("$a is in aList")
            a in 1..6 -> println("$a is in [1,6]")
            else -> println("")
        }
        //写法多变，但是所有判断都是互斥的，只要有ture的分支达成，后面的分支是不会去判断的。
    }

    //Kotlin 循环控制
    //FOR循环
    fun testFor() {
        var items = Array<String>(3) { i -> "第${i}个apple" }
        var items2 = listOf<String>("apple", "orange", "banana")
        //第一种
        for (item in items) {
            println(item)
        }
        //第二种能获取到下标
        for (index in items2.indices) {
            println("第${index}个元素是:${items2[index]}")
        }
    }


    //while 与 do...while 循环
    //do…while 循环 对于 while 语句而言，如果不满足条件，则不能进入循环。但有时候我们需要即使不满足条件，也至少执行一次。
    //do…while 循环和 while 循环相似，不同的是，do…while 循环至少会执行一次
    //早期的 Kotlin 版本函数参数是可以定义成 var 的，不过后来官方明确了「函数参数都是不可变」 所以方法入参默认都是val了 我说怎么报红了
    public fun testWhile(a: Int): Unit {
        //第一种while循环
        var b = a
        while (b > 4) {
            println("当前b=${b},值大于4，继续循环-1")
            b--
        }
        println("--------------")
        //第二种do-while  不论是否满足条件，总会走一次逻辑代码
        var c = a
        do {
            when {
                c <= 4 -> println("当前c=${c},值小于等于4，会跳出循环")
                c > 4 -> println("当前c=${c},值大于4，继续循环-1")
                else -> println("这是个啥？")
            }
            c--
        } while (c > 4)
        println("--------------")
    }


    //返回和跳转
    //Kotlin 有三种结构化跳转表达式：
    //    return。默认从最直接包围它的函数或者匿名函数返回。
    //    break。终止最直接包围它的循环。
    //    continue。继续下一次最直接包围它的循环。
    //和java没什么不同

    //Break 和 Continue 标签
    //Kotlin 有函数字面量、局部函数和对象表达式。因此 Kotlin 的函数可以被嵌套。 标签限制的 return 允许我们从外层函数返回。 最重要的一个用途就是从 lambda 表达式中返回
    //这个java也有，但是kotlin不一样，好像能用的范围更广了
    fun testLabel() {
        var ints = listOf<Int>(1, 2, 3, 4, 5, 6, 7)
        var items = listOf<String>("apple", "orange", "banana")
        //普通的return 这个 return 表达式从最直接包围它的函数即 testLabel 中返回。
        items.forEach {
            if (it.equals("orange")) {
//                return println("在 $it 处返回")
            }
            println(it)
        }
        println("-------------")
        //带标签的返回
        items.forEach forEach@{
            if (it.equals("orange")) return@forEach println("在 $it 处返回")//这里返回了循环，等于是没返回
            println(it)
        }

        //试试continue 和 break
        println("-------------")
        ints@ for (i in ints) {
            println("i = $i")
            items@ for (item in items) {
                when {
                    item.equals("orange") && i == 1 -> continue@ints
                    i == 2 -> continue@items//这个 预期i=2时，不会打印所有水果名字，应为continue去了上层循环
                    i == 3 -> break@items // 这个 预期i==3时，不会打印水果名字，因为break掉了这次
                    i == 5 -> break@ints //直接跳出了循环
                    else -> println("i = $i , items = $item")
                }
            }
            println("********")
        }
        //这个标签有点意思啊，这个控制，就是容易自己晕了，所以写的时候必须写好注释，把行为逻辑写清楚
    }


    //类和对象
    //和java差不多，没有new关键字
    var kt3 = Kt3()

    //类的属性
    var age = 12
    var height = 145
    val location = "上海市闵行区啦啦啦小区12243123号"

    //使用
    fun testAttribute() {
        //属性的使用和java差不多
        var newAge = kt3.age + 20

    }

    //Kotlin 中的类可以有一个 主构造器，以及一个或多个次构造器，主构造器是类头部的一部分，位于类名称之后:
    //就是类名后面括号部分就是构造器，Kt3的主构造器就是一个什么都不传，什么都不返回的
    //次级构造器
    constructor(age: Int, height: Int) : this() {
        this.age = age
        this.height = height
    }
    //如果主构造器没有任何注解，也没有任何可见度修饰符，那么constructor关键字可以省略,就是类名上面的可以省略
}

fun Extend.PrintInKt3(){
    println("在Kt3中的扩展")
}

fun main(args: Array<String>) {
//    Kt3().ifTest(6)
//    Kt3().sectionTest(2)
//    Kt3().testWhen(5)
//    Kt3().testFor()
//    Kt3().testWhile(7)
//    println("*****************")
//    Kt3().testWhile(3)
    Kt3().testLabel()

}