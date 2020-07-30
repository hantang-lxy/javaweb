package cn.kgc.tangcco.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/17  20:38
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Option {
    /**
     * html标签option的value
     */
    private Integer value;
    /**
     * html标签option的text文本
     */
    private String text;

}
