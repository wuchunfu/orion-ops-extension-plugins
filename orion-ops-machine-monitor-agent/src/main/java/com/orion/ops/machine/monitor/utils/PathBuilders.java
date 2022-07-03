package com.orion.ops.machine.monitor.utils;

import com.orion.ops.machine.monitor.constant.Const;
import com.orion.utils.Systems;

/**
 * 路径构建器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 17:22
 */
public class PathBuilders {

    private PathBuilders() {
    }

    public static final String CPU_DAY_DATA_PATH = "/cpu_day";

    public static final String MEMORY_DAY_DATA_PATH = "/memory_day";

    public static final String NET_DAY_DATA_PATH = "/net_day";

    public static final String DISK_DAY_DATA_PATH = "/disk_day";

    public static final String CPU_MONTH_DATA_PATH = "/cpu_month";

    public static final String MEMORY_MONTH_DATA_PATH = "/memory_month";

    public static final String NET_MONTH_DATA_PATH = "/net_month";

    public static final String DISK_MONTH_DATA_PATH = "/disk_month";

    // -------------------- 天级数据 粒度为配置参数 --------------------

    /**
     * 获取处理器指标数据 天级数据路径
     *
     * @param time 开始时间
     * @return path
     */
    public static String getCpuDayDataPath(String time) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + CPU_DAY_DATA_PATH
                + "/" + time
                + "." + Const.SUFFIX_LOG;
    }

    /**
     * 获取内存指标数据 天级数据路径
     *
     * @param time 开始时间
     * @return path
     */
    public static String getMemoryDayDataPath(String time) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + MEMORY_DAY_DATA_PATH
                + "/" + time
                + "." + Const.SUFFIX_LOG;
    }

    /**
     * 获取网卡指标数据 天级数据路径
     *
     * @param time 开始时间
     * @return path
     */
    public static String getNetDayDataPath(String time) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + NET_DAY_DATA_PATH
                + "/" + time
                + "." + Const.SUFFIX_LOG;
    }

    /**
     * 获取磁盘指标数据 天级数据路径
     *
     * @param time 开始时间
     * @return path
     */
    public static String getDiskDayDataPath(String time, String tag) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + DISK_DAY_DATA_PATH
                + "/" + tag
                + "-" + time
                + "." + Const.SUFFIX_LOG;
    }

    // -------------------- 月级数据 粒度为时 --------------------

    /**
     * 获取处理器指标数据路径 月级数据
     *
     * @param time 开始时间
     * @return path
     */
    public static String getCpuMonthDataPath(String time) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + CPU_MONTH_DATA_PATH
                + "/" + time
                + "." + Const.SUFFIX_LOG;
    }

    /**
     * 获取内存指标数据路径 月级数据
     *
     * @param time 开始时间
     * @return path
     */
    public static String getMemoryMonthDataPath(String time) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + MEMORY_MONTH_DATA_PATH
                + "/" + time
                + "." + Const.SUFFIX_LOG;
    }

    /**
     * 获取网络带宽指标数据路径 月级数据
     *
     * @param time 开始时间
     * @return path
     */
    public static String getNetMonthDataPath(String time) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + NET_MONTH_DATA_PATH
                + "/" + time
                + "." + Const.SUFFIX_LOG;
    }


}
