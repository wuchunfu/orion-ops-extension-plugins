package com.orion.ops.machine.monitor.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网络带宽指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 14:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NetBandwidthBO extends BaseRangeBO {

    /**
     * 流量上行速率 mpb sentMpbRate
     */
    private Double smr;

    /**
     * 流量下行速率 mpb receivedMpbRate
     */
    private Double rmr;

    /**
     * 上行包 sentPacket
     */
    private Long sp;

    /**
     * 下行包 receivedPacket
     */
    private Long rp;

}
