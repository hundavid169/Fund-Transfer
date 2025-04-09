package com.fund.fund_transfer.model;

import com.fund.fund_transfer.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "gifts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Gift extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gift_code", nullable = false, length = 50)
    private String giftCode;

    @ManyToOne
    @JoinColumn(
            name = "owner_account_number",
            referencedColumnName = "account_number",
            foreignKey = @ForeignKey(name = "fk_owner_account")
    )
    private Account accountOwner;

    @ManyToOne
    @JoinColumn(
            name = "redeem_account_number",
            referencedColumnName = "account_number",
            foreignKey = @ForeignKey(name = "fk_redeem_account")
    )
    private Account accountRedeem;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "remark")
    private String remark;

    @Column(name = "status", nullable = false, length = 1)
    private char status;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @Column(name = "redeem_date")
    private Date redeemDate;
}
