package kaola.monkey

def commond(String commond, String msg)  {
    println("接受到的命令是：" + commond + ", msg: " + msg)
    commond.execute()
    println("命令执行成功")
}