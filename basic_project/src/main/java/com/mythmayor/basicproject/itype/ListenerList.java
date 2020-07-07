package com.mythmayor.basicproject.itype;

import java.util.Iterator;
import java.util.Vector;

/**
 * Created by mythmayor on 2020/6/30.
 * 监听List
 */
public class ListenerList {
    private Vector<BaseListener> mList = new Vector();

    public ListenerList() {
    }

    public int add(BaseListener l) {
        boolean size = false;
        Vector var3 = this.mList;
        synchronized (this.mList) {
            if (l != null && !this.mList.contains(l)) {
                this.mList.add(l);
            }

            int size1 = this.mList.size();
            return size1;
        }
    }

    public int remove(BaseListener l) {
        boolean size = false;
        Vector var3 = this.mList;
        synchronized (this.mList) {
            if (l != null) {
                this.mList.remove(l);
            }

            int size1 = this.mList.size();
            return size1;
        }
    }

    public int removeAll(BaseListener l) {
        boolean size = false;
        Vector var3 = this.mList;
        synchronized (this.mList) {
            if (l != null) {
                Vector tempList = new Vector();
                tempList.add(l);
                this.mList.removeAll(tempList);
                tempList.clear();
            }

            int size1 = this.mList.size();
            return size1;
        }
    }

    public int removeAllSameClass(BaseListener l) {
        boolean size = false;
        Vector var3 = this.mList;
        synchronized (this.mList) {
            if (l != null) {
                Iterator it = this.mList.iterator();

                while (it.hasNext()) {
                    if (it.next().getClass() == l.getClass()) {
                        it.remove();
                    }
                }
            }

            int size1 = this.mList.size();
            return size1;
        }
    }

    public void clear() {
        Vector var1 = this.mList;
        synchronized (this.mList) {
            this.mList.clear();
        }
    }

    public BaseListener[] getAll() {
        BaseListener[] contents = null;
        Vector var2 = this.mList;
        synchronized (this.mList) {
            contents = new BaseListener[this.mList.size()];
            this.mList.toArray(contents);
            return contents;
        }
    }

    public int size() {
        Vector var1 = this.mList;
        synchronized (this.mList) {
            return this.mList.size();
        }
    }
}
