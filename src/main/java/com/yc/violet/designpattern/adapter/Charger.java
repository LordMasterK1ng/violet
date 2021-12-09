package com.yc.violet.designpattern.adapter;

import lombok.Data;

/**
 * @author 姚琛
 * @description 充电器（TypeC）
 * @date 2021/12/9
 */
@Data
public class Charger {

    private String plugType = TypeC.TYPE;

    public TypeC plug() {
        System.out.println("充电器：仅提供TypeC格式的插头");
        return new TypeC();
    }
}
