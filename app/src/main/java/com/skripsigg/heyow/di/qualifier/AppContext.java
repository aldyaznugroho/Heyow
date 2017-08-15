package com.skripsigg.heyow.di.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Aldyaz on 5/8/2017.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface AppContext {
}
