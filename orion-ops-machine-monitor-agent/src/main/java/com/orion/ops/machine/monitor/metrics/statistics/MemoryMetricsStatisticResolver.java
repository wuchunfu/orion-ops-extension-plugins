package com.orion.ops.machine.monitor.metrics.statistics;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.constant.DataMetricsType;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsageBO;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.MemoryMetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.MetricsStatisticsVO;
import com.orion.ops.machine.monitor.utils.TimestampValue;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.time.Dates;

import java.util.List;
import java.util.stream.DoubleStream;

/**
 * 内存数据指标统计
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 14:24
 */
public class MemoryMetricsStatisticResolver extends BaseMetricsStatisticResolver<MemoryUsageBO, MemoryMetricsStatisticsVO> {

    /**
     * 使用量
     */
    private final MetricsStatisticsVO<Double> size;

    /**
     * 使用率
     */
    private final MetricsStatisticsVO<Double> usage;

    public MemoryMetricsStatisticResolver(MetricsStatisticsRequest request) {
        super(request, DataMetricsType.MEMORY, new MemoryMetricsStatisticsVO());
        this.size = new MetricsStatisticsVO<>();
        this.usage = new MetricsStatisticsVO<>();
        metrics.setSize(size);
        metrics.setUsage(usage);
    }

    @Override
    protected void computeMetricsData(List<MemoryUsageBO> rows, Long start, Long end) {
        double avgSize = rows.stream()
                .mapToDouble(MemoryUsageBO::getUs)
                .average()
                .orElse(Const.D_0);
        double avgUsage = rows.stream()
                .mapToDouble(MemoryUsageBO::getUr)
                .average()
                .orElse(Const.D_0);
        size.getMetrics().add(new TimestampValue<>(start, Utils.roundToDouble(avgSize, 3)));
        usage.getMetrics().add(new TimestampValue<>(start, Utils.roundToDouble(avgUsage, 3)));
    }

    @Override
    protected void computeMetricsMax() {
        double sizeMax = super.calcDataAgg(size.getMetrics(), DoubleStream::max);
        double usageMax = super.calcDataAgg(usage.getMetrics(), DoubleStream::max);
        size.setMax(sizeMax);
        usage.setMax(usageMax);
    }

    @Override
    protected void computeMetricsMin() {
        double sizeMin = super.calcDataAgg(size.getMetrics(), DoubleStream::min);
        double usageMin = super.calcDataAgg(usage.getMetrics(), DoubleStream::min);
        size.setMin(sizeMin);
        usage.setMin(usageMin);
    }

    @Override
    protected void computeMetricsAvg() {
        double sizeAvg = super.calcDataAgg(size.getMetrics(), DoubleStream::average);
        double usageAvg = super.calcDataAgg(usage.getMetrics(), DoubleStream::average);
        size.setAvg(sizeAvg);
        usage.setAvg(usageAvg);
    }

    public static void main(String[] args) {
        long s = Dates.parse("202207051225").getTime();
        long e = Dates.parse("202207051325").getTime();
        MetricsStatisticsRequest r = new MetricsStatisticsRequest();
        r.setStartRange(s);
        r.setEndRange(e);
        r.setGranularity(GranularityType.MINUTE_1.getType());
        MemoryMetricsStatisticResolver res = new MemoryMetricsStatisticResolver(r);
        res.statistics();
        System.out.println();
        System.out.println(JSON.toJSONString(res.getMetrics()));
        System.out.println();
    }

}
