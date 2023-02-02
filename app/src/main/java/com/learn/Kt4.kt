package com.learn

/**
 * 学习第四天
 * 类和对象
 */
class Kt4 {

    //我们可以像使用普通函数那样使用构造函数创建类实例
    var helloWorld = HelloWorld()

    //要使用一个属性，只要用名称引用它即可
    var name = helloWorld.name
    var age = helloWorld.age

    //getter 和 setter
    //属性声明的完整语法：
    //var <propertyName>[: <PropertyType>] [= <property_initializer>]
    //    [<getter>]
    //    [<setter>]
    var height: Int = 144
        //Kotlin 中类不能有字段。提供了 Backing Fields(后端变量) 机制,备用字段使用field关键字声明,field 关键词只能用于属性的访问器
        get() = field
        set(value) {
            when {
                value > 100 -> field = 100
                value <= 100 -> field = value
                else -> field = -1
            }
        }

    //getter 和 setter 都是可选
    //如果属性类型可以从初始化语句或者类的成员函数中推断出来，那就可以省去类型，val不允许设置setter函数，因为它是只读的
    //大部分属性 getter和setter方法用默认的就好，除非需要添加自己特殊的逻辑


    constructor(helloWorld: HelloWorld, name: String, age: Int, height: Int) {
        this.helloWorld = helloWorld
        this.name = name
        this.age = age
        this.height = height
    }

    var str: String? = null//？表示允许为空null

    //非空属性必须在定义的时候初始化,kotlin提供了一种可以延迟初始化的方案,使用 lateinit 关键字描述属性
    lateinit var person: Person

    //    lateinit var ages:Int //'lateinit' modifier is not allowed on properties of primitive types  报红提示基本数据不能用这个，所以这个延迟初始化只能用在对象上
    lateinit var arr: Array<String>
}

//Kotlin 类可以包含：构造函数和初始化代码块、函数、属性、内部类、对象声明。
//Kotlin 中使用关键字 class 声明类，后面紧跟类名

class HelloWorld {
    //可以在类中定义成员函数
    fun hello() {
        println("hello world")
    }

    //类的属性
    //类的属性可以用关键字 var 声明为可变的，否则使用只读关键字 val 声明为不可变
    var name: String = ""
    var age: Int = 0
}

//我们也可以定义一个空类
class Empty

//Kotlin 中的类可以有一个 主构造器，以及一个或多个次构造器，主构造器是类头部的一部分，位于类名称之后
class Person constructor(name: String) {
    //主构造器
    //主构造器中不能包含任何代码，初始化代码可以放在初始化代码段中，初始化代码段使用 init 关键字作为前缀
    init {
        println("name is $name")
    }

    //次构造函数
    //类也可以有二级构造函数，需要加前缀 constructor
    //如果类有主构造函数，每个次构造函数都要，或直接或间接通过另一个次构造函数代理主构造函数。在同一个类中代理另一个构造函数使用 this 关键字
    constructor(name: String, age: Int) : this(name) {

    }
    //Teacher的构造函数是private的，所以不能访问 新建对象
//    var teacher = Teacher()  //报红
}

class Teacher private constructor() {
    //如果一个非抽象类没有声明构造函数(主构造函数或次构造函数)，它会产生一个没有参数的构造函数。构造函数是 public 。如果你不想你的类有公共的构造函数，你就得声明一个空的主构造函数
}

//如果主构造器没有任何注解，也没有任何可见度修饰符，那么constructor关键字可以省略
class Student(name: String, age: Int) {

}

//抽象类
open class Base {
    open fun f() {}
}

abstract class Floor : Base() {
    override fun f() {}
}

class SecondFloor : Floor() {

    //扩展声明为成员
    //在一个类内部你可以为另一个类声明扩展。
    //在这个扩展中，有个多个隐含的接受者，其中扩展方法定义所在类的实例称为分发接受者，而扩展方法的目标类型的实例称为扩展接受者
    fun Extend.FloorPrint(out: String) {
        println(out)
    }

    fun ExtendCaller(extend: Extend, out: String) {
        extend.FloorPrint(out)
    }

    override fun f() {

    }

    var height = 5

    //嵌套类
    //好像就是写在别人类内部的类，互相是独立的，只是寻址路径多了一层类名
    class Window {
        var windowNum = 12
        var floorHeight = SecondFloor().height//相当于互相独立的类,拿属性要按规矩办事
    }

    //内部类
    //内部类使用 inner 关键字来表示。
    //内部类会带有一个对外部类的对象的引用，所以内部类可以访问外部类成员属性和成员函数
    inner class Bed() {
        var bedName = "白天鹅"

        //为了消除歧义，要访问来自外部作用域的 this，我们使用this@label，其中 @label 是一个 代指 this 来源的标签。
        var target = this@SecondFloor

        //可以直接访问外部类的属性，因为持有了外部类的引用
        var floorHeight = height
            get() {
                return target.height
            }
    }

    fun setTable(table: Table) {
        table.location()
    }

    //匿名内部类
    //使用对象表达式来创建匿名内部类
    /**
     * 采用对象表达式来创建接口对象，即匿名内部类的实例。
     */
    var location = this.setTable(object : Table {
        override fun location() {
            println("把桌子放到窗户边上")
        }
    })

    //类的修饰符
    //类的修饰符包括 classModifier 和_accessModifier_
    //    classModifier: 类属性修饰符，标示类本身特性。
    //    abstract    // 抽象类
    //    final       // 类不可继承，默认属性
    //    enum        // 枚举类
    //    open        // 类可继承，类默认是final的
    //    annotation  // 注解类
    //
    //    accessModifier: 访问权限修饰符
    //    private    // 仅在同一个文件中可见
    //    protected  // 同一个文件中或子类可见
    //    public     // 所有调用的地方都可见
    //    internal   // 同一个模块中可见
}

interface Table {
    fun location()
}


//Kotlin 继承
//Kotlin 中所有类都继承该 Any 类，它是所有类的超类，对于没有超类型声明的类是默认超类
//如果一个类要被继承，可以使用 open 关键字进行修饰。

//接口和继承都差不多

//Kotlin 扩展
//Kotlin 可以对一个类的属性和方法进行扩展，且不需要继承或使用 Decorator 模式。
//扩展是一种静态行为，对被扩展的类代码本身不会造成任何影响
//扩展函数
//
//扩展函数可以在已有类中添加新的方法，不会对原类做修改，扩展函数定义形式：
//fun receiverType.functionName(params){
//    body
//}
//    receiverType：表示函数的接收者，也就是函数扩展的对象
//    functionName：扩展函数的名称
//    params：扩展函数的参数，可以为NULL

class Extend {
    fun print() {
        println("这是一个需要被扩展的类")
    }
}

//拓展方法
fun Extend.Println(out: String) {
    println(out)
}

//拓展属性只能是val
val Extend.age: Int
    get() = 22

//Kotlin 数据类与密封类
//数据类
//Kotlin 可以创建一个只包含数据的类，关键字为 data
// 为了保证生成代码的一致性以及有意义，数据类需要满足以下条件：
//
//    主构造函数至少包含一个参数。
//
//    所有的主构造函数的参数必须标识为val 或者 var ;
//
//    数据类不可以声明为 abstract, open, sealed 或者 inner;
//
//    数据类不能继承其他类 (但是可以实现接口)。
data class Doctor(var name: String, var age: Int) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        return super.toString()
    }

    //数据类以及解构声明
    //组件函数允许数据类在解构声明中使用
}

//密封类
//密封类用来表示受限的类继承结构：当一个值为有限几种的类型, 而不能有任何其他类型时。在某种意义上，他们是枚举类的扩展：枚举类型的值集合 也是受限的，但每个枚举常量只存在一个实例，而密封类 的一个子类可以有可包含状态的多个实例。
//声明一个密封类，使用 sealed 修饰类，密封类可以有子类，但是所有的子类都必须要内嵌在密封类中
sealed class People
data class European(var name: String, var age: Int, var weight: Double, var knighthood: String) :
    People() {
}

data class Indian(var name: String, var age: Int, var weight: Double) : People() {
}

data class OtherPerson(var name: String, var age: Int, var weight: Double) : People() {
}


fun main(args: Array<String>) {
//    var secondFloor = SecondFloor()
//    //嵌套类调用格式
//    var windowNum = SecondFloor.Window().windowNum
//    //内部类调用格式  这个就是和java没什么区别
//    var bedName = secondFloor.Bed().bedName
//    var bed = SecondFloor().Bed().bedName

//    var extend = Extend()
//    extend.Println("扩展后的打印")
//    extend.PrintInKt3()//所以就是在我用的地方需要这个类做其他的事情，就可以原地扩展一下，这样写起来很方便，就是到时候维护的时候比较麻烦。
//    println("age = ${extend.age}")//可以拓展属性
//    SecondFloor().ExtendCaller(extend,"扩展声明为成员")

    var doctor = Doctor("张三", 22);
    var doctor2 = doctor.copy(name = "李四")//copy函数 可以赋值数据，同时支持修改数据，不影响元数据
    println("doctor name = ${doctor.name} | doctor2 name = ${doctor2.name}")

    //组件函数允许数据类在解构声明中使用  这个方便啊，直接使用
    var (name, age) = doctor
    var (name2, age2) = doctor2

    println("name = $name,age = $age || name2 = $name2,age2 = $age2")

    var person1 = European("jack", 22, 45.3, "侯爵")
    var person2 = Indian("阿三", 21, 55.0)
    var person3 = OtherPerson("张三", 32, 44.5)

    var persons = listOf<People>(person1, person2, person3)

    //使用密封类的关键好处在于使用 when 表达式 的时候，如果能够 验证语句覆盖了所有情况，就不需要为该语句再添加一个 else 子句了。
    for (person: People in persons) {
        when (person) {
            is European -> println("${person.name} 的爵位是${person.knighthood}")
            is Indian -> println("${person.name} 是印度人")
            is OtherPerson -> println("${person.name} 的年纪是${person.age}")
        }
    }
}