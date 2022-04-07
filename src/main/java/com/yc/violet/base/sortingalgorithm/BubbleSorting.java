package com.yc.violet.base.sortingalgorithm;

import java.util.Arrays;

/**
 * @author 姚琛
 * @description 冒泡排序：通过每一次遍历获取最大/最小值，将最大值/最小值放在尾部/头部
 * @date 2021/12/27
 */
public class BubbleSorting {
    public static void main(String[] args) {
        int arr[] = {8, 5, 3, 2, 4};
        System.out.println(Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            //外部循环控制循环次数
            for (int j = 0; j < arr.length - i - 1; j++) {
                //内部循环作做对比交换
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            System.out.println("第" + i + "次排序：" + Arrays.toString(arr));
        }
        System.out.println("最终结果：" + Arrays.toString(arr));
    }
}
