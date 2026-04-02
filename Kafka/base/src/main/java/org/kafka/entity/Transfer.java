package org.kafka.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transfer")
public class Transfer {
    @Id
    private UUID id;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "status")
    private TranserStatus status;
    @Column(name = "id_from")
    private UUID idFrom;
    @Column(name = "id_to")
    private UUID idTo;
    public enum TranserStatus{
        DONE, ERROR
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdFrom() {
        return idFrom;
    }

    public void setIdFrom(UUID idFrom) {
        this.idFrom = idFrom;
    }

    public UUID getIdTo() {
        return idTo;
    }

    public void setIdTo(UUID idTo) {
        this.idTo = idTo;
    }

    public TranserStatus getStatus() {
        return status;
    }

    public void setStatus(TranserStatus status) {
        this.status = status;
    }
}
