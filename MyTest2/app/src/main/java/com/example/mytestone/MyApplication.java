package com.example.mytestone;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * 反射实现真实Application
 */
public class MyApplication extends TinkerApplication {

    public MyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.mytestone.MyApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
