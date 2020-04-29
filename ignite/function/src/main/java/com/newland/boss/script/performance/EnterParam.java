package com.newland.boss.script.performance;

import com.newland.boss.entity.performance.Constant;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

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
    private int index = 0 ;

    public EnterParam(int count, int threadNum, int batchSize, int loop,int index) {
        this.count = count;
        this.threadNum = threadNum;
        this.batchSize = batchSize;
        this.loop = loop;
        this.commitSize = count/batchSize ;
        this.index = index ;
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
        this.commitSize = count/batchSize;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "EnterParam{" +
                "总数量=" + count +
                ", 线程数=" + threadNum +
                ", 批次数量=" + batchSize +
                ", 循环数量=" + loop +
                ", 批量提交=" + commitSize +
                ", index=" + index +
                '}';
    }

    @NotNull
    public static EnterParam getEnterParam(String[] args) throws Exception {
        int count = Constant.count;
        int threadNum = 2 ;
        int batchSize = 4 ;
        int loop = 2 ;
        int index = 1 ;
        EnterParam enterParam = null ;

        if (args.length==5){
            count = Integer.parseInt(args[0]) ;
            threadNum = Integer.parseInt(args[1]) ;
            batchSize  = Integer.parseInt(args[2]) ;
            loop  = Integer.parseInt(args[3]) ;
            index = Integer.parseInt(args[4]) ;
            enterParam = new EnterParam(count,threadNum,batchSize,loop,index);
        }else if(args.length==0){
            enterParam = new EnterParam(count,threadNum,batchSize,loop,index);
        }else {
            throw new Exception("参数不对") ;
        }
        return enterParam;
    }

}
