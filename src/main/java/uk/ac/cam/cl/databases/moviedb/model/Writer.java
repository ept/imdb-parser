package uk.ac.cam.cl.databases.moviedb.model;

public class Writer extends Person {
    private Integer lineOrder, groupOrder, subgroupOrder;

    public Integer getLineOrder() {
        return lineOrder;
    }

    public void setLineOrder(Integer lineOrder) {
        this.lineOrder = lineOrder;
    }

    public Integer getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(Integer groupOrder) {
        this.groupOrder = groupOrder;
    }

    public Integer getSubgroupOrder() {
        return subgroupOrder;
    }

    public void setSubgroupOrder(Integer subgroupOrder) {
        this.subgroupOrder = subgroupOrder;
    }
}
