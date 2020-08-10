package com.dfz.annotation.stereotype;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ParentAnno
public @interface SunAnno {
}
