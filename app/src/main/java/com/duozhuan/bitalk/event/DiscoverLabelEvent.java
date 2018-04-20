package com.duozhuan.bitalk.event;

public class DiscoverLabelEvent {
    private String label;
    private boolean isOpenNew;

    public DiscoverLabelEvent() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isOpenNew() {
        return isOpenNew;
    }

    public void setOpenNew(boolean openNew) {
        isOpenNew = openNew;
    }
}
