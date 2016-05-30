package com.enjoyor.healthhouse.utils;

import android.app.Activity;

import com.enjoyor.healthhouse.ui.BaseActivity;

import java.util.Iterator;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class AppManagerUtil
{

    private static Stack<BaseActivity> activityStack;
    private static AppManagerUtil instance;

    private AppManagerUtil()
    {

    }

    /**
     * 单一实例
     */
    public static AppManagerUtil getAppManager ()
    {
        if (instance == null)
        {
            instance = new AppManagerUtil ();
        }
        if (activityStack == null)
        {
            activityStack = new Stack<BaseActivity>();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public static void addActivity (BaseActivity activity)
    {
        activityStack.add (activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public BaseActivity currentActivity ()
    {
        if (activityStack.size () == 0)
        {
            return null;
        }
        BaseActivity activity = activityStack.lastElement ();
        return activity;
    }


    public BaseActivity getActivityAtPosition ()
    {
        if (activityStack.size () == 0)
        {
            return null;
        }
        BaseActivity activity = activityStack.get(activityStack.size() - 1);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity ()
    {
        if (activityStack.size () == 0)
        {
            return;
        }
        BaseActivity activity = activityStack.lastElement();
        finishActivity (activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity (BaseActivity activity)
    {
        if (activity != null)
        {
            activityStack.remove (activity);
            activity.finish ();
            activity = null;
        }
    }

    /**
     * 延迟结束指定的Activity
     */
    public void delayFinishActivity (final BaseActivity activity, int milisec)
    {
        if (activity != null)
        {
            Timer timer = new Timer();
            TimerTask task = new TimerTask()
            {
                @Override
                public void run ()
                {
                    activityStack.remove (activity);
                    activity.finish ();

                }
            };
            timer.schedule (task, milisec);
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity (BaseActivity activity)
    {
        if (activity != null)
        {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity (Class<?> cls)
    {

        Iterator<BaseActivity> it = activityStack.iterator ();
        while (it.hasNext ())
        {
            BaseActivity activity = it.next ();
            if (activity.getClass ().equals (cls))
            {
                finishActivity (activity);
                return;
            }
        }

    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity ()
    {
        Iterator<BaseActivity> it = activityStack.iterator ();
        while (it.hasNext ())
        {
            BaseActivity activity = it.next ();
            activity.finish ();
            activity=null;
        }
        activityStack.clear ();

    }



    /**
     *  排除指定的activity以外，结束所有Activity
     */
    public void finishAllActivityExcept (Class<?> cls)
    {
        Iterator<BaseActivity> it = activityStack.iterator ();
        while (it.hasNext ())
        {
            BaseActivity activity = it.next ();
            if (!activity.getClass ().equals (cls)) {
                activity.finish();
            }
        }
    }

    public BaseActivity getBaseActivityByName (String aname)
    {
        BaseActivity base = null;
        for (Activity act : activityStack)
        {
            String name = act.getClass ().getName ();
            int index = name.indexOf (aname);
            if (index >= 0)
            {
                if (act instanceof BaseActivity)
                {
                    base = (BaseActivity) act;

                }
                break;
            }
        }
        return base;
    }

    /**
     * 获取当前是否有打开的Activity
     */
    public boolean hasActivity ()
    {
        if (activityStack.size () == 0)
        {
            return false;
        }
        return true;
    }

    /**
     * 退出应用程序
     *
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit (Boolean isBackground)
    {
        try
        {
            finishAllActivity ();
//            if (ImageLoader.getInstance ().isInited ())
//            {
//                ImageLoader.getInstance ().stop ();
//            }
//            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e)
        {

        } finally
        {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground)
            {
                System.exit(0);
            }
        }
    }
//    public static void finish(BaseActivity activity)
//    {
//        if (activity != null)
//        {
//            activity.finish();
//            activityStack.remove(activity);
//            activity = null;
//        }
//    }
//    public static void finishAll()
//    {
//        while (!activityStack.isEmpty())
//        {
//            finish( );
//        }
//        activityStack.clear();
//    }
//    public static BaseActivity getTopActivity()
//    {
//        if (!activityStack.isEmpty())
//        {
//            return activityStack.pop();
//        }
//        return null;
//    }
//    public static void pop(BaseActivity activity)
//    {
//        if (activity != null)
//        {
//            activityStack.remove(activity);
//        }
//    }
}
