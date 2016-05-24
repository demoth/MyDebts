package com.dao.mydebts.server

import grails.converters.JSON
import org.grails.web.json.JSONObject

class DebtController {
    static allowedMethods = [
            debts     : 'POST',
            approve   : 'POST',
            createDebt: 'POST',
    ]

    def debts() {
        def id = request.JSON.me.id
        render Debt.findBySrc(id) as JSON ?: '{}'
    }

    def approve() {
        Debt debt = Debt.findById(request.JSON.debtIdToApprove)
        if (!debt)
            return
        if (debt.approvedBySrc)
            debt.approvedByDest = true
        else
            debt.approvedBySrc = true
        debt.save()
        render '{"result":"aproved"}'
    }

    def createDebt() {
        JSONObject js = request.JSON
        def d = new Debt(src: js.src.id, dest: js.dest.id, amount: js.amount, comment: js.comment,
                approvedBySrc: js.approvedBySrc, approvedByDest: js.approvedByDest)
        if (d.approvedBySrc && !d.approvedByDest || !d.approvedBySrc && d.approvedByDest) {
            d.save(failOnError: true)
            render '{"result" : "created"}'
        } else
            render '{"result" : "not created"}'
    }

    def handleException(Exception e) {
        render """ {"exception" : "${e.class.name}($e.message)"} """
    }
}
