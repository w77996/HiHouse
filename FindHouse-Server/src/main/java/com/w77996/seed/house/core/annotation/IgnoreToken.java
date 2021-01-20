package com.w77996.seed.house.core.annotation;

/**
 * @author w77996
 * @description
 * @date 2020/5/18 21:46
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 忽略token
 * @author: straw
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE})
public @interface IgnoreToken {
}