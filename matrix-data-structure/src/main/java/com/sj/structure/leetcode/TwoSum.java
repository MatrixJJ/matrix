package com.sj.structure.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和 day1
 * 给定一个数组和一个目标和，从数组中找两个数字相加等于目标和，输出这两个数字的下标。
 */
public class TwoSum {

    public  int[] towSum(int[] nums,int target){
        Map<Integer,Integer> retMap = new HashMap<>() ;
        for (int i = 0; i < nums.length; i++) {
            retMap.put(nums[i],i) ;
        }
        for (int i = 0; i < nums.length; i++) {
            int sub = target-nums[i] ;
            if(retMap.containsKey(sub)&&retMap.get(sub)!=i){
                return new int[]{i,retMap.get(sub)} ;
            }
        }
        throw new IllegalArgumentException("没有对应两数字之和") ;
    }


//    public int[] twoSum(int[] nums, int target) {
//        Map<Integer,Integer> map=new HashMap<>();
//        for(int i=0;i<nums.length;i++){
//            int sub=target-nums[i];
//            if(map.containsKey(sub)){
//                return new int[]{i,map.get(sub)};
//            }
//            map.put(nums[i], i);
//        }
//        throw new IllegalArgumentException("No two sum solution");
//    }
}
