package com.learn

class Kt1 public constructor(ins : String){
    var input : String = ins

    fun print(){
        println(input)
    }
}

//第一步 hello
fun main(args: Array<String>) {
    println("hello kotlin ")

    PrintHelloWithName("MJ").print();//创建对象不用 new关键字

    PrintHelloWithName(1, 4).print()
}

//构造函数
class ConstructorTest() {
    constructor(name: String) : this() {

    }

    constructor(name: String, age: Int) : this() {

    }
}


class PrintHelloWithName(var name: String) {

    constructor(a: Int, b: Int) : this("") {
        name = "a + b = ${plusNumber(a, b)}"
    }

    constructor(name: String, sex: String) : this(name) {

    }

    //带入参的打印
    fun print() {
        println("hello $name")
    }

    //带结构体的函数
    fun plusNumber(a: Int, b: Int): Int {
        return a + b
    }

    //表达式作为函数体，返回类型自动推断：
    fun plusNumber2(a: Int, b: Int) = a + b

    // public 方法则必须明确写出返回类型
    public fun plusNumber3(a: Int, b: Int): Int = a + b

}

