package com.sj.structure.order;

public class BubbleSort {

    public static int[] bubbleSort(int[] arr) {
        int temp = 0;
        boolean swap = false;
        for (int i = arr.length - 1; i > 0; i--) { // 每次需要排序的长度
            // 增加一个swap的标志，当前一轮没有进行交换时，说明数组已经有序 swap = false;
            for (int j = 0; j < i; j++) { // 从第一个元素到第i个元素
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }
        return arr;
    }


    public static void main(String[] args) {
        int[] arr = {1, 5, 2, 9, 22, 11, 3};
        bubbleSort(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }
}
