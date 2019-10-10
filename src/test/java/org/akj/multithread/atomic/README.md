## AtomicXxx
有效地保证了  
(1) 可见性  
(2) 有序性
(3) 原子性  

- volatile修饰的变量，可以保证可见性和有序性  
- CAS算法，是CPU级别的同步指令，相当于乐观锁，它可以检测到其他线程对共享变量的修改变化情况  

CAS 的问题： ABA 问题，即在CAS处理过程中，有其他线程将数据快速的修改为其他值然后在改回预期值，对于当前CPU线程并没有感知。

解决办法： 引入AtomicStampedReference，在AtomicReference的基础上增加stamp，起作用类似于乐观锁，如果stamp不想等则数据修改失败
```$xslt
public boolean compareAndSet(V   expectedReference,
                             V   newReference,
                             int expectedStamp,
                             int newStamp) {
    Pair<V> current = pair;
    return
        expectedReference == current.reference &&
        expectedStamp == current.stamp &&
        ((newReference == current.reference &&
          newStamp == current.stamp) ||
         casPair(current, Pair.of(newReference, newStamp)));
}
```