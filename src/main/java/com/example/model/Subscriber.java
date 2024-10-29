package org.subhanstudio.savesubscribers.model;

public class Subscriber {
    private String PartitionKey;
    private String RowKey;
    private String Email;

    public Subscriber(String partitionKey, String rowKey, String email) {
        this.PartitionKey=partitionKey;
        this.RowKey=rowKey;
        this.Email=email;
    }

    public void setPartitionKey(String partitionKey) {
        PartitionKey = partitionKey;
    }

    public void setRowKey(String rowKey) {
        RowKey = rowKey;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPartitionKey() {
        return PartitionKey;
    }

    public String getRowKey() {
        return RowKey;
    }

    public String getEmail() {
        return Email;
    }


}
