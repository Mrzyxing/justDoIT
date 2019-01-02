package ext

/**
 * Created by zyxing on 2018/11/8.
 */

def binding = new Binding()
def engine = new GroovyScriptEngine(this.class.getResource("/").getPath())

while (true) {
    def greeter = engine.run('sayHello.groovy', binding)
    println greeter.sayHello()
    Thread.sleep(1000)
}