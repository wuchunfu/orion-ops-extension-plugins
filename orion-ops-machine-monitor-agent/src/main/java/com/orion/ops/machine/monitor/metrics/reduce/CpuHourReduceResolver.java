package com.orion.ops.machine.monitor.metrics.reduce;

import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import org.springframework.stereotype.Component;

/**
 * cpu 时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/2 16:09
 */
@Component
public class CpuHourReduceResolver extends BaseMetricsHourReduceResolver<CpuUsingBO> {

    public CpuHourReduceResolver() {
        super((prevHour) -> PathBuilders.getCpuMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected CpuUsingBO computeHourReduceData(String currentHour, String prevHour) {
        // 设置规约数据
        CpuUsingBO reduce = new CpuUsingBO();
        reduce.setU(this.getAvgReduceData(CpuUsingBO::getU, 3));
        return reduce;
    }

}
