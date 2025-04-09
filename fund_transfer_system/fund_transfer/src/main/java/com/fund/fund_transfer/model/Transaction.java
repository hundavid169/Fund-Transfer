package com.fund.fund_transfer.model;

import com.fund.fund_transfer.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "transaction_id", nullable = false, length = 50)
    private String transactionId;

    @ManyToOne
    @JoinColumn(
            name = "sender_account_number",
            referencedColumnName = "account_number",
            foreignKey = @ForeignKey(name = "fk_sender_account"),
            nullable = false
    )
    private Account accountSender;

    @ManyToOne
    @JoinColumn(
            name = "receiver_account_number",
            referencedColumnName = "account_number",
            foreignKey = @ForeignKey(name = "fk_receiver_account")
    )
    private Account accountReceiver;

    @Column(name = "amount_deducted", nullable = false)
    private BigDecimal amountDeducted;

    @Column(name = "amount_received")
    private BigDecimal amountReceived;

    @Column(name = "remark")
    private String remark;

    @Column(name = "transaction_date", nullable = false)
    private Date transactionDate;

    public Transaction(String transactionId, Account accountSender, Account accountReceiver, BigDecimal amountDeducted, BigDecimal amountReceived, String remark, Date transactionDate) {
        this.transactionId = transactionId;
        this.accountSender = accountSender;
        this.accountReceiver = accountReceiver;
        this.amountDeducted = amountDeducted;
        this.amountReceived = amountReceived;
        this.remark = remark;
        this.transactionDate = transactionDate;
    }
}
