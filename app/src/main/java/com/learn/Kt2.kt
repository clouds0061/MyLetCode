package com.learn

/**
 * 学习Kotlin第二天
 */
class Kt2 {

    //可变长参数函数
    //入参可以用vararg 关键字修饰
    fun changeableVars(vararg a: Int) {
        //for循环
        for (vars in a) println(vars)
    }

    //lambda 匿名函数
    //函数名 : (入参类型,入参类型,....) -> 返参类型 = {入参，入参，... -> 逻辑}
    val sumLambda: (Int, Int) -> Int = { x, y -> x + y }

    //定义常量与变量
    //可变变量定义 var 关键字
    // var 标识符 : 变量类型 = 初始化值
    var a: String = "123"

    //不可变变量定义 val,只能赋值一次的变量(类似java中的final修饰)
    // val 标识符 : 类型 = 值
    val b: String = "222"

    //常量与变量都可以没有初始化值,但是在引用前必须初始化
    //这句存疑，我写的时候都是报红，按下不表
    //var aa : String  (会报红)

    //编译器支持自动类型判断,即声明时可以不指定类型,由编译器判断。
    var intA = 1
    var stringA = "222"
    val intB = 2

    //可变参数 可以重新赋值
    fun testFunction() {
        intA = 5;
        stringA = "234"
    }

    //字符串模板
    // $ 表示一个变量名或者变量值
    //$varName 表示变量值
    //${varName.fun()} 表示变量的方法返回值
    var intC = 1
    var s1 = "intC is $intC"//

    fun testStringMode() {
        intC = 4
        var s2: String = "${s1.replace("is", "was")},but now is $intC"
        println(s2)
    }


    //区间
    //区间表达式由具有操作符形式 .. 的rangeTo函数辅以in 和 !in 形成
    //区间是为任何比较类型定义的，但对于整个原生类型，它有一个优化的实现。
    fun testSection() {
        for (i in 1..4) println(i)//输出1，2，3，4
        //等效于
        /**
         * for(int i = 0 ;i<5;i++){}
         */

        //使用step指定步长，即每次增加的数
        for (i in 1..4 step 2) println(i) //输出1，3
        //等效于
        /**
         * for(int i = 0 ;i<5;i=i+2){}
         */

        //使用until函数爆出结束元素
        for (i in 1 until 4) println(i) //输出1，2，3
        //等效于
        /**
         *  for (i in 1 .. 3)
         * for(int i = 0 ;i<4;i++){}
         */

        //感觉和fore fori 差不多，但是又很不一样
    }

    //数据类型
    //Kotlin 的基本数值类型包括 Byte、Short、Int、Long、Float、Double 等。不同于 Java 的是，字符不属于数值类型，是一个独立的数据类型

    //字面常量
    //下面是所有类型的字面常量：
    //十进制：123
    //长整型以大写的 L 结尾：123L
    //16 进制以 0x 开头：0x0F
    //2 进制以 0b 开头：0b00001011
    //注意：8进制不支持

    //你可以使用下划线使数字常量更易读：
    var onMillion = 1_000_000
    var idfNum = 420528_1444_01_01_1435
    var hexBytes = 0xff_aa_bb
    var longNum = 999_999_999L
    var bytes = 0b1011_0101_1111

    //比较两个数字
    // Kotlin 中没有基础数据类型，只有封装的数字类型，你每定义的一个变量，其实 Kotlin 帮你封装了一个对象，这样可以保证不会出现空指针。数字类型也一样，所以在比较两个数字的时候，就有比较数据大小和比较两个对象是否相同的区别了。
    //在 Kotlin 中，三个等号 === 表示比较对象地址，两个 == 表示比较两个值大小
    fun testCompare() {
        val a: Int = 1_000
        println(a === a) //true ,值相等，对象地址相同

        //装箱，创建两个对象
        val boxedA: Int? = a
        val boxedB: Int? = a

        //虽然装箱了，但是值相同，但对象不同，地址不同
        println(boxedA == boxedB) //true 值相同
        println(boxedA === boxedB) //false 值相同，但是对象地址不同
    }


    //类型转换
    //由于不同的表示方式，较小类型并不是较大类型的子类型，较小的类型不能隐式转换为较大的类型。 这意味着在不进行显式转换的情况下我们不能把 Byte 型值赋给一个 Int 变量。
    fun testTypeChange() {
        val a: Int = 1
        //报红
        /**
        Type mismatch.
        Required:Byte
        Found:Int
         */
//        val b : Byte = a
        val b: Byte = a.toByte()//使用方法可以转换类型

        //有些情况下也是可以使用自动类型转化的，前提是可以根据上下文环境推断出正确的数据类型而且数学操作符会做相应的重载。例如下面是正确的：
        val c = 1L + 3

    }

    //位操作符
    // 对于Int和Long类型，还有一系列的位操作符可以使用，分别是：
    //shl(bits) – 左移位 (Java’s <<)
    //shr(bits) – 右移位 (Java’s >>)
    //ushr(bits) – 无符号右移位 (Java’s >>>)
    //and(bits) – 与
    //or(bits) – 或
    //xor(bits) – 异或
    //inv() – 反向
    var orA = 4
    fun test() {
//        orA.or(3)
        println(orA.inv())
    }

    //这些操作符啥意思，教程没有说，试了下也不知道，4.inv() 后值是-5，不晓得什么个操作，先看完，看完再去搜索看看

    //字符
    //和 Java 不一样，Kotlin 中的 Char 不能直接和数字操作，Char 必需是单引号 ' 包含起来的。比如普通字符 '0'，'a'。

    //数组
    //数组用类 Array 实现，并且还有一个 size 属性及 get 和 set 方法，由于使用 [] 重载了 get 和 set 方法，所以我们可以通过下标很方便的获取或者设置数组对应位置的值。
    //数组的创建两种方式：一种是使用函数arrayOf()；另外一种是使用工厂函数。如下所示，我们分别是两种方式创建了两个数组：
    fun testArray() {
        var aArray = arrayOf(1, 2, 3, 4)
        var bArray = Array(3) { i -> (i * 2) }
        //val sumLambda: (Int, Int) -> Int = { x, y -> x + y }
        //lambda表达式还是有点不懂
        var c: (Int) -> Int = { x -> x + 2 }
        println(bArray[2])

        for (i in bArray) println(i)
        println(c(2))

        aArray.get(1) == aArray[1]

        var cArray = Array<Kt1>(4) { i -> Kt1("第${i}个Kt1") }
        var dArray = Array<Kt1>(4){
            i -> when{
                i == 0 -> Kt1("第一个Kt1,有点特殊")
                else -> Kt1("default")
            }
        }
        cArray.forEach {
            it.print()
        }
        dArray.forEach {
            it.print()
        }
    }


    //字符串
    //和 Java 一样，String 是不可变的。方括号 [] 语法可以很方便的获取字符串中的某个字符，也可以通过 for 循环来遍历：

    fun testString() {
        val str: String = "a2123a"
        for (c in str) println(c)

        //Kotlin 支持三个引号 """ 扩起来的字符串，支持多行字符串，比如
        val text = """
            |你好！
               |这是一个多行字符串，
            |牛逼吧!
            """
        println(text)

        //String 可以通过 trimMargin() 方法来删除多余的空白。
        println(text.trimMargin())

        println(text.trimMargin("|这"))
        //默认 | 用作边界前缀，但你可以选择其他字符并作为参数传入，比如 trimMargin(">")。

        //懂了就是从选定字符向前缩进到最前面，但是选定字符必须是在首位，可以是多个字符一起。不穿值默认字符"|".
    }


    //操作符
    //位操作符
    // 对于Int和Long类型，还有一系列的位操作符可以使用，分别是：
    //shl(bits) – 左移位 (Java’s <<)
    //shr(bits) – 右移位 (Java’s >>)
    //ushr(bits) – 无符号右移位 (Java’s >>>)
    //and(bits) – 与
    //or(bits) – 或
    //xor(bits) – 异或
    //inv() – 反向
    fun operatorTest() {
        var a = 1
        var b = 3

        var aOb = 0b0001
        var bOb = 0b0011

        println(a == aOb) //结果是true 两者相等，只是一个是十进制，一个是二进制
        println(a.shl(1) == aOb.shl(1))//结果是true 相等说明 这个操作符，就是把十进制转变成二进制然后做二进制的运算操作
        println(a.shl(1) == 2) //结果是 true 就是二进制左移一位
    }
}

fun main(args: Array<String>) {
//    Kt2().changeableVars(1, 2, 3, 4, 5)

//    println(Kt2().sumLambda(1, 4))

//    Kt2().testStringMode()

//    Kt2().testSection()]

//    Kt2().testCompare()

//    Kt2().test()

    Kt2().testArray()

//    Kt2().testString()

//    Kt2().operatorTest()
}