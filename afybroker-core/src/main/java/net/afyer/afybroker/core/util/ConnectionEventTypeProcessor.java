package net.afyer.afybroker.core.util;


import com.alipay.remoting.Connection;
import com.alipay.remoting.ConnectionEventProcessor;
import com.alipay.remoting.ConnectionEventType;

/**
 * @author Nipuru
 * @since 2025/09/02 19:00
 */
public interface ConnectionEventTypeProcessor extends ConnectionEventProcessor {
    ConnectionEventType getType();

    /**
     * Returns the processor that owns any broker-aware state.  Wrapping a
     * ConnectionEventProcessor must not hide BrokerClientAware or
     * BrokerServerAware from the builders' dependency injection step.
     */
    default Object getDelegate() {
        return this;
    }

    static ConnectionEventTypeProcessor wrap(ConnectionEventType type, ConnectionEventProcessor processor) {
        return new ConnectionEventTypeProcessor() {
            @Override
            public void onEvent(String remoteAddress, Connection connection) {
                processor.onEvent(remoteAddress, connection);
            }

            @Override
            public ConnectionEventType getType() {
                return type;
            }

            @Override
            public Object getDelegate() {
                return processor;
            }
        };
    }
}
