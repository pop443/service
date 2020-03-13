package com.newland.boss.script.performance;

import com.newland.boss.entity.performance.Constant;

/**
 * Created by xz on 2020/3/13.
 */
public class EnterParam {
    private int count = 0;
    //批量次数
    private int threadNum = 0 ;
    private int batchSize = 0 ;
    private int loop = 0 ;
    private int commitSize = 0 ;

    public EnterParam(int count, int threadNum, int batchSize, int loop) {
        this.count = count;
        this.threadNum = threadNum;
        this.batchSize = batchSize;
        this.loop = loop;
        this.commitSize = count/threadNum ;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public int getLoop() {
        return loop;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }

    public int getCommitSize() {
        return commitSize;
    }

    public void setCommitSize(int commitSize) {
        this.commitSize = commitSize;
    }

    @Override
    public String toString() {
        return "EnterParam{" +
                "总数量=" + count +
                ", 线程数=" + threadNum +
                ", 批次数量=" + batchSize +
                ", 循环数量=" + loop +
                ", 批量提交=" + commitSize +
                '}';
    }
}
