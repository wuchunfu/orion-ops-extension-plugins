package com.orion.ops.machine.monitor.controller;

import com.orion.lang.utils.convert.Converts;
import com.orion.ops.machine.monitor.annotation.RestWrapper;
import com.orion.ops.machine.monitor.entity.agent.dto.DiskIoUsageDTO;
import com.orion.ops.machine.monitor.entity.agent.dto.DiskStoreUsageDTO;
import com.orion.ops.machine.monitor.entity.agent.vo.*;
import com.orion.ops.machine.monitor.metrics.MetricsProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机器指标 api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 16:23
 */
@Api(tags = "机器指标")
@RestWrapper
@RestController
@RequestMapping("/orion/machine-monitor-agent/api/metrics")
public class MachineMetricsController {

    @Resource
    private MetricsProvider metricsProvider;

    @GetMapping("/info")
    @ApiOperation(value = "获取机器信息")
    public OsInfoVO getOsInfo() {
        return Converts.to(metricsProvider.getOsInfo(), OsInfoVO.class);
    }

    @GetMapping("/system-load")
    @ApiOperation(value = "获取系统负载")
    public SystemLoadVO getSystemLoad() {
        return Converts.to(metricsProvider.getSystemLoad(), SystemLoadVO.class);
    }

    @GetMapping("/cpu-usage")
    @ApiOperation(value = "获取cpu使用信息")
    public CpuUsageVO getCpuUsage() {
        return Converts.to(metricsProvider.getCpuUsage(), CpuUsageVO.class);
    }

    @GetMapping("/memory-usage")
    @ApiOperation(value = "获取内存使用信息")
    public MemoryUsageVO getMemoryUsage() {
        return Converts.to(metricsProvider.getMemoryUsage(), MemoryUsageVO.class);
    }

    @GetMapping("/net-bandwidth")
    @ApiOperation(value = "获取网络带宽使用信息")
    public NetBandwidthVO getNetBandwidth() {
        return Converts.to(metricsProvider.getNetBandwidth(), NetBandwidthVO.class);
    }

    @GetMapping("/disk-store-usage")
    @ApiOperation(value = "获取硬盘空间使用信息")
    public List<DiskStoreUsageVO> getDiskStoreUsage() {
        return Converts.toList(metricsProvider.getDiskStoreUsage(), DiskStoreUsageVO.class);
    }

    @GetMapping("/disk-store-usage-merge")
    @ApiOperation(value = "合并获取硬盘空间使用信息")
    public DiskStoreUsageVO getDiskStoreUsageMerge() {
        List<DiskStoreUsageDTO> store = metricsProvider.getDiskStoreUsage();
        if (store.size() == 1) {
            return Converts.to(store.get(0), DiskStoreUsageVO.class);
        }
        // 合并
        long totalSpace = store.stream().mapToLong(DiskStoreUsageDTO::getTotalSpace).sum();
        long freeSpace = store.stream().mapToLong(DiskStoreUsageDTO::getFreeSpace).sum();
        long usageSpace = totalSpace - freeSpace;
        DiskStoreUsageDTO merge = new DiskStoreUsageDTO();
        merge.setTotalSpace(totalSpace);
        merge.setUsageSpace(usageSpace);
        merge.setFreeSpace(freeSpace);
        merge.setUsage((double) usageSpace / (double) totalSpace);
        return Converts.to(merge, DiskStoreUsageVO.class);
    }

    @GetMapping("/disk-name")
    @ApiOperation(value = "获取硬盘名称")
    public List<DiskNameVO> getDiskName() {
        return metricsProvider.getDiskName();
    }

    @GetMapping("/disk-io-usage")
    @ApiOperation(value = "获取硬盘io使用信息")
    public List<DiskIoUsageVO> getDiskIoUsage() {
        return Converts.toList(metricsProvider.getDiskIoUsage(), DiskIoUsageVO.class);
    }

    @GetMapping("/disk-io-usage-merge")
    @ApiOperation(value = "合并获取硬盘io使用信息")
    public DiskIoUsageVO getDiskIoUsageMerge() {
        List<DiskIoUsageDTO> io = metricsProvider.getDiskIoUsage();
        if (io.size() == 1) {
            return Converts.to(io.get(0), DiskIoUsageVO.class);
        }
        // 合并
        long readCount = io.stream().mapToLong(DiskIoUsageDTO::getReadCount).sum();
        long readBytes = io.stream().mapToLong(DiskIoUsageDTO::getReadBytes).sum();
        long writeCount = io.stream().mapToLong(DiskIoUsageDTO::getWriteCount).sum();
        long writeBytes = io.stream().mapToLong(DiskIoUsageDTO::getWriteBytes).sum();
        long usageTime = io.stream().mapToLong(DiskIoUsageDTO::getUsageTime).sum();
        DiskIoUsageDTO merge = new DiskIoUsageDTO();
        merge.setReadCount(readCount);
        merge.setReadBytes(readBytes);
        merge.setWriteCount(writeCount);
        merge.setWriteBytes(writeBytes);
        merge.setUsageTime(usageTime);
        return Converts.to(merge, DiskIoUsageVO.class);
    }

    @GetMapping("/top-processes")
    @ApiOperation(value = "获取Top进程")
    public List<SystemProcessVO> getTopProgress(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam("limit") Integer limit) {
        return Converts.toList(metricsProvider.getProcesses(name, limit), SystemProcessVO.class);
    }

}
