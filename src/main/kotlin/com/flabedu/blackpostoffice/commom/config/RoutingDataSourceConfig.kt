package com.flabedu.blackpostoffice.commom.config

import com.flabedu.blackpostoffice.commom.utils.constants.MASTER
import com.flabedu.blackpostoffice.commom.utils.constants.SLAVE
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import org.springframework.transaction.support.TransactionSynchronizationManager

class RoutingDataSourceConfig : AbstractRoutingDataSource() {

    override fun determineCurrentLookupKey() =
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) SLAVE else MASTER

}
