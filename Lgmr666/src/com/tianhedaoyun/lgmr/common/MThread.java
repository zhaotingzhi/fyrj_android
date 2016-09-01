package com.tianhedaoyun.lgmr.common;

public abstract class MThread extends Thread
{
    public abstract void doRun();
    
    @Override
    public void run() {
        this.doRun();
    }
}
