package com.yc.violet.designpattern.adapter;

/**
 * @author 姚琛
 * @description 适配器模式
 * @date 2021/12/9
 */

import java.util.Arrays;
import java.util.List;

/**
 * TypeC->Lighting的转接头
 * 适配器类
 */
public class Exchange extends Charger implements LightingService {

    //提供的插孔
    private List<String> jack = Arrays.asList(TypeC.TYPE);
    //转换后的插头
    private List<String> plug = Arrays.asList(Lighting.TYPE);

    public boolean support(String type) {
        return jack.contains(type);
    }

    @Override
    public Lighting lighting() {
        TypeC typeC = this.plug();
        System.out.println("转接头：TypeC->Lighting格式转换中");
        return new Lighting();
    }

    public static void main(String[] args) {
        Exchange exchange = new Exchange();
        Charger charger = new Charger();
        if (exchange.support(charger.getPlugType())) {
            Lighting lighting = exchange.lighting();
            if (null != lighting) {
                System.out.println("转换完成，已获取Lighting格式插头");
            }
        }
    }
}