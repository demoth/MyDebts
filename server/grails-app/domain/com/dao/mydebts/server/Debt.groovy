package com.dao.mydebts.server

import groovy.transform.ToString

@ToString
class Debt {
    Long id
    String src
    String dest
    String comment
    BigDecimal amount
    Boolean approvedBySrc
    Boolean approvedByDest

    static constraints = {
        src blank:false
        dest blank:false
        amount nullable:false
    }

    static mapping = {
        id generator: 'hilo',
                params: [table: 'hi_value', column: 'next_value', max_lo: 100]
    }
}
