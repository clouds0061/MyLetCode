package com.learn

import android.provider.ContactsContract.Data
import android.support.v4.app.INotificationSideChannel

/**
 * 学习Kotlin第五天
 */
class Kt5 {
    // 定义泛型类型变量，可以完整地写明类型参数，如果编译器可以自动推定类型参数，也可以省略类型参数。
    //Kotlin 泛型函数的声明与 Java 相同，类型参数要放在函数名的前面
    fun <T> setUpBox(value: T): Box<T> {
        return Box(value)
    }

    //自 Kotlin 1.1 起，可以使用 enumValues<T>() 和 enumValueOf<T>() 函数以泛型的方式访问枚举类中的常量 ：
    inline fun <reified T : Enum<T>> printAllValues() {
        println(enumValues<T>().joinToString {
            "${it.ordinal} : ${it.name}"
        })
    }
}

//Kotlin 泛型
//泛型，即 "参数化类型"，将类型参数化，可以用在类，接口，方法上。
//与 Java 一样，Kotlin 也提供泛型，为类型安全提供保证，消除类型强转的烦恼

//定义一个泛型
class Box<T>(t: T) {
    var value = t
        get() = field
        set(value) {
            when (value) {
                is String -> println("String ")
                is Int -> println("Int")
                is Indian -> println("Indian")
                else -> println("Any")
            }
            field = value
        }

}


//泛型约束
//没咋看懂，这也太随意了

//Kotlin 枚举类
//枚举类最基本的用法是实现一个类型安全的枚举。
//枚举常量用逗号分隔,每个枚举常量都是一个对象
enum class Color {
    BLUE, RED, ORANGE, PINK
}

//枚举初始化
//每一个枚举都是枚举类的实例，它们可以被初始化
enum class Colors(var rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

//Kotlin 对象表达式和对象声明
//Kotlin 用对象表达式和对象声明来实现创建一个对某个类做了轻微改动的类的对象，且不需要去声明一个新的子类
//对象表达式
//通过对象表达式实现一个匿名内部类的对象用于方法的参数中
fun testObjectExpression() {

}

//对象可以继承于某个基类，或者实现其他接口
open class A(a: Int) {
    public open val aa: Int = a
}

interface B


//对象声明
//Kotlin 使用 object 关键字来声明一个对象。
//Kotlin 中我们可以方便的通过对象声明来获得一个单例
object Date {
    fun print() {
        println("$data")
    }

    var data: Int = 1
}

//伴生对象
//类内部的对象声明可以用 companion 关键字标记，这样它就与外部类关联在一起，我们就可以直接通过外部类访问到对象的内部元素
class OutClass{
    companion object Single{
        fun instance():OutClass = OutClass()
    }
}


fun main(args: Array<String>) {
    //创建类的实例时我们需要指定类型参数
//    val box: Box<Int> = Box(1)
//    box.value = 2
//    val box2: Box<Indian> = Box(Indian("阿三", 22, 42.2))
//
//    var (age, name, weight) = box2.value
//    println(box.value)
//    println(box2.value)
//    println("$age,$name,$weight")
//
//    var box3 = Kt5().setUpBox(European("jack",21,55.5,"伯爵"))
//    println("${box3.value}")


//    //Kotlin 中的枚举类具有合成方法，允许遍历定义的枚举常量，并通过其名称获取枚举常数
//    var color: Color = Color.BLUE
//    println(Color.values())// 以数组的形式，返回枚举值
//    println(Color.valueOf("RED"))// 转换指定 name 为枚举值，若未匹配成功，会抛出IllegalArgumentException
//    println(color.name)//获取枚举名称
//    println(color.ordinal)//获取枚举值在所有枚举数组中定义的顺序
//
//    Kt5().printAllValues<Color>()
//    //自 Kotlin 1.1 起，可以使用 enumValues<T>() 和 enumValueOf<T>() 函数以泛型的方式访问枚举类中的常量
//    println(enumValues<Color>().joinToString { it.name })


    //对象可以继承于某个基类，或者实现其他接口
    //如果超类型有一个构造函数，则必须传递参数给它。多个超类型和接口可以用逗号分隔。
//    var c = object : A(1), B {
//        override val aa = 12
//    }
//    println(c.aa)
//    //通过对象表达式可以越过类的定义直接得到一个对象
//    var site = object {
//        var name: String = "张三"
//        var age: Int = 16
//    }
//    println("name = ${site.name} ||  age = ${site.age}")


    //引用该对象，我们直接使用其名称即可
    Date.print()
    //当然你也可以定义一个变量来获取这个对象，当时当你定义两个不同的变量来获取这个对象时，你会发现你并不能得到两个不同的变量。也就是说通过这种方式，我们获得一个单例
    var date1 = Date
    var date2 = Date
    date1.data = 2222
    println(date1.data == date2.data)

    //类内部的对象声明可以用 companion 关键字标记，这样它就与外部类关联在一起，我们就可以直接通过外部类访问到对象的内部元素
    var instance = OutClass.instance()
    //注意：一个类里面只能声明一个内部关联对象，即关键字 companion 只能使用一次。
    //请伴生对象的成员看起来像其他语言的静态成员，但在运行时他们仍然是真实对象的实例成员。

    //对象表达式和对象声明之间的语义差异
    //对象表达式和对象声明之间有一个重要的语义差别：
    //    对象表达式是在使用他们的地方立即执行的
    //    对象声明是在第一次被访问到时延迟初始化的
    //    伴生对象的初始化是在相应的类被加载（解析）时，与 Java 静态初始化器的语义相匹配

}