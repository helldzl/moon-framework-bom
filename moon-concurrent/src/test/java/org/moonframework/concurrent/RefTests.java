package org.moonframework.concurrent;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.List;

/**
 * @author quzile
 * @version 1.0
 * @since 2016/6/2
 */
public class RefTests {

    public void b(List list){

        Iterator it = list.iterator();
        int index = 0;
        while (it.hasNext())
        {
            Object obj = it.next();
            //if (needDelete(obj))  //needDelete返回boolean，决定是否要删除
           // {
                //todo delete
            //}
            index ++;
        }
    }

    public static class MyObject {
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("final called");
        }
    }

    public void a() {
        MyObject object = new MyObject();

        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();

        Reference<MyObject> softReference =  new SoftReference<>(object, referenceQueue);


        try {
            Reference<? extends MyObject> remove = referenceQueue.remove();
            MyObject object1 = remove.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
