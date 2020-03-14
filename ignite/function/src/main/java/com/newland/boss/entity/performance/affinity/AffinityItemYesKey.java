package com.newland.boss.entity.performance.affinity;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

/**
 * Created by xz on 2020/3/10.
 */
public class AffinityItemYesKey {
    private String itemId;
    @AffinityKeyMapped
    private String mainId;

    public AffinityItemYesKey() {
    }

    public AffinityItemYesKey(String mainId, String yesId) {
        this.mainId = mainId;
        this.itemId = yesId;
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
