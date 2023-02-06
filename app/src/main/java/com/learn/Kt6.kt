package com.learn

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * 学习Kt第六天
 */
class Kt6 {

}

//1
//kotlin 委托
//委托模式是软件设计模式中的一项基本技巧。在委托模式中，有两个对象参与处理同一个请求，接受请求的对象将请求委托给另一个对象来处理。
//Kotlin 直接支持委托模式，更加优雅，简洁。Kotlin 通过关键字 by 实现委托

//类委托
//类的委托即一个类中定义的方法实际是调用另一个类的对象的方法来实现的。
//以下实例中派生类 Derived 继承了接口 Base 所有方法，并且委托一个传入的 Base 类的对象来执行这些方法
//形容的怎么像代理模式，大概吧

//1-1.创建接口
interface BasePrint {
    fun print()
}

//1-2.实现接口的被委托类
class BaseImpl(var input: Int) : BasePrint {
    override fun print() {
        println(input)
    }
}

//1-3.通过关键字by  建立委托
class BaseUser(var base: BasePrint) : BasePrint by base


//2.属性委托
//属性委托指的是一个类的某个属性值不是在类中直接进行定义，而是将其托付给一个代理类，从而实现对该类的属性统一管理
//属性委托语法格式：
//val/var <属性名>: <类型> by <表达式
//var/val：属性类型(可变/只读)
//属性名：属性名称
//类型：属性的数据类型
//表达式：委托代理类
//by 关键字之后的表达式就是委托, 属性的 get() 方法(以及set() 方法)将被委托给这个对象的 getValue() 和 setValue() 方法。属性委托不必实现任何接口, 但必须提供 getValue() 函数(对于 var属性,还需要 setValue() 函数)

//2-1.定义一个被委托的类
//该类需要包含 getValue() 方法和 setValue() 方法，且参数 thisRef 为进行委托的类的对象，prop 为进行委托的属性的对象
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef,这里委托了${property.name}属性"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的${property.name}属性赋值为$value")
    }
}

//2-2.定义包含属性委托的类
class DelegateUser {
    var info: String by Delegate()//牛逼 这个委托也不用初始化欸
}


//标准委托
//Kotlin 的标准库中已经内置了很多工厂方法来实现属性的委托
//3.延迟属性 Lazy
//lazy() 是一个函数, 接受一个 Lambda 表达式作为参数, 返回一个 Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托： 第一次调用 get() 会执行已传递给 lazy() 的 lamda 表达式并记录结果， 后续调用 get() 只是返回记录的结果
//3-1 定义val属性的lazy委托
class TestLazy {
    val lazyValue: String by lazy {
        println("?????") //这个是第一次调用会跑的地方，以后调用不会走这里

        "hello world"//这是后续运行会调用的地方
    }

    //不能做var属性的委托。报红
    val lazyValue2: Int by lazy {
        var vaule = 1 + 2 //第一次可以使用一个求值等
        vaule
    }
}

val lazyValue: String by lazy {
    println("?????") //这个是第一次调用会跑的地方，以后调用不会走这里

    "hello world"//这是后续运行会调用的地方
}

//不能做var属性的委托。报红
val lazyValue2: Int by lazy {
    var vaule = 1 + 2 //第一次可以使用一个求值等
    vaule
}

//4.可观察属性 Observable
//observable 可以用于实现观察者模式。
//Delegates.observable() 函数接受两个参数: 第一个是初始化值, 第二个是属性值变化事件的响应器(handler)。
//在属性赋值后会执行事件的响应器(handler)，它有三个参数：被赋值的属性、旧值和新值
//4-1.这教程也太少了，你特么的写上来搞jb
class ObservableUser {
    var info: String by Delegates.observable("初始化信息") { property, oldValue, newValue ->
        println("${property.name} 的 oldValue = $oldValue , newValue = $newValue")
    }
}

//5.把属性储存在映射中
//一个常见的用例是在一个映射（map）里存储属性的值。 这经常出现在像解析 JSON 或者做其他"动态"事情的应用中。 在这种情况下，你可以使用映射实例自身作为委托来实现委托属性
//5-1.构建一个类，属性是by的map
class MapUser(val map: Map<String, Any>) {
    //Type 'Map<String, Any>' has no method 'setValue(MapUser, KProperty<*>, String)' and thus it cannot serve as a delegate for var (read-write property)
    //这里只能用val，要不然报红
    val name: String by map
    val url: String by map
}

//如果使用 var 属性，需要把 Map 换成 MutableMap
class MapUserVar(var map: MutableMap<String, String>) {
    var name: String by map
    var url: String by map
}


//6 Not Null
//notNull 适用于那些无法在初始化阶段就确定属性值的场合
//6-1 定义notNull属性
class TestNotNull {
    var notNull: String by Delegates.notNull<String>()
}

//7.局部委托属性
//你可以将局部变量声明为委托属性。 例如，你可以使一个局部变量惰性初始化
//这个示例代码真的无头无尾...菜鸟的编辑是累了么，眼看就最后几个了随便copy了一段代码？
fun partDelegation(computeFoo: () -> TestNotNull) {
    val memoizedFoo by lazy(computeFoo)
    val condition = true
    memoizedFoo.notNull = "不懂...."
    if (condition) {
        println(memoizedFoo.notNull)
    }
}


//属性委托要求
//对于只读属性(也就是说val属性), 它的委托必须提供一个名为getValue()的函数。该函数接受以下参数：
//    thisRef —— 必须与属性所有者类型（对于扩展属性——指被扩展的类型）相同或者是它的超类型
//    property —— 必须是类型 KProperty<*> 或其超类型
//这个函数必须返回与属性相同的类型（或其子类型）。
//对于一个值可变(mutable)属性(也就是说,var 属性),除 getValue()函数之外,它的委托还必须 另外再提供一个名为setValue()的函数, 这个函数接受以下参数:
//    property —— 必须是类型 KProperty<*> 或其超类型
//    new value —— 必须和属性同类型或者是它的超类型。



fun main(args: Array<String>) {
//    //1-4 使用委托
//    var base = BaseImpl(12)
//    BaseUser(base).print()
//    //这么写的好处呢....
//
    //2-3 使用委托属性
    var user = DelegateUser()
    println(user.info)//访问属性 调用getValue()方法

    user.info = "hello world" //调用setValue()方法
    println(user.info)
    //？？？？ 我辣么打一个user的属性的值呢？ 怎么拿啊？你给我一个被委托的属性的名字有个jb用

    //3-2 使用lazy委托
    println(TestLazy().lazyValue)//第一次
    println(TestLazy().lazyValue)//第二次

    println("------------------")
    //出现差异了
    println(lazyValue)//第一次
    println(lazyValue)//第二次
    println("------------------")
    var testLazy = TestLazy()
    println(testLazy.lazyValue)//第一次
    println(testLazy.lazyValue)//第二次

    println("------------------")
    //那没问题了，使用TestLazy().lazyValue的写法，应该是每次都算第一次
    println("${TestLazy().hashCode()} || ${TestLazy().hashCode()}")
    //940060004 || 234698513
    //这种使用方法，每次都是一个新对象，hashcode值不一样
//
    //4-2 使用
    var observableUser  = ObservableUser()
    observableUser.info = "第一次赋值"
    observableUser.info = "第二次赋值"
    //所以 这个的用处呢...欸菜鸟教程是真的垃圾 算了看完再看看别的吧


    //5-2 使用
    var mapUser = MapUser(
        mapOf(
            "name" to "垃圾菜鸟教程",
            "url" to "www.lajiRunoob.com"
        )
    )
    //emmm 本来想到了一个用法，在Adapter里面好像，但是Array并不能这么用
    println(mapUser.name)
    println(mapUser.url)
    println("---------------")
    //var的用法
    var mapUser2 = MapUserVar(
        mutableMapOf(
            "name" to "菜鸟教程真的rua圾",
            "url" to "www.ruajiRunoob.com"
        )
    )
    println(mapUser2.name)
    println(mapUser2.url)

    //6-2 使用
    var notNull = TestNotNull()
    notNull.notNull = "real not null~"
    println(notNull.notNull)
    //emmm 我也不知道意思是啥
}
















