package cn.kgc.tangcco.util.tools.generator;

import cn.kgc.tangcco.util.tools.ChineseCharUtils;
import org.apache.commons.lang3.RandomUtils;

import cn.kgc.tangcco.util.tools.generator.base.GenericGenerator;

public class ChineseAddressGenerator extends GenericGenerator {
    private static GenericGenerator instance = new ChineseAddressGenerator();

    private ChineseAddressGenerator() {
    }

    public static GenericGenerator getInstance() {
        return instance;
    }

    @Override
    public String generate() {
        StringBuilder result = new StringBuilder(genProvinceAndCity());
        result.append(cn.kgc.tangcco.util.tools.ChineseCharUtils.genRandomLengthChineseChars(2, 3) + "路");
        result.append(RandomUtils.nextInt(1, 8000) + "号");
        result
            .append(ChineseCharUtils.genRandomLengthChineseChars(2, 3) + "小区");
        result.append(RandomUtils.nextInt(1, 20) + "单元");
        result.append(RandomUtils.nextInt(101, 2500) + "室");
        return result.toString();
    }

    private static String genProvinceAndCity() {
        return cn.kgc.tangcco.util.tools.generator.ChineseAreaList.provinceCityList.get(
            RandomUtils.nextInt(0, ChineseAreaList.provinceCityList.size()));
    }

}
