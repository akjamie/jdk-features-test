### 3个重要概念in java并发编程
#### 1. 原子性
是指一个操作是不可中断的。即使是在多个线程一起执行的时候，一个操作一旦开始，就不会被其它线程干扰。
Java中的原子操作包括：  
1）除long和double之外的基本类型的赋值操作  
2）所有引用reference的赋值操作  
3）java.concurrent.Atomic.* 包中所有类的一切操作。  
但是java对long和double的赋值操作是非原子操作！  

#### 2.可见性
可见性是指当多个线程访问同一个变量时，一个线程修改了这个变量的值，其他线程能够立即看得到修改的值。  

Thread-1            &nbsp;|&nbsp;          Thread-2  
                    &nbsp;&nbsp;  
int i = 0;          &nbsp;|&nbsp;          int j = i;  
                    &nbsp;&nbsp;  
i = 10;             &nbsp;|&nbsp;  
                    &nbsp;&nbsp;  
cache(i = 10)       &nbsp;|&nbsp;          cache(j = 0, _maybe_)          <-- cpu 缓存，除本身外对其他线程不可见  
                    &nbsp;&nbsp;  
i = 10              &nbsp;|&nbsp;          j = 10                         <--  主存   

*只有写入内存后才对其他线程可见*
> volatile 关键字：
- 保证了不同线程对共享数据的可见性，高速缓存的一致性
- 禁止对其进行重排序，保证了有序性，内存屏障
- 并未保证原子性

#### 3.有序性（顺序性）
有序性即程序执行的顺序按照代码的先后顺序执行。  
在Java内存模型中，允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性  

重排序只要求最终一致性。
```$xslt
{
    int i = 0;               (1)
    i = 1;                   (2)
    boolean flag = false;    (3)
    flag = true;             (4)
}
```
代码的执行顺序并不一定是（1）（2）（3）（4）。


好文推荐：  
https://www.cnblogs.com/java1024/p/8589537.html

