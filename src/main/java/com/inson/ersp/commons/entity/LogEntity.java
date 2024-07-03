package com.inson.ersp.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "LOG_API_SERVER")
public class LogEntity {
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "REQ_CONTENT")
    private String reqContent;

    @Column(name = "RES_CODE")
    private int resCode;

    @Column(name = "RES_MESSAGE")
    private String resMessage;

    @Column(name = "RES_CONTENT")
    private String resContent;

    @Column(name = "TIME_SPENT")
    private Long timeSpent;

    @Column(name = "ANKETA_ID")
    private Long anketaId;

    @Column(name = "PRODUCT_TYPE")
    private int productType;

    @Column(name = "MICROSERVICE")
    private String microservice;

    @Column(name = "METHOD")
    private String method;
}
