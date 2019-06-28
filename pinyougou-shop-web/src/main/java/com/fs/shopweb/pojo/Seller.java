package com.fs.shopweb.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_seller")
@Data
public class Seller {
    @Id
    @Column(name = "seller_id")
    private String sellerId;
    private String name;
    @Column(name = "nick_name")
    private String nickName;
    private String password;
    private String email;
    private String mobile;
    private String telephone;
    private String status;
    @Column(name = "address_detail")
    private String addressDetail;
    @Column(name = "linkman_name")
    private String linkmanName;
    @Column(name = "linkman_qq")
    private String linkmanQq;
    @Column(name = "linkman_mobile")
    private String linkmanMobile;
    @Column(name = "linkman_email")
    private String linkmanEmail;
    @Column(name = "license_number")
    private String licenseNumber;
    @Column(name = "tax_number")
    private String taxNumber;
    @Column(name = "org_number")
    private String orgNumber;
    private String address;
    @Column(name = "logo_pic")
    private String logoPic;
    private String brief;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "legal_person")
    private String legalPerson;
    @Column(name = "legal_person_card_id")
    private String legalPersonCardId;
    @Column(name = "bank_user")
    private String bankUser;
    @Column(name = "bank_name")
    private String bankName;
}
