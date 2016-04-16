package com.guard.dagger2demo.demo6;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.StringKey;

/**
 * Created by apple on 16/4/16.
 */
@Module
public class ParentModule {
    @Provides(type = Provides.Type.MAP)
    @StringKey("one") static int one() {
        return 1;
    }

    @Provides(type = Provides.Type.MAP)
    @StringKey("two") static int two() {
        return 2;
    }

    @Provides(type = Provides.Type.SET)
    static String a() {
        return "a";
    }

    @Provides(type = Provides.Type.SET)
    static String b() {
        return "b";
    }
}
