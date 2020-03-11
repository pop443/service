package com.newland.boss.entity.performance.affinity;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

/**
 * Created by xz on 2020/3/10.
 */
public class AffinityItemKey {
    private String itemId;
    @AffinityKeyMapped
    private String mainId;

    public AffinityItemKey() {
    }

    public AffinityItemKey(AffinityMain main, AffinityItem item) {
        this.mainId = main.getId();
        this.itemId = item.getId();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }
}
