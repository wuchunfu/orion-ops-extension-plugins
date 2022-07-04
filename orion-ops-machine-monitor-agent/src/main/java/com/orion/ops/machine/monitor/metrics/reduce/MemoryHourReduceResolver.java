package com.orion.ops.machine.monitor.metrics.reduce;

import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import org.springframework.stereotype.Component;

/**
 * 内存时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/3 22:45
 */
@Component
public class MemoryHourReduceResolver extends BaseMetricsHourReduceResolver<MemoryUsingBO> {

    public MemoryHourReduceResolver() {
        super((prevHour) -> PathBuilders.getMemoryMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected MemoryUsingBO computeHourReduceData(String currentHour, String prevHour) {
        // 设置规约数据
        MemoryUsingBO reduce = new MemoryUsingBO();
        reduce.setUr(this.getAvgReduceData(MemoryUsingBO::getUr, 3));
        reduce.setUs(this.getAvgReduceData(MemoryUsingBO::getUs));
        return reduce;
    }

}
