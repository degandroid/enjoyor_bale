package com.enjoyor.healthhouse.common;

import android.content.Context;

import com.enjoyor.healthhouse.utils.PreferencesUtils;
import com.enjoyor.healthhouse.utils.StringUtils;

/**
 * Created by Administrator on 2016/5/13.
 */
public class BaseDate {
    public static Long accountId;


    public static Long getSessionId(Context context) {
        if (StringUtils.isBlank("" + accountId))
            accountId = PreferencesUtils.getLong(context, Constant.SP_TOKEN);
        return accountId;
    }

    public static void setSessionId(Context context, Long _accountId) {
        accountId = _accountId;
        PreferencesUtils.putLong(context, Constant.SP_TOKEN, _accountId);
    }
}
